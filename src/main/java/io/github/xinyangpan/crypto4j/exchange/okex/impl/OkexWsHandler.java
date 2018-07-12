package io.github.xinyangpan.crypto4j.exchange.okex.impl;

import static io.github.xinyangpan.crypto4j.exchange.ExchangeUtils.objectMapper;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;

import io.github.xinyangpan.crypto4j.core.handler.BaseWsHandler;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.DepthData;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.OkexWsResponse;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.ResultData;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.TickerData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter(AccessLevel.PACKAGE)
public class OkexWsHandler extends BaseWsHandler<OkexWsSubscriber> {

	public OkexWsHandler(OkexWsSubscriber okexWsSubscriber) {
		super("okex", okexWsSubscriber);
	}

	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String jsonMessage = message.getPayload();
		log.debug("handling message: {}", jsonMessage);
		JsonNode root = objectMapper().readTree(jsonMessage);
		// pong
		JsonNode eventNode = root.findValue("event");
		if (eventNode != null && "pong".equals(eventNode.asText())) {
			onPong();
			return;
		}
		// channel message
		String channel = root.findValue("channel").asText();
		if (channel.contains("ticker")) {
			OkexWsResponse<TickerData>[] responses = this.parse(jsonMessage, TickerData.class);
			for (OkexWsResponse<TickerData> response : responses) {
				onTickerData(response);
			}
			return;
		} else if (channel.contains("depth")) {
			OkexWsResponse<DepthData>[] responses = this.parse(jsonMessage, DepthData.class);
			for (OkexWsResponse<DepthData> response : responses) {
				onDepthData(response);
			}
			return;
		} else if (channel.contains("addChannel")) {
			OkexWsResponse<ResultData>[] responses = this.parse(jsonMessage, ResultData.class);
			for (OkexWsResponse<ResultData> response : responses) {
				onResultData(response);
			}
			return;
		}
		// 
		wsSubscriber.unhandledMessage(jsonMessage);
	}

	private <T> OkexWsResponse<T>[] parse(String payload, Class<T> clazz) throws Exception {
		JavaType type = getType(clazz);
		return objectMapper().readValue(payload, type);
	}

	private <T> JavaType getType(Class<T> clazz) {
		JavaType type = objectMapper().getTypeFactory().constructParametricType(OkexWsResponse.class, clazz);
		return objectMapper().getTypeFactory().constructArrayType(type);
	}

	private void onPong() {
		log.debug("Pond recieved.");
		wsHeartbeat.onPong();
	}

	private void onResultData(OkexWsResponse<ResultData> response) {
		log.info("Result: {}", response);
	}

	private void onTickerData(OkexWsResponse<TickerData> response) {
		wsSubscriber.getTickerListener().accept(response);
	}

	private void onDepthData(OkexWsResponse<DepthData> response) {
		wsSubscriber.getDepthListener().accept(response);
	}

}
