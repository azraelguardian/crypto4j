package io.github.xinyangpan.crypto4j.exchange.okex.dto.common;

import lombok.Data;

@Data
public class OkexWsResponse<T> {
	
	private Integer binary;
	private String channel;
	private T data;

}