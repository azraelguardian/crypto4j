package io.github.xinyangpan.crypto4j.exchange.okex.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OrderResponse {

	private Boolean result;
	@JsonProperty("order_id")
	private String orderId;

}
