package io.github.xinyangpan.crypto4j.exchange.binance.dto.websocket.depth;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.github.xinyangpan.crypto4j.exchange.binance.dto.util.DepthEntryDeserializer;
import lombok.Data;

@Data
@JsonDeserialize(using = DepthEntryDeserializer.class)
public class DepthEntry {

	private BigDecimal price;
	private BigDecimal quantity;

}
