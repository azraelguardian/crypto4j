package io.github.xinyangpan.crypto4j.exchange.okex;

import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import io.github.xinyangpan.crypto4j.exchange.okex.impl.OkexWsHandler;
import io.github.xinyangpan.crypto4j.exchange.okex.impl.OkexWsSubscriberImpl;

public class OkexWsConnector {
	private String url = "wss://real.okex.com:10441/websocket";
	private WebSocketConnectionManager manager;

	public OkexWsConnector() {
	}

	public OkexWsConnector(String url) {
		this.url = url;
	}

	public OkexWsSubscriberImpl connect(OkexWsHandler wsHandler) {
		// 
		StandardWebSocketClient client = new StandardWebSocketClient();
		manager = new WebSocketConnectionManager(client, wsHandler, url);
		manager.start();
		wsHandler.waitUtilConnectionEstablished();
		return new OkexWsSubscriberImpl(wsHandler);
	}

	public OkexWsSubscriberImpl connect() {
		return this.connect(new OkexWsHandler());
	}

	public void disconnect() {
		manager.stop();
	}

}
