package io.github.xinyangpan.crypto4j.exchange.example.binance;

import io.github.xinyangpan.crypto4j.binance.websocket.BinanceMarketManager;
import io.github.xinyangpan.crypto4j.binance.websocket.impl.BinanceSubscriber;
import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;

public class WsMarketExample {

	public static BinanceSubscriber binanceSubscriber() {
		BinanceSubscriber binanceSubscriber = new BinanceSubscriber();
		binanceSubscriber.depthAndTicker(5, "btcusdt");
//		binanceSubscriber.kline("btcusdt", "1m");
		binanceSubscriber.setDepthListener(Crypto4jUtils.noOp());
		binanceSubscriber.setTickerListener(Crypto4jUtils.noOp());
		binanceSubscriber.setAccountInfoListener(Crypto4jUtils.noOp());
		binanceSubscriber.setExecutionReportListener(Crypto4jUtils.noOp());
		return binanceSubscriber;
	}

	public static void main(String[] args) throws InterruptedException {
		BinanceSubscriber binanceSubscriber = binanceSubscriber();
		// 
		String url = binanceSubscriber.getUrl("wss://stream.binance.com:9443/stream?streams=");
		BinanceMarketManager connector = new BinanceMarketManager(url);
		connector.setSubscriber(binanceSubscriber);
		connector.connect();
		
		Thread.sleep(Long.MAX_VALUE);
	}
}
