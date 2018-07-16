package io.github.xinyangpan.crypto4j.exchange.example;

import io.github.xinyangpan.crypto4j.exchange.binance.rest.BinanceUserStreamService;
import io.github.xinyangpan.crypto4j.exchange.binance.rest.dto.ListenKey;
import io.github.xinyangpan.crypto4j.exchange.binance.websocket.BinanceUserStreamWsConnector;
import io.github.xinyangpan.crypto4j.exchange.binance.websocket.impl.BinanceSubscriber;

public class BinanceUserStreamConnectExample {

	public static void main(String[] args) throws InterruptedException {
		// 
		BinanceUserStreamService binanceUserStreamService = new BinanceUserStreamService();
		ListenKey listenKey = binanceUserStreamService.start();
		//
		BinanceSubscriber subscription = new BinanceSubscriber();
		subscription.setAccountInfoListener(System.out::println);
		subscription.setExecutionReportListener(System.out::println);
		// 
		BinanceUserStreamWsConnector connector = new BinanceUserStreamWsConnector(subscription, listenKey);
		connector.connect();
		// 
		Thread.sleep(Long.MAX_VALUE);
	}

}
