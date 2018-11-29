package io.github.xinyangpan.crypto4j.chainup.websocket.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.function.Consumer;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream;
import com.google.common.base.Preconditions;

import io.github.xinyangpan.crypto4j.chainup.dto.ws.event.Event;
import io.github.xinyangpan.crypto4j.chainup.dto.ws.event.tick.DepthTick;
import io.github.xinyangpan.crypto4j.chainup.dto.ws.event.tick.Tick;
import io.github.xinyangpan.crypto4j.chainup.dto.ws.event.tick.TradeTick;
import io.github.xinyangpan.crypto4j.chainup.dto.ws.sub.EventAck;
import io.github.xinyangpan.crypto4j.chainup.dto.ws.sub.EventSub;
import io.github.xinyangpan.crypto4j.chainup.dto.ws.sub.params.DepthParam;
import io.github.xinyangpan.crypto4j.chainup.dto.ws.sub.params.Param;
import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.core.websocket.Subscriber;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class ChainupSubscriber extends Subscriber {

	private final static TypeReference<EventAck> ACK = new TypeReference<EventAck>() {};
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
		// ack
		eventNode = root.findValue("event_rep");
		if (eventNode != null && "subed".equals(eventNode.textValue())) {
			onAck(objectMapper.readValue(jsonMessage, ACK));
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
	
	private String getTextMessage(ByteBuffer payload) throws IOException {
		GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteBufferBackedInputStream(payload));
		String json = IOUtils.toString(gzipInputStream, Charset.forName("utf-8"));
		return json;
	}
	
	private void onAck(EventAck eventAck) {
		log.info("Ack<{}>: {}", this.getName(), eventAck);
	}

	private void onPing(String jsonMessage) {
		log.debug("{}: Ping - {}", this.getName(), jsonMessage);
	}

	public void depth(String symbol, int step, int askDepth, int bidDepth) {
		// 
		Preconditions.checkNotNull(symbol);
		Preconditions.checkArgument(step >= 0);
		Preconditions.checkArgument(askDepth >= 0);
		Preconditions.checkArgument(bidDepth >= 0);
		// 
		log.info("Subscribing marketDepth. symbol={}, step={}, askDepth={}, bidDepth={}.", symbol, step, askDepth, bidDepth);
		String channel = String.format("market_%s_depth_step%s", symbol, step);
		// 
		DepthParam depthParam = new DepthParam();
		depthParam.setChannel(channel);
		depthParam.setCbId(channel);
		depthParam.setAsks(askDepth);
		depthParam.setBids(bidDepth);
		// 
		EventSub<Param> eventSub = new EventSub<>();
		eventSub.setEvent("sub");
		eventSub.setParams(depthParam);
		this.send(eventSub);
	}

	public void depth(String symbol, int depth) {
		this.depth(symbol, 0, depth, depth);
	}

	public void trade(String symbol) {
		// 
		Preconditions.checkNotNull(symbol);
		// 
		log.info("Subscribing trade. symbol={}.", symbol);
		String channel = String.format("market_%s_trade_ticker", symbol);
		EventSub<Param> eventSub = new EventSub<>();
		eventSub.setEvent("sub");
		eventSub.setParams(new Param(channel, channel));
		this.send(eventSub);
	}

	public void tick(String symbol) {
		// 
		Preconditions.checkNotNull(symbol);
		// 
		log.info("Subscribing ticker. symbol={}.", symbol);
		String channel = String.format("market_%s_ticker", symbol);
		EventSub<Param> eventSub = new EventSub<>();
		eventSub.setEvent("sub");
		eventSub.setParams(new Param(channel, channel));
		this.send(eventSub);
	}

}
