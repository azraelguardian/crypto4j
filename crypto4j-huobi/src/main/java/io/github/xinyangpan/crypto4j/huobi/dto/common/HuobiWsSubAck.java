package io.github.xinyangpan.crypto4j.huobi.dto.common;

import lombok.Data;

@Data
public class HuobiWsSubAck {

	private String id;
	private String status;
	private String subbed;
	private Long ts;

}