package io.github.xinyangpan.crypto4j.exchange.binance.dto.common;

import lombok.Data;

@Data
public class StreamData<T> {

	private String stream;
	private T data;

}
