package io.github.xinyangpan.crypto4j.exchange.huobi.dto.depth;

import lombok.Data;

@Data
public class DepthData {

	private String ch;
	private Long ts;
	private Tick tick;

}