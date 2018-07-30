package io.github.xinyangpan.crypto4j.exchange.okex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.xinyangpan.crypto4j.exchange.okex.dto.common.RestResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OrderResponse extends RestResponse {

	@JsonProperty("order_id")
	private Long orderId;
	@JsonProperty("error_code")
	private Integer errorCode;

	@Override
	public OrderResponse throwExeceptionWhenError() {
		return (OrderResponse) this.throwExeceptionWhenError(String.format("errCode: %s.", errorCode));
	}

}
