package io.github.xinyangpan.crypto4j.chainup.dto.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum TradeSort {
	ASC(0), DESC(1);

	@JsonValue
	private int code;

	private TradeSort(int code) {
		this.code = code;
	}

	@JsonCreator
	public static TradeSort of(int code) {
		switch (code) {
		case 0:
			return TradeSort.ASC;
		case 1:
			return TradeSort.DESC;
		default:
			throw new IllegalArgumentException("Not supported code=" + code);
		}
	}

}
