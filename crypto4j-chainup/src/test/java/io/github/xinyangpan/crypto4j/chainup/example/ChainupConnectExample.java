package io.github.xinyangpan.crypto4j.chainup.example;

import java.util.concurrent.ExecutionException;

import io.github.xinyangpan.crypto4j.chainup.websocket.ChainupManager;
import io.github.xinyangpan.crypto4j.chainup.websocket.impl.ChainupSubscriber;
import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;

public class ChainupConnectExample {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ChainupSubscriber chainupSubscriber = new ChainupSubscriber();
		chainupSubscriber.setDepthListener(Crypto4jUtils.noOp());
		chainupSubscriber.setTradeListener(Crypto4jUtils.noOp());
		chainupSubscriber.trade("btcusdt");
//		chainupSubscriber.depth("btcusdt", 5);
		// 
		ChainupManager connector = new ChainupManager();
		// 54.95.129.249
		connector.setUrl("wss://ws.hiex.pro/kline-api/ws");
		connector.setSubscriber(chainupSubscriber);
		connector.connect();
		// 
		Thread.sleep(Long.MAX_VALUE);
	}

}
