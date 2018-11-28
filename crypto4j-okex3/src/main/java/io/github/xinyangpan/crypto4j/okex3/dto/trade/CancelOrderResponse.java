package io.github.xinyangpan.crypto4j.okex3.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.xinyangpan.crypto4j.okex3.dto.common.ErrorCode;
import io.github.xinyangpan.crypto4j.okex3.dto.common.OkexRestResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CancelOrderResponse extends OkexRestResponse {

	@JsonProperty("order_id")
	private Long orderId;
	private String success;
	private String error;
	@JsonProperty("error_code")
	private ErrorCode errorCode;

	@Override
	public OkexRestResponse throwExceptionWhenError() {
		return this.throwExceptionWhenError(String.format("errCode: %s. error: %s", errorCode, error));
	}

}
