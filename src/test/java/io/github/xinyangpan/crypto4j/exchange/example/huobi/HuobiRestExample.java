package io.github.xinyangpan.crypto4j.exchange.example.huobi;

import java.math.BigDecimal;

import io.github.xinyangpan.crypto4j.exchange.example.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.exchange.huobi.HuobiProperties;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.enums.OrderType;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.rest.Order;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.rest.RestResponse;
import io.github.xinyangpan.crypto4j.exchange.huobi.rest.HuobiRestService;

public class HuobiRestExample {
	
	public static void main(String[] args) throws InterruptedException {
		HuobiProperties huobiProperties = new HuobiProperties();
		huobiProperties.setRestBaseUrl("https://api.huobi.pro");
		huobiProperties.setRestKey(Crypto4jUtils.getSecret("huobi.key"));
		huobiProperties.setRestSecret(Crypto4jUtils.getSecret("huobi.secret"));
		// 
		Order order = new Order();
		order.setAccountId(4275858L);
		order.setAmount(new BigDecimal("0.01"));
		order.setPrice(new BigDecimal("8000"));
		order.setSymbol("btcusdt");
		order.setType(OrderType.BUY_IOC);
		// 
		HuobiRestService huobiRestService = new HuobiRestService(huobiProperties);
//		System.out.println(huobiRestService.tickers());
//		System.out.println(huobiRestService.accounts());
		RestResponse<String> restResponse = huobiRestService.placeOrder(order);
		System.out.println(restResponse);
		System.out.println(huobiRestService.queryOrder(restResponse.getData()));
		System.out.println(huobiRestService.queryExecution(restResponse.getData()));
//		System.out.println(huobiRestService.queryOrder(new QueryOrder(order.getSymbol(), 834593837)));
//		System.out.println(huobiRestService.cancelOrder(new CancelOrder(order.getSymbol(), 835120964, 835120003)));
//		System.out.println(huobiRestService.userinfo());
	}

}
	