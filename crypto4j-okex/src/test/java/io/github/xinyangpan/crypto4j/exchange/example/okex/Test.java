package io.github.xinyangpan.crypto4j.exchange.example.okex;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import io.github.xinyangpan.crypto4j.core.ExchangeUtils;
import io.github.xinyangpan.crypto4j.okex.dto.enums.OrderType;
import io.github.xinyangpan.crypto4j.okex.dto.trade.Order;

public class Test {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Order order = new Order();
		order.setAmount(new BigDecimal("1.0"));
		order.setPrice(new BigDecimal("680"));
		order.setSymbol("btc_usdt");
		order.setType(OrderType.buy);
		// 
		Map<String, Object> value = (Map<String, Object>) ExchangeUtils.objectMapper().convertValue(order, Map.class);
		value.put("api_key", "c821db84-6fbd-11e4-a9e3-c86000d26d7c");
		// 
		String param = value.entrySet().stream()//
			.filter(e -> e.getValue() != null)// filter out null field
			.sorted(Comparator.comparing(Entry::getKey)).map(e -> String.format("%s=%s", e.getKey(), e.getValue()))// entry to string ${key}=${value}
			.collect(Collectors.joining("&"));// joining by &
		System.out.println(param);
	}

}
