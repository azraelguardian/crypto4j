package io.github.xinyangpan.crypto4j.exchange.example.binance.util;

import io.github.xinyangpan.crypto4j.binance.BinanceProperties;
import io.github.xinyangpan.crypto4j.binance.BinanceService;
import io.github.xinyangpan.crypto4j.binance.websocket.impl.BinanceSubscriber;
import io.github.xinyangpan.crypto4j.core.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.core.ExchangeUtils;
import io.github.xinyangpan.crypto4j.core.RestProperties;

public class BinanceTestUtils {

	public static BinanceService binanceService() {
		return new BinanceService(binanceSubscriber(), binanceProperties());
	}

	public static BinanceProperties binanceProperties() {
		RestProperties restProperties = new RestProperties();
		restProperties.setRestBaseUrl("https://api.binance.com");
		restProperties.setRestKey(Crypto4jUtils.getSecret("binance.key"));
		restProperties.setRestSecret(Crypto4jUtils.getSecret("binance.secret"));
		// 
		BinanceProperties binanceProperties = new BinanceProperties();
		binanceProperties.setWebsocketMarketBaseUrl("wss://stream.binance.com:9443/stream?streams=");
		binanceProperties.setWebsocketUserStreamBaseUrl("wss://stream.binance.com:9443/ws/");
		binanceProperties.setRestProperties(restProperties);
		return binanceProperties;
	}

	public static BinanceSubscriber binanceSubscriber() {
		BinanceSubscriber binanceSubscriber = new BinanceSubscriber();
		binanceSubscriber.depthAndTicker(5, "btcusdt");
		binanceSubscriber.setDepthListener(ExchangeUtils.noOp());
		binanceSubscriber.setTickerListener(ExchangeUtils.noOp());
		binanceSubscriber.setAccountInfoListener(ExchangeUtils.noOp());
		binanceSubscriber.setExecutionReportListener(ExchangeUtils.noOp());
		return binanceSubscriber;
	}

}
