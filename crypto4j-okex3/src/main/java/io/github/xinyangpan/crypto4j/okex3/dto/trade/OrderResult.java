package io.github.xinyangpan.crypto4j.okex3.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.xinyangpan.crypto4j.core.dto.ThrowExceptionWhenError;
import lombok.Data;

@Data
public class OrderResult implements ThrowExceptionWhenError {

	@JsonProperty("order_id")
	private Long orderId;
	@JsonProperty("client_oid")
	private String clientOid;
	@JsonProperty("result")
	private Boolean result;

	@Override
	public boolean isSuccessful() {
		return result;
	}

}
