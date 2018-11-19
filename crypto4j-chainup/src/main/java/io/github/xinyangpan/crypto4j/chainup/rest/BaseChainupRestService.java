package io.github.xinyangpan.crypto4j.chainup.rest;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.google.common.collect.Lists;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import io.github.xinyangpan.crypto4j.core.RestProperties;
import io.github.xinyangpan.crypto4j.core.rest.BaseRestService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseChainupRestService extends BaseRestService {
	// 
	private final HashFunction HASHING;

	@SuppressWarnings("deprecation")
	public BaseChainupRestService(RestProperties restProperties) {
		super(restProperties);
		HASHING = Hashing.md5();
		objectMapper.configure(Feature.WRITE_NUMBERS_AS_STRINGS, true);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected String toRequestParam(Object object) {
		Map<String, Object> value = null;
		if (object == null) {
			value = new HashMap<>();
		} else {
			value = (Map<String, Object>) objectMapper.convertValue(object, Map.class);
		}
		// 
		String param = value.entrySet().stream()//
			.filter(e -> e.getValue() != null)// filter out null field
			.sorted(Comparator.comparing(Entry::getKey))// sort
			.map(e -> String.format("%s=%s", e.getKey(), e.getValue()))// entry to string ${key}=${value}
			.collect(Collectors.joining("&"));// joining by &
		return param;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> toRequestParamMap(Object object) {
		Map<String, Object> requestParamMap = null;
		if (object == null) {
			requestParamMap = new HashMap<>();
		} else {
			requestParamMap = (Map<String, Object>) objectMapper.convertValue(object, Map.class);
		}
		requestParamMap.put("api_key", restProperties.getRestKey());
		requestParamMap.put("time", System.currentTimeMillis());
		return requestParamMap;
	}

	private String getToSignString(Map<String, Object> requestParamMap) {
		String toSignString = requestParamMap.entrySet().stream()//
			.filter(e -> e.getValue() != null)// filter out null field
			.sorted(Comparator.comparing(Entry::getKey))// sort
			.map(e -> String.format("%s%s", e.getKey(), e.getValue()))// entry to string ${key}${value}
			.collect(Collectors.joining(""));// joining by 
		return toSignString + restProperties.getRestSecret();
	}

	protected String toSignedRequestParam(Object object) {
		// 
		Map<String, Object> requestParamMap = toRequestParamMap(object);
		String toSignString = getToSignString(requestParamMap);
		log.debug("toSignString: {}", toSignString);
		String sign = HASHING.hashBytes(toSignString.getBytes()).toString();
		requestParamMap.put("sign", sign);
		return toRequestParam(requestParamMap);
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
		headers.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");
		headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "My Agent");
		// Requesting
		HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
		return requestEntity;
	}

}
