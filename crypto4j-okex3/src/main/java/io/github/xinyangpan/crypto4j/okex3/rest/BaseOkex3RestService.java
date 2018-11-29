package io.github.xinyangpan.crypto4j.okex3.rest;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.google.common.collect.Lists;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import io.github.xinyangpan.crypto4j.core.rest.BaseRestService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseOkex3RestService extends BaseRestService {
	// 
	private final HashFunction hashFunction;

	public BaseOkex3RestService(Okex3RestProperties okex3RestProperties) {
		super(okex3RestProperties);
		String restSecret = restProperties.getRestSecret();
		if (restSecret != null) {
			hashFunction = Hashing.hmacSha256(restSecret.getBytes());
		} else {
			hashFunction = null;
		}
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
	}

	public String getPassphrase() {
		return ((Okex3RestProperties) this.getRestProperties()).getPassphrase();
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
		String sign = hashFunction.hashBytes(toSignParam.getBytes()).toString().toUpperCase();
		return String.format("%s&sign=%s", param, sign);
	}

	protected String getUrl(String path, Object... objects) {
		if (objects == null || objects.length == 0) {
			return restProperties.getRestBaseUrl() + path;
		}
		path = String.format(path, objects);
		return String.format("%s%s", restProperties.getRestBaseUrl(), path);
	}

	protected HttpEntity<String> buildSignedRequestEntity(String requestPath, HttpMethod method, Object bodyObj) {
		String body = bodyObj == null ? "" : toJson(bodyObj);
		String ts = getTimestampString();
		String toSignString = ts + method.name() + requestPath + body;
		log.debug("toSignString: {}", toSignString);
		HashCode hashCode = hashFunction.hashString(toSignString, Charset.forName("utf8"));
		String string = new String(Base64.getEncoder().encode(hashCode.asBytes()));
		// body
		// Header
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
		headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON_UTF8));
		headers.add("User-Agent", "My Agent");
		headers.add("OK-ACCESS-KEY", restProperties.getRestKey());
		headers.add("OK-ACCESS-TIMESTAMP", String.valueOf(ts));
		headers.add("OK-ACCESS-PASSPHRASE", this.getPassphrase());
		headers.add("OK-ACCESS-SIGN", string);
		// Requesting
		HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
		return requestEntity;
	}

	private String getTimestampString() {
		long milli = System.currentTimeMillis();
		long sec = System.currentTimeMillis() / 1000;
		long ms = milli - sec * 1000;
		return sec + "." + ms;
	}

}
