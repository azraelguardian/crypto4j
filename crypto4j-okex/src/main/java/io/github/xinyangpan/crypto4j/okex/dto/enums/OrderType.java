package io.github.xinyangpan.crypto4j.okex.dto.enums;

public enum OrderType {
	buy, sell, buy_market, sell_market;

	public boolean isLimit() {
		return this == buy || this == sell;
	}

	public boolean isMarket() {
		return this == buy_market || this == sell_market;
	}

}
