package io.github.xinyangpan.crypto4j.core.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import io.github.xinyangpan.crypto4j.exchange.ExchangeUtils;

public class BaseRestService {
	// 
	protected final RestTemplate restTemplate = ExchangeUtils.restTemplate();


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
	
}
