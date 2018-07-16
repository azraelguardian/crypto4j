package io.github.xinyangpan.crypto4j.exchange.example.binance;

import java.math.BigDecimal;

import io.github.xinyangpan.crypto4j.exchange.binance.dto.enums.OrderType;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.enums.Side;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.enums.TimeInForce;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.rest.Order;
import io.github.xinyangpan.crypto4j.exchange.binance.rest.BinanceRestService;
import io.github.xinyangpan.crypto4j.exchange.example.binance.util.BinanceTestUtils;

public class BinanceRestExample {

	public static void main(String[] args) throws InterruptedException {
		// 
		Order order = new Order();
		order.setSymbol("ETHBTC");
		order.setSide(Side.BUY);
		order.setType(OrderType.LIMIT);
		order.setTimeInForce(TimeInForce.IOC);
		order.setQuantity(new BigDecimal("1"));
		order.setPrice(new BigDecimal("0.01"));
		order.setRecvWindow(5000L);
		order.setTimestamp(System.currentTimeMillis());
		// 
		BinanceRestService binanceRestService = BinanceTestUtils.binanceService().restService();
		System.out.println(binanceRestService.bookTicker("ETHBTC"));
		System.out.println(binanceRestService.placeOrder(order));
	}

}
