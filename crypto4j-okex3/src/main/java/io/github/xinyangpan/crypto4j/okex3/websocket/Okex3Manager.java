package io.github.xinyangpan.crypto4j.okex3.websocket;

import java.time.LocalDateTime;

import io.github.xinyangpan.crypto4j.core.websocket.WebSocketManager;
import io.github.xinyangpan.crypto4j.okex3.websocket.impl.Okex3Heartbeat;
import io.github.xinyangpan.crypto4j.okex3.websocket.impl.Okex3Subscriber;

public class Okex3Manager extends WebSocketManager<Okex3Subscriber> {
	private static String DEFAULT_URL = "wss://real.okex.com:10442/ws/v3";

	public Okex3Manager() {
		this.setUrl(DEFAULT_URL);
		this.setName(String.format("Okex<%s>", LocalDateTime.now()));
		this.setHeartbeat(new Okex3Heartbeat());
	}
}
