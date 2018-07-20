package io.github.xinyangpan.crypto4j.exchange.huobi.rest;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import io.github.xinyangpan.crypto4j.exchange.huobi.HuobiProperties;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.rest.AccountInfo;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.rest.Order;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.rest.RestResponse;

public class HuobiRestService extends BaseHuobiRestService {
	private static final Logger log = LoggerFactory.getLogger(HuobiRestService.class);
	private static ParameterizedTypeReference<RestResponse<List<AccountInfo>>> ACCOUNT_INFO = new ParameterizedTypeReference<RestResponse<List<AccountInfo>>>() {};
	private static ParameterizedTypeReference<RestResponse<Long>> ORDER_RESPONSE = new ParameterizedTypeReference<RestResponse<Long>>() {};

	public HuobiRestService(HuobiProperties huobiProperties) {
		super(huobiProperties);
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

	public RestResponse<Long> placeOrder(Order order) {
		log.debug("{}", order);
		URI url = this.getUrlWithSignature("/v1/order/orders/place", RequestType.POST, null);
		HttpEntity<String> requestEntity = this.buildPostRequestEntity(order);
		return restTemplate.exchange(url, HttpMethod.POST, requestEntity, ORDER_RESPONSE).getBody();
	}
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
