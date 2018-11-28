package io.github.xinyangpan.crypto4j.okex3.example;

import io.github.xinyangpan.crypto4j.core.RestProperties;
import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.okex3.rest.Okex3RestService;

public class Okex3RestExample {
	static final String BTCUSDT = "BTC-USDT";
	static Okex3RestService okex3RestService;
	
	static {
		RestProperties restProperties = new RestProperties();
		restProperties.setRestBaseUrl("https://www.okex.com");
		restProperties.setRestKey(Crypto4jUtils.getSecret("okex.key"));
		restProperties.setRestSecret(Crypto4jUtils.getSecret("okex.secret"));
		// 
		okex3RestService = new Okex3RestService(restProperties);
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println(okex3RestService.ticker());
	}

}
