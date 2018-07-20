package io.github.xinyangpan.crypto4j.exchange.example.huobi;

import java.math.BigDecimal;

import io.github.xinyangpan.crypto4j.exchange.example.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.exchange.huobi.HuobiProperties;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.enums.OrderType;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.rest.Order;
import io.github.xinyangpan.crypto4j.exchange.huobi.rest.HuobiRestService;

public class HuobiRestExample {
	
	public static void main(String[] args) throws InterruptedException {
		HuobiProperties huobiProperties = new HuobiProperties();
		huobiProperties.setRestBaseUrl("https://api.huobi.pro");
		huobiProperties.setRestKey("061b36c2-2d372ce2-dbec74c0-b52cb");
		huobiProperties.setRestSecret(Crypto4jUtils.getSecret("huobi.secret"));
		// 
		Order order = new Order();
		order.setAccountId(4275858L);
		order.setAmount(new BigDecimal("0.01"));
		order.setPrice(new BigDecimal("9465"));
		order.setSymbol("btcusdt");
		order.setType(OrderType.SELL_LIMIT);
		// 
		HuobiRestService huobiRestService = new HuobiRestService(huobiProperties);
//		System.out.println(huobiRestService.tickers());
//		System.out.println(huobiRestService.accounts());
		System.out.println(huobiRestService.placeOrder(order));
//		System.out.println(huobiRestService.queryOrder(new QueryOrder(order.getSymbol(), 834593837)));
//		System.out.println(huobiRestService.cancelOrder(new CancelOrder(order.getSymbol(), 835120964, 835120003)));
//		System.out.println(huobiRestService.userinfo());
	}

}
	