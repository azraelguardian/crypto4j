package io.github.xinyangpan.crypto4j.exchange.example.okex;

import io.github.xinyangpan.crypto4j.okex.websocket.OkexManager;
import io.github.xinyangpan.crypto4j.okex.websocket.impl.OkexSubscriber;

public class OkexConnectExample {

	public static void main(String[] args) throws InterruptedException {
		OkexSubscriber subscriber = new OkexSubscriber();
		subscriber.setDepthListener(System.out::println);
		subscriber.setTickerListener(System.out::println);
		subscriber.depth("btc_usdt", 20);
		subscriber.ticker("btc_usdt");

		OkexManager connector = new OkexManager();
		connector.setSubscriber(subscriber);
		connector.connect();
		// 
		Thread.sleep(Long.MAX_VALUE);
	}

}
