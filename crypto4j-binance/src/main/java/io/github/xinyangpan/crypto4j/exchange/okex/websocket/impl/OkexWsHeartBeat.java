package io.github.xinyangpan.crypto4j.exchange.okex.websocket.impl;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import io.github.xinyangpan.crypto4j.common.websocket.BaseWsConnector;
import io.github.xinyangpan.crypto4j.common.websocket.failurehandler.FailureHandler;
import io.github.xinyangpan.crypto4j.common.websocket.heartbeat.WsHeartbeat;

public class OkexWsHeartBeat extends WsHeartbeat {

	public OkexWsHeartBeat(BaseWsConnector<?> wsConnector) {
		super(wsConnector);
	}

	public OkexWsHeartBeat(FailureHandler failureHandler) {
		super(failureHandler);
	}

	@Override
	protected void sendPing(WebSocketSession session) throws IOException {
		session.sendMessage(new TextMessage("{'event':'ping'}"));
	}

}
