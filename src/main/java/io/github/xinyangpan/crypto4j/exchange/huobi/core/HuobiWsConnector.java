package io.github.xinyangpan.crypto4j.exchange.huobi.core;

import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

public class HuobiWsConnector {
	private static final String HUOBI_PRO_WS = "wss://api.huobi.pro/ws";

	private WebSocketConnectionManager manager;

	public HuobiWsSubscriber connect(HuobiWsHandler wsHandler) {
		// 
		StandardWebSocketClient client = new StandardWebSocketClient();
		manager = new WebSocketConnectionManager(client, wsHandler, HUOBI_PRO_WS);
		manager.start();
		wsHandler.waitUtilConnectionEstablished();
		return new HuobiWsSubscriber(wsHandler);
	}

	public void disconnect() {
		manager.stop();
	}

}
