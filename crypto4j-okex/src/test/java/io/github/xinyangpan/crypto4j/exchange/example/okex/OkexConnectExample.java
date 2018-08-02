package io.github.xinyangpan.crypto4j.exchange.example.okex;

import io.github.xinyangpan.crypto4j.okex.websocket.OkexWsConnector;
import io.github.xinyangpan.crypto4j.okex.websocket.impl.OkexWsSubscriber;

public class OkexConnectExample {
	
	public static void main(String[] args) throws InterruptedException {
		OkexWsSubscriber subscriber = new OkexWsSubscriber();
		subscriber.setDepthListener(System.out::println);
		subscriber.setTickerListener(System.out::println);
		
		OkexWsConnector connector = new OkexWsConnector(subscriber);
		connector.connect();
		subscriber.depth("btc_usdt", 20);
//		subscriber.ticker("bch_btc");
//		subscriber.ticker("ltc_btc");
		// 
		Thread.sleep(Long.MAX_VALUE);
	}

}
