package io.github.xinyangpan.crypto4j.chainup.dto.ws.event.tick.entry;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;

import io.github.xinyangpan.crypto4j.core.dto.market.DepthEntry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ChainupDepthEntry extends DepthEntry {

	@JsonCreator
	public static ChainupDepthEntry create(String[] data) {
		final String price = data[0];
		final String qty = data[1];
		//
		ChainupDepthEntry orderBookEntry = new ChainupDepthEntry();
		orderBookEntry.setPrice(new BigDecimal(price));
		orderBookEntry.setQuantity(new BigDecimal(qty));
		return orderBookEntry;
	}

}
