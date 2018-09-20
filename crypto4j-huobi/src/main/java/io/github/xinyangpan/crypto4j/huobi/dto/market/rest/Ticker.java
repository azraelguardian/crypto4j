package io.github.xinyangpan.crypto4j.huobi.dto.market.rest;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Ticker {
	
	private String symbol;
	private BigDecimal open;
	private BigDecimal close;
	private BigDecimal low;
	private BigDecimal high;
	private BigDecimal amount;
	private Integer count;
	private BigDecimal vol;
	
}
