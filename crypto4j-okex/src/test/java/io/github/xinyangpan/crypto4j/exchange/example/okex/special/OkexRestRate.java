package io.github.xinyangpan.crypto4j.exchange.example.okex.special;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.github.xinyangpan.crypto4j.core.RestProperties;
import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.okex.rest.OkexRestService;

public class OkexRestRate {
	private static OkexRestService okexRestService;
	static {
		RestProperties restProperties = new RestProperties();
		restProperties.setRestBaseUrl("https://www.okex.com");
		restProperties.setRestKey(Crypto4jUtils.getSecret("okex.key"));
		restProperties.setRestSecret(Crypto4jUtils.getSecret("okex.secret"));
		// 
		okexRestService = new OkexRestService(restProperties);
	}
	private static OkexRestService okexRestServiceSean;
	static {
		RestProperties restProperties = new RestProperties();
		restProperties.setRestBaseUrl("https://www.okex.com");
		restProperties.setRestKey(Crypto4jUtils.getSecret("okex_sean.key"));
		restProperties.setRestSecret(Crypto4jUtils.getSecret("okex_sean.secret"));
		// 
		okexRestServiceSean = new OkexRestService(restProperties);
	}
	private static OkexRestService okexRestServiceSean1;
	static {
		RestProperties restProperties = new RestProperties();
		restProperties.setRestBaseUrl("https://www.okex.com");
		restProperties.setRestKey(Crypto4jUtils.getSecret("okex_sean1.key"));
		restProperties.setRestSecret(Crypto4jUtils.getSecret("okex_sean1.secret"));
		// 
		okexRestServiceSean1 = new OkexRestService(restProperties);
	}
	private static OkexRestService[] okexRestServices = { okexRestService, okexRestServiceSean, okexRestServiceSean1 };

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(100);
		for (int i = 0; i < 100; i++) {
			Thread.sleep(250);
			int j = i;
			executorService.submit(() -> {
				OkexRestService okexRestService = okexRestServices[j % 3];
				String key = okexRestService.getRestProperties().getRestKey();
				Object message;
				PrintStream out;
				try {
					message = okexRestService.userinfo();
					out = System.out;
				} catch (Exception e) {
					message = e.getMessage();
					e.printStackTrace();
					out = System.err;
				}
				out.println(String.format("%s [%s/%s] - %s", j + 1, LocalDateTime.now(), key, message));
			});
		}
		executorService.shutdown();
	}

}
