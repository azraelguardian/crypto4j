package io.github.xinyangpan.crypto4j.okex.websocket.impl;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;

import io.github.xinyangpan.crypto4j.core.websocket.Heartbeat;

public class OkexHeartbeat extends Heartbeat {

	public OkexHeartbeat() {
	}

	@Override
	protected void sendPing() throws IOException {
		subscriber.sendMessage(new TextMessage("{'event':'ping'}"), true);
	}

}
