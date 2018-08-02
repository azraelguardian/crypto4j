package io.github.xinyangpan.crypto4j.huobi.websocket.impl;

import static io.github.xinyangpan.crypto4j.core.Crypto4jUtils.objectMapper;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream;

import io.github.xinyangpan.crypto4j.core.websocket.Handler;
import io.github.xinyangpan.crypto4j.huobi.dto.common.HuobiWsAck;
import io.github.xinyangpan.crypto4j.huobi.dto.market.depth.DepthData;
import io.github.xinyangpan.crypto4j.huobi.dto.market.kline.KlineData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HuobiHandler extends Handler {

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
		String jsonMessage = getTextMessage(message.getPayload());
		HuobiSubscriber subscriber = (HuobiSubscriber)webSocketManager.getSubscriber();
		log.debug("handling message: {}", jsonMessage);
		JsonNode rootNode = objectMapper().readTree(jsonMessage);
		// ping message
		JsonNode evalNode = rootNode.at("/ping");
		if (!evalNode.isMissingNode()) {
			onPingMessage(evalNode.asLong());
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
		// market depth message
		evalNode = rootNode.at("/tick/bids");
		if (!evalNode.isMissingNode()) {
			subscriber.onDepthData(objectMapper().readValue(jsonMessage, DepthData.class));
			return;
		}
		// kline message
		evalNode = rootNode.at("/tick/open");
		if (!evalNode.isMissingNode()) {
			subscriber.onKlineData(objectMapper().readValue(jsonMessage, KlineData.class));
			return;
		}
		// 
		subscriber.unhandledMessage(jsonMessage);
	}

	private String getTextMessage(ByteBuffer payload) throws IOException {
		GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteBufferBackedInputStream(payload));
		String json = IOUtils.toString(gzipInputStream, Charset.forName("utf-8"));
		return json;
	}

	private void onPingMessage(long pingTs) throws Exception {
		log.debug("responding ping message: {}, {}", pingTs, System.currentTimeMillis() - pingTs);
		session.sendMessage(new TextMessage(String.format("{'pong': %s}", pingTs)));
	}

	private void onAcknowledge(HuobiWsAck huobiWsAck) {
		log.info("onAcknowledge: {}", huobiWsAck);
	}

}
