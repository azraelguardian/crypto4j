package io.github.xinyangpan.crypto4j.exchange.example.binance;

import io.github.xinyangpan.crypto4j.binance.websocket.BinanceMarketStreamWsConnector;
import io.github.xinyangpan.crypto4j.exchange.example.binance.util.BinanceTestUtils;

public class WsMarketExample {

	public static void main(String[] args) throws InterruptedException {
		// 
		BinanceMarketStreamWsConnector connector = BinanceTestUtils.binanceService().marketStream();
		connector.connect();
//		Thread.sleep(5 * 1000);
//		connector.reconnect();
		// 
		Thread.sleep(Long.MAX_VALUE);
	}

}
