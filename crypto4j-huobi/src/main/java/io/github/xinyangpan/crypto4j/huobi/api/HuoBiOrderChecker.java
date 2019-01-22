package io.github.xinyangpan.crypto4j.huobi.api;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.google.common.collect.Lists;

import io.github.xinyangpan.crypto4j.core.RestProperties;
import io.github.xinyangpan.crypto4j.core.dto.api.OrderCheckResult;
import io.github.xinyangpan.crypto4j.huobi.dto.trade.OrderResult;
import io.github.xinyangpan.crypto4j.huobi.dto.trade.QueryAllOrdersRequest;
import io.github.xinyangpan.crypto4j.huobi.rest.HuobiRestService;

public class HuoBiOrderChecker {

	private HuobiRestService huobiRestService;

	public HuoBiOrderChecker(String url, String key, String secret) {
		RestProperties restProperties = new RestProperties();
		restProperties.setRestBaseUrl(url);
		restProperties.setRestKey(key);
		restProperties.setRestSecret(secret);

		huobiRestService = new HuobiRestService(restProperties);
	}

	public List<OrderCheckResult> queryOrders(String symbol, String fromDate, String toDate) {

		QueryAllOrdersRequest queryRequest = QueryAllOrdersRequest.builder().symbol(symbol).startDate(fromDate)
				.endDate(toDate).build();
		
		List<OrderResult> orderList = huobiRestService.queryAllOrders(queryRequest).fethData();
		
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		ZoneId zone = ZoneId.systemDefault();
		List<OrderCheckResult> retList = Lists.newArrayList();
		OrderCheckResult checkResult;
		for(OrderResult item : orderList) {
			Instant instant = Instant.ofEpochMilli(item.getCreatedAt());
		    //System.out.println(String.format("%s [%s]",LocalDateTime.ofInstant(instant, zone).format(df),item.toString()));
		    checkResult = new OrderCheckResult();
		    checkResult.setCreateTime(LocalDateTime.ofInstant(instant, zone).format(df));
		    checkResult.setSymbol(symbol);
		    checkResult.setOrderId(item.getId());
		    checkResult.setPrice(item.getPrice());
		    checkResult.setVolume(item.getAmount());
		    retList.add(checkResult);
		}

		return retList;
	}

}
