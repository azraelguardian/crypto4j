package io.github.xinyangpan.crypto4j.exchange.example.huobi;

import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.huobi.websocket.HuobiManager;
import io.github.xinyangpan.crypto4j.huobi.websocket.impl.HuobiSubscriber;

public class HuobiConnectExample {

	public static void main(String[] args) throws InterruptedException {
		HuobiSubscriber huobiSubscriber = new HuobiSubscriber();
		huobiSubscriber.setDepthListener(Crypto4jUtils.noOp());
		huobiSubscriber.setKlineListener(Crypto4jUtils.noOp());
		huobiSubscriber.setDepthListener(Crypto4jUtils.noOp());
		huobiSubscriber.depth("btcusdt", "step0");
//		huobiSubscriber.kline("btcusdt", "1day");
		
		// 
		HuobiManager connector = new HuobiManager();
		connector.setUrl("wss://api.huobi.pro/ws");
		connector.setSubscriber(huobiSubscriber);
		connector.connect();
//		huobiSubscriber.kline("btcusdt", "1min");
		// 
		Thread.sleep(Long.MAX_VALUE);
	}

}
