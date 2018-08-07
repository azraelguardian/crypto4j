package io.github.xinyangpan.crypto4j.binance.websocket.impl;

import static io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils.objectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;

import io.github.xinyangpan.crypto4j.binance.dto.depth.Depth;
import io.github.xinyangpan.crypto4j.binance.dto.websocket.Ticker;
import io.github.xinyangpan.crypto4j.binance.dto.websocket.common.StreamData;
import io.github.xinyangpan.crypto4j.binance.dto.websocket.userstream.AccountInfo;
import io.github.xinyangpan.crypto4j.binance.dto.websocket.userstream.ExecutionReport;
import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.core.websocket.Subscriber;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class BinanceSubscriber extends Subscriber {
	private final List<String> streamNames = new ArrayList<>();
	private Consumer<StreamData<Depth>> depthListener = Crypto4jUtils.logConsumer();
	private Consumer<StreamData<Ticker>> tickerListener = Crypto4jUtils.logConsumer();
	private Consumer<AccountInfo> accountInfoListener = Crypto4jUtils.logConsumer();
	private Consumer<ExecutionReport> executionReportListener = Crypto4jUtils.logConsumer();

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String jsonMessage = message.getPayload();
		log.debug(MSG_TRACK, "{}: handling message: {}", this.getName(), jsonMessage);
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
		this.unhandledMessage(jsonMessage);
	}

	private void userStream(String jsonMessage, JsonNode eventTypeNode) throws IOException, JsonParseException, JsonMappingException {
		String eventType = eventTypeNode.asText();
		switch (eventType) {
		case "outboundAccountInfo":
			accountInfoListener.accept(objectMapper().readValue(jsonMessage, AccountInfo.class));
			return;
		case "executionReport":
			executionReportListener.accept(objectMapper().readValue(jsonMessage, ExecutionReport.class));
			return;
		default:
			this.unhandledMessage(jsonMessage);
			return;
		}
	}

	private void marketStream(String jsonMessage, JsonNode streamNode) throws IOException, JsonParseException, JsonMappingException {
		String stream = streamNode.asText();
		DataType dataType = DataType.getDataType(stream);
		if (dataType == null) {
			this.unhandledMessage(jsonMessage);
			return;
		}
		JavaType javaType = dataType.getJavaType(objectMapper());
		switch (dataType) {
		case TICKER:
			tickerListener.accept(objectMapper().readValue(jsonMessage, javaType));
			return;
		case DEPTH:
			depthListener.accept(objectMapper().readValue(jsonMessage, javaType));
			return;
		default:
			this.unhandledMessage(jsonMessage);
			return;
		}
	}

	// -----------------------------
	// ----- Sub
	// -----------------------------

	public String getUrl(String websocketMarketBaseUrl) {
		return websocketMarketBaseUrl + this.getUrlParameter();
	}

	private String getUrlParameter() {
		return Joiner.on('/').join(streamNames);
	}

	public BinanceSubscriber depthAndTicker(int level, String... symbols) {
		Preconditions.checkNotNull(symbols);
		// 
		this.depth(level, symbols);
		this.ticker(symbols);
		return this;
	}

	public BinanceSubscriber depth(int level, String... symbols) {
		Preconditions.checkNotNull(symbols);
		// 
		for (String symbol : symbols) {
			streamNames.add(String.format("%s@depth%s", symbol, level));
		}
		return this;
	}

	public BinanceSubscriber ticker(String... symbols) {
		Preconditions.checkNotNull(symbols);
		// 
		for (String symbol : symbols) {
			streamNames.add(String.format("%s@ticker", symbol));
		}
		return this;
	}

}
