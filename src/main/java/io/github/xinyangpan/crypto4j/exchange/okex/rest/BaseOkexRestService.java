package io.github.xinyangpan.crypto4j.exchange.okex.rest;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Lists;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import io.github.xinyangpan.crypto4j.exchange.ExchangeUtils;

public class BaseOkexRestService {
	// 
	protected final OkexProperties okexProperties;
	private final HashFunction HASHING;
	// 
	protected RestTemplate restTemplate = ExchangeUtils.restTemplate();

	@SuppressWarnings("deprecation")
	public BaseOkexRestService(OkexProperties okexProperties) {
		this.okexProperties = okexProperties;
		HASHING = Hashing.md5();
	}

	@SuppressWarnings("unchecked")
	protected String toRequestParam(Object object) {
		Map<String, Object> value = null;
		if (object == null) {
			value = new HashMap<>();
		} else {
			value = (Map<String, Object>) ExchangeUtils.objectMapper().convertValue(object, Map.class);
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
	
	protected HttpEntity<String> buildGetRequestEntity() {
		// Header
		HttpHeaders headers = new HttpHeaders();
		headers.add("User-Agent", "My Agent");
		// Requesting
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		return requestEntity;
	}

	protected HttpEntity<String> buildSignedRequestEntity(Object object, boolean sign) {
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
		headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "My Agent");
		// Requesting
		HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
		return requestEntity;
	}

}
