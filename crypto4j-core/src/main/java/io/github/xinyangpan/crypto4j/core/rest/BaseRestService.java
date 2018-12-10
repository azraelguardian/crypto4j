package io.github.xinyangpan.crypto4j.core.rest;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.xinyangpan.crypto4j.core.RestProperties;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseRestService {
	// 
	protected final RestTemplate restTemplate = new RestTemplate();
	protected final ObjectMapper objectMapper = new ObjectMapper();
	@Getter
	protected final RestProperties restProperties;

	public BaseRestService(RestProperties restProperties) {
		this.restProperties = restProperties;
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

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

	@SuppressWarnings("unchecked")
	protected String toRequestParam(Object object) {
		Map<String, Object> value = null;
		if (object == null) {
			value = new HashMap<>();
		} else if (object instanceof Map) {
			value = new HashMap<>((Map<String, Object>) object);
		} else {
			value = (Map<String, Object>) objectMapper.convertValue(object, Map.class);
		}
		// 
		String param = value.entrySet().stream()//
			.filter(e -> e.getValue() != null)// filter out null field
			.map(e -> String.format("%s=%s", e.getKey(), e.getValue()))// entry to string ${key}=${value}
			.collect(Collectors.joining("&"));// joining by &
		return param;
	}

	@SneakyThrows
	protected <T> T exchange(URI uri, HttpMethod method, @NonNull HttpEntity<?> requestEntity, TypeReference<T> typeReference) throws RestClientException {
		String bodyText = restTemplate.exchange(uri, method, requestEntity, String.class).getBody();
		log.debug("Body: {}", bodyText);
		return objectMapper.readValue(bodyText, typeReference);
	}

	@SneakyThrows
	protected <T> T exchange(String url, HttpMethod method, @NonNull HttpEntity<?> requestEntity, TypeReference<T> typeReference) throws RestClientException {
		String bodyText = this.getBodyText(url, method, requestEntity);
		return objectMapper.readValue(bodyText, typeReference);
	}

	@SneakyThrows
	@SuppressWarnings("unchecked")
	protected <T> T exchange(String url, HttpMethod method, @NonNull HttpEntity<?> requestEntity, Class<T> clazz) throws RestClientException {
		String bodyText = this.getBodyText(url, method, requestEntity);
		if (String.class.equals(clazz)) {
			return (T) bodyText;
		}
		return objectMapper.readValue(bodyText, clazz);
	}

	private String getBodyText(String url, HttpMethod method, HttpEntity<?> requestEntity) {
		String bodyText = restTemplate.exchange(url, method, requestEntity, String.class).getBody();
		log.debug("Body: {}", bodyText);
		return bodyText;
	}

}
