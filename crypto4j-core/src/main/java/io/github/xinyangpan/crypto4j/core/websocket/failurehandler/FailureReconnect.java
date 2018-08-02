package io.github.xinyangpan.crypto4j.core.websocket.failurehandler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import io.github.xinyangpan.crypto4j.core.websocket.WebSocketManager;

public class FailureReconnect implements FailureHandler {
	private final WebSocketManager wsConnector;

	public FailureReconnect(WebSocketManager wsConnector) {
		this.wsConnector = wsConnector;
	}

	@Override
	public void pingTimeout(WebSocketSession session) {
		wsConnector.reconnect();
	}

	@Override
	public void onAbnormalConnectionClosed(CloseStatus status) {
		wsConnector.reconnect();
	}

}
