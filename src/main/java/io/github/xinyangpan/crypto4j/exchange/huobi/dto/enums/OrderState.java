package io.github.xinyangpan.crypto4j.exchange.huobi.dto.enums;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderState {
	SUBMITTING("submitting"), //
	SUBMITTED("submitted"), // 已提交
	PARTIAL_FILLED("partial-filled"), // 部分成交
	PARTIAL_CANCELED("partial-canceled"), // 部分成交撤销
	FILLED("filled"), // 完全成交
	CANCELED("canceled"); // 已撤销

	//
	private static final Map<String, OrderState> INDEX = new HashMap<>();

	static {
		OrderState[] values = values();
		for (OrderState v : values) {
			INDEX.put(v.value, v);
		}
	}

	private final String value;

	private OrderState(String value) {
		this.value = value;
	}

	@JsonCreator
	public static OrderState parse(String value) {
		return INDEX.get(value);
	}

	@JsonValue
	public String getValue() {
		return value;
	}
}
