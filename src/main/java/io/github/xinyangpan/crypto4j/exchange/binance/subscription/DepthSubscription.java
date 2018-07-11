package io.github.xinyangpan.crypto4j.exchange.binance.subscription;

public class DepthSubscription extends BaseStreamSubscription {
	
	public DepthSubscription(String symbol, int level) {
		this.streamName = String.format("%s@depth%s", symbol, level);
	}
	
}
