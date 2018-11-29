package io.github.xinyangpan.crypto4j.chainup.websocket;

import java.time.LocalDateTime;

import io.github.xinyangpan.crypto4j.chainup.websocket.impl.ChainupSubscriber1;
import io.github.xinyangpan.crypto4j.core.websocket.WebSocketManager;

public class ChainupManager extends WebSocketManager<ChainupSubscriber1> {
	private static String DEFAULT_URL = "wss://ws.hiex.pro/kline-api/ws";

	public ChainupManager() {
		this.setUrl(DEFAULT_URL);
		this.setName(String.format("Chainup<%s>", LocalDateTime.now()));
	}

}
