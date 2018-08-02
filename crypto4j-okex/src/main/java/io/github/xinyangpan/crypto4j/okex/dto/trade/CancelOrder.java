package io.github.xinyangpan.crypto4j.okex.dto.trade;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;

import lombok.Data;

@Data
public class CancelOrder {
	private String symbol;
	@JsonProperty("order_id")
	private String orderIds;

	public CancelOrder() {
	}

	public CancelOrder(String symbol, long... orderIds) {
		super();
		this.symbol = symbol;
		Preconditions.checkNotNull(orderIds);
		Preconditions.checkArgument(orderIds.length > 0);
		// 
		this.orderIds = Arrays.stream(orderIds).boxed()//
			.map(String::valueOf)//
			.collect(Collectors.joining(","));
	}

}
