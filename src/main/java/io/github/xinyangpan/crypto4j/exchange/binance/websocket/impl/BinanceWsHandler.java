package io.github.xinyangpan.crypto4j.exchange.binance.websocket.impl;

import static io.github.xinyangpan.crypto4j.exchange.ExchangeUtils.objectMapper;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;

import io.github.xinyangpan.crypto4j.core.handler.BaseWsHandler;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter(AccessLevel.PACKAGE)
public class BinanceWsHandler extends BaseWsHandler<BinanceSubscriber> {

	public BinanceWsHandler(BinanceSubscriber binanceSubscriber) {
		super("binance", binanceSubscriber);
	}

	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String jsonMessage = message.getPayload();
		log.debug("handling message: {}", jsonMessage);
		JsonNode rootNode = objectMapper().readTree(jsonMessage);
		String stream = rootNode.findValue("stream").asText();
		DataType dataType = DataType.getDataType(stream);
		if (dataType == null) {
			wsSubscriber.unhandledMessage(jsonMessage);
			return;
		}
		JavaType javaType = dataType.getJavaType(objectMapper());
		switch (dataType) {
		case TICKER:
			wsSubscriber.onTicker(objectMapper().readValue(jsonMessage, javaType));
			return;
		case DEPTH:
			wsSubscriber.onDepth(objectMapper().readValue(jsonMessage, javaType));
			return;
		default:
			throw new IllegalArgumentException(String.format("Not Supported Type: %s", dataType));
		}
	}

}
