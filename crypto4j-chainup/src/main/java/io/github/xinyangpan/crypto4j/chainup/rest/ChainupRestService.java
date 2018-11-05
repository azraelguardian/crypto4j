package io.github.xinyangpan.crypto4j.chainup.rest;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.core.type.TypeReference;

import io.github.xinyangpan.crypto4j.chainup.dto.ChainupResponse;
import io.github.xinyangpan.crypto4j.chainup.dto.data.Symbol;
import io.github.xinyangpan.crypto4j.chainup.dto.data.Tick;
import io.github.xinyangpan.crypto4j.core.RestProperties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChainupRestService extends BaseChainupRestService {
	private static TypeReference<ChainupResponse<List<Symbol>>> SYMBOLS_RESULT = new TypeReference<ChainupResponse<List<Symbol>>>() {};
	private static TypeReference<ChainupResponse<Tick>> TICK_RESULT = new TypeReference<ChainupResponse<Tick>>() {};

	public ChainupRestService(RestProperties restProperties) {
		super(restProperties);
	}

	public ChainupResponse<List<Symbol>> getAllSymbols() {
		String url = this.getUrl("/exchange-open-api/open/api/common/symbols");
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		String body = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();
		return this.readValue(body, SYMBOLS_RESULT);
	}

	public ChainupResponse<Tick> getTick(String symbol) {
		String url = this.getUrl("/exchange-open-api/open/api/get_ticker?symbol=%s", symbol);
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		String body = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();
		return this.readValue(body, TICK_RESULT);
	}

}
