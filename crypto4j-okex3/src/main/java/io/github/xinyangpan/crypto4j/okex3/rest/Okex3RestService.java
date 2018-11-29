package io.github.xinyangpan.crypto4j.okex3.rest;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import io.github.xinyangpan.crypto4j.okex3.dto.account.BalanceInfo;
import io.github.xinyangpan.crypto4j.okex3.dto.trade.CancelOrder;
import io.github.xinyangpan.crypto4j.okex3.dto.trade.Order;
import io.github.xinyangpan.crypto4j.okex3.dto.trade.OrderResult;
import io.github.xinyangpan.crypto4j.okex3.dto.trade.PlaceOrder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Okex3RestService extends BaseOkex3RestService {
	private static final ParameterizedTypeReference<List<BalanceInfo>> BALANCE_INFO_LIST = new ParameterizedTypeReference<List<BalanceInfo>>() {};

	public Okex3RestService(Okex3RestProperties okex3RestProperties) {
		super(okex3RestProperties);
	}

	public String ticker() {
		String url = this.getUrl("/api/spot/v3/instruments/ticker");
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();
	}

	public List<BalanceInfo> account() {
		log.debug("account");
		String requestPath = "/api/spot/v3/accounts";
		HttpMethod method = HttpMethod.GET;
		// 
		String url = this.getUrl(requestPath);
		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(requestPath, method, null);
		return restTemplate.exchange(url, method, requestEntity, BALANCE_INFO_LIST).getBody();
	}

	public OrderResult placeOrder(PlaceOrder placeOrder) {
		String requestPath = "/api/spot/v3/orders";
		HttpMethod method = HttpMethod.POST;
		// 
		String url = this.getUrl(requestPath);
		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(requestPath, method, placeOrder);
		return restTemplate.exchange(url, method, requestEntity, OrderResult.class).getBody();
	}

	public OrderResult cancelOrder(long orderId, @NonNull String instrumentId, String clientOid) {
		String requestPath = String.format("/api/spot/v3/cancel_orders/%s", orderId);
		HttpMethod method = HttpMethod.POST;
		// 
		String url = this.getUrl(requestPath);
		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(requestPath, method, new CancelOrder(clientOid, instrumentId));
		OrderResult orderResult = restTemplate.exchange(url, method, requestEntity, OrderResult.class).getBody();
		log.debug("{}", orderResult);
		return orderResult;
	}

	public Order queryOrder(String instrumentId, long orderId) {
		String requestPath = String.format("/api/spot/v3/orders/%s?instrument_id=%s", orderId, instrumentId);
		HttpMethod method = HttpMethod.GET;
		// 
		String url = this.getUrl(requestPath);
		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(requestPath, method, null);
		Order order = restTemplate.exchange(url, method, requestEntity, Order.class).getBody();
		log.debug("{}", order);
		return order;
	}
	
//
//	public OrderResponse placeOrder(Order order) {
//		log.debug("{}", order);
//		String url = this.getUrl("/api/v1/trade.do");
//		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(order);
//		OrderResponse response = restTemplate.postForObject(url, requestEntity, OrderResponse.class);
//		log.debug("{}", response);
//		return response;
//	}
//
//	public CancelOrderResponse cancelOrder(CancelOrder cancelOrder) {
//		log.debug("{}", cancelOrder);
//		String url = this.getUrl("/api/v1/cancel_order.do");
//		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(cancelOrder);
//		CancelOrderResponse response = restTemplate.postForObject(url, requestEntity, CancelOrderResponse.class);
//		log.debug("{}", response);
//		return response;
//	}
//
//	public OrderResult queryOrder(String symbol, long orderId) {
//		QueryOrder queryOrder = new QueryOrder(symbol, orderId);
//		log.debug("{}", queryOrder);
//		String url = this.getUrl("/api/v1/order_info.do");
//		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(queryOrder);
//		QueryOrderResponse response = restTemplate.postForObject(url, requestEntity, QueryOrderResponse.class);
//		log.debug("{}", response);
//		return this.getOrderResult(response);
//	}
//
//	@SneakyThrows
//	public OrderResult queryOrderForFinalStatus(String symbol, long orderId, int attempt) {
//		OrderResult orderResult = null;
//		for (int i = 0; i < attempt; i++) {
//			Thread.sleep(100 * (i + 1));
//			orderResult = this.queryOrder(symbol, orderId);
//			log.debug("orderDetail[{}]: {}", i, orderResult);
//			if (orderResult == null) {
//				continue;
//			}
//			OrderStatus orderStatus = orderResult.getStatus();
//			if (orderStatus == OrderStatus.NEW || orderStatus == OrderStatus.PENDING_CANCEL) {
//				continue;
//			} else if (orderStatus == OrderStatus.FILLED && orderResult.getDealAmount().compareTo(orderResult.getAmount()) != 0) {
//				continue;
//			} else if (orderStatus == OrderStatus.PARTIALLY_FILLED && orderResult.getDealAmount().compareTo(BigDecimal.ZERO) <= 0) {
//				continue;
//			} else {
//				return orderResult;
//			}
//		}
//		throw new UnknownOrderException(String.valueOf(orderId), "No valid order result returned. ref=" + orderResult);
//	}
//
//	private OrderResult getOrderResult(QueryOrderResponse queryOrderResponse) {
//		queryOrderResponse.throwExceptionWhenError();
//		List<OrderResult> orders = queryOrderResponse.getOrders();
//		if (orders == null || orders.isEmpty()) {
//			return null;
//		}
//		log.debug("OrderResults: {}", orders);
//		Preconditions.checkState(orders != null && orders.size() == 1);
//		OrderResult orderResult = orders.get(0);
//		return orderResult;
//	}
//
//	public OrderResult placeAndQueryOrder(Order order) {
//		OrderResponse orderResponse = this.placeOrder(order).throwExceptionWhenError();
//		OrderType orderType = order.getType();
//		if (orderType == OrderType.buy_market || orderType == OrderType.sell_market) {
//			return this.queryOrderForFinalStatus(order.getSymbol(), orderResponse.getOrderId(), 3);
//		} else {
//			return this.queryOrder(order.getSymbol(), orderResponse.getOrderId());
//		}
//	}
//
//	public OrderResult simulateIocAndQueryOrder(Order order) {
//		// Place Order
//		OrderResponse orderResponse = this.placeOrder(order).throwExceptionWhenError();
//		// Cancel Order
//		CancelOrderResponse cancelOrderResponse = this.cancelOrder(new CancelOrder(order.getSymbol(), orderResponse.getOrderId()));
//		log.debug("{}", cancelOrderResponse);
//		// 1009 没有订单, filled, throw Exception if not 1009
//		// 1051	订单已完成交易
//		// 1019	撤销订单失败
//		ErrorCode errorCode = cancelOrderResponse.getErrorCode();
//		if (errorCode != null) {
//			Integer code = errorCode.getCode();
//			if (code != null && code != 1009 && code != 1019 && code != 1051) {
//				cancelOrderResponse.throwExceptionWhenError();
//			}
//		}
//		return this.queryOrderForFinalStatus(order.getSymbol(), orderResponse.getOrderId(), 3);
//	}

}
