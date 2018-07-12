package io.github.xinyangpan.crypto4j.exchange;

import java.util.function.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ExchangeUtils {

	public static ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	public static <T> Consumer<T> noOpConsumer() {
		return (t) -> {};
	}

}
