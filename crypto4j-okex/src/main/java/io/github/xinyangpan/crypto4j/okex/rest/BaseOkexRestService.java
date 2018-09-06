package io.github.xinyangpan.crypto4j.okex.rest;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.google.common.collect.Lists;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import io.github.xinyangpan.crypto4j.core.RestProperties;
import io.github.xinyangpan.crypto4j.core.rest.BaseRestService;
import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;

public class BaseOkexRestService extends BaseRestService {
	// 
	private final HashFunction HASHING;

	@SuppressWarnings("deprecation")
	public BaseOkexRestService(RestProperties restProperties) {
		super(restProperties);
		HASHING = Hashing.md5();
	}

	@Override
	@SuppressWarnings("unchecked")
	protected String toRequestParam(Object object) {
		Map<String, Object> value = null;
		if (object == null) {
			value = new HashMap<>();
		} else {
			value = (Map<String, Object>) Crypto4jUtils.objectMapper().convertValue(object, Map.class);
		}
		value.put("api_key", restProperties.getRestKey());
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
		String toSignParam = String.format("%s&secret_key=%s", param, restProperties.getRestSecret());
		String sign = HASHING.hashBytes(toSignParam.getBytes()).toString().toUpperCase();
		return String.format("%s&sign=%s", param, sign);
	}

	protected String getUrl(String path, Object... objects) {
		if (objects == null || objects.length == 0) {
			return restProperties.getRestBaseUrl() + path;
		}
		path = String.format(path, objects);
		return String.format("%s%s", restProperties.getRestBaseUrl(), path);
	}

	protected HttpEntity<String> buildSignedRequestEntity(Object object) {
		// body
		String body = toSignedRequestParam(object);
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
