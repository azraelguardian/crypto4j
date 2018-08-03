package io.github.xinyangpan.crypto4j.huobi.dto.common;

import io.github.xinyangpan.crypto4j.huobi.dto.HuobiHasSymbol;
import lombok.Data;

@Data
public class HuobiWsResponse<T> implements HuobiHasSymbol {

	private String ch;
	private Long ts;
	private T tick;

}