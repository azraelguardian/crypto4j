package io.github.xinyangpan.crypto4j.exchange.okex.impl;

import static io.github.xinyangpan.crypto4j.exchange.common.HuobiUtils.objectMapper;

import java.util.Map;
import java.util.function.Consumer;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.JavaType;
import com.google.common.collect.Maps;

import io.github.xinyangpan.crypto4j.core.WebSocketHandler;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.common.DepthData;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.common.OkexWsResponse;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.common.TickerData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter(AccessLevel.PACKAGE)
public class OkexWsHandler extends WebSocketHandler {
	// ch -> listener
	private final Map<String, Consumer<OkexWsResponse<DepthData>>> depthListenerMap = Maps.newHashMap();
	private final Map<String, Consumer<OkexWsResponse<TickerData>>> tickerListenerMap = Maps.newHashMap();

	public OkexWsHandler() {
		super("okex");
	}
	
	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
		throw new UnsupportedOperationException();
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		log.debug("handling message: {}", payload);
		String channel = objectMapper().readTree(payload).findValue("channel").asText();
		if (channel.contains("ticker")) {
			JavaType type = getType(TickerData.class);
			OkexWsResponse<TickerData>[] responses = objectMapper().readValue(payload, type);
			for (OkexWsResponse<TickerData> response : responses) {
				onTickerData(response);
			}
			return;
		} else if (channel.contains("depth")) {
			JavaType type = getType(DepthData.class);
			OkexWsResponse<DepthData>[] responses = objectMapper().readValue(payload, type);
			for (OkexWsResponse<DepthData> response : responses) {
				onDepthData(response);
			}
			return;
		}
		// 
		log.warn("Unhandled message: {}", payload);
	}

	private <T> JavaType getType(Class<T> clazz) {
		JavaType type = objectMapper().getTypeFactory().constructParametricType(OkexWsResponse.class, clazz);
		return objectMapper().getTypeFactory().constructArrayType(type);
	}


	private void onTickerData(OkexWsResponse<TickerData> response) {
		Consumer<OkexWsResponse<TickerData>> listener = tickerListenerMap.get(response.getChannel());
		listener.accept(response);
	}

	private void onDepthData(OkexWsResponse<DepthData> response) {
		Consumer<OkexWsResponse<DepthData>> listener = depthListenerMap.get(response.getChannel());
		listener.accept(response);
	}

}
