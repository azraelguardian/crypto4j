package io.github.xinyangpan.crypto4j.exchange.example.binance;

import io.github.xinyangpan.crypto4j.exchange.binance.dto.rest.common.ListenKey;
import io.github.xinyangpan.crypto4j.exchange.binance.rest.BinanceUserStreamService;
import io.github.xinyangpan.crypto4j.exchange.example.binance.util.BinanceTestUtils;

public class RestUserStreamExample {

	public static void main(String[] args) throws InterruptedException {
		// 
		BinanceUserStreamService binanceUserStreamService = new BinanceUserStreamService(BinanceTestUtils.binanceProperties().getRestProperties());
		ListenKey listenKey = binanceUserStreamService.start();
		System.out.println(listenKey);
		binanceUserStreamService.keeplive(listenKey.getListenKey());
		binanceUserStreamService.close(listenKey.getListenKey());
	}

}
