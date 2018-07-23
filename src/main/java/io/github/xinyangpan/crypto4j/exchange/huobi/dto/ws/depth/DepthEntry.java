package io.github.xinyangpan.crypto4j.exchange.huobi.dto.ws.depth;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Data;

@Data
public class DepthEntry {

	private BigDecimal price;
	private BigDecimal quantity;

	@JsonCreator
	public static DepthEntry create(String[] data) {
		final String price = (String) data[0];
		final String qty = (String) data[1];
		//
		DepthEntry orderBookEntry = new DepthEntry();
		orderBookEntry.setPrice(new BigDecimal(price));
		orderBookEntry.setQuantity(new BigDecimal(qty));
		return orderBookEntry;
	}
	
}
