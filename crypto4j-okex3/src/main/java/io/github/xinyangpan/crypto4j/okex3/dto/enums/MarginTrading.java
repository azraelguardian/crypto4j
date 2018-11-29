package io.github.xinyangpan.crypto4j.okex3.dto.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum MarginTrading {
	C2C(1);

	@JsonValue
	private int code;

	private MarginTrading(int code) {
		this.code = code;
	}

}
