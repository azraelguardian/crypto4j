package io.github.xinyangpan.crypto4j.okex.websocket;

import io.github.xinyangpan.crypto4j.core.websocket.BaseWsConnector;
import io.github.xinyangpan.crypto4j.core.websocket.failurehandler.FailureHandler;
import io.github.xinyangpan.crypto4j.core.websocket.failurehandler.FailureReconnect;
import io.github.xinyangpan.crypto4j.okex.websocket.impl.OkexWsHandler;
import io.github.xinyangpan.crypto4j.okex.websocket.impl.OkexWsHeartBeat;
import io.github.xinyangpan.crypto4j.okex.websocket.impl.OkexWsSubscriber;

public class OkexWsConnector extends BaseWsConnector<OkexWsHandler> {
	private static String DEFAULT_URL = "wss://real.okex.com:10441/websocket";

	public OkexWsConnector(OkexWsSubscriber okexWsSubscriber) {
		this(okexWsSubscriber, DEFAULT_URL);
	}

	public OkexWsConnector(OkexWsSubscriber okexWsSubscriber, String url) {
		super(url, new OkexWsHandler(okexWsSubscriber));
		FailureHandler failureHandler = new FailureReconnect(this);
		this.wsHandler.setWsHeartbeat(new OkexWsHeartBeat(failureHandler));
		this.wsHandler.setFailureHandler(failureHandler);
	}

	public OkexWsConnector(OkexWsSubscriber okexWsSubscriber, String url, FailureHandler failureHandler) {
		super(url, new OkexWsHandler(okexWsSubscriber));
		this.wsHandler.setWsHeartbeat(new OkexWsHeartBeat(failureHandler));
		this.wsHandler.setFailureHandler(failureHandler);
	}

}
