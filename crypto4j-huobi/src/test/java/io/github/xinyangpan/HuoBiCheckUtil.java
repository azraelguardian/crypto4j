package io.github.xinyangpan;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import io.github.xinyangpan.crypto4j.core.RestProperties;
import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.huobi.dto.trade.OrderResult;
import io.github.xinyangpan.crypto4j.huobi.dto.trade.QueryAllOrdersRequest;
import io.github.xinyangpan.crypto4j.huobi.rest.HuobiRestService;

public class HuoBiCheckUtil {
	private static final HuobiRestService huobiRestService;

	private static final String BTCUSDT = "btcusdt";

	static {
		RestProperties restProperties = new RestProperties();
		restProperties.setRestBaseUrl("https://api.huobi.pro");
		restProperties.setRestKey(Crypto4jUtils.getSecret("huobi.key"));
		restProperties.setRestSecret(Crypto4jUtils.getSecret("huobi.secret"));
		
		System.out.println(restProperties);
		// 
		huobiRestService = new HuobiRestService(restProperties);
	}
	
	
	public static void main(String[] args) {
		
		QueryAllOrdersRequest queryRequest = QueryAllOrdersRequest.builder().symbol(BTCUSDT).build();
		
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		ZoneId zone = ZoneId.systemDefault();
		List<OrderResult> retList = huobiRestService.queryAllOrders(queryRequest).fethData();
		System.out.println(retList.size());
		for(OrderResult item : retList) {
			Instant instant = Instant.ofEpochMilli(item.getCreatedAt());
		    System.out.println(String.format("%s [%s]",LocalDateTime.ofInstant(instant, zone).format(df),item.toString()));
		}
	}
}
