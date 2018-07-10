package io.github.xinyangpan.crypto4j.exchange.huobi.impl;

import static io.github.xinyangpan.crypto4j.exchange.ExchangeUtils.objectMapper;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.function.Consumer;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream;
import com.google.common.collect.Maps;

import io.github.xinyangpan.crypto4j.core.BaseWsHandler;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.common.HuobiWsAck;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.depth.DepthData;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.kline.KlineData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter(AccessLevel.PACKAGE)
public class HuobiWsHandler extends BaseWsHandler {
	// ch -> listener
	private final Map<String, Consumer<DepthData>> depthListenerMap = Maps.newHashMap();
	private final Map<String, Consumer<KlineData>> klineListenerMap = Maps.newHashMap();
	
	public HuobiWsHandler() {
		super("huobi");
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
		String jsonMessage = getTextMessage(message.getPayload());
		log.debug("handling message: {}", jsonMessage);
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
			onDepthData(objectMapper().readValue(jsonMessage, DepthData.class));
			return;
		}
		// kline message
		evalNode = rootNode.at("/tick/open");
		if (!evalNode.isMissingNode()) {
			onKlineData(objectMapper().readValue(jsonMessage, KlineData.class));
			return;
		}
		// 
		log.warn("Unhandled message: {}", jsonMessage);
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

	private void onDepthData(DepthData depthData) {
		Consumer<DepthData> listener = depthListenerMap.get(depthData.getCh());
		listener.accept(depthData);
	}

	private void onKlineData(KlineData klineData) {
		Consumer<KlineData> listener = klineListenerMap.get(klineData.getCh());
		listener.accept(klineData);
	}

}
