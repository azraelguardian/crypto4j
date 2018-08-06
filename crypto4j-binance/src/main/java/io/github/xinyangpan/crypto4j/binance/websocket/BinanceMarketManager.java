package io.github.xinyangpan.crypto4j.binance.websocket;

import java.time.LocalDateTime;

import io.github.xinyangpan.crypto4j.binance.websocket.impl.BinanceSubscriber;
import io.github.xinyangpan.crypto4j.core.websocket.Heartbeat;
import io.github.xinyangpan.crypto4j.core.websocket.WebSocketManager;

public class BinanceMarketManager extends WebSocketManager<BinanceSubscriber> {

	public BinanceMarketManager() {
		this.setName("Binance");
		this.setHeartbeat(new Heartbeat());
	}

	public BinanceMarketManager(String url) {
		this.setUrl(url);
		this.setName(String.format("Binance<%s>", LocalDateTime.now()));
		this.setHeartbeat(new Heartbeat());
	}

}
