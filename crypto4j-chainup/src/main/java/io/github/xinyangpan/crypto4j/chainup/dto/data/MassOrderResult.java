package io.github.xinyangpan.crypto4j.chainup.dto.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MassOrderResult {

	private Integer code;
	private String msg;
	@JsonProperty("order_id")
	private Long orderId;

}
