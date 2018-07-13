package io.github.xinyangpan.crypto4j.exchange.example;

import io.github.xinyangpan.crypto4j.exchange.binance.websocket.BinanceWsConnector;
import io.github.xinyangpan.crypto4j.exchange.binance.websocket.impl.BinanceSubscriber;

public class BinanceConnectExample {

	public static void main(String[] args) throws InterruptedException {
		BinanceSubscriber subscription = new BinanceSubscriber()//
			.depthAndTicker(20, "bnbbtc")//
			.depthListener(System.out::println)//
			.tickerListener(System.out::println);
		// 
		BinanceWsConnector connector = new BinanceWsConnector(subscription);
		connector.connect();
		Thread.sleep(5*1000);
		connector.reconnect();
		// 
		Thread.sleep(Long.MAX_VALUE);
	}

}
