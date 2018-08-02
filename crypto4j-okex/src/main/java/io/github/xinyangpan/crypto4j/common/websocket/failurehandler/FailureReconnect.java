package io.github.xinyangpan.crypto4j.common.websocket.failurehandler;

import org.springframework.web.socket.CloseStatus;

import io.github.xinyangpan.crypto4j.common.websocket.BaseWsConnector;

public class FailureReconnect implements FailureHandler {
	private final BaseWsConnector<?> wsConnector;

	public FailureReconnect(BaseWsConnector<?> wsConnector) {
		this.wsConnector = wsConnector;
	}

	@Override
	public void pingTimeout() {
		wsConnector.reconnect();
	}

	@Override
	public void onAbnormalConnectionClosed(CloseStatus status) {
		wsConnector.reconnect();
	}

}
