package io.github.xinyangpan.crypto4j.exchange.okex.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import io.github.xinyangpan.crypto4j.exchange.okex.dto.rest.CancelOrder;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.rest.CancelOrderResponse;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.rest.Order;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.rest.OrderResponse;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.rest.QueryOrder;

public class OkexRestService extends BaseOkexRestService {
	private static final Logger log = LoggerFactory.getLogger(OkexRestService.class);

	public OkexRestService(OkexProperties okexProperties) {
		super(okexProperties);
	}

	public String ticker(String symbol) {
		String url = this.getUrl("/api/v1/ticker.do?symbol=%s", symbol);
		HttpEntity<String> requestEntity = this.buildGetRequestEntity();
		return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();
	}

	public OrderResponse placeOrder(Order order) {
		log.debug("{}", order);
		String url = this.getUrl("/api/v1/trade.do");
		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(order, true);
		return restTemplate.postForObject(url, requestEntity, OrderResponse.class);
	}

	public CancelOrderResponse cancelOrder(CancelOrder cancelOrder) {
		log.debug("{}", cancelOrder);
		String url = this.getUrl("/api/v1/cancel_order.do");
		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(cancelOrder, true);
		return restTemplate.postForObject(url, requestEntity, CancelOrderResponse.class);
	}

	public String queryOrder(QueryOrder queryOrder) {
		log.debug("{}", queryOrder);
		String url = this.getUrl("/api/v1/order_info.do");
		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(queryOrder, true);
		return restTemplate.postForObject(url, requestEntity, String.class);
	}

	public String userinfo() {
		log.debug("userinfo");
		String url = this.getUrl("/api/v1/userinfo.do");
		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(null, true);
		return restTemplate.postForObject(url, requestEntity, String.class);
	}

}
