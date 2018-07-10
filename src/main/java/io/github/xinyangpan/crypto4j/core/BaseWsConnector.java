package io.github.xinyangpan.crypto4j.core;

import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

public abstract class BaseWsConnector<S extends WsSubscriber, H extends BaseWsHandler> {
	private final String url;
	private final H wsHandler;
	private final S wsSubscriber;
	private WebSocketConnectionManager manager;

	public BaseWsConnector(String url, H wsHandler) {
		this.url = url;
		this.wsHandler = wsHandler;
		this.wsSubscriber = this.createSubscriber(wsHandler);
	}

	protected abstract S createSubscriber(H wsHandler);

	public S connect() {
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

}
