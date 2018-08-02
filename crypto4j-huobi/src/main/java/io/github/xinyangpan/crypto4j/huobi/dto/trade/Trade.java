package io.github.xinyangpan.crypto4j.huobi.dto.trade;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Trade {

	private Long id;
	private BigDecimal price;
	private Long time;
	private BigDecimal amount;
	private String direction;
	private Long tradeId;
	private Long ts;

}