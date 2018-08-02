package io.github.xinyangpan.crypto4j.huobi.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.github.xinyangpan.crypto4j.huobi.dto.enums.OrderType;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonInclude(Include.NON_NULL)
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
