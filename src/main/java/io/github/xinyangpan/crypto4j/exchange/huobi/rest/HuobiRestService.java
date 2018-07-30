package io.github.xinyangpan.crypto4j.exchange.huobi.rest;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.core.type.TypeReference;

import io.github.xinyangpan.crypto4j.common.RestProperties;
import io.github.xinyangpan.crypto4j.exchange.ExchangeUtils;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.rest.AccountInfo;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.rest.Execution;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.rest.Order;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.rest.OrderResult;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.rest.RestChannelResponse;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.rest.RestResponse;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.websocket.depth.Depth;

public class HuobiRestService extends BaseHuobiRestService {
	private static final Logger log = LoggerFactory.getLogger(HuobiRestService.class);
	private static ParameterizedTypeReference<RestResponse<List<AccountInfo>>> ACCOUNT_INFO = new ParameterizedTypeReference<RestResponse<List<AccountInfo>>>() {};
	private static ParameterizedTypeReference<RestResponse<String>> ORDER_RESPONSE = new ParameterizedTypeReference<RestResponse<String>>() {};
	private static ParameterizedTypeReference<RestResponse<OrderResult>> ORDER_RESULT = new ParameterizedTypeReference<RestResponse<OrderResult>>() {};
	private static ParameterizedTypeReference<RestResponse<List<Execution>>> EXECUTION_RESULT = new ParameterizedTypeReference<RestResponse<List<Execution>>>() {};
	private static TypeReference <RestChannelResponse<Depth>> DEPTH_RESULT = new TypeReference <RestChannelResponse<Depth>>() {};

	public HuobiRestService(RestProperties restProperties) {
		super(restProperties);
	}

	public RestResponse<Depth> depth(String symbol) {
		return this.depth(symbol, "step0");
	}

	public RestResponse<Depth> depth(String symbol, String type) {
		try {
			String url = this.getUrl("/market/depth?symbol=%s&type=%s", symbol, type);
			HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
			String body = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();
			return ExchangeUtils.objectMapper().readValue(body, DEPTH_RESULT);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String tickers() {
		String url = this.getUrl("/market/tickers");
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();
	}

	public RestResponse<List<AccountInfo>> accounts() {
		URI uri = this.getUrlWithSignature("/v1/account/accounts", RequestType.GET, null);
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		return restTemplate.exchange(uri, HttpMethod.GET, requestEntity, ACCOUNT_INFO).getBody();
	}

	public RestResponse<String> placeOrder(Order order) {
		log.debug("{}", order);
		URI url = this.getUrlWithSignature("/v1/order/orders/place", RequestType.POST, null);
		HttpEntity<String> requestEntity = this.buildPostRequestEntity(order);
		return restTemplate.exchange(url, HttpMethod.POST, requestEntity, ORDER_RESPONSE).getBody();
	}

	public RestResponse<OrderResult> queryOrder(String orderId) {
		log.debug("{}", orderId);
		URI url = this.getUrlWithSignature("/v1/order/orders/" + orderId, RequestType.GET, null);
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		return restTemplate.exchange(url, HttpMethod.GET, requestEntity, ORDER_RESULT).getBody();
	}

	public RestResponse<List<Execution>> queryExecution(String orderId) {
		log.debug("{}", orderId);
		URI url = this.getUrlWithSignature(String.format("/v1/order/orders/%s/matchresults", orderId), RequestType.GET, null);
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		return restTemplate.exchange(url, HttpMethod.GET, requestEntity, EXECUTION_RESULT).getBody();
	}

}
