package io.github.xinyangpan.crypto4j.binance.operation;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.xinyangpan.crypto4j.binance.dto.StreamData;

public class MarketOperation extends TextWebSocketHandler {
	private static final Logger log = LoggerFactory.getLogger(MarketOperation.class);
	// stream.binance.com:9443/ws/bnbbtc@depth
	// stream.binance.com:9443/ws/ETHBTC@trade
	//  /stream?streams=<streamName1>/<streamName2>/<streamName3>
	private String url = "stream.binance.com:9443/stream?streams=btcusdt@trade/btcusdt@aggTrade";
	private ObjectMapper objectMapper = new ObjectMapper();
	// 
	private Consumer<StreamData> consumer;
	private WebSocketConnectionManager manager;

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		log.info("payload - {}", payload);
		JsonNode rootNode = objectMapper.readTree(payload);
		String stream = rootNode.findValue("stream").asText();
		JsonNode dataNode = rootNode.findValue("data");
		String data  = dataNode.toString();
//		System.out.println("****************"+stream);
//		System.out.println("****************"+dataNode.toString());
//		StreamData streamData = objectMapper.readValue(payload, StreamData.class);
		consumer.accept(new StreamData(stream, data));
	}
	
	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
		log.info("BinaryMessage - {}", message);
	}
	
	@Override
	protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
		log.info("PongMessage - {}", message);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("status - " + status);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("afterConnectionEstablished");
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		System.out.println("exception - " + exception);
	}

	public void registerConsumer(Consumer<StreamData> consumer) {
		this.consumer = consumer;
	}

	public void start() {
		// 
		WebSocketClient client = new StandardWebSocketClient();
		manager = new WebSocketConnectionManager(client, this, "wss://" + url);
		manager.start();
	}

	public void stop() {
		manager.stop();
	}

}
