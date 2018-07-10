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
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.common.HuobiWsAck;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.depth.DepthData;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.kline.KlineData;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.common.OkexWsResponse;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.common.TickerData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter(AccessLevel.PACKAGE)
public class OkexWsHandler extends WebSocketHandler {
	// ch -> listener
	private final Map<String, Consumer<DepthData>> depthListenerMap = Maps.newHashMap();
	private final Map<String, Consumer<KlineData>> klineListenerMap = Maps.newHashMap();

	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
		throw new UnsupportedOperationException();
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		String channel = objectMapper().readTree(payload).findValue("channel").asText();
		if (channel.contains("ticker")) {
			JavaType type = objectMapper().getTypeFactory().constructParametricType(OkexWsResponse.class, TickerData.class);
			onTickerData(objectMapper().readValue(payload, type));
			return;
		} else if (channel.contains("depth")) {
			JavaType type = objectMapper().getTypeFactory().constructParametricType(OkexWsResponse.class, DepthData.class);
			onDepthData(objectMapper().readValue(payload, type));
			return;
		}
		// 
		log.warn("Unhandled message: {}", payload);
	}


	private void onTickerData(OkexWsResponse<TickerData> readValue) {
		// TODO Auto-generated method stub
		
	}

	private void onDepthData(OkexWsResponse<DepthData> readValue) {
		// TODO Auto-generated method stub
		
	}
	
	private void onPingMessage(long pingTs) throws Exception {
		session.sendMessage(new TextMessage(String.format("{'pong': %s}", pingTs)));
	}

	private void onAcknowledge(HuobiWsAck huobiWsAck) {
		log.info("onAcknowledge: {}", huobiWsAck);
	}

	private void onMarketDepthData(DepthData depthData) {
		log.debug("onMarketDepthData: {}", depthData.getCh());
		Consumer<DepthData> listener = depthListenerMap.get(depthData.getCh());
		listener.accept(depthData);
	}

	private void onKlineData(KlineData klineData) {
		log.debug("onMarketDepthData: {}", klineData.getCh());
		Consumer<KlineData> listener = klineListenerMap.get(klineData.getCh());
		listener.accept(klineData);
	}

}
