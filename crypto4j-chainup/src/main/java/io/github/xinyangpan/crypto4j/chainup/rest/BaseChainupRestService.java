package io.github.xinyangpan.crypto4j.chainup.rest;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import io.github.xinyangpan.crypto4j.core.RestProperties;
import io.github.xinyangpan.crypto4j.core.rest.BaseRestService;
import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;

public class BaseChainupRestService extends BaseRestService {
	private static final Logger log = LoggerFactory.getLogger(BaseChainupRestService.class);
	// 
	private final HashFunction HASHING;
	private final DefaultUriBuilderFactory builderFactory;

	public BaseChainupRestService(RestProperties restProperties) {
		super(restProperties);
		String restSecret = restProperties.getRestSecret();
		if (restSecret != null) {
			HASHING = Hashing.hmacSha256(restSecret.getBytes());
		} else {
			HASHING = null;
		}
		builderFactory = new DefaultUriBuilderFactory();
		builderFactory.setEncodingMode(EncodingMode.NONE);
	}

	@SuppressWarnings("unchecked")
	protected String toRequestParam(Object object, boolean encode) {
		Map<String, Object> value = null;
		if (object == null) {
			value = new HashMap<>();
		} else if (object instanceof Map) {
			value = new HashMap<>((Map<String, Object>) object);
		} else {
			value = (Map<String, Object>) Crypto4jUtils.objectMapper().convertValue(object, Map.class);
		}
		value.put("AccessKeyId", restProperties.getRestKey());
		value.put("SignatureMethod", "HmacSHA256");
		value.put("SignatureVersion", "2");
		value.put("Timestamp", LocalDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
		// 
		String param = value.entrySet().stream()//
			.filter(e -> e.getValue() != null)// filter out null field
			.sorted(Comparator.comparing(Entry::getKey))// sort
			.map(e -> String.format("%s=%s", e.getKey(), this.getValue(e.getValue(), encode)))// entry to string ${key}=${value}
			.collect(Collectors.joining("&"));// joining by &
		return param;
	}

	private String getValue(Object o, boolean encode) {
		if (encode) {
			return urlEncode(String.valueOf(o));
		} else {
			return String.valueOf(o);
		}
	}


	protected String getUrl(String path, Object... objects) {
		if (objects == null || objects.length == 0) {
			return restProperties.getRestBaseUrl() + path;
		}
		path = String.format(path, objects);
		return String.format("%s%s", restProperties.getRestBaseUrl(), path);
	}

	protected HttpEntity<String> buildPostRequestEntity(Object object) {
		// body
		String body = toJson(object);
		// Header
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", "My Agent");
		// Requesting
		HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
		return requestEntity;
	}

	protected <T> T readValue(String body, TypeReference<T> typeReference) {
		try {
			return Crypto4jUtils.objectMapper().readValue(body, typeReference);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
