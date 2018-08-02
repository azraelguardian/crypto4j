package io.github.xinyangpan.crypto4j.exchange.binance.dto.websocket.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Trade extends BaseTrade {

	@JsonProperty("t")
	private long tradeId;
	@JsonProperty("b")
	private long buyerOrderId;
	@JsonProperty("a")
	private long sellerOrderId;

}