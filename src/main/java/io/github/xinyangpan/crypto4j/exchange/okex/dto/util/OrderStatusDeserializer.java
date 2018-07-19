package io.github.xinyangpan.crypto4j.exchange.okex.dto.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import io.github.xinyangpan.crypto4j.exchange.okex.dto.enums.OrderStatus;

public class OrderStatusDeserializer extends JsonDeserializer<OrderStatus> {

	@Override
	public OrderStatus deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
		JsonNode node = jp.getCodec().readTree(jp);
		int status = node.intValue();
		//
		return OrderStatus.valueOf(status);
	}

}