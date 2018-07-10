package io.github.xinyangpan.crypto4j.exchange.okex.impl;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import io.github.xinyangpan.crypto4j.core.heartbeat.HeartbeatHandler;

public interface OkexWsHeartbeatHandler extends HeartbeatHandler {

	@Override
	default void sendPing(WebSocketSession session) throws IOException {
		session.sendMessage(new TextMessage("{'event':'ping'}"));
	}
	
	@Override
	void pingTimeout();
	
}
