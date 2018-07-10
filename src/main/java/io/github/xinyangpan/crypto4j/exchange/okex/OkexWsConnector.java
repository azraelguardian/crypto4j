package io.github.xinyangpan.crypto4j.exchange.okex;

import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import io.github.xinyangpan.crypto4j.exchange.huobi.impl.HuobiWsHandler;
import io.github.xinyangpan.crypto4j.exchange.huobi.impl.HuobiWsSubscriberImpl;

public class OkexWsConnector {
	private String url = "wss://real.okex.com:10441/websocket";
	private WebSocketConnectionManager manager;

	public OkexWsConnector() {
	}

	public OkexWsConnector(String url) {
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

	public HuobiWsSubscriberImpl connect() {
		return this.connect(new HuobiWsHandler());
	}

	public void disconnect() {
		manager.stop();
	}

}
