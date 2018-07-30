package io.github.xinyangpan.crypto4j.exchange.okex.dto.trade;

import java.math.BigDecimal;

import io.github.xinyangpan.crypto4j.exchange.okex.dto.enums.OrderType;
import lombok.Data;

@Data
public class Order {

	private String symbol;
	private OrderType type;
	private BigDecimal price;
	private BigDecimal amount;

}
