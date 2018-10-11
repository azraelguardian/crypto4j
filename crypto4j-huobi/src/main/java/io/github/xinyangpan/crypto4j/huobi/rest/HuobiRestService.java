package io.github.xinyangpan.crypto4j.huobi.rest;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Preconditions;

import io.github.xinyangpan.crypto4j.core.RestProperties;
import io.github.xinyangpan.crypto4j.core.UnknownOrderException;
import io.github.xinyangpan.crypto4j.huobi.dto.account.AccountInfo;
import io.github.xinyangpan.crypto4j.huobi.dto.account.BalanceInfo;
import io.github.xinyangpan.crypto4j.huobi.dto.common.HuobiRestChannelResponse;
import io.github.xinyangpan.crypto4j.huobi.dto.common.HuobiRestResponse;
import io.github.xinyangpan.crypto4j.huobi.dto.enums.OrderState;
import io.github.xinyangpan.crypto4j.huobi.dto.market.depth.Depth;
import io.github.xinyangpan.crypto4j.huobi.dto.market.kline.Kline;
import io.github.xinyangpan.crypto4j.huobi.dto.market.kline.KlineParam;
import io.github.xinyangpan.crypto4j.huobi.dto.market.rest.Symbol;
import io.github.xinyangpan.crypto4j.huobi.dto.market.rest.Ticker;
import io.github.xinyangpan.crypto4j.huobi.dto.trade.Execution;
import io.github.xinyangpan.crypto4j.huobi.dto.trade.Order;
import io.github.xinyangpan.crypto4j.huobi.dto.trade.OrderDetail;
import io.github.xinyangpan.crypto4j.huobi.dto.trade.OrderResult;
import lombok.SneakyThrows;

public class HuobiRestService extends BaseHuobiRestService {
	private static final Logger log = LoggerFactory.getLogger(HuobiRestService.class);
	private static ParameterizedTypeReference<HuobiRestResponse<List<AccountInfo>>> ACCOUNT_INFO = new ParameterizedTypeReference<HuobiRestResponse<List<AccountInfo>>>() {};
	private static ParameterizedTypeReference<HuobiRestResponse<BalanceInfo>> BALANCE_INFO = new ParameterizedTypeReference<HuobiRestResponse<BalanceInfo>>() {};
	private static ParameterizedTypeReference<HuobiRestResponse<String>> ORDER_RESPONSE = new ParameterizedTypeReference<HuobiRestResponse<String>>() {};
	private static ParameterizedTypeReference<HuobiRestResponse<OrderResult>> ORDER_RESULT = new ParameterizedTypeReference<HuobiRestResponse<OrderResult>>() {};
	private static ParameterizedTypeReference<HuobiRestResponse<List<Execution>>> EXECUTION_RESULT = new ParameterizedTypeReference<HuobiRestResponse<List<Execution>>>() {};
	private static ParameterizedTypeReference<HuobiRestResponse<List<Symbol>>> SYMBOL_RESULT = new ParameterizedTypeReference<HuobiRestResponse<List<Symbol>>>() {};
	private static ParameterizedTypeReference<HuobiRestResponse<List<Ticker>>> TICKER_RESULT = new ParameterizedTypeReference<HuobiRestResponse<List<Ticker>>>() {};
	private static TypeReference<HuobiRestChannelResponse<Depth>> DEPTH_RESULT = new TypeReference<HuobiRestChannelResponse<Depth>>() {};
	private static TypeReference<HuobiRestChannelResponse<List<Kline>>> KLINE_RESULT = new TypeReference<HuobiRestChannelResponse<List<Kline>>>() {};
	
	public HuobiRestService(RestProperties restProperties) {
		super(restProperties);
	}

	public HuobiRestChannelResponse<Depth> depth(String symbol) {
		return this.depth(symbol, "step0");
	}

	public HuobiRestChannelResponse<Depth> depth(String symbol, String type) {
		String url = this.getUrl("/market/depth?symbol=%s&type=%s", symbol, type);
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		String body = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();
		return this.readValue(body, DEPTH_RESULT);
	}

	public HuobiRestChannelResponse<List<Kline>> kline(KlineParam klineParam) {
		String url = this.getUrl("/market/history/kline?%s", this.toRequestParam(klineParam));
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		String body = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();
		log.debug("{}", body);
		return this.readValue(body, KLINE_RESULT);
	}

	public HuobiRestResponse<List<Symbol>> symbols() {
		String url = this.getUrl("/v1/common/symbols");
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		return restTemplate.exchange(url, HttpMethod.GET, requestEntity, SYMBOL_RESULT).getBody();
	}

	public HuobiRestResponse<List<Ticker>> tickers() {
		String url = this.getUrl("/market/tickers");
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		return restTemplate.exchange(url, HttpMethod.GET, requestEntity, TICKER_RESULT).getBody();
	}

	public HuobiRestResponse<List<AccountInfo>> accounts() {
		URI uri = this.getUrlWithSignature("/v1/account/accounts", RequestType.GET, null);
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		HuobiRestResponse<List<AccountInfo>> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, ACCOUNT_INFO).getBody();
		log.debug("{}", response);
		return response;
	}

	public HuobiRestResponse<BalanceInfo> balanceInfo(Long accountId) {
		URI uri = this.getUrlWithSignature(String.format("/v1/account/accounts/%s/balance", accountId), RequestType.GET, null);
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		HuobiRestResponse<BalanceInfo> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, BALANCE_INFO).getBody();
		log.debug("{}", response);
		return response;
	}

	public HuobiRestResponse<String> placeOrder(Order order) {
		log.debug("{}", order);
		URI url = this.getUrlWithSignature("/v1/order/orders/place", RequestType.POST, null);
		HttpEntity<String> requestEntity = this.buildPostRequestEntity(order);
		HuobiRestResponse<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, ORDER_RESPONSE).getBody();
		log.debug("{}", response);
		return response;
	}

	public HuobiRestResponse<OrderResult> queryOrder(String orderId) {
		log.debug("{}", orderId);
		URI url = this.getUrlWithSignature("/v1/order/orders/" + orderId, RequestType.GET, null);
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		HuobiRestResponse<OrderResult> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, ORDER_RESULT).getBody();
		log.debug("{}", response);
		return response;
	}

	public HuobiRestResponse<List<Execution>> queryExecution(String orderId) {
		log.debug("{}", orderId);
		URI url = this.getUrlWithSignature(String.format("/v1/order/orders/%s/matchresults", orderId), RequestType.GET, null);
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		HuobiRestResponse<List<Execution>> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, EXECUTION_RESULT).getBody();
		log.debug("{}", response);
		return response;
	}

	public HuobiRestResponse<List<Execution>> queryExecution(String orderId, int attempt) {
		HuobiRestResponse<List<Execution>> response = null;
		try {
			for (int i = 0; i < attempt; i++) {
				response = this.queryExecution(orderId);
				if (response.isSuccessful()) {
					return response;
				}
				log.debug("retry ... RestResponse[{}]: {}", i, response);
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {}
		Preconditions.checkNotNull(response);
		response.throwExceptionWhenError();
		return response;
	}

	public OrderDetail placeAndQueryDetails(Order order) {
		String orderId = this.placeOrder(order).fethData();
		switch (order.getType()) {
		case BUY_IOC:
		case SELL_IOC:
		case BUY_MARKET:
		case SELL_MARKET:
			return queryOrderDetailForFinalState(orderId, 3);
		default:
			return this.queryOrderDetail(orderId);
		}
	}

	@SneakyThrows
	public OrderDetail queryOrderDetailForFinalState(String orderId, int attempt) {
		OrderDetail orderDetail = null;
		for (int i = 0; i < attempt; i++) {
			Thread.sleep(100 * (i + 1));
			orderDetail = this.queryOrderDetail(orderId);
			log.debug("orderDetail[{}]: {}", i, orderDetail);
			OrderState orderState = orderDetail.getOrderResult().getState();
			if (orderState == OrderState.SUBMITTING || orderState == OrderState.SUBMITTED) {
				continue;
			} else {
				return orderDetail;
			}
		}
		throw new UnknownOrderException(orderId, "No valid order detail returned. ref=" + orderDetail);
	}

	public OrderDetail queryOrderDetail(String orderId) {
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setOrderId(orderId);
		OrderResult orderResult = this.queryOrder(orderId).fethData();
		orderDetail.setOrderResult(orderResult);
		if (orderResult.getFieldAmount().compareTo(BigDecimal.ZERO) > 0) {
			List<Execution> executions = this.queryExecution(orderId, 5).fethData();
			orderDetail.setExecutions(executions);
		}
		// 
		return orderDetail;
	}

}
