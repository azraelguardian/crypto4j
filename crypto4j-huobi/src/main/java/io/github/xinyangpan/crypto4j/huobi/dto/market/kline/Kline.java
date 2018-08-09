package io.github.xinyangpan.crypto4j.huobi.dto.market.kline;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import lombok.Data;

@Data
public class Kline {

	private Long id;
	private BigDecimal amount;
	private Integer count;
	private BigDecimal open;
	private BigDecimal close;
	private BigDecimal low;
	private BigDecimal high;
	private BigDecimal vol;
	
	public LocalDateTime getOpenTime() {
		Instant instant = Instant.ofEpochSecond(id, 0);
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	}
	
}
