package io.github.xinyangpan.crypto4j.okex3.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.xinyangpan.crypto4j.okex3.dto.enums.MarginTrading;
import io.github.xinyangpan.crypto4j.okex3.dto.enums.OrderType;
import io.github.xinyangpan.crypto4j.okex3.dto.enums.Side;
import lombok.Data;

@Data
public class PlaceOrder {

	@JsonProperty("client_oid")
	private String clientOid;
	@JsonProperty("instrument_id")
	private String instrumentId;
	private Side side;
	private OrderType type;
	private BigDecimal size;
	private BigDecimal price;
	private BigDecimal notional;
	@JsonProperty("margin_trading")
	private MarginTrading marginTrading;

}
