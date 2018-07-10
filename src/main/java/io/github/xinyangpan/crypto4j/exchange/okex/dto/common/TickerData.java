package io.github.xinyangpan.crypto4j.exchange.okex.dto.common;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TickerData {

	private BigDecimal open;
	private BigDecimal high;
	private BigDecimal last;
	private BigDecimal close;
	private BigDecimal low;
	private BigDecimal change;
	private BigDecimal vol;
	private BigDecimal buy;
	private BigDecimal sell;
	private BigDecimal dayLow;
	private BigDecimal dayHigh;
	private Long timestamp;

}
