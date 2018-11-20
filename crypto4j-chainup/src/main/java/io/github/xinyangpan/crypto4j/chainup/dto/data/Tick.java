package io.github.xinyangpan.crypto4j.chainup.dto.data;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Tick {

	private BigDecimal high;
	private BigDecimal vol;
	private BigDecimal last;
	private BigDecimal low;
	private BigDecimal buy;
	private BigDecimal sell;
	private BigDecimal rose;
	private Long time;

}
