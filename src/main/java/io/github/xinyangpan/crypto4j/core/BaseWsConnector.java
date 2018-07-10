package io.github.xinyangpan.crypto4j.core;

import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseWsConnector<S extends WsSubscriber, H extends BaseWsHandler> {
	private final String url;
	protected final H wsHandler;
	private @Getter final S wsSubscriber;
	private WebSocketConnectionManager manager;

	public BaseWsConnector(String url, H wsHandler) {
		this.url = url;
		this.wsHandler = wsHandler;
		this.wsSubscriber = this.createSubscriber(wsHandler);
	}

	public S connect() {
		// 
		log.info("connecting to {}.", url);
		// 
		StandardWebSocketClient client = new StandardWebSocketClient();
		manager = new WebSocketConnectionManager(client, wsHandler, url);
		manager.start();
		wsHandler.waitUtilConnectionEstablished();
		return wsSubscriber;
	}

	public void disconnect() {
		if (manager == null) {
			return;
		}
		manager.stop();
	}

	public S reconnect() {
		log.info("try to reconnect...");
		this.disconnect();
		return this.connect();
	}

	protected abstract S createSubscriber(H wsHandler);

}
