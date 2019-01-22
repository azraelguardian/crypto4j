package io.github.xinyangpan;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import io.github.xinyangpan.crypto4j.binance.BinanceProperties;
import io.github.xinyangpan.crypto4j.binance.BinanceService;
import io.github.xinyangpan.crypto4j.binance.dto.rest.order.QueryAllOrdersRequset;
import io.github.xinyangpan.crypto4j.binance.dto.rest.order.QueryAllOrdersResponse;
import io.github.xinyangpan.crypto4j.binance.rest.BinanceRestService;
import io.github.xinyangpan.crypto4j.binance.websocket.impl.BinanceSubscriber;
import io.github.xinyangpan.crypto4j.core.RestProperties;
import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;

public class BinanceCheckUtil {
	public static BinanceRestService binanceRestService() {
		return new BinanceService(binanceSubscriber(), binanceProperties()).restService();
	}

	public static BinanceProperties binanceProperties() {
		RestProperties restProperties = new RestProperties();
		restProperties.setRestBaseUrl("https://api.binance.com");
		restProperties.setRestKey(Crypto4jUtils.getSecret("binance.key"));
		restProperties.setRestSecret(Crypto4jUtils.getSecret("binance.secret"));
		//
		BinanceProperties binanceProperties = new BinanceProperties();
		binanceProperties.setWebsocketMarketBaseUrl("wss://stream.binance.com:9443/stream?streams=");
		binanceProperties.setWebsocketUserStreamBaseUrl("wss://stream.binance.com:9443/ws/");
		binanceProperties.setRestProperties(restProperties);
		return binanceProperties;
	}

	public static BinanceSubscriber binanceSubscriber() {
		BinanceSubscriber binanceSubscriber = new BinanceSubscriber();
		binanceSubscriber.depthAndTicker(5, "btcusdt");
		binanceSubscriber.setDepthListener(Crypto4jUtils.noOp());
		binanceSubscriber.setTickerListener(Crypto4jUtils.noOp());
		binanceSubscriber.setDepthListener(Crypto4jUtils.noOp());
		binanceSubscriber.setAccountInfoListener(Crypto4jUtils.noOp());
		binanceSubscriber.setExecutionReportListener(Crypto4jUtils.noOp());
		return binanceSubscriber;
	}
	
	public static void main(String[] args) {
		QueryAllOrdersRequset queryRequest = new QueryAllOrdersRequset();
		queryRequest.setSymbol("BTCUSDT");
		List<QueryAllOrdersResponse> responseList = binanceRestService().queryAllOrders(queryRequest);
		

		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		ZoneId zone = ZoneId.systemDefault();
		for(QueryAllOrdersResponse item : responseList) {
			Instant instant = Instant.ofEpochMilli(item.getTime());
		    System.out.println(String.format("%s [%s]",LocalDateTime.ofInstant(instant, zone).format(df),item.toString()));
		}
	}
}
