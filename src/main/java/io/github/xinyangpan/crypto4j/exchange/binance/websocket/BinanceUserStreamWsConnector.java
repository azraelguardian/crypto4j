package io.github.xinyangpan.crypto4j.exchange.binance.websocket;

import io.github.xinyangpan.crypto4j.core.BaseWsConnector;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.rest.ListenKey;
import io.github.xinyangpan.crypto4j.exchange.binance.websocket.impl.BinanceSubscriber;
import io.github.xinyangpan.crypto4j.exchange.binance.websocket.impl.BinanceWsHandler;

public class BinanceUserStreamWsConnector extends BaseWsConnector<BinanceWsHandler> {
	private static String DEFAULT_URL = "wss://stream.binance.com:9443/ws/";

	public BinanceUserStreamWsConnector(BinanceSubscriber subscription, ListenKey listenKey) {
		super(DEFAULT_URL + listenKey.getListenKey(), new BinanceWsHandler(subscription));
	}

}
