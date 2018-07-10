package io.github.xinyangpan.crypto4j.exchange.huobi;

import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import io.github.xinyangpan.crypto4j.exchange.huobi.impl.HuobiWsHandler;
import io.github.xinyangpan.crypto4j.exchange.huobi.impl.HuobiWsSubscriberImpl;

public class HuobiWsConnector {
	private static String DEFAULT_URL = "wss://api.huobi.pro/ws";
	private final String url;
	private final HuobiWsHandler wsHandler;
	private final HuobiWsSubscriber huobiWsSubscriber;
	private WebSocketConnectionManager manager;

	public HuobiWsConnector() {
		this(DEFAULT_URL, new HuobiWsHandler());
	}

	public HuobiWsConnector(String url, HuobiWsHandler wsHandler) {
		this.url = url;
		this.wsHandler = wsHandler;
		this.huobiWsSubscriber = new HuobiWsSubscriberImpl(wsHandler);
	}

	public HuobiWsSubscriber connect() {
		// 
		StandardWebSocketClient client = new StandardWebSocketClient();
		manager = new WebSocketConnectionManager(client, wsHandler, url);
		manager.start();
		wsHandler.waitUtilConnectionEstablished();
		return huobiWsSubscriber;
	}

	public void disconnect() {
		if (manager == null) {
			return;
		}
		manager.stop();
	}

}
