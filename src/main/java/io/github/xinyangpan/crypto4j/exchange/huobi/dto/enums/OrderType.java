package io.github.xinyangpan.crypto4j.exchange.huobi.dto.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderType {
	// buy-market：市价买, sell-market：市价卖, buy-limit：限价买, sell-limit：限价卖, 
	// buy-ioc：IOC买单, sell-ioc：IOC卖单, buy-limit-maker, sell-limit-maker(详细说明见下)
	BUY_MARKET("buy-market"), SELL_MARKET("sell-market"), //
	BUY_LIMIT("buy-limit"), SELL_LIMIT("sell-limit"), //
	BUY_IOC("buy-ioc"), SELL_IOC("sell-ioc"), //
	BUY_LIMIT_MAKER("buy-limit-maker"), SELL_LIMIT_MAKER("sell-limit-maker");//

	private final String value;

	private OrderType(String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}

}
