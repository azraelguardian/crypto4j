package io.github.xinyangpan.crypto4j.binance.dto.rest.market;

import lombok.Data;

@Data
public class KlineParam {

	private String symbol;
	private String interval;
	private Long startTime;
	private Long endTime;
	private Integer limit;

}
