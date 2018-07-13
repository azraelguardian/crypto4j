
package io.github.xinyangpan.crypto4j.exchange.binance.websocket.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AggregateTrade extends BaseTrade {

	@JsonProperty("a")
	private long aggregateTradeId;
	@JsonProperty("f")
	private long firstTradeId;
	@JsonProperty("l")
	private long lastTradeId;

}