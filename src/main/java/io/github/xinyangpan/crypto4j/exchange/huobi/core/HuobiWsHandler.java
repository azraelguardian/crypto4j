package io.github.xinyangpan.crypto4j.exchange.huobi.core;

import static io.github.xinyangpan.crypto4j.exchange.huobi.util.HuobiUtils.objectMapper;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream;

import io.github.xinyangpan.crypto4j.core.WebSocketHandler;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.common.HuobiWsAck;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.marketdepth.MarketDepthData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HuobiWsHandler extends WebSocketHandler {
	private Map<String, Consumer<Object>> listeners = new HashMap<>();
	//
	private Consumer<MarketDepthData> marketDepthListener;
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
		String jsonMessage = getTextMessage(message.getPayload());
		log.info("handling message: {}", jsonMessage);
		JsonNode rootNode = objectMapper().readTree(jsonMessage);
		// ping message
		JsonNode evalNode = rootNode.at("/ping");
		if (!evalNode.isMissingNode()) {
			onPingMessage(evalNode.asLong());
			return;
		}
		// ack message
		evalNode = rootNode.at("/subbed");
		if (!evalNode.isMissingNode()) {
			onAcknowledge(objectMapper().readValue(jsonMessage, HuobiWsAck.class));
			return;
		}
		// market depth message
		evalNode = rootNode.at("/tick/bids");
		if (!evalNode.isMissingNode()) {
			onMarketDepthData(objectMapper().readValue(jsonMessage, MarketDepthData.class));
			return;
		}
		
		log.warn("Unhandled message", jsonMessage);
	}

	private String getTextMessage(ByteBuffer payload) throws IOException {
		GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteBufferBackedInputStream(payload));
		String json = IOUtils.toString(gzipInputStream, Charset.forName("utf-8"));
		return json;
	}

	private void onPingMessage(long pingTs) throws Exception {
		session.sendMessage(new TextMessage(String.format("{'pong': %s}", pingTs)));
	}

	private void onAcknowledge(HuobiWsAck huobiWsAck) {
		log.info("onAcknowledge: {}", huobiWsAck);
	}

	private void onMarketDepthData(MarketDepthData marketDepthData) {
		log.info("onMarketDepthData: {}", marketDepthData.getCh());
		Consumer<Object> listener = listeners.get(marketDepthData.getCh());
		listener.accept(marketDepthData);
	}

}
