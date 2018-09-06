package io.github.xinyangpan.crypto4j.binance.rest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import io.github.xinyangpan.crypto4j.core.RestProperties;
import io.github.xinyangpan.crypto4j.core.rest.BaseRestService;

public class BaseBinanceRestService extends BaseRestService {
	private final HashFunction HASHING;

	public BaseBinanceRestService(RestProperties restProperties) {
		super(restProperties);
		HASHING = Hashing.hmacSha256(restProperties.getRestSecret().getBytes());
	}

	protected String toSignedRequestParam(Object object) {
		// 
		String param = toRequestParam(object);
		String signature = HASHING.hashBytes(param.getBytes()).toString();
		return String.format("%s&signature=%s", param, signature);
	}

	protected HttpEntity<String> buildSignedRequestEntity(Object object) {
		return this.buildRequestEntity(object, true);
	}

	protected HttpEntity<String> buildRequestEntity(Object object, boolean sign) {
		// body
		String body = null;
		if (object != null) {
			if (sign) {
				body = toSignedRequestParam(object);
			} else {
				body = toRequestParam(object);
			}
		}
		// Header
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-MBX-APIKEY", restProperties.getRestKey());
		// Requesting
		HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
		return requestEntity;
	}

	protected String getUrl(String path, Object... objects) {
		if (objects == null || objects.length == 0) {
			return restProperties.getRestBaseUrl() + path;
		}
		path = String.format(path, objects);
		return String.format("%s%s", restProperties.getRestBaseUrl(), path);
	}

}
