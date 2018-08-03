package io.github.xinyangpan.crypto4j.okex.websocket.impl;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;

import io.github.xinyangpan.crypto4j.core.websocket.Heartbeat;

public class OkexHeartBeat extends Heartbeat {

	public OkexHeartBeat() {
	}

	@Override
	protected void sendPing() throws IOException {
		session.sendMessage(new TextMessage("{'event':'ping'}"));
	}

}
