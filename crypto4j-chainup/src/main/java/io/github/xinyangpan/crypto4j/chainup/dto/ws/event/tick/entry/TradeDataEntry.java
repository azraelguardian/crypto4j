package io.github.xinyangpan.crypto4j.chainup.dto.ws.event.tick.entry;

import java.math.BigDecimal;

import io.github.xinyangpan.crypto4j.chainup.dto.enums.Side;
import lombok.Data;

@Data
public class TradeDataEntry {

	private Long id;
	private Side side;
	private BigDecimal price;
	private BigDecimal vol;
	private BigDecimal amount;
	private Long ts;
	private String ds;

}
