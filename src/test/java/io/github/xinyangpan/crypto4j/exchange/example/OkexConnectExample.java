package io.github.xinyangpan.crypto4j.exchange.example;

import io.github.xinyangpan.crypto4j.exchange.okex.OkexWsConnector;
import io.github.xinyangpan.crypto4j.exchange.okex.OkexWsSubscriber;

public class OkexConnectExample {
	
	public static void main(String[] args) throws InterruptedException {
		OkexWsConnector connector = new OkexWsConnector();
		OkexWsSubscriber subscriber = connector.connect();
//		subscriber.depth("bch_btc", 20, System.out::println);
		subscriber.ticker("bch_btc", System.out::println);
		subscriber.ticker("ltc_btc", System.out::println);
		// 
		Thread.sleep(Long.MAX_VALUE);
	}
	
}
