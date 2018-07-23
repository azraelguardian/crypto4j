package io.github.xinyangpan.crypto4j.exchange.huobi.rest;

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

import com.google.common.collect.Lists;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import io.github.xinyangpan.crypto4j.common.rest.BaseRestService;
import io.github.xinyangpan.crypto4j.exchange.ExchangeUtils;
import io.github.xinyangpan.crypto4j.exchange.huobi.HuobiProperties;

public class BaseHuobiRestService extends BaseRestService {
	private static final Logger log = LoggerFactory.getLogger(BaseHuobiRestService.class);
	// 
	protected final HuobiProperties huobiProperties;
	private final HashFunction HASHING;
	private final DefaultUriBuilderFactory builderFactory;

	public BaseHuobiRestService(HuobiProperties huobiProperties) {
		this.huobiProperties = huobiProperties;
		HASHING = Hashing.hmacSha256(huobiProperties.getRestSecret().getBytes());
		builderFactory = new DefaultUriBuilderFactory();
		builderFactory.setEncodingMode(EncodingMode.NONE);
	}

	@SuppressWarnings("unchecked")
	protected String toRequestParam(Object object, boolean encode) {
		Map<String, Object> value = null;
		if (object == null) {
			value = new HashMap<>();
		} else if (object instanceof Map) {
			value = new HashMap<>((Map<String, Object>)object);
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

	protected String toSignedRequestParam(Object object, String path, RequestType requestType) {
		// 
		String param = toRequestParam(object, true);
		StringBuilder sb = new StringBuilder();
		sb.append(requestType.name()).append("\n");
		sb.append("api.huobi.pro").append("\n");
		sb.append(path).append("\n");
		sb.append(param);
		log.debug("To sign string\n{}", sb);
		// 
		param = toRequestParam(object, false);
		String signature = Base64.getEncoder().encodeToString(HASHING.hashBytes(sb.toString().getBytes()).asBytes());
		return String.format("%s&Signature=%s", param, encodeSignature(signature));
	}

	private String encodeSignature(String signature) {
		return signature.replaceAll("\\+", "%2B").replaceAll("=", "%3D");
	}

	protected String getUrl(String path, Object... objects) {
		if (objects == null || objects.length == 0) {
			return huobiProperties.getRestBaseUrl() + path;
		}
		path = String.format(path, objects);
		return String.format("%s%s", huobiProperties.getRestBaseUrl(), path);
	}

	protected URI getUrlWithSignature(String path, RequestType requestType, Object object) {
		String signedRequestParam = this.toSignedRequestParam(object, path, requestType);
		String url = String.format("%s%s?%s", huobiProperties.getRestBaseUrl(), path, signedRequestParam);
		return builderFactory.expand(url);
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

}
