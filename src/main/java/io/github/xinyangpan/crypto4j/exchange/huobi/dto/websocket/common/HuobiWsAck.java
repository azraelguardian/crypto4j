package io.github.xinyangpan.crypto4j.exchange.huobi.dto.websocket.common;

import lombok.Data;

@Data
public class HuobiWsAck {

	private String id;
	private String status;
	private String subbed;
	private Long ts;

}