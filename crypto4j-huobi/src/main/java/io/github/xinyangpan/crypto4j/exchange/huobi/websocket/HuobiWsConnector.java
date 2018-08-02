package io.github.xinyangpan.crypto4j.exchange.huobi.websocket;

import io.github.xinyangpan.crypto4j.common.websocket.BaseWsConnector;
import io.github.xinyangpan.crypto4j.common.websocket.failurehandler.FailureHandler;
import io.github.xinyangpan.crypto4j.common.websocket.failurehandler.FailureReconnect;
import io.github.xinyangpan.crypto4j.exchange.huobi.websocket.impl.HuobiSubscriber;
import io.github.xinyangpan.crypto4j.exchange.huobi.websocket.impl.HuobiWsHandler;
import io.github.xinyangpan.crypto4j.exchange.huobi.websocket.impl.HuobiWsHeartBeat;

public class HuobiWsConnector extends BaseWsConnector<HuobiWsHandler> {
	private static String DEFAULT_URL = "wss://api.huobi.pro/ws";

	public HuobiWsConnector(HuobiSubscriber huobiSubscriber) {
		this(huobiSubscriber, DEFAULT_URL);
	}

	public HuobiWsConnector(HuobiSubscriber huobiSubscriber, String url) {
		super(url, new HuobiWsHandler(huobiSubscriber));
		FailureHandler failureHandler = new FailureReconnect(this);
		this.wsHandler.setWsHeartbeat(new HuobiWsHeartBeat(failureHandler));
		this.wsHandler.setFailureHandler(failureHandler);
	}

	public HuobiWsConnector(HuobiSubscriber huobiSubscriber, String url, FailureHandler failureHandler) {
		super(url, new HuobiWsHandler(huobiSubscriber));
		this.wsHandler.setWsHeartbeat(new HuobiWsHeartBeat(failureHandler));
		this.wsHandler.setFailureHandler(failureHandler);
	}
	
}
