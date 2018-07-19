package io.github.xinyangpan.crypto4j.exchange.binance.dto.util;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import io.github.xinyangpan.crypto4j.exchange.binance.dto.websocket.depth.DepthEntry;

public class DepthEntryDeserializer extends JsonDeserializer<DepthEntry> {

	@Override
	public DepthEntry deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
		JsonNode node = jp.getCodec().readTree(jp);
		final String price = node.get(0).asText();
		final String qty = node.get(1).asText();
		//
		DepthEntry orderBookEntry = new DepthEntry();
		orderBookEntry.setPrice(new BigDecimal(price));
		orderBookEntry.setQuantity(new BigDecimal(qty));
		return orderBookEntry;
	}

}