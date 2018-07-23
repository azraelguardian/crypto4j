package io.github.xinyangpan.crypto4j.exchange.huobi.websocket.impl;

import static io.github.xinyangpan.crypto4j.exchange.ExchangeUtils.objectMapper;

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

import io.github.xinyangpan.crypto4j.common.websocket.handler.BaseWsHandler;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.ws.common.HuobiWsAck;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.ws.depth.DepthData;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.ws.kline.KlineData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter(AccessLevel.PACKAGE)
public class HuobiWsHandler extends BaseWsHandler<HuobiSubscriber> {

	public HuobiWsHandler(HuobiSubscriber huobiSubscriber) {
		super("huobi", huobiSubscriber);
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
			wsSubscriber.onDepthData(objectMapper().readValue(jsonMessage, DepthData.class));
			return;
		}
		// kline message
		evalNode = rootNode.at("/tick/open");
		if (!evalNode.isMissingNode()) {
			wsSubscriber.onKlineData(objectMapper().readValue(jsonMessage, KlineData.class));
			return;
		}
		// 
		wsSubscriber.unhandledMessage(jsonMessage);
	}

	private String getTextMessage(ByteBuffer payload) throws IOException {
		GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteBufferBackedInputStream(payload));
		String json = IOUtils.toString(gzipInputStream, Charset.forName("utf-8"));
		return json;
	}

	private void onPingMessage(long pingTs) throws Exception {
		log.debug("responding ping message: {}", pingTs);
		session.sendMessage(new TextMessage(String.format("{'pong': %s}", pingTs)));
	}

	private void onAcknowledge(HuobiWsAck huobiWsAck) {
		log.info("onAcknowledge: {}", huobiWsAck);
	}

}
