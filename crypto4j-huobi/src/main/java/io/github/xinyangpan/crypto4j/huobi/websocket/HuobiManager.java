package io.github.xinyangpan.crypto4j.huobi.websocket;

import io.github.xinyangpan.crypto4j.core.websocket.WebSocketManager;
import io.github.xinyangpan.crypto4j.huobi.websocket.impl.HuobiHeartBeat;
import io.github.xinyangpan.crypto4j.huobi.websocket.impl.HuobiHandler;

public class HuobiManager extends WebSocketManager {
	private static String DEFAULT_URL = "wss://api.huobi.pro/ws";

	public HuobiManager() {
		this.setUrl(DEFAULT_URL);
		this.setName("Huobi");
		this.handler = new HuobiHandler();
		this.setHeartbeat(new HuobiHeartBeat());
	}

}
