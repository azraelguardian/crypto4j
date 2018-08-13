package io.github.xinyangpan.crypto4j.exchange.example.huobi;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import io.github.xinyangpan.crypto4j.core.RestProperties;
import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.huobi.dto.common.HuobiWsResponse;
import io.github.xinyangpan.crypto4j.huobi.dto.market.kline.Kline;
import io.github.xinyangpan.crypto4j.huobi.dto.market.kline.KlineRequest;
import io.github.xinyangpan.crypto4j.huobi.rest.HuobiRestService;
import io.github.xinyangpan.crypto4j.huobi.websocket.HuobiManager;
import io.github.xinyangpan.crypto4j.huobi.websocket.impl.HuobiSubscriber;
import io.github.xinyangpan.crypto4j.huobi.websocket.impl.HuobiSync;

public class HuobiConnectExample {

	public static HuobiRestService huobiRestService() {
		RestProperties restProperties = new RestProperties();
		restProperties.setRestBaseUrl("https://api.huobi.pro");
		restProperties.setRestKey(Crypto4jUtils.getSecret("huobi.key"));
		restProperties.setRestSecret(Crypto4jUtils.getSecret("huobi.secret"));
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
		//		huobiSubscriber.kline("btcusdt", "1day");

		List<String> symbols = huobiRestService().symbols().fethData().stream()//
			.filter(symbol -> !symbol.getSymbol().equals("bt1btc") && !symbol.getSymbol().equals("bt2btc"))//
			.map(symbol -> symbol.getSymbol())//
			.collect(Collectors.toList());

		// 
		HuobiManager connector = new HuobiManager();
		connector.setUrl("wss://api.huobi.pro/ws");
		connector.setSubscriber(huobiSubscriber);
		connector.connect();
		Thread.sleep(3000);
		//		huobiSubscriber.kline("btcusdt", "1min");
//		test(huobiSubscriber, symbols);
		test1(huobiSubscriber, symbols);
		// 
		Thread.sleep(Long.MAX_VALUE);
	}

	public static void test1(HuobiSubscriber huobiSubscriber, List<String> symbols) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		for (String symbol : symbols) {
			KlineRequest klineRequest = new KlineRequest(symbol, "1min");
			klineRequest.setFrom(ZonedDateTime.now().minusMinutes(2).toEpochSecond());
			klineRequest.setTo(ZonedDateTime.now().toEpochSecond());
//			huobiSubscriber.kline(symbol, "1min");
//			huobiSubscriber.request(klineRequest);
			executorService.execute(() -> {
				try {
					CompletableFuture<HuobiWsResponse<?>> request = huobiSubscriber.request(klineRequest);
					request.whenComplete((t, e) -> System.out.println(t));
//					HuobiWsResponse<?> huobiWsResponse = request.get();
//					System.out.println(huobiWsResponse);
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
//			HuobiWsResponse<List<Kline>> response = huobiSubscriber.request(klineRequest);
//			System.out.println(response.fethData());
		}
	}

	public static void test(HuobiSubscriber huobiSubscriber, List<String> symbols) throws InterruptedException, ExecutionException {
		ForkJoinPool forkJoinPool = new ForkJoinPool(1);
		List<Kline> klines = forkJoinPool.submit(() -> {
			return symbols.parallelStream()//
				.map(symbol -> {
					try {
						KlineRequest klineRequest = new KlineRequest(symbol, "1min");
						klineRequest.setFrom(ZonedDateTime.now().minusMinutes(1).toEpochSecond());
						klineRequest.setTo(ZonedDateTime.now().toEpochSecond());
						HuobiWsResponse<List<Kline>> response = huobiSubscriber.requestSync(klineRequest);
						return response.fethData().get(0);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.err.println(symbol);
						return null;
					}
				})//
				.collect(Collectors.toList());
		}).get();
		System.out.println(klines.size());
	}

}
