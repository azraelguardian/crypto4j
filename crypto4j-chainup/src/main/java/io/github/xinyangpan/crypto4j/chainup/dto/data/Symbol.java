package io.github.xinyangpan.crypto4j.chainup.dto.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Symbol {

	@JsonProperty("symbol")
	private String symbol;
	@JsonProperty("count_coin")
	private String countCoin;
	@JsonProperty("amount_precision")
	private Integer amountPrecision;
	@JsonProperty("base_coin")
	private String baseCoin;
	@JsonProperty("price_precision")
	private Integer pricePrecision;

}
