package io.github.xinyangpan.crypto4j.huobi.websocket.impl;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import io.github.xinyangpan.crypto4j.core.websocket.Heartbeat;

public class HuobiHeartBeat extends Heartbeat {

	public HuobiHeartBeat() {
		this.setTimeout(10);
	}

	@Override
	protected void sendPing(WebSocketSession session) throws IOException {
		String pingMsg = String.format("{\"ping\": %s}", System.currentTimeMillis());
		session.sendMessage(new TextMessage(pingMsg));
	}

}
