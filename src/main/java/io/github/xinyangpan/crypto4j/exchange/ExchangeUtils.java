package io.github.xinyangpan.crypto4j.exchange;

import java.util.function.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExchangeUtils {

	public static ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	public static <T> Consumer<T> logConsumer() {
		return (t) -> {
			log.warn("No listener registerred for the message. message: {}", t);
		};
	}

}
