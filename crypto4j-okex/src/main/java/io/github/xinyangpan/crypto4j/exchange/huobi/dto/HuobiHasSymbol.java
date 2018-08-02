package io.github.xinyangpan.crypto4j.exchange.huobi.dto;

import io.github.xinyangpan.crypto4j.common.dto.HasSymbol;

public interface HuobiHasSymbol extends HasSymbol {

	@Override
	default String toSymbol() {
		return this.getCh().split("\\.")[1];
	}

	String getCh();

}
