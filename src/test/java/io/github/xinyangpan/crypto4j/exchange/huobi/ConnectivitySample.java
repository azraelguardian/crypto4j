package io.github.xinyangpan.crypto4j.exchange.huobi;

import io.github.xinyangpan.crypto4j.exchange.huobi.core.HuobiWsConnector;
import io.github.xinyangpan.crypto4j.exchange.huobi.core.HuobiWsHandler;
import io.github.xinyangpan.crypto4j.exchange.huobi.core.HuobiWsSubscriber;

public class ConnectivitySample {
	
	public static void main(String[] args) throws InterruptedException {
		HuobiWsHandler wsHandler = new HuobiWsHandler();
		HuobiWsConnector connector = new HuobiWsConnector();
		HuobiWsSubscriber subscriber = connector.connect(wsHandler);
		subscriber.marketDepth("btcusdt", "step0", null);
		// 
		Thread.sleep(Long.MAX_VALUE);
	}
	
}
