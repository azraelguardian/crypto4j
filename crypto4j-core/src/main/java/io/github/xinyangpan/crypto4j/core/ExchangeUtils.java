package io.github.xinyangpan.crypto4j.core;

import java.util.function.Consumer;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExchangeUtils {

	public static ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	public static RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static <T> Consumer<T> logConsumer() {
		return (t) -> {
			log.warn("No listener registerred for the message. message: {}", t);
		};
	}

	public static <T> Consumer<T> noOp() {
		return (t) -> {};
	}

}
