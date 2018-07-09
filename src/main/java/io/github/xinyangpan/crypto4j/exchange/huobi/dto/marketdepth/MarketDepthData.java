package io.github.xinyangpan.crypto4j.exchange.huobi.dto.marketdepth;

import lombok.Data;

@Data
public class MarketDepthData {

	private String ch;
	private Long ts;
	private Tick tick;

}