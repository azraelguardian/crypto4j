package io.github.xinyangpan.crypto4j.binance.dto.rest.market;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BookTicker {

	private String symbol;
	private BigDecimal bidPrice;
	private BigDecimal bidQty;
	private BigDecimal askPrice;
	private BigDecimal askQty;

}
