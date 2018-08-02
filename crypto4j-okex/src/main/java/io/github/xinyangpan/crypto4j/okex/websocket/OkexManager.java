package io.github.xinyangpan.crypto4j.okex.websocket;

import io.github.xinyangpan.crypto4j.core.websocket.WebSocketManager;
import io.github.xinyangpan.crypto4j.okex.websocket.impl.OkexHandler;
import io.github.xinyangpan.crypto4j.okex.websocket.impl.OkexHeartBeat;

public class OkexManager extends WebSocketManager {
	private static String DEFAULT_URL = "wss://real.okex.com:10441/websocket";

	public OkexManager() {
		this.setUrl(DEFAULT_URL);
		this.setName("Okex");
		this.handler = new OkexHandler();
		this.setHeartbeat(new OkexHeartBeat());
	}

}
