package io.github.xinyangpan.crypto4j.exchange.okex.impl;

import static io.github.xinyangpan.crypto4j.exchange.ExchangeUtils.objectMapper;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;

import io.github.xinyangpan.crypto4j.core.BaseWsHandler;
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

	public void setOkexWsHeartbeatHandler(OkexWsHeartbeatHandler okexWsHeartbeatHandler) {
		this.setHeartbeatHandler(okexWsHeartbeatHandler);
	}

	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		log.debug("handling message: {}", payload);
		JsonNode root = objectMapper().readTree(payload);
		// pong
		JsonNode eventNode = root.findValue("event");
		if (eventNode != null && "pong".equals(eventNode.asText())) {
			onPong();
			return;
		}
		// channel message
		String channel = root.findValue("channel").asText();
		if (channel.contains("ticker")) {
			OkexWsResponse<TickerData>[] responses = this.parse(payload, TickerData.class);
			for (OkexWsResponse<TickerData> response : responses) {
				onTickerData(response);
			}
			return;
		} else if (channel.contains("depth")) {
			OkexWsResponse<DepthData>[] responses = this.parse(payload, DepthData.class);
			for (OkexWsResponse<DepthData> response : responses) {
				onDepthData(response);
			}
			return;
		} else if (channel.contains("addChannel")) {
			OkexWsResponse<ResultData>[] responses = this.parse(payload, ResultData.class);
			for (OkexWsResponse<ResultData> response : responses) {
				onResultData(response);
			}
			return;
		}
		// 
		log.warn("Unhandled message: {}", payload);
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
		heartbeat.onPong();
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
