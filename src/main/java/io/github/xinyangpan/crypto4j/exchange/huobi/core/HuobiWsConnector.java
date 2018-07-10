package io.github.xinyangpan.crypto4j.exchange.huobi.core;

import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

public class HuobiWsConnector {
	private String url = "wss://api.huobi.pro/ws";
	private WebSocketConnectionManager manager;

	public HuobiWsConnector() {
	}

	public HuobiWsConnector(String url) {
		this.url = url;
	}

	public HuobiWsSubscriberImpl connect(HuobiWsHandler wsHandler) {
		// 
		StandardWebSocketClient client = new StandardWebSocketClient();
		manager = new WebSocketConnectionManager(client, wsHandler, url);
		manager.start();
		wsHandler.waitUtilConnectionEstablished();
		return new HuobiWsSubscriberImpl(wsHandler);
	}

	public void disconnect() {
		manager.stop();
	}

}
