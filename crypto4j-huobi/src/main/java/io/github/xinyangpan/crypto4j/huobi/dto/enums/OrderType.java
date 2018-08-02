package io.github.xinyangpan.crypto4j.huobi.dto.enums;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderType {
	// buy-market：市价买, sell-market：市价卖, buy-limit：限价买, sell-limit：限价卖, 
	// buy-ioc：IOC买单, sell-ioc：IOC卖单, buy-limit-maker, sell-limit-maker(详细说明见下)
	BUY_MARKET("buy-market"), SELL_MARKET("sell-market"), //
	BUY_LIMIT("buy-limit"), SELL_LIMIT("sell-limit"), //
	BUY_IOC("buy-ioc"), SELL_IOC("sell-ioc"), //
	BUY_LIMIT_MAKER("buy-limit-maker"), SELL_LIMIT_MAKER("sell-limit-maker");//

	//
	private static final Map<String, OrderType> INDEX = new HashMap<>();
	
	static {
		OrderType[] values = values();
		for (OrderType v : values) {
			INDEX.put(v.value, v);
		}
	}

	private final String value;

	private OrderType(String value) {
		this.value = value;
	}

	@JsonCreator
	public static OrderType parse(String value) {
		return INDEX.get(value);
	}

	@JsonValue
	public String getValue() {
		return value;
	}

}
