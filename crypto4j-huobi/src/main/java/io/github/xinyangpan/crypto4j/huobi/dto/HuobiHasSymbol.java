package io.github.xinyangpan.crypto4j.huobi.dto;

import io.github.xinyangpan.crypto4j.core.dto.HasSymbol;

public interface HuobiHasSymbol extends HasSymbol {

	@Override
	default String toSymbol() {
		return this.getCh().split("\\.")[1];
	}

	String getCh();

}
