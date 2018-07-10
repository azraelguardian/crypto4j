package io.github.xinyangpan.crypto4j.exchange.okex.dto.common;

import lombok.Data;

@Data
public class OkexWsRequest {

	private String event;
	private String channel;

}
