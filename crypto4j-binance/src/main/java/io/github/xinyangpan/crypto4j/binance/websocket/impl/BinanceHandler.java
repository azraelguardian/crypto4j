package io.github.xinyangpan.crypto4j.binance.websocket.impl;

import static io.github.xinyangpan.crypto4j.core.Crypto4jUtils.objectMapper;

import java.io.IOException;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import io.github.xinyangpan.crypto4j.binance.dto.websocket.userstream.AccountInfo;
import io.github.xinyangpan.crypto4j.binance.dto.websocket.userstream.ExecutionReport;
import io.github.xinyangpan.crypto4j.core.websocket.Handler;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter(AccessLevel.PACKAGE)
public class BinanceHandler extends Handler {

	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String jsonMessage = message.getPayload();
		log.debug("handling message: {}", jsonMessage);
		this.onPong("Other Msg");
		JsonNode rootNode = objectMapper().readTree(jsonMessage);
		JsonNode eventTypeNode = rootNode.at("/e");
		if (!eventTypeNode.isMissingNode()) {
			userStream(jsonMessage, eventTypeNode);
			return;
		}
		JsonNode streamNode = rootNode.at("/stream");
		if (!streamNode.isMissingNode()) {
			marketStream(jsonMessage, streamNode);
			return;
		}
		this.getSubscriber().unhandledMessage(jsonMessage);
	}

	private void userStream(String jsonMessage, JsonNode eventTypeNode) throws IOException, JsonParseException, JsonMappingException {
		String eventType = eventTypeNode.asText();
		BinanceSubscriber subscriber = this.getSubscriber();
		switch (eventType) {
		case "outboundAccountInfo":
			subscriber.onAccountInfo(objectMapper().readValue(jsonMessage, AccountInfo.class));
			return;
		case "executionReport":
			subscriber.onExecutionReport(objectMapper().readValue(jsonMessage, ExecutionReport.class));
			return;
		default:
			subscriber.unhandledMessage(jsonMessage);
			return;
		}
	}

	private void marketStream(String jsonMessage, JsonNode streamNode) throws IOException, JsonParseException, JsonMappingException {
		String stream = streamNode.asText();
		DataType dataType = DataType.getDataType(stream);
		BinanceSubscriber subscriber = this.getSubscriber();
		if (dataType == null) {
			subscriber.unhandledMessage(jsonMessage);
			return;
		}
		JavaType javaType = dataType.getJavaType(objectMapper());
		switch (dataType) {
		case TICKER:
			subscriber.onTicker(objectMapper().readValue(jsonMessage, javaType));
			return;
		case DEPTH:
			subscriber.onDepth(objectMapper().readValue(jsonMessage, javaType));
			return;
		default:
			subscriber.unhandledMessage(jsonMessage);
			return;
		}
	}

	private BinanceSubscriber getSubscriber() {
		return (BinanceSubscriber)this.webSocketManager.getSubscriber();
	}
	
}