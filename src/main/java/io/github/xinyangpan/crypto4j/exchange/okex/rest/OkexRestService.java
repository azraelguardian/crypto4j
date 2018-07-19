package io.github.xinyangpan.crypto4j.exchange.okex.rest;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import io.github.xinyangpan.crypto4j.exchange.ExchangeUtils;
import io.github.xinyangpan.crypto4j.exchange.okex.rest.dto.Order;
import io.github.xinyangpan.crypto4j.exchange.okex.rest.dto.OrderResponse;

public class OkexRestService {
	private static final Logger log = LoggerFactory.getLogger(OkexRestService.class);
	// 
	private final OkexProperties okexProperties;
	private final HashFunction HASHING;
	// 
	protected RestTemplate restTemplate = ExchangeUtils.restTemplate();

	@SuppressWarnings("deprecation")
	public OkexRestService(OkexProperties okexProperties) {
		super();
		this.okexProperties = okexProperties;
		HASHING = Hashing.md5();
	}

	protected String toRequestParam(Object object) {
		@SuppressWarnings("unchecked")
		Map<String, Object> value = (Map<String, Object>) ExchangeUtils.objectMapper().convertValue(object, Map.class);
		if (value == null) {
			value = new HashMap<>();
		}
		value.put("api_key", okexProperties.getRestKey());
		// 
		String param = value.entrySet().stream()//
			.filter(e -> e.getValue() != null)// filter out null field
			.sorted(Comparator.comparing(Entry::getKey))// sort
			.map(e -> String.format("%s=%s", e.getKey(), e.getValue()))// entry to string ${key}=${value}
			.collect(Collectors.joining("&"));// joining by &
		return param;
	}

	protected String toSignedRequestParam(Object object) {
		// 
		String param = toRequestParam(object);
		String toSignParam = String.format("%s&secret_key=%s", param, okexProperties.getRestSecret());
		String sign = HASHING.hashBytes(toSignParam.getBytes()).toString().toUpperCase();
		return String.format("%s&sign=%s", param, sign);
	}

	protected String getUrl(String path, Object... objects) {
		if (objects == null || objects.length == 0) {
			return okexProperties.getRestBaseUrl() + path;
		}
		path = String.format(path, objects);
		return String.format("%s%s", okexProperties.getRestBaseUrl(), path);
	}

	protected HttpEntity<String> buildRequestEntity(Object object, boolean sign) {
		// body
		String body = null;
		if (sign) {
			body = toSignedRequestParam(object);
		} else {
			body = toRequestParam(object);
		}
		// Header
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
		headers.add("User-Agent", "My Agent");
//		headers.add("cache-control", "no-cache");
//		headers.add("postman-token", "5e818259-7bd3-a09d-24d0-c588bfd228b2");
//		headers.med
		// Requesting
		HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
		return requestEntity;
	}
	
	public String ticker(String symbol) {
		String url = this.getUrl("/api/v1/ticker.do?symbol=%s", "ltc_btc");
		HttpHeaders headers = new HttpHeaders();
		headers.add("User-Agent", "My Agent");
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();
//		return restTemplate.getForObject(url, String.class);
	}
	
	public OrderResponse placeOrder(Order order) {
		log.debug("Place order: {}", order);
		String url = this.getUrl("/api/v1/trade.do?%s", this.toSignedRequestParam(order));
		HttpEntity<String> requestEntity = this.buildRequestEntity(order, true);
		return restTemplate.postForObject(url, requestEntity, OrderResponse.class);
	}
	
	public String userinfo() {
		log.debug("userinfo");
		String url = this.getUrl("/api/v1/userinfo.do");
		HttpEntity<String> requestEntity = this.buildRequestEntity(null, true);
		return restTemplate.postForObject(url, requestEntity, String.class);
	}
	
}
