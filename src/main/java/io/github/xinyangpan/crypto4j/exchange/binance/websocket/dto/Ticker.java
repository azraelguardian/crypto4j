package io.github.xinyangpan.crypto4j.exchange.binance.websocket.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.xinyangpan.crypto4j.exchange.binance.websocket.dto.common.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Ticker extends BaseDto {

	@JsonProperty("s")
	private String symbol;
	@JsonProperty("p")
	private BigDecimal change;
	@JsonProperty("P")
	private BigDecimal changePercent;
	@JsonProperty("w")
	private BigDecimal weightedAveragePrice;
	@JsonProperty("x")
	private BigDecimal previousClose;
	@JsonProperty("c")
	private BigDecimal currentClose;
	@JsonProperty("Q")
	private BigDecimal CloseTradeQuantity;
	@JsonProperty("b")
	private BigDecimal bestBid;
	@JsonProperty("B")
	private BigDecimal bestBidQuantity;
	@JsonProperty("a")
	private BigDecimal bestAsk;
	@JsonProperty("A")
	private BigDecimal bestAskQuantity;
	@JsonProperty("o")
	private BigDecimal open;
	@JsonProperty("h")
	private BigDecimal high;
	@JsonProperty("l")
	private BigDecimal low;
	@JsonProperty("v")
	private BigDecimal volume;
	@JsonProperty("q")
	private BigDecimal quantity;
	@JsonProperty("O")
	private Long openTime;
	@JsonProperty("C")
	private Long closeTime;
	@JsonProperty("F")
	private Integer firstTradeId;
	@JsonProperty("L")
	private Integer lastTradeId;
	@JsonProperty("n")
	private Integer numOfTrades;

}
