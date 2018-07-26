package io.github.xinyangpan.crypto4j.exchange.example.binance;

import java.math.BigDecimal;

import io.github.xinyangpan.crypto4j.exchange.binance.dto.enums.OrderType;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.enums.Side;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.enums.TimeInForce;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.rest.order.PlaceOrderRequest;
import io.github.xinyangpan.crypto4j.exchange.binance.rest.BinanceRestService;
import io.github.xinyangpan.crypto4j.exchange.example.binance.util.BinanceTestUtils;

public class RestExample {

	public static void main(String[] args) throws InterruptedException {
		// 
		PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest();
		placeOrderRequest.setSymbol("BTCUSDT");
		placeOrderRequest.setSide(Side.SELL);
		placeOrderRequest.setType(OrderType.LIMIT);
		placeOrderRequest.setTimeInForce(TimeInForce.IOC);
		placeOrderRequest.setQuantity(new BigDecimal("0.01"));
		placeOrderRequest.setPrice(new BigDecimal("7700"));
		placeOrderRequest.setRecvWindow(5000L);
		placeOrderRequest.setTimestamp(System.currentTimeMillis());
		// 
		BinanceRestService binanceRestService = BinanceTestUtils.binanceService().restService();
		System.out.println(binanceRestService.account());
//		System.out.println(binanceRestService.bookTicker("BTCUSDT"));
//		System.out.println(binanceRestService.placeOrderRequest(placeOrderRequest));
		
//		QueryOrderRequest queryOrderRequest = new QueryOrderRequest();
//		queryOrderRequest.setOrderId(136685394L);
//		queryOrderRequest.setSymbol("BTCUSDT");
//		queryOrderRequest.setRecvWindow(5000L);
//		queryOrderRequest.setTimestamp(System.currentTimeMillis());
//		System.out.println(binanceRestService.queryOrder(queryOrderRequest));
		
		// Query Trade
//		QueryTradeRequest queryTradeRequest = new QueryTradeRequest();
//		queryTradeRequest.setSymbol("BTCUSDT");
//		System.out.println(binanceRestService.queryTrade(queryTradeRequest));
	}

}
