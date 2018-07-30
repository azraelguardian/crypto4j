package io.github.xinyangpan.crypto4j.exchange.okex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryOrder {

	private String symbol;
	@JsonProperty("order_id")
	private long orderId;

}
