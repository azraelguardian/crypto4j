package io.github.xinyangpan.crypto4j.binance.api;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;

import io.github.xinyangpan.crypto4j.binance.BinanceProperties;
import io.github.xinyangpan.crypto4j.binance.BinanceService;
import io.github.xinyangpan.crypto4j.binance.dto.rest.order.QueryAllOrdersRequset;
import io.github.xinyangpan.crypto4j.binance.dto.rest.order.QueryAllOrdersResponse;
import io.github.xinyangpan.crypto4j.core.RestProperties;
import io.github.xinyangpan.crypto4j.core.dto.api.OrderCheckResult;

public class BinanceOrderChecker {
	
	private BinanceService binanceService;
	
	public BinanceOrderChecker(String url, String key, String secret) {
		binanceService = new BinanceService(binanceProperties(url,key,secret));
	}
	
	public static BinanceProperties binanceProperties(String url,String key,String secret) {
		RestProperties restProperties = new RestProperties();
		restProperties.setRestBaseUrl(url);
		restProperties.setRestKey(key);
		restProperties.setRestSecret(secret);
		//
		BinanceProperties binanceProperties = new BinanceProperties();
		binanceProperties.setWebsocketMarketBaseUrl("wss://stream.binance.com:9443/stream?streams=");
		binanceProperties.setWebsocketUserStreamBaseUrl("wss://stream.binance.com:9443/ws/");
		binanceProperties.setRestProperties(restProperties);
		return binanceProperties;
	}
	
	public List<OrderCheckResult> queryOrders(String symbol, String fromDate, String toDate) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		ZoneId zone = ZoneId.systemDefault();
		
		QueryAllOrdersRequset queryRequest = new QueryAllOrdersRequset();
		queryRequest.setSymbol(symbol.toUpperCase());
		queryRequest.setStartTime(StringUtils.isEmpty(fromDate) ? null : LocalDateTime.parse(fromDate, df).atZone(zone).toEpochSecond());
		queryRequest.setEndTime(StringUtils.isEmpty(toDate) ? null : LocalDateTime.parse(toDate, df).atZone(zone).toEpochSecond());
		List<QueryAllOrdersResponse> orderList = binanceService.getBinanceRestService().queryAllOrders(queryRequest);
		

		
		
		List<OrderCheckResult> retList = Lists.newArrayList();
		OrderCheckResult checkResult;
		for(QueryAllOrdersResponse item : orderList) {
		    Instant instant = Instant.ofEpochMilli(item.getTime());
		    checkResult = new OrderCheckResult();
		    checkResult.setCreateTime(LocalDateTime.ofInstant(instant, zone).format(df));
		    checkResult.setSymbol(symbol);
		    checkResult.setOrderId(item.getOrderId().toString());
		    checkResult.setPrice(item.getPrice());
		    checkResult.setVolume(item.getOrigQty());
		    checkResult.setClientOrderId(item.getClientOrderId());
		    
		    retList.add(checkResult);
		}
		
		return retList;
	}
}
