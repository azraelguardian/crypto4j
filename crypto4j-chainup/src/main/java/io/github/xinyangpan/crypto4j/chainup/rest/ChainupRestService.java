package io.github.xinyangpan.crypto4j.chainup.rest;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;

import io.github.xinyangpan.crypto4j.chainup.dto.ChainupResponse;
import io.github.xinyangpan.crypto4j.chainup.dto.data.AccountInfo;
import io.github.xinyangpan.crypto4j.chainup.dto.data.MassReplaceResult;
import io.github.xinyangpan.crypto4j.chainup.dto.data.OrderId;
import io.github.xinyangpan.crypto4j.chainup.dto.data.OrderQuery;
import io.github.xinyangpan.crypto4j.chainup.dto.data.Symbol;
import io.github.xinyangpan.crypto4j.chainup.dto.data.Tick;
import io.github.xinyangpan.crypto4j.chainup.dto.data.TradeResponse;
import io.github.xinyangpan.crypto4j.chainup.dto.request.Order;
import io.github.xinyangpan.crypto4j.chainup.dto.request.OrderIdAndSymbol;
import io.github.xinyangpan.crypto4j.chainup.dto.request.TradeParam;
import io.github.xinyangpan.crypto4j.chainup.dto.request.replace.MassReplace;
import io.github.xinyangpan.crypto4j.core.RestProperties;
import lombok.NonNull;

public class ChainupRestService extends BaseChainupRestService {
	private static TypeReference<ChainupResponse<List<Symbol>>> SYMBOLS_RESULT = new TypeReference<ChainupResponse<List<Symbol>>>() {};
	private static TypeReference<ChainupResponse<Tick>> TICK_RESULT = new TypeReference<ChainupResponse<Tick>>() {};
	private static TypeReference<ChainupResponse<AccountInfo>> ACCOUNT_INFO_RESULT = new TypeReference<ChainupResponse<AccountInfo>>() {};
	private static TypeReference<ChainupResponse<OrderId>> ORDER_ID_RESULT = new TypeReference<ChainupResponse<OrderId>>() {};
	private static TypeReference<ChainupResponse<Void>> CANCEL_ORDER_RESULT = new TypeReference<ChainupResponse<Void>>() {};
	private static TypeReference<ChainupResponse<OrderQuery>> ORDER_INFO_RESULT = new TypeReference<ChainupResponse<OrderQuery>>() {};
	private static TypeReference<ChainupResponse<TradeResponse>> TRADE_RESPONSE_REULST = new TypeReference<ChainupResponse<TradeResponse>>() {};
	private static TypeReference<ChainupResponse<MassReplaceResult>> MASS_REPLACE_RESULT = new TypeReference<ChainupResponse<MassReplaceResult>>() {};

	// TypeReference
	public ChainupRestService(RestProperties restProperties) {
		super(restProperties);
	}

	public ChainupResponse<List<Symbol>> getAllSymbols() {
		String url = this.getUrl("/exchange-open-api/open/api/common/symbols");
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		return exchange(url, HttpMethod.GET, requestEntity, SYMBOLS_RESULT);
	}

	public ChainupResponse<Tick> getTick(@NonNull String symbol) {
		String url = this.getUrl("/exchange-open-api/open/api/get_ticker?symbol=%s", symbol);
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		return exchange(url, HttpMethod.GET, requestEntity, TICK_RESULT);
	}

	public ChainupResponse<AccountInfo> getAccountInfo() {
		String url = this.getUrl("/exchange-open-api/open/api/user/account?%s", toSignedRequestParam(null));
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		return exchange(url, HttpMethod.GET, requestEntity, ACCOUNT_INFO_RESULT);
	}

	public ChainupResponse<MassReplaceResult> massReplace(@NonNull MassReplace massReplace) {
		String url = this.getUrl("/exchange-open-api/open/api/mass_replace");
		HashMap<String, String> map = Maps.newHashMap();
		map.put("symbol", massReplace.getSymbol());
		if (massReplace.getMassPlace() != null) {
			map.put("mass_place", toJson(massReplace.getMassPlace()));
		}
		if (massReplace.getMassCancel() != null) {
			map.put("mass_cancel", toJson(massReplace.getMassCancel()));
		}
		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(map);
		return exchange(url, HttpMethod.POST, requestEntity, MASS_REPLACE_RESULT);
	}

	public ChainupResponse<OrderId> createOrder(@NonNull Order order) {
		String url = this.getUrl("/exchange-open-api/open/api/create_order");
		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(order);
		return exchange(url, HttpMethod.POST, requestEntity, ORDER_ID_RESULT);
	}

	public ChainupResponse<Void> cancelOrder(long orderId, @NonNull String symbol) {
		String url = this.getUrl("/exchange-open-api/open/api/cancel_order");
		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(new OrderIdAndSymbol(orderId, symbol));
		return exchange(url, HttpMethod.POST, requestEntity, CANCEL_ORDER_RESULT);
	}

	public ChainupResponse<OrderQuery> getOrderInfo(long orderId, @NonNull String symbol) {
		String url = this.getUrl("/exchange-open-api/open/api/order_info?%s", toSignedRequestParam(new OrderIdAndSymbol(orderId, symbol)));
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		return exchange(url, HttpMethod.GET, requestEntity, ORDER_INFO_RESULT);
	}

	public ChainupResponse<TradeResponse> getAllTrades(@NonNull String symbol) {
		TradeParam tradeParam = new TradeParam();
		tradeParam.setSymbol(symbol);
		return this.getAllTrades(tradeParam);
	}

	public ChainupResponse<TradeResponse> getAllTrades(@NonNull TradeParam tradeParam) {
		String url = this.getUrl("/exchange-open-api/open/api/all_trade?%s", toSignedRequestParam(tradeParam));
		HttpEntity<String> requestEntity = this.requestEntityWithUserAgent();
		return exchange(url, HttpMethod.GET, requestEntity, TRADE_RESPONSE_REULST);
	}

}
