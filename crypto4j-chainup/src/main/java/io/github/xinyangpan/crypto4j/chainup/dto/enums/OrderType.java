package io.github.xinyangpan.crypto4j.chainup.dto.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum OrderType {
	LIMIT(1), MARKET(2);

	@JsonValue
	private int code;

	private OrderType(int code) {
		this.code = code;
	}

	@JsonCreator
	public static OrderType of(int code) {
		switch (code) {
		case 1:
			return OrderType.LIMIT;
		case 2:
			return OrderType.MARKET;
		default:
			throw new IllegalArgumentException("Not supported code=" + code);
		}
	}

}
