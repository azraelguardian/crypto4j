package io.github.xinyangpan.crypto4j.exchange.binance.websocket.impl;

import static io.github.xinyangpan.crypto4j.exchange.ExchangeUtils.objectMapper;

import java.io.IOException;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import io.github.xinyangpan.crypto4j.common.websocket.handler.BaseWsHandler;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.websocket.userstream.AccountInfo;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.websocket.userstream.ExecutionReport;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter(AccessLevel.PACKAGE)
public class BinanceWsHandler extends BaseWsHandler<BinanceSubscriber> {

	public BinanceWsHandler(BinanceSubscriber binanceSubscriber) {
		super("binance", binanceSubscriber);
	}

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
		wsSubscriber.unhandledMessage(jsonMessage);
	}

	private void userStream(String jsonMessage, JsonNode eventTypeNode) throws IOException, JsonParseException, JsonMappingException {
		String eventType = eventTypeNode.asText();
		switch (eventType) {
		case "outboundAccountInfo":
			wsSubscriber.getAccountInfoListener().accept(objectMapper().readValue(jsonMessage, AccountInfo.class));
			return;
		case "executionReport":
			wsSubscriber.getExecutionReportListener().accept(objectMapper().readValue(jsonMessage, ExecutionReport.class));
			return;
		default:
			wsSubscriber.unhandledMessage(jsonMessage);
			return;
		}
	}

	private void marketStream(String jsonMessage, JsonNode streamNode) throws IOException, JsonParseException, JsonMappingException {
		String stream = streamNode.asText();
		DataType dataType = DataType.getDataType(stream);
		if (dataType == null) {
			wsSubscriber.unhandledMessage(jsonMessage);
			return;
		}
		JavaType javaType = dataType.getJavaType(objectMapper());
		switch (dataType) {
		case TICKER:
			wsSubscriber.onTicker(objectMapper().readValue(jsonMessage, javaType));
			return;
		case DEPTH:
			wsSubscriber.onDepth(objectMapper().readValue(jsonMessage, javaType));
			return;
		default:
			wsSubscriber.unhandledMessage(jsonMessage);
			return;
		}
	}

}
