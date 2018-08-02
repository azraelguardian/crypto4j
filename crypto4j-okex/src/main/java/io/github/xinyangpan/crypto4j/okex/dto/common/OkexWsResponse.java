package io.github.xinyangpan.crypto4j.okex.dto.common;

import io.github.xinyangpan.crypto4j.okex.dto.OkexHasSymbol;
import lombok.Data;

@Data
public class OkexWsResponse<T> implements OkexHasSymbol {
	
	private Integer binary;
	private String channel;
	private T data;

}
