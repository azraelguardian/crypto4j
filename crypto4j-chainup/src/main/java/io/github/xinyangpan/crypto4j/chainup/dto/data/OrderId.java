package io.github.xinyangpan.crypto4j.chainup.dto.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OrderId {

	@JsonProperty("order_id")
	private Long orderId;

}
