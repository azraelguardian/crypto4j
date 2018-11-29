package io.github.xinyangpan.crypto4j.chainup.websocket.impl;

import java.util.function.Consumer;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

import io.github.xinyangpan.crypto4j.chainup.dto.ws.event.Event;
import io.github.xinyangpan.crypto4j.chainup.dto.ws.event.tick.DepthTick;
import io.github.xinyangpan.crypto4j.chainup.dto.ws.event.tick.Tick;
import io.github.xinyangpan.crypto4j.chainup.dto.ws.event.tick.TradeTick;
import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class ChainupSubscriber2 extends ChainupSubscriber {
	private final static TypeReference<Event<TradeTick>> TRADE = new TypeReference<Event<TradeTick>>() {};
	private final static TypeReference<Event<DepthTick>> DEPTH = new TypeReference<Event<DepthTick>>() {};
	private final static TypeReference<Event<Tick>> TICK = new TypeReference<Event<Tick>>() {};

	private Consumer<Event<TradeTick>> tradeListener = Crypto4jUtils.logConsumer();
	private Consumer<Event<DepthTick>> depthListener = Crypto4jUtils.logConsumer();
	private Consumer<Event<Tick>> tickListener = Crypto4jUtils.logConsumer();

	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
		String jsonMessage = this.getTextMessage(message.getPayload());
		log.debug(MSG_TRACK, "{}: Handling message: {}", this.getName(), jsonMessage);
		JsonNode root = objectMapper.readTree(jsonMessage);
		// ping
		JsonNode eventNode = root.findValue("ping");
		if (eventNode != null) {
			onPing(jsonMessage);
			return;
		}
		// channel message
		String channel = root.findValue("channel").asText();
		if (channel.endsWith("_trade_ticker")) {
			Event<TradeTick> event = objectMapper.readValue(jsonMessage, TRADE);
			tradeListener.accept(event);
			return;
		} else if (channel.endsWith("_ticker")) {
			Event<Tick> event = objectMapper.readValue(jsonMessage, TICK);
			tickListener.accept(event);
			return;
		} else if (channel.contains("_depth_step")) {
			Event<DepthTick> event = objectMapper.readValue(jsonMessage, DEPTH);
			depthListener.accept(event);
			return;
		}
		// 
		this.unhandledMessage(jsonMessage);
	}

}
