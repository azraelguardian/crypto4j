package io.github.xinyangpan.crypto4j.exchange.example.huobi;

import io.github.xinyangpan.crypto4j.core.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.huobi.websocket.HuobiWsConnector;
import io.github.xinyangpan.crypto4j.huobi.websocket.impl.HuobiSubscriber;

public class HuobiConnectExample {

	public static void main(String[] args) throws InterruptedException {
		HuobiSubscriber huobiSubscriber = new HuobiSubscriber();
		huobiSubscriber.setDepthListener(Crypto4jUtils.noOp());
		huobiSubscriber.setKlineListener(Crypto4jUtils.noOp());
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
