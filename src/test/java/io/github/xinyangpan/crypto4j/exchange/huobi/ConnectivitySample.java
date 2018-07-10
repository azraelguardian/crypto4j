package io.github.xinyangpan.crypto4j.exchange.huobi;

import io.github.xinyangpan.crypto4j.exchange.huobi.core.HuobiWsConnector;

public class ConnectivitySample {
	
	public static void main(String[] args) throws InterruptedException {
		HuobiWsConnector connector = new HuobiWsConnector();
		HuobiWsSubscriber subscriber = connector.connect();
		subscriber.marketDepth("btcusdt", "step0", System.out::println);
		subscriber.kline("btcusdt", "1day", System.out::println);
		// 
		Thread.sleep(5*1000);
		connector.disconnect();
		Thread.sleep(Long.MAX_VALUE);
	}
	
}
