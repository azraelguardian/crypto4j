package io.github.xinyangpan;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.okex3.dto.trade.Order;
import io.github.xinyangpan.crypto4j.okex3.dto.trade.QueryAllOrderRequest;
import io.github.xinyangpan.crypto4j.okex3.rest.Okex3RestProperties;
import io.github.xinyangpan.crypto4j.okex3.rest.Okex3RestService;

public class Okex3CheckUtil {
	static final String BTCUSDT = "BTC-USDT";
	static Okex3RestService okex3RestService;

	static {
		Okex3RestProperties restProperties = new Okex3RestProperties();
		restProperties.setRestBaseUrl("https://www.okex.com");
		restProperties.setRestKey(Crypto4jUtils.getSecret("okex.key"));
		restProperties.setRestSecret(Crypto4jUtils.getSecret("okex.secret"));
		restProperties.setPassphrase(Crypto4jUtils.getSecret("okex.passphrase"));
		
		okex3RestService = new Okex3RestService(restProperties);
	}
	
	public static void main(String[] args) {
		QueryAllOrderRequest queryRequest = new QueryAllOrderRequest();
		queryRequest.setInstrumentId(BTCUSDT);
		
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		ZoneId zone = ZoneId.systemDefault();
		List<Order> retList = okex3RestService.queryAllOrders(queryRequest);
		for(Order item : retList) {
			Instant instant = Instant.ofEpochMilli(item.getTimestamp().getTime());
			System.out.println(String.format("%s [%s]",LocalDateTime.ofInstant(instant, zone).format(df),item.toString()));
		}
	}
}
