package io.github.xinyangpan.crypto4j.okex.dto;

import io.github.xinyangpan.crypto4j.core.dto.HasSymbol;

public interface OkexHasSymbol extends HasSymbol {

	@Override
	default String toSymbol() {
		String[] split = this.getChannel().split("_");
		return split[3] + "_" + split[4];
	}

	String getChannel();

}
