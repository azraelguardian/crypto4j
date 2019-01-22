package io.github.xinyangpan.crypto4j.exchange.example.huobi;

import java.util.concurrent.ExecutionException;

import io.github.xinyangpan.crypto4j.core.RestProperties;
import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.huobi.dto.auth.Auth;
import io.github.xinyangpan.crypto4j.huobi.rest.HuobiRestService;
import io.github.xinyangpan.crypto4j.huobi.websocket.HuobiManager;
import io.github.xinyangpan.crypto4j.huobi.websocket.impl.HuobiSubscriber;
import io.github.xinyangpan.crypto4j.huobi.websocket.impl.HuobiSync;

public class HuobiConnectExample {

	public static HuobiRestService huobiRestService() {
		RestProperties restProperties = new RestProperties();
		restProperties.setRestBaseUrl("https://api.huobi.pro");
		restProperties.setRestKey(Crypto4jUtils.getSecret("huobi-dev.key"));
		restProperties.setRestSecret(Crypto4jUtils.getSecret("huobi-dev.secret"));
		return new HuobiRestService(restProperties);
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		HuobiSubscriber huobiSubscriber = new HuobiSubscriber();
		huobiSubscriber.setDepthListener(Crypto4jUtils.noOp());
		huobiSubscriber.setKlineListener(Crypto4jUtils.noOp());
		huobiSubscriber.setTickerListener(Crypto4jUtils.noOp());
		huobiSubscriber.setKlineResponse(System.out::println);
		huobiSubscriber.setHuobiSync(new HuobiSync());
//		huobiSubscriber.depth("btcusdt", "step0");
		Auth auth = Auth.auth(Crypto4jUtils.getSecret("huobi-dev.key"), Crypto4jUtils.getSecret("huobi-dev.secret"));
		huobiSubscriber.send(auth);
		// 
		HuobiManager connector = new HuobiManager();
		connector.setUrl("wss://api.huobi.pro/ws/v1");
		connector.setSubscriber(huobiSubscriber);
		connector.connect();
		// 
		Thread.sleep(Long.MAX_VALUE);
	}


}
