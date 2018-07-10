package io.github.xinyangpan.crypto4j.exchange.okex;

import io.github.xinyangpan.crypto4j.core.BaseWsConnector;
import io.github.xinyangpan.crypto4j.core.heartbeat.Heartbeat;
import io.github.xinyangpan.crypto4j.exchange.okex.impl.OkexWsHandler;
import io.github.xinyangpan.crypto4j.exchange.okex.impl.OkexWsHeartbeatHandler;
import io.github.xinyangpan.crypto4j.exchange.okex.impl.OkexWsSubscriberImpl;

public class OkexWsConnector extends BaseWsConnector<OkexWsSubscriber, OkexWsHandler> implements OkexWsHeartbeatHandler {
	private static String DEFAULT_URL = "wss://real.okex.com:10441/websocket";

	public OkexWsConnector() {
		super(DEFAULT_URL, new OkexWsHandler());
		this.wsHandler.setHeartbeat(new Heartbeat(this));
	}

	@Override
	protected OkexWsSubscriber createSubscriber(OkexWsHandler wsHandler) {
		return new OkexWsSubscriberImpl(wsHandler);
	}
	
	@Override
	public void pingTimeout() {
		this.reconnect();
	}
	
}
