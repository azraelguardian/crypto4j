package io.github.xinyangpan.crypto4j.core.failurehandler;

import org.springframework.web.socket.CloseStatus;

import io.github.xinyangpan.crypto4j.core.BaseWsConnector;

public class SimpleFailureHandler implements FailureHandler {
	private final BaseWsConnector<?> wsConnector;

	public SimpleFailureHandler(BaseWsConnector<?> wsConnector) {
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
