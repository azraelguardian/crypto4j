package io.github.xinyangpan.crypto4j.okex3.dto.trade;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.xinyangpan.crypto4j.okex3.dto.enums.OrderStatus;
import io.github.xinyangpan.crypto4j.okex3.dto.enums.OrderType;
import io.github.xinyangpan.crypto4j.okex3.dto.enums.Side;
import lombok.Data;

@Data
public class Order {

	private Long orderId;
	private BigDecimal notional;
	private BigDecimal price;
	private BigDecimal size;
	@JsonProperty("instrument_id")
	private String instrumentId;
	private Side side;
	private OrderType type;
	private LocalDateTime timestamp;
	@JsonProperty("filled_size")
	private BigDecimal filledSize;
	@JsonProperty("filled_notional")
	private BigDecimal filledNotional;
	private OrderStatus status;

}
