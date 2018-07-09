package io.github.xinyangpan.crypto4j.exchange.huobi;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream;

public class HuobiTest extends AbstractWebSocketHandler {
	private static final Logger log = LoggerFactory.getLogger(HuobiTest.class);
	// 
	private ObjectMapper objectMapper = new ObjectMapper();
	// 
	private WebSocketConnectionManager manager;

	@SuppressWarnings("unchecked")
	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
		ByteBuffer payload = message.getPayload();
		GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteBufferBackedInputStream(payload));
		String json = IOUtils.toString(gzipInputStream, Charset.forName("utf-8"));
		Map<String, Object> convertValue = (Map<String, Object>) objectMapper.readValue(json, Map.class);
		Object object = convertValue.get("ping");
		log.info("payload[Binary] - {} / toString: {}", payload, json);
		if (object != null) {
			// 
			session.sendMessage(new TextMessage(String.format("{'pong': %s}", object)));
		} else {
			log.info("**************** payload[Binary] - {} / message: {}", payload, json);
		}
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		log.info("**************** payload - {}", payload);
		//		OrderBook orderBook = objectMapper.readValue(payload, OrderBook.class);
		//		consumer.accept(orderBook);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("status - " + status);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("afterConnectionEstablished");
		//		session.sendMessage(new PingMessage());
		session.sendMessage(new TextMessage("{\"sub\": \"market.btcusdt.depth.step0\",\"id\": \"id10\"}"));
		//		session.sendMessage(new BinaryMessage("{'sub': 'market.btcusdt.kline.1min', 'id': 'id12'}".getBytes()));
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		System.out.println("exception - " + exception);
		exception.printStackTrace();
	}

	public void start() {
		// 
		StandardWebSocketClient client = new StandardWebSocketClient();
		WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

		manager = new WebSocketConnectionManager(client, this, "wss://api.huobi.pro/ws");
		manager.setHeaders(headers);
		manager.start();

	}

	public void stop() {
		manager.stop();
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println(Charset.defaultCharset());
		HuobiTest huobiTest = new HuobiTest();
		huobiTest.start();

		Thread.sleep(Long.MAX_VALUE);
	}

}
