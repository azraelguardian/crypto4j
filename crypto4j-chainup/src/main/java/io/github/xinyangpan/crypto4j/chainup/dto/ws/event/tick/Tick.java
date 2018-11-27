package io.github.xinyangpan.crypto4j.chainup.dto.ws.event.tick;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Tick {

	private BigDecimal amount;
	private BigDecimal close;
	private BigDecimal high;
	private BigDecimal low;
	private BigDecimal open;
	private BigDecimal rose;
	private BigDecimal vol;

}
