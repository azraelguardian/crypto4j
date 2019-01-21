package io.github.xinyangpan.crypto4j.okex3.dto.market;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TradeTicker {
	private String instrument_id;
	private BigDecimal price;
	private String side;
	private BigDecimal size;
	private String timestamp;
	private String trade_id;
}
