package io.github.xinyangpan.crypto4j.exchange.huobi;

import io.github.xinyangpan.crypto4j.core.BaseWsConnector;
import io.github.xinyangpan.crypto4j.exchange.huobi.impl.HuobiSubscriber;
import io.github.xinyangpan.crypto4j.exchange.huobi.impl.HuobiWsHandler;

public class HuobiWsConnector extends BaseWsConnector<HuobiWsHandler> {
	private static String DEFAULT_URL = "wss://api.huobi.pro/ws";

	public HuobiWsConnector(HuobiSubscriber huobiSubscriber) {
		super(DEFAULT_URL, new HuobiWsHandler(huobiSubscriber));
	}

}
