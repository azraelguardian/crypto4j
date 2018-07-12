package io.github.xinyangpan.crypto4j.exchange.binance;

import io.github.xinyangpan.crypto4j.core.BaseWsConnector;
import io.github.xinyangpan.crypto4j.exchange.binance.impl.BinanceSubscriber;
import io.github.xinyangpan.crypto4j.exchange.binance.impl.BinanceWsHandler;

public class BinanceWsConnector extends BaseWsConnector<BinanceWsHandler> {
	private static String DEFAULT_URL = "wss://stream.binance.com:9443/stream?streams=";

	public BinanceWsConnector(BinanceSubscriber subscription) {
		super(DEFAULT_URL + subscription.getUrlParameter(), new BinanceWsHandler(subscription));
	}

}
