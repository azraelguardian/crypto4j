package io.github.xinyangpan.crypto4j.exchange.binance.dto.depth;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.github.xinyangpan.crypto4j.exchange.binance.json.OrderBookEntryDeserializer;
import lombok.Data;

@Data
@JsonDeserialize(using = OrderBookEntryDeserializer.class)
public class DepthEntry {

	private BigDecimal price;
	private BigDecimal quantity;

}
