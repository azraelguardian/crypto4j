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
public class OrderResponse extends OkexRestResponse {

	@JsonProperty("order_id")
	private Long orderId;
	@JsonProperty("error_code")
	private ErrorCode errorCode;

	@Override
	public OrderResponse throwExceptionWhenError() {
		return (OrderResponse) this.throwExceptionWhenError(String.format("errCode: %s.", errorCode));
	}

}
