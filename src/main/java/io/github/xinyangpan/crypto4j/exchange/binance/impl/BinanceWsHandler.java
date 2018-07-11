package io.github.xinyangpan.crypto4j.exchange.binance.impl;

import static io.github.xinyangpan.crypto4j.exchange.ExchangeUtils.objectMapper;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;

import io.github.xinyangpan.crypto4j.core.BaseWsHandler;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.Ticker;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.common.StreamData;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.depth.Depth;
import io.github.xinyangpan.crypto4j.exchange.binance.subscription.BinanceSubscription;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter(AccessLevel.PACKAGE)
public class BinanceWsHandler extends BaseWsHandler {
	private BinanceSubscription binanceSubscription;

	public BinanceWsHandler(BinanceSubscription binanceSubscription) {
		super("binance");
		this.binanceSubscription = binanceSubscription;
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
			log.warn("Unhandled message: {}", jsonMessage);
			return;
		}
		JavaType javaType = dataType.getJavaType(objectMapper());
		switch (dataType) {
		case TICKER:
			onTicker(objectMapper().readValue(jsonMessage, javaType));
			return;
		case DEPTH:
			onDepth(objectMapper().readValue(jsonMessage, javaType));
			return;
		default:
			throw new IllegalArgumentException(String.format("Not Supported Type: %s", dataType));
		}
	}

	private void onTicker(StreamData<Ticker> data) {
		binanceSubscription.getTickerListener().accept(data);
	}

	private void onDepth(StreamData<Depth> data) {
		binanceSubscription.getDepthListener().accept(data);
	}

}
