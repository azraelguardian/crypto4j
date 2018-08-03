package io.github.xinyangpan.crypto4j.huobi.websocket.impl;

import static io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils.objectMapper;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream;

import io.github.xinyangpan.crypto4j.core.websocket.Handler;
import io.github.xinyangpan.crypto4j.huobi.dto.common.HuobiWsAck;
import io.github.xinyangpan.crypto4j.huobi.dto.common.HuobiWsResponse;
import io.github.xinyangpan.crypto4j.huobi.dto.market.depth.Depth;
import io.github.xinyangpan.crypto4j.huobi.dto.market.kline.Kline;
import io.github.xinyangpan.crypto4j.huobi.dto.market.tick.Ticker;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HuobiHandler extends Handler {
	private final static TypeReference<HuobiWsResponse<Depth>> DEPTH = new TypeReference<HuobiWsResponse<Depth>>() {};
	private final static TypeReference<HuobiWsResponse<Kline>> KLINE = new TypeReference<HuobiWsResponse<Kline>>() {};
	private final static TypeReference<HuobiWsResponse<Ticker>> TICK = new TypeReference<HuobiWsResponse<Ticker>>() {};

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
		String jsonMessage = getTextMessage(message.getPayload());
		HuobiSubscriber subscriber = (HuobiSubscriber) webSocketManager.getSubscriber();
		log.debug("handling message: {}", jsonMessage);
		JsonNode rootNode = objectMapper().readTree(jsonMessage);
		// response message
		JsonNode evalNode = rootNode.at("/ch");
		if (!evalNode.isMissingNode()) {
			String ch = evalNode.asText();
			System.out.println(ch);
			if (ch.contains("depth")) {
				// market depth message
				subscriber.onDepthData(objectMapper().readValue(jsonMessage, DEPTH));
				return;
			} else if (ch.contains("kline")) {
				// kline message
				subscriber.onKlineData(objectMapper().readValue(jsonMessage, KLINE));
				return;
			} else if (ch.contains("detail")) {
				// tick message
				subscriber.onTickData(objectMapper().readValue(jsonMessage, TICK));
				return;
			}
		}
		// ping message
		evalNode = rootNode.at("/ping");
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
