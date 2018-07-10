package io.github.xinyangpan.crypto4j.exchange.example;

import io.github.xinyangpan.crypto4j.exchange.huobi.HuobiWsConnector;
import io.github.xinyangpan.crypto4j.exchange.huobi.impl.HuobiWsSubscriber;

public class HuobiConnectExample {
	
	public static void main(String[] args) throws InterruptedException {
		HuobiWsConnector connector = new HuobiWsConnector();
		HuobiWsSubscriber subscriber = connector.connect();
		subscriber.depth("btcusdt", "step0", System.out::println);
		subscriber.kline("btcusdt", "1day", System.out::println);
		// 
		Thread.sleep(Long.MAX_VALUE);
	}
	
}
