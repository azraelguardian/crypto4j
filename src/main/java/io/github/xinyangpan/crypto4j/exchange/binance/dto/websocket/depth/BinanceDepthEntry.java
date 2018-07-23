package io.github.xinyangpan.crypto4j.exchange.binance.dto.websocket.depth;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;

import io.github.xinyangpan.crypto4j.common.dto.market.DepthEntry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BinanceDepthEntry extends DepthEntry {

	@JsonCreator
	public static BinanceDepthEntry create(Object[] data) {
		final String price = (String) data[0];
		final String qty = (String) data[1];
		//
		BinanceDepthEntry orderBookEntry = new BinanceDepthEntry();
		orderBookEntry.setPrice(new BigDecimal(price));
		orderBookEntry.setQuantity(new BigDecimal(qty));
		return orderBookEntry;
	}

}
