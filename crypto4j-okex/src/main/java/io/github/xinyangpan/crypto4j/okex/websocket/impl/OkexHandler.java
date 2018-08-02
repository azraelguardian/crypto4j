package io.github.xinyangpan.crypto4j.okex.websocket.impl;

import static io.github.xinyangpan.crypto4j.core.Crypto4jUtils.objectMapper;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;

import io.github.xinyangpan.crypto4j.core.websocket.Handler;
import io.github.xinyangpan.crypto4j.okex.dto.common.OkexWsResponse;
import io.github.xinyangpan.crypto4j.okex.dto.common.ResultData;
import io.github.xinyangpan.crypto4j.okex.dto.market.Depth;
import io.github.xinyangpan.crypto4j.okex.dto.market.TickerData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OkexHandler extends Handler {

	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String jsonMessage = message.getPayload();
		OkexSubscriber subscriber = (OkexSubscriber) this.webSocketManager.getSubscriber();
		log.debug("handling message: {}", jsonMessage);
		JsonNode root = objectMapper().readTree(jsonMessage);
		// pong
		JsonNode eventNode = root.findValue("event");
		if (eventNode != null && "pong".equals(eventNode.asText())) {
			onPong(jsonMessage);
			return;
		}
		// channel message
		String channel = root.findValue("channel").asText();
		if (channel.contains("ticker")) {
			OkexWsResponse<TickerData>[] responses = this.parse(jsonMessage, TickerData.class);
			for (OkexWsResponse<TickerData> response : responses) {
				subscriber.onTickerData(response);
			}
			return;
		} else if (channel.contains("depth")) {
			OkexWsResponse<Depth>[] responses = this.parse(jsonMessage, Depth.class);
			for (OkexWsResponse<Depth> response : responses) {
				subscriber.onDepthData(response);
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
		subscriber.unhandledMessage(jsonMessage);
	}

	private <T> OkexWsResponse<T>[] parse(String payload, Class<T> clazz) throws Exception {
		JavaType type = getType(clazz);
		return objectMapper().readValue(payload, type);
	}

	private <T> JavaType getType(Class<T> clazz) {
		JavaType type = objectMapper().getTypeFactory().constructParametricType(OkexWsResponse.class, clazz);
		return objectMapper().getTypeFactory().constructArrayType(type);
	}

	private void onResultData(OkexWsResponse<ResultData> response) {
		log.info("Result: {}", response);
	}

}
