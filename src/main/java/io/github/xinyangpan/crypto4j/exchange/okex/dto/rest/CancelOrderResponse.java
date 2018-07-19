package io.github.xinyangpan.crypto4j.exchange.okex.dto.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CancelOrderResponse {

	private boolean result;
	@JsonProperty("order_id")
	private Long orderId;
	private String success;
	private String error;
	@JsonProperty("error_code")
	private Integer errorCode;

}
