package io.github.xinyangpan.crypto4j.exchange.example.okex;

import java.math.BigDecimal;

import io.github.xinyangpan.crypto4j.exchange.example.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.exchange.okex.OkexProperties;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.enums.OrderType;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.rest.order.Order;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.rest.order.OrderResponse;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.rest.order.QueryOrder;
import io.github.xinyangpan.crypto4j.exchange.okex.rest.OkexRestService;

public class OkexRestExample {
	
	public static void main(String[] args) throws InterruptedException {
		OkexProperties okexProperties = new OkexProperties();
		okexProperties.setRestBaseUrl("https://www.okex.com");
		okexProperties.setRestKey(Crypto4jUtils.getSecret("okex.key"));
		okexProperties.setRestSecret(Crypto4jUtils.getSecret("okex.secret"));
		// 
		Order order = new Order();
		order.setAmount(new BigDecimal("0.09"));
		order.setPrice(new BigDecimal("7823"));
		order.setSymbol("btc_usdt");
		order.setType(OrderType.buy);
		// 
		OkexRestService okexRestService = new OkexRestService(okexProperties);
//		System.out.println(okexRestService.ticker("btc_usdt"));
		OrderResponse orderResponse = okexRestService.placeOrder(order);
		System.out.println(orderResponse);
		System.out.println(okexRestService.queryOrder(new QueryOrder(order.getSymbol(), orderResponse.getOrderId())));
//		System.out.println(okexRestService.cancelOrder(new CancelOrder(order.getSymbol(), 835120964, 835120003)));
//		System.out.println(okexRestService.userinfo());
	}

}
	