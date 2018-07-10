package io.github.xinyangpan.crypto4j.exchange.huobi.dto.kline;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Tick {

	private Long id;
	private BigDecimal amount;
	private Long count;
	private BigDecimal open;
	private BigDecimal close;
	private BigDecimal low;
	private BigDecimal high;
	private BigDecimal vol;

}
