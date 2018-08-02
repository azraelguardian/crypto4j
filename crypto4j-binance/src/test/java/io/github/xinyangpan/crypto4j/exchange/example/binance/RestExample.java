package io.github.xinyangpan.crypto4j.exchange.example.binance;

import java.math.BigDecimal;

import org.springframework.web.client.HttpClientErrorException;

import com.google.common.base.MoreObjects;

import io.github.xinyangpan.crypto4j.binance.dto.enums.OrderType;
import io.github.xinyangpan.crypto4j.binance.dto.enums.Side;
import io.github.xinyangpan.crypto4j.binance.dto.enums.TimeInForce;
import io.github.xinyangpan.crypto4j.binance.dto.rest.account.QueryTradeRequest;
import io.github.xinyangpan.crypto4j.binance.dto.rest.market.BookTicker;
import io.github.xinyangpan.crypto4j.binance.dto.rest.order.PlaceOrderRequest;
import io.github.xinyangpan.crypto4j.binance.dto.rest.order.PlaceOrderResponse;
import io.github.xinyangpan.crypto4j.binance.dto.rest.order.QueryOrderRequest;
import io.github.xinyangpan.crypto4j.binance.rest.BinanceRestService;
import io.github.xinyangpan.crypto4j.exchange.example.binance.util.BinanceTestUtils;

public class RestExample {

	private static final String BTCUSDT = "BTCUSDT";
	private static final BinanceRestService binanceRestService = BinanceTestUtils.binanceService().restService();

	static QueryTradeRequest queryTradeRequest() {
		QueryTradeRequest queryTradeRequest = new QueryTradeRequest();
		queryTradeRequest.setSymbol(BTCUSDT);
		return queryTradeRequest;
	}

	static QueryOrderRequest queryOrderRequest() {
		QueryOrderRequest queryOrderRequest = new QueryOrderRequest();
		queryOrderRequest.setOrderId(136685394L);
		queryOrderRequest.setSymbol(BTCUSDT);
		queryOrderRequest.setRecvWindow(5000L);
		queryOrderRequest.setTimestamp(System.currentTimeMillis());
		return queryOrderRequest;
	}

	static PlaceOrderRequest placeOrderRequest(BigDecimal price, BigDecimal qty) {
		price = MoreObjects.firstNonNull(price, new BigDecimal("8100"));
		qty = MoreObjects.firstNonNull(qty, new BigDecimal("0.01"));
		//
		PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest();
		placeOrderRequest.setSymbol(BTCUSDT);
		placeOrderRequest.setSide(Side.BUY);
		placeOrderRequest.setType(OrderType.LIMIT);
		placeOrderRequest.setTimeInForce(TimeInForce.IOC);
		placeOrderRequest.setPrice(price);
		placeOrderRequest.setQuantity(qty);
//		placeOrderRequest.setNewOrderRespType(NewOrderRespType.FULL);
		return placeOrderRequest;
	}

	static void tryPartialFill() throws InterruptedException {
		while(true) {
			BookTicker bookTicker = binanceRestService.bookTicker(BTCUSDT);
			BigDecimal amount = bookTicker.getAskPrice().multiply(bookTicker.getAskQty());
			if (amount.compareTo(new BigDecimal("700")) > 0) {
				System.out.println("continue ...");
				Thread.sleep(1000L);
				continue;
			}
			System.out.println(bookTicker);
			PlaceOrderRequest placeOrderRequest = placeOrderRequest(bookTicker.getAskPrice(), bookTicker.getAskQty());
			PlaceOrderResponse placeOrderResponse = binanceRestService.placeOrder(placeOrderRequest);
			System.out.println(placeOrderResponse);
			break;
		}
	}

	public static void main(String[] args) throws InterruptedException {
		try {
			System.out.println(binanceRestService.account());
		} catch (HttpClientErrorException e) {
			System.out.println(e.getResponseBodyAsString());
		}
	}

}