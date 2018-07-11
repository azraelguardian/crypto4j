package io.github.xinyangpan.crypto4j.exchange.binance;

import io.github.xinyangpan.crypto4j.core.BaseWsConnector;
import io.github.xinyangpan.crypto4j.core.WsSubscriber;
import io.github.xinyangpan.crypto4j.exchange.binance.impl.BinanceWsHandler;
import io.github.xinyangpan.crypto4j.exchange.binance.subscription.BinanceSubscription;

public class BinanceWsConnector extends BaseWsConnector<WsSubscriber, BinanceWsHandler> {
	private static String DEFAULT_URL = "wss://stream.binance.com:9443/stream?streams=";

	public BinanceWsConnector(BinanceSubscription subscription) {
		super(DEFAULT_URL + subscription.getUrlParameter(), new BinanceWsHandler(subscription));
	}

	@Override
	protected WsSubscriber createSubscriber(BinanceWsHandler wsHandler) {
		return null;
	}

}
