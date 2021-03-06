package io.github.xinyangpan.crypto4j.binance.dto.websocket.common;

import io.github.xinyangpan.crypto4j.binance.dto.BinanceHasSymbol;
import lombok.Data;

@Data
public class StreamData<T> implements BinanceHasSymbol {

	private String stream;
	private T data;

}
