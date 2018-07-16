package io.github.xinyangpan.crypto4j.exchange.binance.rest;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import io.github.xinyangpan.crypto4j.exchange.ExchangeUtils;

public class BaseBinanceRestService {
	private static String KEY = "RRNGWvMoqYvvTRjrVMzc54coOupxm7W5VqCzT60y5aENk6fM9mikjEqJ4KyWkDnt";
	private static String SECRET = "";
	private static String BASE_URL = "https://api.binance.com";
	private static HashFunction HASHING = Hashing.hmacSha256(SECRET.getBytes());
	// 
	protected RestTemplate restTemplate = ExchangeUtils.restTemplate();

	protected String toRequestParam(Object object) {
		@SuppressWarnings("unchecked")
		Map<String, ?> value = (Map<String, ?>) ExchangeUtils.objectMapper().convertValue(object, Map.class);
		// 
		String param = value.entrySet().stream()//
			.filter(e -> e.getValue() != null)// filter out null field
			.map(e -> String.format("%s=%s", e.getKey(), e.getValue()))// entry to string ${key}=${value}
			.collect(Collectors.joining("&"));// joining by &
		return param;
	}
	
	protected String toSignedRequestParam(Object object) {
		// 
		String param = toRequestParam(object);
		String signature = HASHING.hashBytes(param.getBytes()).toString();
		return String.format("%s&signature=%s", param, signature);
	}

	protected HttpEntity<String> buildSignedRequestEntity(Object object) {
		return this.buildRequestEntity(object, true);
	}

	protected HttpEntity<String> buildRequestEntity(Object object, boolean sign) {
		// body
		String body = null; 
		if (object != null) {
			if (sign) {
				body = toSignedRequestParam(object);
			} else {
				body = toRequestParam(object);
			}
		}
		// Header
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-MBX-APIKEY", KEY);
		// Requesting
		HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
		return requestEntity;
	}

	protected String getUrl(String path, Object... objects) {
		if (objects == null || objects.length == 0) {
			return BASE_URL + path;
		}
		path = String.format(path, objects);
		return String.format("%s%s", BASE_URL, path);
	}

}
