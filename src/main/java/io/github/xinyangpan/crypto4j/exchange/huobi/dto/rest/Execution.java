package io.github.xinyangpan.crypto4j.exchange.huobi.dto.rest;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.xinyangpan.crypto4j.exchange.huobi.dto.enums.OrderType;
import lombok.Data;

@Data
public class Execution {
	
	@JsonProperty("id")
	private Long id;
	@JsonProperty("order-id")
	private Long orderId;
	@JsonProperty("match-id")
	private Long matchId;
	@JsonProperty("symbol")
	private String symbol;
	@JsonProperty("type")
	private OrderType type;
	@JsonProperty("source")
	private String source;
	@JsonProperty("price")
	private BigDecimal price;
	@JsonProperty("filled-amount")
	private BigDecimal filledAmount;
	@JsonProperty("filled-fees")
	private BigDecimal filledFees;
	@JsonProperty("filled-points")
	private BigDecimal filledPoints;
	@JsonProperty("created-at")
	private Long createdAt;
	
}
