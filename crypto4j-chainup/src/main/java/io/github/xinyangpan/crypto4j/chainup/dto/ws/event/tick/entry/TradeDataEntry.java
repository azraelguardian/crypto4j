package io.github.xinyangpan.crypto4j.chainup.dto.ws.event.tick.entry;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.xinyangpan.crypto4j.chainup.dto.enums.Side;

public class TradeDataEntry {

	@JsonProperty("id")
	private Long id;
	@JsonProperty("side")
	private Side side;
	@JsonProperty("price")
	private BigDecimal price;
	@JsonProperty("vol")
	private BigDecimal vol;
	@JsonProperty("amount")
	private BigDecimal amount;
	@JsonProperty("ts")
	private Long ts;
	@JsonProperty("ts")
	private String ds;

}
