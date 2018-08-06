package io.github.xinyangpan.crypto4j.huobi.websocket.impl;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;

import io.github.xinyangpan.crypto4j.core.websocket.Heartbeat;

public class HuobiHeartbeat extends Heartbeat {

	public HuobiHeartbeat() {
		this.setTimeout(10);
	}

	@Override
	protected void sendPing() throws IOException {
		String pingMsg = String.format("{\"ping\": %s}", System.currentTimeMillis());
		session.sendMessage(new TextMessage(pingMsg));
	}

}
