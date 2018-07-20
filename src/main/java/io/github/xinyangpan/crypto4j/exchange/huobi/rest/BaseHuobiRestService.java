package io.github.xinyangpan.crypto4j.exchange.huobi.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Lists;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import io.github.xinyangpan.crypto4j.exchange.ExchangeUtils;
import io.github.xinyangpan.crypto4j.exchange.huobi.HuobiProperties;

public class BaseHuobiRestService {
	private static final Logger log = LoggerFactory.getLogger(BaseHuobiRestService.class);
	// 
	protected final HuobiProperties huobiProperties;
	private final HashFunction HASHING;
	// 
	protected RestTemplate restTemplate = ExchangeUtils.restTemplate();

	public BaseHuobiRestService(HuobiProperties huobiProperties) {
		this.huobiProperties = huobiProperties;
		HASHING = Hashing.hmacSha256(huobiProperties.getRestSecret().getBytes());
	}

	@SuppressWarnings("unchecked")
	protected String toRequestParam(Object object) {
		Map<String, Object> value = null;
		if (object == null) {
			value = new HashMap<>();
		} else {
			value = (Map<String, Object>) ExchangeUtils.objectMapper().convertValue(object, Map.class);
		}
		value.put("AccessKeyId", huobiProperties.getRestKey());
		value.put("SignatureMethod", "HmacSHA256");
		value.put("SignatureVersion", "2");
		value.put("Timestamp", LocalDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
		// 
		String param = value.entrySet().stream()//
			.filter(e -> e.getValue() != null)// filter out null field
			.sorted(Comparator.comparing(Entry::getKey))// sort
			.map(e -> String.format("%s=%s", e.getKey(), e.getValue()))// entry to string ${key}=${value}
			.collect(Collectors.joining("&"));// joining by &
		return param;
	}

	@SuppressWarnings("unchecked")
	protected String toRequestParam1(Object object) {
		Map<String, Object> value = null;
		if (object == null) {
			value = new HashMap<>();
		} else {
			value = (Map<String, Object>) ExchangeUtils.objectMapper().convertValue(object, Map.class);
		}
		value.put("AccessKeyId", huobiProperties.getRestKey());
		value.put("SignatureMethod", "HmacSHA256");
		value.put("SignatureVersion", "2");
		value.put("Timestamp", LocalDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
		// 
		String param = value.entrySet().stream()//
			.filter(e -> e.getValue() != null)// filter out null field
			.sorted(Comparator.comparing(Entry::getKey))// sort
			.map(e -> {
				try {
					return String.format("%s=%s", e.getKey(), URLEncoder.encode(String.valueOf(e.getValue()), "utf-8"));
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			})// entry to string ${key}=${value}
			.collect(Collectors.joining("&"));// joining by &
		return param;
	}

	protected String toSignedRequestParam(Object object, String path, String methodType) {
		// 
		String param = toRequestParam1(object);
		StringBuilder sb = new StringBuilder();
		sb.append(methodType).append("\n");
		sb.append("api.huobi.pro").append("\n");
		sb.append(path).append("\n");
		sb.append(param);
		log.debug("To sign string\n{}", sb);
		// 
		param = toRequestParam(object);
		String sign = Base64.getEncoder().encodeToString(HASHING.hashBytes(sb.toString().getBytes()).asBytes());
		log.debug("Signature: {}", sign);
		try {
			return String.format("%s&Signature=%s", param, sign.replaceAll("\\+", "%2B").replaceAll("=", "%3D"));
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String encodeURIComponent(String s) {
	    String result;

	    try {
	        result = URLEncoder.encode(s, "UTF-8")
	                .replaceAll("\\+", "%2B")
	                .replaceAll("\\%21", "!")
	                .replaceAll("\\%27", "'")
	                .replaceAll("\\%28", "(")
	                .replaceAll("\\%29", ")")
	                .replaceAll("\\%7E", "~");
	    } catch (UnsupportedEncodingException e) {
	        result = s;
	    }

	    return result;
	}
	
	protected String getUrl(String path, Object... objects) {
		if (objects == null || objects.length == 0) {
			return huobiProperties.getRestBaseUrl() + path;
		}
		path = String.format(path, objects);
		return String.format("%s%s", huobiProperties.getRestBaseUrl(), path);
	}

	protected String toGetUrl(String path, Object object) {
		String signedRequestParam = this.toSignedRequestParam(object, path, "GET");
		return String.format("%s%s?%s", huobiProperties.getRestBaseUrl(), path, signedRequestParam);
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
			//			body = toSignedRequestParam(object, "POST");
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
