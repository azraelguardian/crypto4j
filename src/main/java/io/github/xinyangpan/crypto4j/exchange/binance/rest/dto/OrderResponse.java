package io.github.xinyangpan.crypto4j.exchange.binance.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import io.github.xinyangpan.crypto4j.exchange.binance.rest.dto.enums.OrderStatus;
import io.github.xinyangpan.crypto4j.exchange.binance.rest.dto.enums.OrderType;
import io.github.xinyangpan.crypto4j.exchange.binance.rest.dto.enums.Side;
import io.github.xinyangpan.crypto4j.exchange.binance.rest.dto.enums.TimeInForce;
import lombok.Data;

@Data
public class OrderResponse {

	private String symbol;
	private Long orderId;
	private String clientOrderId;
	private Long transactTime;
	private BigDecimal price;
	private BigDecimal origQty;
	private BigDecimal executedQty;
	private OrderStatus status;
	private TimeInForce timeInForce;
	private OrderType type;
	private Side side;
	private List<Fill> fills;

}
