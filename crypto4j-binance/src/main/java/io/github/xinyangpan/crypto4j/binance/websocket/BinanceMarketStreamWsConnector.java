package io.github.xinyangpan.crypto4j.binance.websocket;

import io.github.xinyangpan.crypto4j.binance.BinanceProperties;
import io.github.xinyangpan.crypto4j.binance.websocket.impl.BinanceSubscriber;
import io.github.xinyangpan.crypto4j.binance.websocket.impl.BinanceWsHandler;
import io.github.xinyangpan.crypto4j.binance.websocket.impl.BinanceWsHeartBeat;
import io.github.xinyangpan.crypto4j.core.websocket.BaseWsConnector;
import io.github.xinyangpan.crypto4j.core.websocket.failurehandler.FailureHandler;
import io.github.xinyangpan.crypto4j.core.websocket.failurehandler.FailureReconnect;
import io.github.xinyangpan.crypto4j.core.websocket.heartbeat.WsHeartbeat;

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
