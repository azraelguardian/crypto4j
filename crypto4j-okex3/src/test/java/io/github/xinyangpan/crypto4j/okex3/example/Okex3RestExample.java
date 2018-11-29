package io.github.xinyangpan.crypto4j.okex3.example;

import java.math.BigDecimal;

import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.okex3.dto.enums.MarginTrading;
import io.github.xinyangpan.crypto4j.okex3.dto.enums.OrderType;
import io.github.xinyangpan.crypto4j.okex3.dto.enums.Side;
import io.github.xinyangpan.crypto4j.okex3.dto.trade.PlaceOrder;
import io.github.xinyangpan.crypto4j.okex3.rest.Okex3RestProperties;
import io.github.xinyangpan.crypto4j.okex3.rest.Okex3RestService;

public class Okex3RestExample {
	static final String BTCUSDT = "BTC-USDT";
	static Okex3RestService okex3RestService;

	static {
		Okex3RestProperties restProperties = new Okex3RestProperties();
		restProperties.setRestBaseUrl("https://www.okex.com");
		restProperties.setRestKey(Crypto4jUtils.getSecret("okex.key"));
		restProperties.setRestSecret(Crypto4jUtils.getSecret("okex.secret"));
		restProperties.setPassphrase(Crypto4jUtils.getSecret("okex.passphrase"));
		// 
		okex3RestService = new Okex3RestService(restProperties);
	}

	public static void placeOrder() {
		PlaceOrder placeOrder = new PlaceOrder();
		placeOrder.setInstrumentId(BTCUSDT);
		placeOrder.setMarginTrading(MarginTrading.C2C);
		placeOrder.setSide(Side.buy);
		placeOrder.setType(OrderType.limit);
		placeOrder.setPrice(new BigDecimal("3500"));
		placeOrder.setSize(new BigDecimal("0.01"));
		System.out.println(okex3RestService.placeOrder(placeOrder));
	}

	public static void placeAndQuery() {
		PlaceOrder placeOrder = new PlaceOrder();
		placeOrder.setInstrumentId(BTCUSDT);
		placeOrder.setMarginTrading(MarginTrading.C2C);
		placeOrder.setSide(Side.sell);
		placeOrder.setType(OrderType.limit);
		placeOrder.setPrice(new BigDecimal("4700"));
		placeOrder.setSize(new BigDecimal("0.001"));
		System.out.println(okex3RestService.placeAndQuery(placeOrder));
	}

	public static void iocAndQuery() {
		PlaceOrder placeOrder = new PlaceOrder();
		placeOrder.setInstrumentId(BTCUSDT);
		placeOrder.setMarginTrading(MarginTrading.C2C);
		placeOrder.setSide(Side.sell);
		placeOrder.setType(OrderType.limit);
		placeOrder.setPrice(new BigDecimal("3700"));
		placeOrder.setSize(new BigDecimal("0.001"));
		System.out.println(okex3RestService.iocAndQuery(placeOrder));
	}

	public static void main(String[] args) {
		try {
//			System.out.println(okex3RestService.account());
			iocAndQuery();
			// 1884764826503168
//			System.out.println(okex3RestService.queryOrder("BTC-USDT", 1884872990861312L));
		} catch (org.springframework.web.client.HttpClientErrorException e) {
			System.out.println(e.getResponseBodyAsString());
		}
	}

}
