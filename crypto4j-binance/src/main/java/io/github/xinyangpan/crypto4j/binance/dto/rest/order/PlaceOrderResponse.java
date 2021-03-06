package io.github.xinyangpan.crypto4j.binance.dto.rest.order;

import java.math.BigDecimal;
import java.util.List;

import io.github.xinyangpan.crypto4j.binance.dto.enums.OrderStatus;
import io.github.xinyangpan.crypto4j.binance.dto.enums.OrderType;
import io.github.xinyangpan.crypto4j.binance.dto.enums.Side;
import io.github.xinyangpan.crypto4j.binance.dto.enums.TimeInForce;
import lombok.Data;

@Data
public class PlaceOrderResponse {

	private String symbol;
	private Long orderId;
	private String clientOrderId;
	private Long transactTime;
	private BigDecimal price;
	private BigDecimal origQty;
	private BigDecimal executedQty;
	private BigDecimal cummulativeQuoteQty;
	private OrderStatus status;
	private TimeInForce timeInForce;
	private OrderType type;
	private Side side;
	private List<Fill> fills;

}
