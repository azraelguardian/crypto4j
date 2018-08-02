package io.github.xinyangpan.crypto4j.exchange.binance.dto;

import io.github.xinyangpan.crypto4j.common.dto.HasSymbol;

public interface BinanceHasSymbol extends HasSymbol {

	@Override
	default String toSymbol() {
		return this.getStream().split("@")[0];
	}

	String getStream();

}
