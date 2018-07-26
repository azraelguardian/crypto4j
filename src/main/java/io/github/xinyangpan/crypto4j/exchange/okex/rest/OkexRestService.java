package io.github.xinyangpan.crypto4j.exchange.okex.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import io.github.xinyangpan.crypto4j.common.RestProperties;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.rest.account.UserInfo;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.rest.order.CancelOrder;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.rest.order.CancelOrderResponse;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.rest.order.Order;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.rest.order.OrderResponse;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.rest.order.QueryOrder;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.rest.order.QueryOrderResponse;

public class OkexRestService extends BaseOkexRestService {
	private static final Logger log = LoggerFactory.getLogger(OkexRestService.class);

	public OkexRestService(RestProperties restProperties) {
		super(restProperties);
	}

	public String ticker(String symbol) {
		String url = this.getUrl("/api/v1/ticker.do?symbol=%s", symbol);
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();
	}

	public OrderResponse placeOrder(Order order) {
		log.debug("{}", order);
		String url = this.getUrl("/api/v1/trade.do");
		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(order);
		return restTemplate.postForObject(url, requestEntity, OrderResponse.class);
	}

	public CancelOrderResponse cancelOrder(CancelOrder cancelOrder) {
		log.debug("{}", cancelOrder);
		String url = this.getUrl("/api/v1/cancel_order.do");
		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(cancelOrder);
		return restTemplate.postForObject(url, requestEntity, CancelOrderResponse.class);
	}

	public QueryOrderResponse queryOrder(QueryOrder queryOrder) {
		log.debug("{}", queryOrder);
		String url = this.getUrl("/api/v1/order_info.do");
		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(queryOrder);
		return restTemplate.postForObject(url, requestEntity, QueryOrderResponse.class);
	}

	public UserInfo userinfo() {
		log.debug("userinfo");
		String url = this.getUrl("/api/v1/userinfo.do");
		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(null);
		return restTemplate.postForObject(url, requestEntity, UserInfo.class);
	}

}
