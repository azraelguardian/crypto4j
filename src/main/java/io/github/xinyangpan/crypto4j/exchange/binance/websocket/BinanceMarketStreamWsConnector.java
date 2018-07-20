package io.github.xinyangpan.crypto4j.exchange.binance.websocket;

import io.github.xinyangpan.crypto4j.core.websocket.BaseWsConnector;
import io.github.xinyangpan.crypto4j.exchange.binance.BinanceProperties;
import io.github.xinyangpan.crypto4j.exchange.binance.websocket.impl.BinanceSubscriber;
import io.github.xinyangpan.crypto4j.exchange.binance.websocket.impl.BinanceWsHandler;

public class BinanceMarketStreamWsConnector extends BaseWsConnector<BinanceWsHandler> {

	public BinanceMarketStreamWsConnector(BinanceSubscriber binanceSubscriber, BinanceProperties binanceProperties) {
		this(binanceSubscriber, binanceProperties.getWebsocketMarketBaseUrl());
	}

	public BinanceMarketStreamWsConnector(BinanceSubscriber binanceSubscriber, String websocketMarketBaseUrl) {
		super(websocketMarketBaseUrl + binanceSubscriber.getUrlParameter(), new BinanceWsHandler(binanceSubscriber));
	}

}
