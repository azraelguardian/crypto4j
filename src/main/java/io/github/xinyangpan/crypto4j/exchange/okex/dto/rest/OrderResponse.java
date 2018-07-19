package io.github.xinyangpan.crypto4j.exchange.okex.dto.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OrderResponse {

	private Boolean result;
	@JsonProperty("order_id")
	private Long orderId;
	@JsonProperty("error_code")
	private Integer errorCode;

}
