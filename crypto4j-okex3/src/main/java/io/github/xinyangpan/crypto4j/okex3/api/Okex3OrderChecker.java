package io.github.xinyangpan.crypto4j.okex3.api;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.google.common.collect.Lists;

import io.github.xinyangpan.crypto4j.core.dto.api.OrderCheckResult;
import io.github.xinyangpan.crypto4j.okex3.dto.trade.Order;
import io.github.xinyangpan.crypto4j.okex3.dto.trade.QueryAllOrderRequest;
import io.github.xinyangpan.crypto4j.okex3.rest.Okex3RestProperties;
import io.github.xinyangpan.crypto4j.okex3.rest.Okex3RestService;

public class Okex3OrderChecker {
	static Okex3RestService okex3RestService;

	public Okex3OrderChecker(String url,String key,String secret,String passphrase) {
		Okex3RestProperties restProperties = new Okex3RestProperties();
		restProperties.setRestBaseUrl(url);
		restProperties.setRestKey(key);
		restProperties.setRestSecret(secret);
		restProperties.setPassphrase(passphrase);
		
		okex3RestService = new Okex3RestService(restProperties);
	}
	
	public List<OrderCheckResult> queryOrders(String symbol, String fromOrderId, String toOrderId) {
		QueryAllOrderRequest queryRequest = new QueryAllOrderRequest();
		queryRequest.setInstrumentId(symbol);
		queryRequest.setFrom(fromOrderId);
		queryRequest.setTo(toOrderId);
		
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		ZoneId zone = ZoneId.systemDefault();
		List<Order> orderList = okex3RestService.queryAllOrders(queryRequest);
		
		List<OrderCheckResult> retList = Lists.newArrayList();
		OrderCheckResult checkResult;
		for(Order item : orderList) {
			Instant instant = Instant.ofEpochMilli(item.getTimestamp().getTime());
		    checkResult = new OrderCheckResult();
		    checkResult.setCreateTime(LocalDateTime.ofInstant(instant, zone).format(df));
		    checkResult.setSymbol(symbol);
		    checkResult.setOrderId(item.getOrderId().toString());
		    checkResult.setPrice(item.getPrice());
		    checkResult.setVolume(item.getSize());
		    
		    retList.add(checkResult);
		}
		
		
		return retList;
	}
}
