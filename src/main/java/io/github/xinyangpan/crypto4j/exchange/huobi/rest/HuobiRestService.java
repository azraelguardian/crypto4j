package io.github.xinyangpan.crypto4j.exchange.huobi.rest;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;

import io.github.xinyangpan.crypto4j.exchange.huobi.HuobiProperties;

public class HuobiRestService extends BaseHuobiRestService {
	private static final Logger log = LoggerFactory.getLogger(HuobiRestService.class);

	public HuobiRestService(HuobiProperties huobiProperties) {
		super(huobiProperties);
	}

	public String tickers() {
		String url = this.getUrl("/market/tickers");
		HttpEntity<String> requestEntity = this.buildGetRequestEntity();
		return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();
	}

	public String accounts() {
		DefaultUriBuilderFactory builderFactory = new DefaultUriBuilderFactory();
		builderFactory.setEncodingMode(EncodingMode.NONE);
		String url = this.toGetUrl("/v1/account/accounts", null);
		URI uri = builderFactory.expand(url);
		HttpEntity<String> requestEntity = this.buildGetRequestEntity();
		return restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class).getBody();
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
