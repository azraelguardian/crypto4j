package io.github.xinyangpan.crypto4j.exchange.example;

import io.github.xinyangpan.crypto4j.exchange.binance.BinanceWsConnector;
import io.github.xinyangpan.crypto4j.exchange.binance.impl.BinanceSubscription;

public class BinanceConnectExample {

	public static void main(String[] args) throws InterruptedException {
		BinanceSubscription subscription = new BinanceSubscription()//
			.depthAndTicker(20, "bnbbtc")//
			.depthListener(System.out::println)//
			.tickerListener(System.out::println);
		// 
		BinanceWsConnector connector = new BinanceWsConnector(subscription);
		connector.connect();
		// 
		Thread.sleep(Long.MAX_VALUE);
	}

}
