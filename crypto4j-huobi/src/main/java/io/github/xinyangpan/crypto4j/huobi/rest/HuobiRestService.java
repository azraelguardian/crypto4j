package io.github.xinyangpan.crypto4j.huobi.rest;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.core.type.TypeReference;

import io.github.xinyangpan.crypto4j.core.RestProperties;
import io.github.xinyangpan.crypto4j.core.UnknownOrderException;
import io.github.xinyangpan.crypto4j.huobi.dto.account.AccountInfo;
import io.github.xinyangpan.crypto4j.huobi.dto.account.BalanceInfo;
import io.github.xinyangpan.crypto4j.huobi.dto.common.HuobiRestChannelResponse;
import io.github.xinyangpan.crypto4j.huobi.dto.common.HuobiRestResponse;
import io.github.xinyangpan.crypto4j.huobi.dto.market.depth.Depth;
import io.github.xinyangpan.crypto4j.huobi.dto.market.kline.Kline;
import io.github.xinyangpan.crypto4j.huobi.dto.market.kline.KlineParam;
import io.github.xinyangpan.crypto4j.huobi.dto.market.rest.Symbol;
import io.github.xinyangpan.crypto4j.huobi.dto.market.rest.Ticker;
import io.github.xinyangpan.crypto4j.huobi.dto.trade.Execution;
import io.github.xinyangpan.crypto4j.huobi.dto.trade.Order;
import io.github.xinyangpan.crypto4j.huobi.dto.trade.OrderDetail;
import io.github.xinyangpan.crypto4j.huobi.dto.trade.OrderResult;
import io.github.xinyangpan.crypto4j.huobi.dto.trade.QueryAllOrdersRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HuobiRestService extends BaseHuobiRestService {
	private static TypeReference<HuobiRestResponse<List<AccountInfo>>> ACCOUNT_INFO = new TypeReference<HuobiRestResponse<List<AccountInfo>>>() {};
	private static TypeReference<HuobiRestResponse<BalanceInfo>> BALANCE_INFO = new TypeReference<HuobiRestResponse<BalanceInfo>>() {};
	private static TypeReference<HuobiRestResponse<String>> ORDER_RESPONSE = new TypeReference<HuobiRestResponse<String>>() {};
	private static TypeReference<HuobiRestResponse<OrderResult>> ORDER_RESULT = new TypeReference<HuobiRestResponse<OrderResult>>() {};
	private static TypeReference<HuobiRestResponse<List<Execution>>> EXECUTION_RESULT = new TypeReference<HuobiRestResponse<List<Execution>>>() {};
	private static TypeReference<HuobiRestResponse<List<Symbol>>> SYMBOL_RESULT = new TypeReference<HuobiRestResponse<List<Symbol>>>() {};
	private static TypeReference<HuobiRestResponse<List<Ticker>>> TICKER_RESULT = new TypeReference<HuobiRestResponse<List<Ticker>>>() {};
	private static TypeReference<HuobiRestChannelResponse<Depth>> DEPTH_RESULT = new TypeReference<HuobiRestChannelResponse<Depth>>() {};
	private static TypeReference<HuobiRestChannelResponse<List<Kline>>> KLINE_RESULT = new TypeReference<HuobiRestChannelResponse<List<Kline>>>() {};
	private static TypeReference<HuobiRestResponse<List<OrderResult>>> ALL_ORDERS_RESULT = new TypeReference<HuobiRestResponse<List<OrderResult>>>() {};

	public HuobiRestService(RestProperties restProperties) {
		super(restProperties);
	}

	public HuobiRestChannelResponse<Depth> depth(String symbol) {
		return this.depth(symbol, "step0");
	}

	public HuobiRestChannelResponse<Depth> depth(String symbol, String type) {
		String url = this.getUrl("/market/depth?symbol=%s&type=%s", symbol, type);
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		return exchange(url, HttpMethod.GET, requestEntity, DEPTH_RESULT);
	}

	public HuobiRestChannelResponse<List<Kline>> kline(KlineParam klineParam) {
		String url = this.getUrl("/market/history/kline?%s", this.toRequestParam(klineParam));
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		return exchange(url, HttpMethod.GET, requestEntity, KLINE_RESULT);
	}

	public HuobiRestResponse<List<Symbol>> symbols() {
		String url = this.getUrl("/v1/common/symbols");
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		return exchange(url, HttpMethod.GET, requestEntity, SYMBOL_RESULT);
	}

	public HuobiRestResponse<List<Ticker>> tickers() {
		String url = this.getUrl("/market/tickers");
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		return exchange(url, HttpMethod.GET, requestEntity, TICKER_RESULT);
	}

	public HuobiRestResponse<List<AccountInfo>> accounts() {
		URI uri = this.getUrlWithSignature("/v1/account/accounts", RequestType.GET, null);
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		log.debug("requesting uri: {}", uri);
		return exchange(uri, HttpMethod.GET, requestEntity, ACCOUNT_INFO);
	}

	public HuobiRestResponse<BalanceInfo> balanceInfo(Long accountId) {
		URI uri = this.getUrlWithSignature(String.format("/v1/account/accounts/%s/balance", accountId), RequestType.GET, null);
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		return exchange(uri, HttpMethod.GET, requestEntity, BALANCE_INFO);
	}

	public HuobiRestResponse<String> placeOrder(Order order) {
		log.debug("{}", order);
		URI url = this.getUrlWithSignature("/v1/order/orders/place", RequestType.POST, null);
		HttpEntity<String> requestEntity = this.buildPostRequestEntity(order);
		return exchange(url, HttpMethod.POST, requestEntity, ORDER_RESPONSE);
	}

	public HuobiRestResponse<OrderResult> queryOrder(String orderId) {
		log.debug("{}", orderId);
		URI url = this.getUrlWithSignature("/v1/order/orders/" + orderId, RequestType.GET, null);
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		return exchange(url, HttpMethod.GET, requestEntity, ORDER_RESULT);
	}
	
	public HuobiRestResponse<List<OrderResult>> queryAllOrders(QueryAllOrdersRequest queryRequest) {
		URI url = this.getUrlWithSignature("/v1/order/orders",RequestType.GET, queryRequest);
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		return exchange(url, HttpMethod.GET, requestEntity, ALL_ORDERS_RESULT);
	}

	public HuobiRestResponse<List<Execution>> queryExecution(String orderId) {
		log.debug("{}", orderId);
		URI url = this.getUrlWithSignature(String.format("/v1/order/orders/%s/matchresults", orderId), RequestType.GET, null);
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		return exchange(url, HttpMethod.GET, requestEntity, EXECUTION_RESULT);
	}

	private BigDecimal getExecutionsFilledAmount(List<Execution> executions) {
		BigDecimal execFilledAmount = BigDecimal.ZERO;
		if (executions != null) {
			for (Execution execution : executions) {
				execFilledAmount = execFilledAmount.add(execution.getFilledAmount());
			}
		}
		return execFilledAmount;
	}
	
	public HuobiRestResponse<List<Execution>> queryExecution(String orderId, int attempt,BigDecimal executedVolume) {
		HuobiRestResponse<List<Execution>> response = null;
		try {
			for (int i = 0; i < attempt; i++) {
				response = this.queryExecution(orderId);
				if (response.isSuccessful()&&getExecutionsFilledAmount(response.getData()).compareTo(executedVolume)>=0) {
					return response;
				}
				log.debug("retry ... RestResponse[{}]: {}", i, response);
				if(i<attempt-1)Thread.sleep(1000);
			}
		} catch (InterruptedException e) {}
		
		throw new UnknownOrderException(orderId, "No valid Execution detail returned. orderId=" + orderId);
	}

	public OrderDetail placeAndQueryDetails(Order order) {
		String orderId = this.placeOrder(order).fethData();
		try {
			switch (order.getType()) {
			case BUY_IOC:
			case SELL_IOC:
			case BUY_MARKET:
			case SELL_MARKET:
				return queryOrderDetailForFinalState(orderId, 10);
			default:
				return this.queryOrderDetail(orderId);
			}
		} catch (UnknownOrderException e) {
			throw e;
		} catch (Exception e) {
			throw new UnknownOrderException(orderId, " fetch order detail failed ");
		}
	}

	@SneakyThrows
	public OrderDetail queryOrderDetailForFinalState(String orderId, int attempt) {
		OrderDetail orderDetail = null;
		for (int i = 0; i < attempt; i++) {
			Thread.sleep(100 * (i + 1));
			orderDetail = this.queryOrderDetail(orderId);
			log.debug("orderDetail[{}]: {}", i, orderDetail);
			if (orderDetail.isInFinalState()) {
				if(orderDetail.getOrderResult().getFieldAmount()!=null&&orderDetail.getOrderResult().getFieldAmount().compareTo(BigDecimal.ZERO) > 0) {
					List<Execution> executions = this.queryExecution(orderId, 5,orderDetail.getOrderResult().getFieldAmount()).fethData();
					orderDetail.setExecutions(executions);
				}
				return orderDetail;
			} else {
				continue;
			}
		}
		throw new UnknownOrderException(orderId, "No valid order detail returned. ref=" + orderDetail);
	}

	public OrderDetail queryOrderDetail(String orderId) {
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setOrderId(orderId);
		OrderResult orderResult = this.queryOrder(orderId).fethData();
		orderDetail.setOrderResult(orderResult);
		// 
		return orderDetail;
	}

}
