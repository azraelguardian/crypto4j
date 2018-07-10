package io.github.xinyangpan.crypto4j.exchange.huobi;

public class HuobiConnectSample {
	
	public static void main(String[] args) throws InterruptedException {
		HuobiWsConnector connector = new HuobiWsConnector();
		HuobiWsSubscriber subscriber = connector.connect();
		subscriber.marketDepth("btcusdt", "step0", System.out::println);
		subscriber.kline("btcusdt", "1day", System.out::println);
		// 
		Thread.sleep(Long.MAX_VALUE);
	}
	
}
