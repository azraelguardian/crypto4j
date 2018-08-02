package io.github.xinyangpan.crypto4j.exchange.binance.dto.rest.common;

import lombok.Data;

@Data
public class BaseRequest {

	private Long recvWindow;
	private Long timestamp = System.currentTimeMillis();
	
}
