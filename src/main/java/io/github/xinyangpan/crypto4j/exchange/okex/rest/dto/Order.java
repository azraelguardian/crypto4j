package io.github.xinyangpan.crypto4j.exchange.okex.rest.dto;

import java.math.BigDecimal;

import io.github.xinyangpan.crypto4j.exchange.okex.rest.dto.enums.OrderType;
import lombok.Data;

@Data
public class Order {

	private String symbol;
	private OrderType type;
	private BigDecimal price;
	private BigDecimal amount;

}
