package io.github.xinyangpan.crypto4j.exchange.example.binance.special;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.web.client.HttpClientErrorException;

import io.github.xinyangpan.crypto4j.binance.dto.rest.order.QueryOrderRequest;
import io.github.xinyangpan.crypto4j.binance.rest.BinanceRestService;
import io.github.xinyangpan.crypto4j.exchange.example.binance.util.BinanceTestUtils;

public class RestExample {

	private static final String BTCUSDT = "BTCUSDT";
	private static final BinanceRestService binanceRestService = BinanceTestUtils.binanceService().restService();

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(1000);
		for (int i = 0; i < 1; i++) {
			int j = i;
			executorService.submit(() -> {
				String key = binanceRestService.getRestProperties().getRestKey();
				Object message;
				PrintStream out;
				try {
					QueryOrderRequest queryOrderRequest = new QueryOrderRequest();
					queryOrderRequest.setOrderId(159604830L);
					queryOrderRequest.setSymbol(BTCUSDT);
					message = binanceRestService.queryOrder(queryOrderRequest);
					out = System.out;
				} catch (HttpClientErrorException e) {
					message = e.getMessage();
					e.printStackTrace();
					out = System.err;
//					if (e.getStatusCode().value() == 418) {
//						throw e;
//					}
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
