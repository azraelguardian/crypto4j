package io.github.xinyangpan.crypto4j.exchange.binance.rest;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import io.github.xinyangpan.crypto4j.exchange.ExchangeUtils;
import io.github.xinyangpan.crypto4j.exchange.binance.rest.dto.BookTicker;
import io.github.xinyangpan.crypto4j.exchange.binance.rest.dto.Order;
import io.github.xinyangpan.crypto4j.exchange.binance.rest.dto.OrderResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BinanceRestService {
	private static String KEY = "RRNGWvMoqYvvTRjrVMzc54coOupxm7W5VqCzT60y5aENk6fM9mikjEqJ4KyWkDnt";
	private static String BASE_URL = "https://api.binance.com";
	private static HashFunction HASHING = Hashing.hmacSha256(SECRET.getBytes());
	// 
	private RestTemplate restTemplate = ExchangeUtils.restTemplate();

	public static String toSignedRequestParam(Object object) {
		@SuppressWarnings("unchecked")
		Map<String, ?> value = (Map<String, ?>) ExchangeUtils.objectMapper().convertValue(object, Map.class);
		// 
		String param = value.entrySet().stream()//
			.filter(e -> e.getValue() != null)// filter out null field
			.map(e -> String.format("%s=%s", e.getKey(), e.getValue()))// entry to string ${key}=${value}
			.collect(Collectors.joining("&"));// joining by &
		String signature = HASHING.hashBytes(param.getBytes()).toString();
		return String.format("%s&signature=%s", param, signature);
	}

	public BookTicker bookTicker(String symbol) {
		String url = this.getUrl("/api/v3/ticker/bookTicker?symbol=%s", symbol);
		return restTemplate.getForObject(url, BookTicker.class);
	}

	public OrderResponse placeOrder(Order order) {
		log.debug("Place order: {}", order);
		String url = this.getUrl("/api/v3/order");
		// Body
		HttpEntity<String> requestEntity = buildRequestEntity(order);
		return restTemplate.postForObject(url, requestEntity, OrderResponse.class);
	}

	private HttpEntity<String> buildRequestEntity(Object object) {
		// body
		String body = toSignedRequestParam(object);
		// Header
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-MBX-APIKEY", KEY);
		// Requesting
		HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
		return requestEntity;
	}

	private String getUrl(String path, Object... objects) {
		if (objects == null || objects.length == 0) {
			return BASE_URL + path;
		}
		path = String.format(path, objects);
		return String.format("%s%s", BASE_URL, path);
	}

}
