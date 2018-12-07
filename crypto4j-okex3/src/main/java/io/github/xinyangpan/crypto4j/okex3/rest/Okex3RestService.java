package io.github.xinyangpan.crypto4j.okex3.rest;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;

import io.github.xinyangpan.crypto4j.core.UnknownOrderException;
import io.github.xinyangpan.crypto4j.okex3.dto.ErrorResult;
import io.github.xinyangpan.crypto4j.okex3.dto.account.BalanceInfo;
import io.github.xinyangpan.crypto4j.okex3.dto.enums.OrderStatus;
import io.github.xinyangpan.crypto4j.okex3.dto.trade.CancelOrder;
import io.github.xinyangpan.crypto4j.okex3.dto.trade.Execution;
import io.github.xinyangpan.crypto4j.okex3.dto.trade.ExecutionQuery;
import io.github.xinyangpan.crypto4j.okex3.dto.trade.Order;
import io.github.xinyangpan.crypto4j.okex3.dto.trade.OrderDetail;
import io.github.xinyangpan.crypto4j.okex3.dto.trade.OrderResult;
import io.github.xinyangpan.crypto4j.okex3.dto.trade.PlaceOrder;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Okex3RestService extends BaseOkex3RestService {
	private static final TypeReference<List<BalanceInfo>> BALANCE_INFO_LIST = new TypeReference<List<BalanceInfo>>() {};
	private static final TypeReference<List<Execution>> EXECUTION_LIST = new TypeReference<List<Execution>>() {};

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
		return exchange(url, method, requestEntity, BALANCE_INFO_LIST);
	}

	public OrderResult placeOrder(PlaceOrder placeOrder) {
		String requestPath = "/api/spot/v3/orders";
		HttpMethod method = HttpMethod.POST;
		// 
		String url = this.getUrl(requestPath);
		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(requestPath, method, placeOrder);
		return exchange(url, method, requestEntity, OrderResult.class);
	}

	public OrderResult cancelOrder(long orderId, @NonNull String instrumentId, String clientOid) {
		String requestPath = String.format("/api/spot/v3/cancel_orders/%s", orderId);
		HttpMethod method = HttpMethod.POST;
		// 
		String url = this.getUrl(requestPath);
		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(requestPath, method, new CancelOrder(clientOid, instrumentId));
		return exchange(url, method, requestEntity, OrderResult.class);
	}

	public Order queryOrder(String instrumentId, long orderId) {
		String requestPath = String.format("/api/spot/v3/orders/%s?instrument_id=%s", orderId, instrumentId);
		HttpMethod method = HttpMethod.GET;
		// 
		String url = this.getUrl(requestPath);
		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(requestPath, method, null);
		return exchange(url, method, requestEntity, Order.class);
	}

	public Order queryOrderForFinalStatus(String instrumentId, long orderId) {
		return this.queryOrderForFinalStatus(instrumentId, orderId, 3);
	}
	
	@SneakyThrows
	public Order queryOrderForFinalStatus(String instrumentId, long orderId, int attempt) {
		Order order = null;
		for (int i = 0; i < attempt; i++) {
			Thread.sleep(100 * (i + 1));
			order = this.queryOrder(instrumentId, orderId);
			log.debug("orderDetail[{}]: {}", i, order);
			if (order == null) {
				continue;
			}
			OrderStatus orderStatus = order.getStatus();
			if (orderStatus == OrderStatus.open ||orderStatus == OrderStatus.ordering || orderStatus == OrderStatus.canceling) {
				continue;
			} else if (orderStatus == OrderStatus.filled && order.getFilledSize().compareTo(order.getSize()) != 0) {
				continue;
			} else if (orderStatus == OrderStatus.part_filled && order.getFilledSize().compareTo(BigDecimal.ZERO) <= 0) {
				continue;
			} else {
				return order;
			}
		}
		throw new UnknownOrderException(String.valueOf(orderId), "No valid order result returned. ref=" + order);
	}
	
	public List<Execution> queryExecution(ExecutionQuery executionQuery) {
		try {
			String requestPath = String.format("/api/spot/v3/fills?%s", this.toRequestParam(executionQuery));
			HttpMethod method = HttpMethod.GET;
			// 
			String url = this.getUrl(requestPath);
			HttpEntity<String> requestEntity = this.buildSignedRequestEntity(requestPath, method, null);
			return exchange(url, method, requestEntity, EXECUTION_LIST);
		} catch (HttpClientErrorException e) {
			String responseBody = e.getResponseBodyAsString();
			log.debug("{}", responseBody);
			ErrorResult errorResult;
			try {
				errorResult = objectMapper.readValue(e.getResponseBodyAsString(), ErrorResult.class);
			} catch (Exception e1) {
				throw new RuntimeException(e1);
			}
			Integer code = errorResult.getCode();
			if (code != null && code == 30036) {
				return Lists.newArrayList();
			} else {
				throw e;
			}
		}
	}

	public OrderDetail placeAndQuery(PlaceOrder placeOrder) {
		OrderResult orderResult = this.placeOrder(placeOrder);
		orderResult.throwExceptionWhenError(String.format("ref=%s", placeOrder));
		String instrumentId = placeOrder.getInstrumentId();
		Long orderId = orderResult.getOrderId();
		Order order = this.queryOrderForFinalStatus(instrumentId, orderId);
		ExecutionQuery executionQuery = new ExecutionQuery();
		executionQuery.setInstrumentId(instrumentId);
		executionQuery.setOrderId(orderId);
		List<Execution> executions = null;
		executions = this.queryExecution(executionQuery);
		return new OrderDetail(order, executions);
	}

	public OrderDetail iocAndQuery(PlaceOrder placeOrder) {
		OrderResult orderResult = this.placeOrder(placeOrder);
		orderResult.throwExceptionWhenError(String.format("ref=%s", placeOrder));
		String instrumentId = placeOrder.getInstrumentId();
		Long orderId = orderResult.getOrderId();
		orderResult = this.cancelOrder(orderId, instrumentId, null);
		orderResult.throwExceptionWhenError(String.format("ref=%s", placeOrder));
		Order order = this.queryOrderForFinalStatus(instrumentId, orderId);
		ExecutionQuery executionQuery = new ExecutionQuery();
		executionQuery.setInstrumentId(instrumentId);
		executionQuery.setOrderId(orderId);
		List<Execution> executions = null;
		executions = this.queryExecution(executionQuery);
		return new OrderDetail(order, executions);
	}

}
