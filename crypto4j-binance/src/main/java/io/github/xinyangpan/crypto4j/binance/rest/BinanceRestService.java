package io.github.xinyangpan.crypto4j.binance.rest;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import com.google.common.base.MoreObjects;

import io.github.xinyangpan.crypto4j.binance.dto.depth.Depth;
import io.github.xinyangpan.crypto4j.binance.dto.rest.account.Account;
import io.github.xinyangpan.crypto4j.binance.dto.rest.account.QueryTradeRequest;
import io.github.xinyangpan.crypto4j.binance.dto.rest.common.BaseRequest;
import io.github.xinyangpan.crypto4j.binance.dto.rest.market.BookTicker;
import io.github.xinyangpan.crypto4j.binance.dto.rest.market.Kline;
import io.github.xinyangpan.crypto4j.binance.dto.rest.market.KlineParam;
import io.github.xinyangpan.crypto4j.binance.dto.rest.market.ServerTime;
import io.github.xinyangpan.crypto4j.binance.dto.rest.order.PlaceOrderRequest;
import io.github.xinyangpan.crypto4j.binance.dto.rest.order.PlaceOrderResponse;
import io.github.xinyangpan.crypto4j.binance.dto.rest.order.QueryOrderRequest;
import io.github.xinyangpan.crypto4j.core.RestProperties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BinanceRestService extends BaseBinanceRestService {
	private ParameterizedTypeReference<List<Kline>> KLINES = new ParameterizedTypeReference<List<Kline>>() {};

	public BinanceRestService(RestProperties restProperties) {
		super(restProperties);
	}

	public ServerTime serverTime() {
		String url = this.getUrl("/api/v1/time");
		return restTemplate.getForObject(url, ServerTime.class);
	}

	public List<Kline> kline(KlineParam klineParam) {
		String url = this.getUrl("/api/v1/klines?%s", this.toRequestParam(klineParam));
		return restTemplate.exchange(url, HttpMethod.GET, null, KLINES).getBody();
	}

	public BookTicker bookTicker(String symbol) {
		String url = this.getUrl("/api/v3/ticker/bookTicker?symbol=%s", symbol);
		return restTemplate.getForObject(url, BookTicker.class);
	}

	public Depth depth(String symbol, Integer limit) {
		limit = MoreObjects.firstNonNull(limit, 20);
		String url = this.getUrl("/api/v1/depth?symbol=%s&limit=%s", symbol, limit);
		return restTemplate.getForObject(url, Depth.class);
	}

	public Account account() {
		log.debug("account");
		String url = this.getUrl("/api/v3/account?%s", this.toSignedRequestParam(new BaseRequest()));
		HttpEntity<String> requestEntity = this.buildRequestEntity(null, false);
		return restTemplate.exchange(url, HttpMethod.GET, requestEntity, Account.class).getBody();
	}

	public PlaceOrderResponse placeOrder(PlaceOrderRequest placeOrderRequest) {
		log.debug("{}", placeOrderRequest);
		String url = this.getUrl("/api/v3/order");
		HttpEntity<String> requestEntity = this.buildSignedRequestEntity(placeOrderRequest);
		return restTemplate.postForObject(url, requestEntity, PlaceOrderResponse.class);
	}

	public String queryOrder(QueryOrderRequest queryOrderRequest) {
		log.debug("{}", queryOrderRequest);
		String url = this.getUrl("/api/v3/order?%s", this.toSignedRequestParam(queryOrderRequest));
		HttpEntity<String> requestEntity = this.buildRequestEntity(null, false);
		return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();
	}

	public String queryTrade(QueryTradeRequest queryTradeRequest) {
		log.debug("{}", queryTradeRequest);
		String url = this.getUrl("/api/v3/myTrades?%s", this.toSignedRequestParam(queryTradeRequest));
		HttpEntity<String> requestEntity = this.buildRequestEntity(null, false);
		return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();
	}

}
