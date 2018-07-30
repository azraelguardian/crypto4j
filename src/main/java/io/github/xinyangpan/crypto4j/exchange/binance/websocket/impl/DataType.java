package io.github.xinyangpan.crypto4j.exchange.binance.websocket.impl;

import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.xinyangpan.crypto4j.exchange.binance.dto.depth.Depth;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.websocket.Ticker;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.websocket.common.StreamData;

public enum DataType {
	TICKER("\\w*@ticker", Ticker.class), DEPTH("\\w*@depth(5|10|20)", Depth.class);

	private final Pattern pattern;
	private final Class<?> dataClass;

	private DataType(String regex, Class<?> dataClass) {
		this.pattern = Pattern.compile(regex);
		this.dataClass = dataClass;
	}

	public boolean matches(String stream) {
		return this.pattern.matcher(stream).matches();
	}

	public JavaType getJavaType(ObjectMapper objectMapper) {
		return objectMapper.getTypeFactory().constructParametricType(StreamData.class, dataClass);
	}
	
	public static DataType getDataType(String stream) {
		if (DataType.TICKER.matches(stream)) {
			return TICKER;
		} else if (DataType.DEPTH.matches(stream)) {
			return DEPTH;
		}
		return null;
	}

}
