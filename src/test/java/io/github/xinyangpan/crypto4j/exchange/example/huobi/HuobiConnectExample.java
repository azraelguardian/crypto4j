package io.github.xinyangpan.crypto4j.exchange.example.huobi;

import io.github.xinyangpan.crypto4j.exchange.huobi.websocket.HuobiWsConnector;
import io.github.xinyangpan.crypto4j.exchange.huobi.websocket.impl.HuobiSubscriber;

public class HuobiConnectExample {
	
	public static void main(String[] args) throws InterruptedException {
		HuobiSubscriber huobiSubscriber = new HuobiSubscriber();
		huobiSubscriber.setDepthListener(System.out::println);
		huobiSubscriber.setKlineListener(System.out::println);
		// 
		HuobiWsConnector connector = new HuobiWsConnector(huobiSubscriber);
		connector.connect();
		// 
		huobiSubscriber.depth("btcusdt", "step0");
		huobiSubscriber.kline("btcusdt", "1day");
		// 
		Thread.sleep(Long.MAX_VALUE);
	}
	
}