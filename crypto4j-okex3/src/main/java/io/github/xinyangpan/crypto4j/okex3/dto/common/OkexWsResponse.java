package io.github.xinyangpan.crypto4j.okex3.dto.common;

import io.github.xinyangpan.crypto4j.okex3.dto.OkexHasSymbol;
import lombok.Data;

@Data
public class OkexWsResponse<T> implements OkexHasSymbol {
	
	private Integer binary;
	private String channel;
	private T data;

}
