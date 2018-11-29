package io.github.xinyangpan.crypto4j.okex3.dto.trade;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.xinyangpan.crypto4j.okex3.dto.enums.ExecType;
import io.github.xinyangpan.crypto4j.okex3.dto.enums.Side;
import lombok.Data;

@Data
public class Execution {

	@JsonProperty("exec_type")
	private ExecType execType;
	private BigDecimal fee;
	@JsonProperty("instrument_id")
	private String instrumentId;
	@JsonProperty("ledger_id")
	private Long ledgerId;
	@JsonProperty("order_id")
	private Long orderId;
	private BigDecimal price;
	private Side side;
	private BigDecimal size;
	private LocalDateTime timestamp;

}
