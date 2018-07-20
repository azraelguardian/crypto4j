package io.github.xinyangpan.crypto4j.exchange.huobi.dto.rest;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.xinyangpan.crypto4j.exchange.huobi.dto.enums.OrderType;
import lombok.Data;

@Data
public class OrderResult {

	private Long id;
	private String symbol;
	@JsonProperty("account-id")
	private Long accountId;
	private BigDecimal amount;
	private BigDecimal price;
	@JsonProperty("created-at")
	private Long createdAt;
	private OrderType type;
	@JsonProperty("field-amount")
	private BigDecimal fieldAmount;
	@JsonProperty("field-cash-amount")
	private BigDecimal fieldCashAmount;
	@JsonProperty("field-fees")
	private BigDecimal fieldFees;
	@JsonProperty("finished-at")
	private Long finishedAt;
	private String source;
	private String state;
	@JsonProperty("canceled-at")
	private Long canceledAt;

}
