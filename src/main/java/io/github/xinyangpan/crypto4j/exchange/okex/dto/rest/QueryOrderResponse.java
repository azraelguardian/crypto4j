package io.github.xinyangpan.crypto4j.exchange.okex.dto.rest;

import java.util.List;

import lombok.Data;

@Data
public class QueryOrderResponse {

	private Boolean result;
	private List<OrderResult> orders;
	
}
