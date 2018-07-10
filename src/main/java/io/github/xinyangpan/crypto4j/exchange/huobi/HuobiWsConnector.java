package io.github.xinyangpan.crypto4j.exchange.huobi;

import io.github.xinyangpan.crypto4j.core.BaseWsConnector;
import io.github.xinyangpan.crypto4j.exchange.huobi.impl.HuobiWsHandler;
import io.github.xinyangpan.crypto4j.exchange.huobi.impl.HuobiWsSubscriberImpl;

public class HuobiWsConnector extends BaseWsConnector<HuobiWsSubscriber, HuobiWsHandler> {
	private static String DEFAULT_URL = "wss://api.huobi.pro/ws";

	public HuobiWsConnector() {
		super(DEFAULT_URL, new HuobiWsHandler());
		this.wsHandler.setStandardPingHeartbeatHandler(this::reconnect);
	}

	@Override
	protected HuobiWsSubscriber createSubscriber(HuobiWsHandler wsHandler) {
		return new HuobiWsSubscriberImpl(wsHandler);
	}

}
