package io.github.xinyangpan.crypto4j.exchange.binance.dto.rest.order;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.github.xinyangpan.crypto4j.exchange.binance.dto.enums.NewOrderRespType;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.enums.OrderType;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.enums.Side;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.enums.TimeInForce;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.rest.common.SymbolRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(Include.NON_NULL)
public class PlaceOrderRequest extends SymbolRequest {

	private Side side;
	private OrderType type;
	private TimeInForce timeInForce;
	private BigDecimal quantity;
	private BigDecimal price;
	private String newClientOrderId;
	private BigDecimal stopPrice;
	private BigDecimal icebergQty;
	private NewOrderRespType newOrderRespType;

}
