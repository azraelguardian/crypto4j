package io.github.xinyangpan.crypto4j.exchange.example;

import io.github.xinyangpan.crypto4j.exchange.binance.dto.rest.ListenKey;
import io.github.xinyangpan.crypto4j.exchange.binance.rest.BinanceUserStreamService;

public class BinanceUserStreamExample {

	public static void main(String[] args) throws InterruptedException {
		// 
		BinanceUserStreamService binanceUserStreamService = new BinanceUserStreamService();
		ListenKey listenKey = binanceUserStreamService.start();
		System.out.println(listenKey);
		binanceUserStreamService.keeplive(listenKey.getListenKey());
		binanceUserStreamService.close(listenKey.getListenKey());
	}

}
