package io.github.xinyangpan.crypto4j.exchange.example.binance;

import io.github.xinyangpan.crypto4j.exchange.binance.websocket.BinanceUserStreamWsConnector;
import io.github.xinyangpan.crypto4j.exchange.example.binance.util.BinanceTestUtils;

public class WsUserStreamExample {

	public static void main(String[] args) throws InterruptedException {
		// 
		BinanceUserStreamWsConnector connector = BinanceTestUtils.binanceService().userStream();
		connector.connect();
		// 
		Thread.sleep(Long.MAX_VALUE);
	}

}
