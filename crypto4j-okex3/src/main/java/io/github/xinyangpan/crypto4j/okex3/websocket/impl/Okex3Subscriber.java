package io.github.xinyangpan.crypto4j.okex3.websocket.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.function.Consumer;

import org.apache.commons.compress.compressors.deflate64.Deflate64CompressorInputStream;
import org.apache.commons.io.IOUtils;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream;
import com.google.common.base.Preconditions;

import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.core.websocket.Subscriber;
import io.github.xinyangpan.crypto4j.okex3.dto.common.Okex3WsRequest;
import io.github.xinyangpan.crypto4j.okex3.dto.common.Okex3WsResponse;
import io.github.xinyangpan.crypto4j.okex3.dto.market.DepthTicker;
import io.github.xinyangpan.crypto4j.okex3.dto.market.Okex3WsSubParam;
import io.github.xinyangpan.crypto4j.okex3.dto.market.Ticker;
import io.github.xinyangpan.crypto4j.okex3.dto.market.TradeTicker;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class Okex3Subscriber extends Subscriber{
	
	private final static TypeReference<Okex3WsResponse<DepthTicker>> DEPTH = new TypeReference<Okex3WsResponse<DepthTicker>>() {};
	private final static TypeReference<Okex3WsResponse<Ticker>> TICK = new TypeReference<Okex3WsResponse<Ticker>>() {};
	private final static TypeReference<Okex3WsResponse<TradeTicker[]>> TRADE = new TypeReference<Okex3WsResponse<TradeTicker[]>>() {};

	private Consumer<Okex3WsResponse<DepthTicker>> depthListener = Crypto4jUtils.logConsumer();
	private Consumer<Okex3WsResponse<TradeTicker[]>> tradeListener = Crypto4jUtils.logConsumer();
	private Consumer<Okex3WsResponse<Ticker>> tickerListener = Crypto4jUtils.logConsumer();
	
	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
		String jsonMessage = getTextMessage(message.getPayload());
		log.debug(MSG_TRACK, "{}: Handling message: {}", this.getName(), jsonMessage);
		// pong
		if ("pong".equals(jsonMessage)) {
			onPong(jsonMessage);
			return;
		}
		
		JsonNode root = objectMapper.readTree(jsonMessage);
		
		JsonNode eventNode = root.findValue("event");
		if (eventNode != null) {
			//subscribe ack msg
			return;
		}
		
		String channel = root.findValue("table").asText();
		if (channel.equals("spot/ticker")) {
			Okex3WsResponse<Ticker> response = objectMapper.readValue(jsonMessage, TICK);
			tickerListener.accept(response);
			return;
		} else if (channel.equals("spot/depth")) {
			Okex3WsResponse<DepthTicker> response = objectMapper.readValue(jsonMessage, DEPTH);
			depthListener.accept(response);
			return;
		} else if (channel.equals("spot/trade")) {
			Okex3WsResponse<TradeTicker[]> response = objectMapper.readValue(jsonMessage, TRADE);
			tradeListener.accept(response);
			return;
		}
		
		this.unhandledMessage(jsonMessage);
	}
	
	private String getTextMessage(ByteBuffer payload) throws IOException {
		Deflate64CompressorInputStream gzipInputStream = new Deflate64CompressorInputStream(new ByteBufferBackedInputStream(payload));
		String json = IOUtils.toString(gzipInputStream, Charset.forName("utf-8"));
		return json;
	}
	
	public void depth(String symbol, int depth) {
		// 
		Preconditions.checkNotNull(symbol);
		Preconditions.checkArgument(depth >= 0);
		
		log.info("Subscribing marketDepth. symbol={}, depth={}.", symbol, depth);
		
		Okex3WsSubParam subParam = new Okex3WsSubParam();
		subParam.setChannel("depth");
		subParam.setFilter(symbol);
		
		this.send(Okex3WsRequest.subscribe(subParam));
	}

	public void ticker(String symbol) {
		// 
		Preconditions.checkNotNull(symbol);
		// 
		log.info("Subscribing ticker. symbol={}.", symbol);
		
		Okex3WsSubParam subParam = new Okex3WsSubParam();
		subParam.setChannel("ticker");
		subParam.setFilter(symbol);
		
		this.send(Okex3WsRequest.subscribe(subParam));
	}
}
