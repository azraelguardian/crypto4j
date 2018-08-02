package io.github.xinyangpan.crypto4j.exchange.okex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.xinyangpan.crypto4j.exchange.okex.dto.common.ErrorCode;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.common.RestResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CancelOrderResponse extends RestResponse {

	@JsonProperty("order_id")
	private Long orderId;
	private String success;
	private String error;
	@JsonProperty("error_code")
	private ErrorCode errorCode;

	@Override
	public RestResponse throwExceptionWhenError() {
		return this.throwExceptionWhenError(String.format("errCode: %s. error: %s", errorCode, error));
	}

}
