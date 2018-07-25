package io.github.xinyangpan.crypto4j.common.websocket;

import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import io.github.xinyangpan.crypto4j.common.websocket.failurehandler.FailureHandler;
import io.github.xinyangpan.crypto4j.common.websocket.handler.BaseWsHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseWsConnector<H extends BaseWsHandler<?>> {
	protected String url;
	protected @Getter final H wsHandler;
	private WebSocketConnectionManager manager;

	public BaseWsConnector(String url, H wsHandler) {
		this.url = url;
		this.wsHandler = wsHandler;
	}

	public void setFailureHandler(FailureHandler failureHandler) {
		this.wsHandler.setFailureHandler(failureHandler);
		if (this.wsHandler.getWsHeartbeat() != null) {
			this.wsHandler.getWsHeartbeat().setFailureHandler(failureHandler);
		}
	}

	public void connect() {
		// 
		this.manager = createConnectionManager(url, wsHandler);
		// 
		log.info("connecting to {}.", url);
		manager.start();
	}

	public void connectSync() {
		this.connect();
		wsHandler.getSessionUtilReady();
	}

	public void disconnect() {
		if (manager == null) {
			return;
		}
		manager.stop();
		manager = null;
	}

	public void reconnect() {
		log.info("try to reconnect...");
		this.disconnect();
		this.connect();
	}

	protected WebSocketConnectionManager createConnectionManager(String url, H wsHandler) {
		StandardWebSocketClient client = new StandardWebSocketClient();
		return new WebSocketConnectionManager(client, wsHandler, url);
	}

}
