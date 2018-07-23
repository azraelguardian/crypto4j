package io.github.xinyangpan.crypto4j.common.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.xinyangpan.crypto4j.exchange.ExchangeUtils;

public class BaseRestService {
	// 
	protected final RestTemplate restTemplate = ExchangeUtils.restTemplate();
	protected final ObjectMapper objectMapper = ExchangeUtils.objectMapper();

	public <T> String urlEncode(String text) {
		try {
			return URLEncoder.encode(text, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	protected HttpEntity<String> requestEntityWithUserAgent() {
		// Header
		HttpHeaders headers = new HttpHeaders();
		headers.add("User-Agent", "My Agent");
		// Requesting
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		return requestEntity;
	}

	protected String toJson(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
