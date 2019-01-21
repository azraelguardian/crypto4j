package io.github.xinyangpan.crypto4j.okex3.dto.market;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Ticker {

	@JsonProperty(value="instrument_id")
	private String instrumentId;
	private String timestamp;
	private BigDecimal last;
	@JsonProperty(value="best_bid")
	private BigDecimal bestBid;
	@JsonProperty(value="best_ask")
	private BigDecimal bestAsk;
	@JsonProperty(value="open_24h")
	private BigDecimal open24h;
	@JsonProperty(value="high_24h")
	private BigDecimal high24h;
	@JsonProperty(value="low_24h")
	private BigDecimal low24h;
	@JsonProperty(value="base_volume_24h")
	private BigDecimal baseVolume24h;
	@JsonProperty(value="quote_volume_24h")
	private BigDecimal quoteVolume24h;
}
