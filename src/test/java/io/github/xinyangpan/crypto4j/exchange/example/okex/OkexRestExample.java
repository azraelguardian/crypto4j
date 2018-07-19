package io.github.xinyangpan.crypto4j.exchange.example.okex;

import java.math.BigDecimal;

import io.github.xinyangpan.crypto4j.exchange.example.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.exchange.okex.OkexWsConnector;
import io.github.xinyangpan.crypto4j.exchange.okex.impl.OkexWsSubscriber;
import io.github.xinyangpan.crypto4j.exchange.okex.rest.OkexProperties;
import io.github.xinyangpan.crypto4j.exchange.okex.rest.OkexRestService;
import io.github.xinyangpan.crypto4j.exchange.okex.rest.dto.Order;
import io.github.xinyangpan.crypto4j.exchange.okex.rest.dto.OrderResponse;
import io.github.xinyangpan.crypto4j.exchange.okex.rest.dto.enums.OrderType;

public class OkexRestExample {
	
	public static void main(String[] args) throws InterruptedException {
		OkexProperties okexProperties = new OkexProperties();
		okexProperties.setRestBaseUrl("https://www.okex.com");
		okexProperties.setRestKey("e33c4b9a-c20d-46e8-9329-ab8b0f810cf1");
		okexProperties.setRestSecret(Crypto4jUtils.getSecret("okex.secret"));
		// 
		Order order = new Order();
		order.setAmount(new BigDecimal("0.01"));
		order.setPrice(new BigDecimal("9465"));
		order.setSymbol("btc_usdt");
		order.setType(OrderType.sell);
		// 
		OkexRestService okexRestService = new OkexRestService(okexProperties);
//		System.out.println(okexRestService.ticker("btc_usdt"));
//		System.out.println(okexRestService.placeOrder(order));
		System.out.println(okexRestService.userinfo());
	}

}
	