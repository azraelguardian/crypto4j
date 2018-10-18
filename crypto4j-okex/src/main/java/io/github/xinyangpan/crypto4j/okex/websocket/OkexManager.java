package io.github.xinyangpan.crypto4j.okex.websocket;

import java.time.LocalDateTime;

import io.github.xinyangpan.crypto4j.core.websocket.WebSocketManager;
import io.github.xinyangpan.crypto4j.okex.websocket.impl.OkexHeartbeat;
import io.github.xinyangpan.crypto4j.okex.websocket.impl.OkexSubscriber;

public class OkexManager extends WebSocketManager<OkexSubscriber> {
	// ?compress=true
	private static String DEFAULT_URL = "wss://real.okex.com:10441/websocket?compress=true";

	public OkexManager() {
		this.setUrl(DEFAULT_URL);
		this.setName(String.format("Okex<%s>", LocalDateTime.now()));
		this.setHeartbeat(new OkexHeartbeat());
	}

}
