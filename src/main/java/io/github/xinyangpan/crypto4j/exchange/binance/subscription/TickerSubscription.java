package io.github.xinyangpan.crypto4j.exchange.binance.subscription;

public class TickerSubscription extends BaseStreamSubscription {
	
	public TickerSubscription(String symbol) {
		this.streamName = String.format("%s@ticker", symbol);
	}
	
}
