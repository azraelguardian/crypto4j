package io.github.xinyangpan.crypto4j.exchange.okex.websocket.impl;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import io.github.xinyangpan.crypto4j.core.BaseWsConnector;
import io.github.xinyangpan.crypto4j.core.heartbeat.WsHeartbeat;

public class OkexWsHeartBeat extends WsHeartbeat {

	public OkexWsHeartBeat(BaseWsConnector<?> wsConnector) {
		super(wsConnector);
	}

	@Override
	protected void sendPing(WebSocketSession session) throws IOException {
		session.sendMessage(new TextMessage("{'event':'ping'}"));
	}

}
