package io.github.xinyangpan.crypto4j.binance.rest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.google.common.base.Preconditions;

import io.github.xinyangpan.crypto4j.binance.dto.rest.common.ListenKey;
import io.github.xinyangpan.crypto4j.core.RestProperties;

public class BinanceUserStreamService extends BaseBinanceRestService {

	public BinanceUserStreamService(RestProperties restProperties) {
		super(restProperties);
	}

	public ListenKey start() {
		String url = this.getUrl("/api/v1/userDataStream");
		HttpEntity<String> requestEntity = this.buildRequestEntity(null, false);
		return restTemplate.postForObject(url, requestEntity, ListenKey.class);
	}

	public void keeplive(String listenKey) {
		String url = this.getUrl("/api/v1/userDataStream");
		HttpEntity<String> requestEntity = this.buildRequestEntity(new ListenKey(listenKey), false);
		ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
		Preconditions.checkArgument("{}".equals(exchange.getBody()));
	}

	public void close(String listenKey) {
		String url = this.getUrl("/api/v1/userDataStream");
		HttpEntity<String> requestEntity = this.buildRequestEntity(new ListenKey(listenKey), false);
		ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
		Preconditions.checkArgument("{}".equals(exchange.getBody()));
	}

}
