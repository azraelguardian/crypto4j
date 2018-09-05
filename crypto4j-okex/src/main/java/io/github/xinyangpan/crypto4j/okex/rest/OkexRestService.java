package io.github.xinyangpan.crypto4j.okex.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import com.google.common.base.Preconditions;

import io.github.xinyangpan.crypto4j.core.RestProperties;
import io.github.xinyangpan.crypto4j.okex.dto.account.UserInfo;
import io.github.xinyangpan.crypto4j.okex.dto.common.ErrorCode;
import io.github.xinyangpan.crypto4j.okex.dto.enums.OrderStatus;
import io.github.xinyangpan.crypto4j.okex.dto.enums.OrderType;
import io.github.xinyangpan.crypto4j.okex.dto.market.Depth;
import io.github.xinyangpan.crypto4j.okex.dto.trade.CancelOrder;
import io.github.xinyangpan.crypto4j.okex.dto.trade.CancelOrderResponse;
import io.github.xinyangpan.crypto4j.okex.dto.trade.Order;
import io.github.xinyangpan.crypto4j.okex.dto.trade.OrderResponse;
import io.github.xinyangpan.crypto4j.okex.dto.trade.OrderResult;
import io.github.xinyangpan.crypto4j.okex.dto.trade.QueryOrder;
import io.github.xinyangpan.crypto4j.okex.dto.trade.QueryOrderResponse;
import lombok.SneakyThrows;

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

	public OrderResult queryOrder(String symbol, long orderId) {
		QueryOrder queryOrder = new QueryOrder(symbol, orderId);
		log.debug("{}", queryOrder);
		String url = this.getUrl("/api/v1/order_info.do");
		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(queryOrder);
		return this.getOrderResult(restTemplate.postForObject(url, requestEntity, QueryOrderResponse.class));
	}

	@SneakyThrows
	public OrderResult queryOrderForFinalStatus(String symbol, long orderId) {
		Thread.sleep(50);
		OrderResult orderResult = null;
		for (int i = 0; i < 3; i++) {
			orderResult = this.queryOrder(symbol, orderId);
			OrderStatus orderStatus = orderResult.getStatus();
			if (orderStatus == OrderStatus.NEW || orderStatus == OrderStatus.PENDING_CANCEL) {
				Thread.sleep(100);
				continue;
			} else {
				return orderResult;
			}
		}
		return orderResult;
	}

	private OrderResult getOrderResult(QueryOrderResponse queryOrderResponse) {
		queryOrderResponse.throwExceptionWhenError();
		List<OrderResult> orders = queryOrderResponse.getOrders();
		Preconditions.checkState(orders != null && orders.size() == 1);
		OrderResult orderResult = orders.get(0);
		return orderResult;
	}

	public OrderResult placeAndQueryOrder(Order order) {
		OrderResponse orderResponse = this.placeOrder(order).throwExceptionWhenError();
		OrderType orderType = order.getType();
		if (orderType == OrderType.buy_market || orderType == OrderType.sell_market) {
			return this.queryOrderForFinalStatus(order.getSymbol(), orderResponse.getOrderId());
		} else {
			return this.queryOrder(order.getSymbol(), orderResponse.getOrderId());
		}
	}

	public OrderResult simulateIocAndQueryOrder(Order order) {
		OrderResponse orderResponse = this.placeOrder(order).throwExceptionWhenError();

		CancelOrderResponse cancelOrderResponse = this.cancelOrder(new CancelOrder(order.getSymbol(), orderResponse.getOrderId()));
		log.debug("{}", cancelOrderResponse);
		// 1009 没有订单, filled, throw Exception if not 1009
		// 1051	订单已完成交易
		// 1019	撤销订单失败
		ErrorCode errorCode = cancelOrderResponse.getErrorCode();
		if (errorCode != null) {
			Integer code = errorCode.getCode();
			if (code != null && code != 1009 && code != 1019 && code != 1051) {
				cancelOrderResponse.throwExceptionWhenError();
			}
		}
		return this.queryOrderForFinalStatus(order.getSymbol(), orderResponse.getOrderId());
	}

}
