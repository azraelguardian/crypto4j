package io.github.xinyangpan.crypto4j.exchange.binance.rest;

import org.springframework.http.HttpEntity;

import io.github.xinyangpan.crypto4j.exchange.binance.dto.rest.BookTicker;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.rest.Order;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.rest.OrderResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BinanceRestService extends BaseBinanceRestService {

	public BookTicker bookTicker(String symbol) {
		String url = this.getUrl("/api/v3/ticker/bookTicker?symbol=%s", symbol);
		return restTemplate.getForObject(url, BookTicker.class);
	}

	public OrderResponse placeOrder(Order order) {
		log.debug("Place order: {}", order);
		String url = this.getUrl("/api/v3/order");
		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(order);
		return restTemplate.postForObject(url, requestEntity, OrderResponse.class);
	}

}
