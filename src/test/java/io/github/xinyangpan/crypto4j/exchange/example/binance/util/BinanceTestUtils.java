package io.github.xinyangpan.crypto4j.exchange.example.binance.util;

import io.github.xinyangpan.crypto4j.exchange.binance.BinanceService;
import io.github.xinyangpan.crypto4j.exchange.binance.BinanceProperties;
import io.github.xinyangpan.crypto4j.exchange.binance.websocket.impl.BinanceSubscriber;
import io.github.xinyangpan.crypto4j.exchange.example.Crypto4jUtils;

public class BinanceTestUtils {

	public static BinanceService binanceService() {
		return new BinanceService(binanceSubscriber(), binanceProperties());
	}

	public static BinanceProperties binanceProperties() {
		BinanceProperties binanceProperties = new BinanceProperties();
		binanceProperties.setRestBaseUrl("https://api.binance.com");
		binanceProperties.setRestKey("RRNGWvMoqYvvTRjrVMzc54coOupxm7W5VqCzT60y5aENk6fM9mikjEqJ4KyWkDnt");
		binanceProperties.setRestSecret(Crypto4jUtils.getSecret("binance.secret"));
		binanceProperties.setWebsocketMarketBaseUrl("wss://stream.binance.com:9443/stream?streams=");
		binanceProperties.setWebsocketUserStreamBaseUrl("wss://stream.binance.com:9443/ws/");
		return binanceProperties;
	}

	public static BinanceSubscriber binanceSubscriber() {
		BinanceSubscriber binanceSubscriber = new BinanceSubscriber();
		binanceSubscriber.depthAndTicker(5, "ethbtc");
		binanceSubscriber.setDepthListener(System.out::println);
		binanceSubscriber.setTickerListener(System.out::println);
		binanceSubscriber.setAccountInfoListener(System.out::println);
		binanceSubscriber.setExecutionReportListener(System.out::println);
		return binanceSubscriber;
	}

}
