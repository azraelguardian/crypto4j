package io.github.xinyangpan.crypto4j.exchange.huobi.websocket;

import io.github.xinyangpan.crypto4j.common.websocket.BaseWsConnector;
import io.github.xinyangpan.crypto4j.exchange.huobi.websocket.impl.HuobiSubscriber;
import io.github.xinyangpan.crypto4j.exchange.huobi.websocket.impl.HuobiWsHandler;

public class HuobiWsConnector extends BaseWsConnector<HuobiWsHandler> {
	private static String DEFAULT_URL = "wss://api.huobi.pro/ws";

	public HuobiWsConnector(HuobiSubscriber huobiSubscriber) {
		super(DEFAULT_URL, new HuobiWsHandler(huobiSubscriber));
	}

}
