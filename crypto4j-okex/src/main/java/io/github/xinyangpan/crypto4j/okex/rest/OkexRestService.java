package io.github.xinyangpan.crypto4j.okex.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import io.github.xinyangpan.crypto4j.core.RestProperties;
import io.github.xinyangpan.crypto4j.okex.dto.account.UserInfo;
import io.github.xinyangpan.crypto4j.okex.dto.market.Depth;
import io.github.xinyangpan.crypto4j.okex.dto.trade.CancelOrder;
import io.github.xinyangpan.crypto4j.okex.dto.trade.CancelOrderResponse;
import io.github.xinyangpan.crypto4j.okex.dto.trade.Order;
import io.github.xinyangpan.crypto4j.okex.dto.trade.OrderResponse;
import io.github.xinyangpan.crypto4j.okex.dto.trade.QueryOrder;
import io.github.xinyangpan.crypto4j.okex.dto.trade.QueryOrderResponse;

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

	public Depth depth(String symbol) {
		String url = this.getUrl("/api/v1/depth.do?symbol=%s", symbol);
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		return restTemplate.exchange(url, HttpMethod.GET, requestEntity, Depth.class).getBody();
	}

	public UserInfo userinfo() {
		log.debug("userinfo");
		String url = this.getUrl("/api/v1/userinfo.do");
		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(null);
		return restTemplate.postForObject(url, requestEntity, UserInfo.class);
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

	public QueryOrderResponse queryOrder(String symbol, long orderId) {
		return this.queryOrder(new QueryOrder(symbol, orderId));
	}

	public QueryOrderResponse placeAndQueryOrder(Order order) {
		OrderResponse orderResponse = this.placeOrder(order).throwExceptionWhenError();
		return this.queryOrder(order.getSymbol(), orderResponse.getOrderId());
	}

	public QueryOrderResponse simulateIocAndQueryOrder(Order order) {
		OrderResponse orderResponse = this.placeOrder(order).throwExceptionWhenError();
		CancelOrderResponse cancelOrderResponse = this.cancelOrder(new CancelOrder(order.getSymbol(), orderResponse.getOrderId()));
		// 1009 没有订单, filled, throw Exception if not 1009
		if (1009 != cancelOrderResponse.getErrorCode().getCode()) {
			cancelOrderResponse.throwExceptionWhenError();
		}
		return this.queryOrder(order.getSymbol(), orderResponse.getOrderId());
	}
	
}
