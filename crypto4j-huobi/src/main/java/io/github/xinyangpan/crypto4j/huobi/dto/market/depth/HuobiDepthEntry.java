package io.github.xinyangpan.crypto4j.huobi.dto.market.depth;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;

import io.github.xinyangpan.crypto4j.core.dto.market.DepthEntry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class HuobiDepthEntry extends DepthEntry {

	@JsonCreator
	public static HuobiDepthEntry create(String[] data) {
		final String price = data[0];
		final String qty = data[1];
		//
		HuobiDepthEntry orderBookEntry = new HuobiDepthEntry();
		orderBookEntry.setPrice(new BigDecimal(price));
		orderBookEntry.setQuantity(new BigDecimal(qty));
		return orderBookEntry;
	}

}
