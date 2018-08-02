package io.github.xinyangpan.crypto4j.okex.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.xinyangpan.crypto4j.okex.dto.enums.OrderStatus;
import io.github.xinyangpan.crypto4j.okex.dto.enums.OrderType;
import lombok.Data;

@Data
public class OrderResult {
	
	@JsonProperty("amount")
	private BigDecimal amount;
	@JsonProperty("avg_price")
	private BigDecimal avgPrice;
	@JsonProperty("create_date")
	private Long createDate;
	@JsonProperty("deal_amount")
	private Long dealAmount;
	@JsonProperty("order_id")
	private Long orderId;
	@JsonProperty("orders_id")
	private Long ordersId;
	@JsonProperty("price")
	private BigDecimal price;
	@JsonProperty("status")
	private OrderStatus status;
	@JsonProperty("symbol")
	private String symbol;
	@JsonProperty("type")
	private OrderType type;
	
}
