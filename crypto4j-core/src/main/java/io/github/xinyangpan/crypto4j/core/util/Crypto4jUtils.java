package io.github.xinyangpan.crypto4j.core.util;

import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.util.function.Consumer;

import org.apache.commons.io.IOUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Crypto4jUtils {

	public static <T> Consumer<T> logConsumer() {
		return (t) -> {
			log.warn("No listener registerred for the message. message: {}", t);
		};
	}

	public static <T> Consumer<T> noOp() {
		return (t) -> {};
	}

	public static String getSecret(String secretFilePath) {
		try {
			return IOUtils.toString(new FileInputStream(secretFilePath), Charset.defaultCharset()).trim();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
