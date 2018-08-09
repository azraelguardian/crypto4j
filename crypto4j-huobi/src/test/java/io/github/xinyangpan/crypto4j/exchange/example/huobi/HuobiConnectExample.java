package io.github.xinyangpan.crypto4j.exchange.example.huobi;

import java.time.ZonedDateTime;

import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.huobi.dto.market.kline.KlineRequest;
import io.github.xinyangpan.crypto4j.huobi.websocket.HuobiManager;
import io.github.xinyangpan.crypto4j.huobi.websocket.impl.HuobiSubscriber;

public class HuobiConnectExample {

	public static void main(String[] args) throws InterruptedException {
		HuobiSubscriber huobiSubscriber = new HuobiSubscriber();
		huobiSubscriber.setDepthListener(Crypto4jUtils.noOp());
		huobiSubscriber.setKlineListener(Crypto4jUtils.noOp());
		huobiSubscriber.setTickerListener(Crypto4jUtils.noOp());
		huobiSubscriber.setKlineResponse(System.out::println);
//		huobiSubscriber.depth("btcusdt", "step0");
//		huobiSubscriber.kline("btcusdt", "1day");
		
		// 
		HuobiManager connector = new HuobiManager();
		connector.setUrl("wss://api.huobi.pro/ws");
		connector.setSubscriber(huobiSubscriber);
		connector.connect();
//		huobiSubscriber.kline("btcusdt", "1min");
		KlineRequest klineRequest = new KlineRequest("btcusdt", "1min");
		klineRequest.setFrom(ZonedDateTime.now().minusMinutes(1).toEpochSecond());
		klineRequest.setTo(ZonedDateTime.now().toEpochSecond());
		huobiSubscriber.send(klineRequest);
		// 
		Thread.sleep(Long.MAX_VALUE);
	}

}
