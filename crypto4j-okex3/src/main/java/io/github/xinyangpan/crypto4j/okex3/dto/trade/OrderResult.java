package io.github.xinyangpan.crypto4j.okex3.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OrderResult {

	@JsonProperty("order_id")
	private Long orderId;
	@JsonProperty("client_oid")
	private String clientOid;
	@JsonProperty("result")
	private Boolean result;

}
