package io.github.xinyangpan.crypto4j.exchange.huobi.dto.rest;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.xinyangpan.crypto4j.exchange.huobi.dto.enums.OrderType;
import lombok.Data;

@Data
public class Order {
	@JsonProperty("account-id")
	private Long accountId;
	private BigDecimal amount;
	private BigDecimal price;
	private String source;
	private String symbol;
	private OrderType type;
}
