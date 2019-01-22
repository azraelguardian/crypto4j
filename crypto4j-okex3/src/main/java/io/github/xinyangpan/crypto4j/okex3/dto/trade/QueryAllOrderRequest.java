package io.github.xinyangpan.crypto4j.okex3.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.xinyangpan.crypto4j.okex3.dto.enums.OrderStatus;
import lombok.Data;

@Data
public class QueryAllOrderRequest {
	private OrderStatus status = OrderStatus.all;
	
	@JsonProperty("instrument_id")
	private String instrumentId;
	
	private String from;
	private String to;
	private Integer limit;
	
}
