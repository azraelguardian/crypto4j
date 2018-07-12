package io.github.xinyangpan.crypto4j.core;

import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseWsConnector<H extends BaseWsHandler> {
	private final String url;
	protected final H wsHandler;
	private WebSocketConnectionManager manager;

	public BaseWsConnector(String url, H wsHandler) {
		this.url = url;
		this.wsHandler = wsHandler;
	}

	public void connect() {
		// 
		log.info("connecting to {}.", url);
		// 
		StandardWebSocketClient client = new StandardWebSocketClient();
		manager = new WebSocketConnectionManager(client, wsHandler, url);
		manager.start();
	}

	public void disconnect() {
		if (manager == null) {
			return;
		}
		manager.stop();
	}

	public void reconnect() {
		log.info("try to reconnect...");
		this.disconnect();
		this.connect();
	}

}
