package io.github.xinyangpan.crypto4j.okex.websocket.impl;

import static io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils.objectMapper;

import java.util.function.Consumer;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Preconditions;

import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.core.websocket.Subscriber;
import io.github.xinyangpan.crypto4j.okex.dto.common.OkexWsRequest;
import io.github.xinyangpan.crypto4j.okex.dto.common.OkexWsResponse;
import io.github.xinyangpan.crypto4j.okex.dto.common.ResultData;
import io.github.xinyangpan.crypto4j.okex.dto.market.Depth;
import io.github.xinyangpan.crypto4j.okex.dto.market.TickerData;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class OkexSubscriber extends Subscriber {
	private final static TypeReference<OkexWsResponse<TickerData>[]> TICK = new TypeReference<OkexWsResponse<TickerData>[]>() {};
	private final static TypeReference<OkexWsResponse<Depth>[]> DEPTH = new TypeReference<OkexWsResponse<Depth>[]>() {};
	private final static TypeReference<OkexWsResponse<ResultData>[]> RESULT = new TypeReference<OkexWsResponse<ResultData>[]>() {};
	
	private Consumer<OkexWsResponse<Depth>> depthListener = Crypto4jUtils.logConsumer();
	private Consumer<OkexWsResponse<TickerData>> tickerListener = Crypto4jUtils.logConsumer();

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String jsonMessage = message.getPayload();
		log.debug(MSG_TRACK, "{}: Handling message: {}", this.getName(), jsonMessage);
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
			OkexWsResponse<TickerData>[] responses = objectMapper().readValue(jsonMessage, TICK);
			for (OkexWsResponse<TickerData> response : responses) {
				tickerListener.accept(response);
			}
			return;
		} else if (channel.contains("depth")) {
			OkexWsResponse<Depth>[] responses = objectMapper().readValue(jsonMessage, DEPTH);
			for (OkexWsResponse<Depth> response : responses) {
				depthListener.accept(response);
			}
			return;
		} else if (channel.contains("addChannel")) {
			OkexWsResponse<ResultData>[] responses = objectMapper().readValue(jsonMessage, RESULT);
			for (OkexWsResponse<ResultData> response : responses) {
				onResultData(response);
			}
			return;
		}
		// 
		this.unhandledMessage(jsonMessage);
	}

	private void onResultData(OkexWsResponse<ResultData> response) {
		log.info("{}: Ack - {}", this.getName(), response);
	}

	public void depth(String symbol, int depth) {
		// 
		Preconditions.checkNotNull(symbol);
		Preconditions.checkArgument(depth >= 0);
		// 
		log.info("Subscribing marketDepth. symbol={}, type={}.", symbol, depth);
		String channel = String.format("ok_sub_spot_%s_depth_%s", symbol, depth);
		this.send(OkexWsRequest.addChannel(channel));
	}

	public void ticker(String symbol) {
		// 
		Preconditions.checkNotNull(symbol);
		// 
		log.info("Subscribing ticker. symbol={}.", symbol);
		String channel = String.format("ok_sub_spot_%s_ticker", symbol);
		this.send(OkexWsRequest.addChannel(channel));
	}

}
