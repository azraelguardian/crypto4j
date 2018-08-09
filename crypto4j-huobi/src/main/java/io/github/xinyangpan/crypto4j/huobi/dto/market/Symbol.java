package io.github.xinyangpan.crypto4j.huobi.dto.market;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Symbol {

	@JsonProperty("base-currency")
	private String baseCurrency;
	@JsonProperty("quote-currency")
	private String quoteCurrency;
	@JsonProperty("price-precision")
	private Integer pricePrecision;
	@JsonProperty("amount-precision")
	private Integer amountPrecision;
	@JsonProperty("symbol-partition")
	private String symbolPartition;
	@JsonProperty("symbol")
	private String symbol;

}
