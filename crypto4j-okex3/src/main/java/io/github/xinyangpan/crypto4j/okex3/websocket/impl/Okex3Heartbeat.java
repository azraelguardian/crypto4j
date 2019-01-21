package io.github.xinyangpan.crypto4j.okex3.websocket.impl;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;

import io.github.xinyangpan.crypto4j.core.websocket.Heartbeat;

public class Okex3Heartbeat extends Heartbeat {

	public Okex3Heartbeat() {
	}

	@Override
	protected void sendPing() throws IOException {
		subscriber.sendMessage(new TextMessage("ping"), true);
	}

}
