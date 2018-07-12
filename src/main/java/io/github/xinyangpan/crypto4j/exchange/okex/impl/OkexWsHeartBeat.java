package io.github.xinyangpan.crypto4j.exchange.okex.impl;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import io.github.xinyangpan.crypto4j.core.heartbeat.AbstractWsHeartbeat;

public class OkexWsHeartBeat extends AbstractWsHeartbeat {
	private final Runnable run;

	public OkexWsHeartBeat(Runnable run) {
		this.run = run;
	}

	@Override
	protected void sendPing(WebSocketSession session) throws IOException {
		session.sendMessage(new TextMessage("{'event':'ping'}"));
	}

	@Override
	protected void pingTimeout() {
		this.run.run();
	}

}
