package io.github.xinyangpan.crypto4j.exchange.example.huobi.special;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.assertj.core.util.Lists;

import io.github.xinyangpan.crypto4j.core.RestProperties;
import io.github.xinyangpan.crypto4j.exchange.example.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.huobi.rest.HuobiRestService;

public class HuobiRestRate {
	private static final HuobiRestService huobiRestService;
	static {
		RestProperties restProperties = new RestProperties();
		restProperties.setRestBaseUrl("https://api.huobi.pro");
		restProperties.setRestKey(Crypto4jUtils.getSecret("huobi.key"));
		restProperties.setRestSecret(Crypto4jUtils.getSecret("huobi.secret"));
		// 
		huobiRestService = new HuobiRestService(restProperties);
	}
	private static final HuobiRestService huobiRestService1;
	static {
		RestProperties restProperties = new RestProperties();
		restProperties.setRestBaseUrl("https://api.huobi.pro");
		restProperties.setRestKey(Crypto4jUtils.getSecret("huobi1.key"));
		restProperties.setRestSecret(Crypto4jUtils.getSecret("huobi1.secret"));
		// 
		huobiRestService1 = new HuobiRestService(restProperties);
	}

	private static HuobiRestService[] huobiRestServices = { huobiRestService, huobiRestService1 };

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(100);
		List<Messager> output = Lists.newArrayList();
		for (int i = 0; i < 30; i++) {
//			Thread.sleep(150);
			int j = i;
			executorService.submit(() -> {
				HuobiRestService huobiRestService = huobiRestServices[j % 2];
				String key = huobiRestService.getRestProperties().getRestKey();
				Object message;
				PrintStream out;
				try {
					message = huobiRestService.queryOrder("11717547166");
					out = System.out;
				} catch (Exception e) {
					out = System.err;
					message = e.getMessage();
					e.printStackTrace();
				}
				Messager messager = new Messager(out, String.format("%s [%s/%s] - %s", j + 1, LocalDateTime.now(), key, message));
				output.add(messager);
				messager.println();
			});
		}
		executorService.shutdown();
//		Thread.sleep(1000 * 2);
//		System.out.println("=====================");
//		for (Messager messager : output) {
//			messager.println();
//		}
	}

}
