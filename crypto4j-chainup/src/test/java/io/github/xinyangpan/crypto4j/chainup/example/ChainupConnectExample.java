package io.github.xinyangpan.crypto4j.chainup.example;

import java.util.concurrent.ExecutionException;

import io.github.xinyangpan.crypto4j.chainup.websocket.ChainupManager;
import io.github.xinyangpan.crypto4j.chainup.websocket.impl.ChainupSubscriber;

public class ChainupConnectExample {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ChainupSubscriber chainupSubscriber = new ChainupSubscriber();
		chainupSubscriber.setDepthListener(System.out::println);
		chainupSubscriber.setTradeListener(System.out::println);
		chainupSubscriber.setTickListener(System.out::println);
		chainupSubscriber.tick("btcusdt");
		chainupSubscriber.trade("btcusdt");
		chainupSubscriber.depth("btcusdt", 5);
		// 
		ChainupManager connector = new ChainupManager();
		// 54.95.129.249
		connector.setUrl("wss://ws.chaindown.com/kline-api/ws");
//		connector.setUrl("wss://ws.hiex.pro/kline-api/ws");
		connector.setSubscriber(chainupSubscriber);
		connector.connect();
		// 
		Thread.sleep(Long.MAX_VALUE);
	}

}
