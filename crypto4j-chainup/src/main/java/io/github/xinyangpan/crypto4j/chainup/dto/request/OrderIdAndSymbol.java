package io.github.xinyangpan.crypto4j.chainup.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderIdAndSymbol {

	@JsonProperty("order_id")
	private Long orderId;
	private String symbol;

}
