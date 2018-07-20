package io.github.xinyangpan.crypto4j.exchange.huobi.rest;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import io.github.xinyangpan.crypto4j.exchange.huobi.HuobiProperties;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.rest.AccountInfo;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.rest.RestResponse;

public class HuobiRestService extends BaseHuobiRestService {
	private static final Logger log = LoggerFactory.getLogger(HuobiRestService.class);
	private static ParameterizedTypeReference<RestResponse<AccountInfo>> ACCOUNT_INFO = new ParameterizedTypeReference<RestResponse<AccountInfo>>() {};

	public HuobiRestService(HuobiProperties huobiProperties) {
		super(huobiProperties);
	}

	public String tickers() {
		String url = this.getUrl("/market/tickers");
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();
	}

	public RestResponse<AccountInfo> accounts() {
		URI uri = this.getUrlWithSignature("/v1/account/accounts", null);
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		return restTemplate.exchange(uri, HttpMethod.GET, requestEntity, ACCOUNT_INFO).getBody();
	}

	//	public OrderResponse placeOrder(Order order) {
	//		log.debug("{}", order);
	//		String url = this.getUrl("/api/v1/trade.do");
	//		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(order, true);
	//		return restTemplate.postForObject(url, requestEntity, OrderResponse.class);
	//	}
	//
	//	public CancelOrderResponse cancelOrder(CancelOrder cancelOrder) {
	//		log.debug("{}", cancelOrder);
	//		String url = this.getUrl("/api/v1/cancel_order.do");
	//		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(cancelOrder, true);
	//		return restTemplate.postForObject(url, requestEntity, CancelOrderResponse.class);
	//	}
	//
	//	public String queryOrder(QueryOrder queryOrder) {
	//		log.debug("{}", queryOrder);
	//		String url = this.getUrl("/api/v1/order_info.do");
	//		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(queryOrder, true);
	//		return restTemplate.postForObject(url, requestEntity, String.class);
	//	}
	//
	//	public String userinfo() {
	//		log.debug("userinfo");
	//		String url = this.getUrl("/api/v1/userinfo.do");
	//		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(null, true);
	//		return restTemplate.postForObject(url, requestEntity, String.class);
	//	}

}
