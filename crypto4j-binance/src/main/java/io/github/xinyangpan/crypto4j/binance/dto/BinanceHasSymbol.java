package io.github.xinyangpan.crypto4j.binance.dto;

import io.github.xinyangpan.crypto4j.core.dto.HasSymbol;

public interface BinanceHasSymbol extends HasSymbol {

	@Override
	default String toSymbol() {
		return this.getStream().split("@")[0];
	}

	String getStream();

}
