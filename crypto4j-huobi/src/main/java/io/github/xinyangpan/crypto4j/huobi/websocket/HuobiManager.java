package io.github.xinyangpan.crypto4j.huobi.websocket;

import java.time.LocalDateTime;

import io.github.xinyangpan.crypto4j.core.websocket.WebSocketManager;
import io.github.xinyangpan.crypto4j.huobi.websocket.impl.HuobiHeartbeat;
import io.github.xinyangpan.crypto4j.huobi.websocket.impl.HuobiSubscriber;

public class HuobiManager extends WebSocketManager<HuobiSubscriber> {
	private static String DEFAULT_URL = "wss://api.huobi.pro/ws";

	public HuobiManager() {
		this.setUrl(DEFAULT_URL);
		this.setName(String.format("Huobi<%s>", LocalDateTime.now()));
		this.setHeartbeat(new HuobiHeartbeat());
	}

}
