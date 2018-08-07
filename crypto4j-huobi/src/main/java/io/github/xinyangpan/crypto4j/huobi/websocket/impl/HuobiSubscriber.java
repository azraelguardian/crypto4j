package io.github.xinyangpan.crypto4j.huobi.websocket.impl;

import static io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils.objectMapper;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.function.Consumer;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream;
import com.google.common.base.Preconditions;

import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.core.websocket.Subscriber;
import io.github.xinyangpan.crypto4j.huobi.dto.common.HuobiWsAck;
import io.github.xinyangpan.crypto4j.huobi.dto.common.HuobiWsRequest;
import io.github.xinyangpan.crypto4j.huobi.dto.common.HuobiWsResponse;
import io.github.xinyangpan.crypto4j.huobi.dto.market.depth.Depth;
import io.github.xinyangpan.crypto4j.huobi.dto.market.kline.Kline;
import io.github.xinyangpan.crypto4j.huobi.dto.market.tick.Ticker;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class HuobiSubscriber extends Subscriber {
	// 
	private final static TypeReference<HuobiWsResponse<Depth>> DEPTH = new TypeReference<HuobiWsResponse<Depth>>() {};
	private final static TypeReference<HuobiWsResponse<Kline>> KLINE = new TypeReference<HuobiWsResponse<Kline>>() {};
	private final static TypeReference<HuobiWsResponse<Ticker>> TICK = new TypeReference<HuobiWsResponse<Ticker>>() {};
	// 
	private Consumer<HuobiWsResponse<Depth>> depthListener = Crypto4jUtils.logConsumer();
	private Consumer<HuobiWsResponse<Kline>> klineListener = Crypto4jUtils.logConsumer();
	private Consumer<HuobiWsResponse<Ticker>> tickerListener = Crypto4jUtils.logConsumer();

	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
		String jsonMessage = getTextMessage(message.getPayload());
		log.debug("handling message: {}", jsonMessage);
		JsonNode rootNode = objectMapper().readTree(jsonMessage);
		// response message
		JsonNode evalNode = rootNode.at("/ch");
		if (!evalNode.isMissingNode()) {
			String ch = evalNode.asText();
			if (ch.contains("depth")) {
				// market depth message
				depthListener.accept(objectMapper().readValue(jsonMessage, DEPTH));
				return;
			} else if (ch.contains("kline")) {
				// kline message
				klineListener.accept(objectMapper().readValue(jsonMessage, KLINE));
				return;
			} else if (ch.contains("detail")) {
				// tick message
				tickerListener.accept(objectMapper().readValue(jsonMessage, TICK));
				return;
			}
		}
		// ping message
		evalNode = rootNode.at("/ping");
		if (!evalNode.isMissingNode()) {
			onPingMessage(session, evalNode.asLong());
			return;
		}
		// pong message
		evalNode = rootNode.at("/pong");
		if (!evalNode.isMissingNode()) {
			onPong(jsonMessage);
			return;
		}
		// ack message
		evalNode = rootNode.at("/subbed");
		if (!evalNode.isMissingNode()) {
			onAcknowledge(objectMapper().readValue(jsonMessage, HuobiWsAck.class));
			return;
		}
		// 
		this.unhandledMessage(jsonMessage);
	}

	private String getTextMessage(ByteBuffer payload) throws IOException {
		GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteBufferBackedInputStream(payload));
		String json = IOUtils.toString(gzipInputStream, Charset.forName("utf-8"));
		return json;
	}

	private void onPingMessage(WebSocketSession session, long pingTs) throws Exception {
		log.debug("responding ping message: {}, {}", pingTs, System.currentTimeMillis() - pingTs);
		session.sendMessage(new TextMessage(String.format("{'pong': %s}", pingTs)));
	}

	private void onAcknowledge(HuobiWsAck huobiWsAck) {
		log.info("onAcknowledge: {}", huobiWsAck);
	}

	public void depth(String symbol, String type) {
		// 
		Preconditions.checkNotNull(symbol);
		Preconditions.checkNotNull(type);
		// 
		log.info("Subscribing marketDepth. symbol={}, type={}.", symbol, type);
		String sub = String.format("market.%s.depth.%s", symbol, type);
		String id = sub;
		this.subscribe(new HuobiWsRequest(id, sub));
	}

	public void kline(String symbol, String period) {
		// 
		Preconditions.checkNotNull(symbol);
		Preconditions.checkNotNull(period);
		// 
		log.info("Subscribing kline. symbol={}, type={}.", symbol, period);
		String sub = String.format("market.%s.kline.%s", symbol, period);
		String id = sub;
		this.subscribe(new HuobiWsRequest(id, sub));
	}

	public void ticker(String symbol) {
		// 
		Preconditions.checkNotNull(symbol);
		// 
		log.info("Subscribing ticker. symbol={}.", symbol);
		String sub = String.format("market.%s.detail", symbol);
		String id = sub;
		this.subscribe(new HuobiWsRequest(id, sub));
	}

}
