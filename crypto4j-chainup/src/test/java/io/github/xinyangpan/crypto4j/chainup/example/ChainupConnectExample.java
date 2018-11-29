package io.github.xinyangpan.crypto4j.chainup.example;

import java.util.concurrent.ExecutionException;

import io.github.xinyangpan.crypto4j.chainup.websocket.ChainupManager;
import io.github.xinyangpan.crypto4j.chainup.websocket.impl.ChainupSubscriber1;

public class ChainupConnectExample {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ChainupSubscriber1 chainupSubscriber1 = new ChainupSubscriber1();
		chainupSubscriber1.setDepthListener(System.out::println);
		chainupSubscriber1.setTradeListener(System.out::println);
		chainupSubscriber1.setTickListener(System.out::println);
		chainupSubscriber1.setDepthListener(depth -> {System.out.println(String.format("ask size=%s, bid size=%s", depth.getTick().getAsks().size(), depth.getTick().getBids().size()));});
//		chainupSubscriber.tick("btcusdt");
//		chainupSubscriber.trade("btcusdt");
		chainupSubscriber1.depth("btcusdt", 5);
		// 
		ChainupManager connector = new ChainupManager();
		// 54.95.129.249
		connector.setUrl("ws://dev5ws.chaindown.com/kline-api/ws");
//		connector.setUrl("wss://ws.hiex.pro/kline-api/ws");
		connector.setSubscriber(chainupSubscriber1);
		connector.connect();
		// 
		Thread.sleep(Long.MAX_VALUE);
	}

}
