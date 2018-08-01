package io.github.xinyangpan.crypto4j.exchange.binance.websocket;

import io.github.xinyangpan.crypto4j.common.websocket.BaseWsConnector;
import io.github.xinyangpan.crypto4j.common.websocket.failurehandler.FailureHandler;
import io.github.xinyangpan.crypto4j.common.websocket.failurehandler.FailureReconnect;
import io.github.xinyangpan.crypto4j.common.websocket.heartbeat.WsHeartbeat;
import io.github.xinyangpan.crypto4j.exchange.binance.BinanceProperties;
import io.github.xinyangpan.crypto4j.exchange.binance.websocket.impl.BinanceSubscriber;
import io.github.xinyangpan.crypto4j.exchange.binance.websocket.impl.BinanceWsHandler;
import io.github.xinyangpan.crypto4j.exchange.binance.websocket.impl.BinanceWsHeartBeat;

public class BinanceMarketStreamWsConnector extends BaseWsConnector<BinanceWsHandler> {

	public BinanceMarketStreamWsConnector(BinanceSubscriber binanceSubscriber, BinanceProperties binanceProperties) {
		this(binanceSubscriber, binanceProperties.getWebsocketMarketBaseUrl());
	}

	public BinanceMarketStreamWsConnector(BinanceSubscriber binanceSubscriber, String websocketMarketBaseUrl) {
		super(websocketMarketBaseUrl + binanceSubscriber.getUrlParameter(), new BinanceWsHandler(binanceSubscriber));
		FailureHandler failureHandler = new FailureReconnect(this);
		WsHeartbeat wsHeartbeat = new BinanceWsHeartBeat(failureHandler);
		wsHeartbeat.setTimeout(10);
		this.wsHandler.setWsHeartbeat(wsHeartbeat);
		this.wsHandler.setFailureHandler(failureHandler);
	}

}
