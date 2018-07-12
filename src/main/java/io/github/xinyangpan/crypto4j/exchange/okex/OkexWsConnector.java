package io.github.xinyangpan.crypto4j.exchange.okex;

import io.github.xinyangpan.crypto4j.core.BaseWsConnector;
import io.github.xinyangpan.crypto4j.exchange.okex.impl.OkexWsHandler;
import io.github.xinyangpan.crypto4j.exchange.okex.impl.OkexWsHeartBeat;
import io.github.xinyangpan.crypto4j.exchange.okex.impl.OkexWsSubscriber;

public class OkexWsConnector extends BaseWsConnector<OkexWsHandler> {
	private static String DEFAULT_URL = "wss://real.okex.com:10441/websocket";

	public OkexWsConnector(OkexWsSubscriber okexWsSubscriber) {
		super(DEFAULT_URL, new OkexWsHandler(okexWsSubscriber));
		this.wsHandler.setWsHeartbeat(new OkexWsHeartBeat(this));
	}

}
