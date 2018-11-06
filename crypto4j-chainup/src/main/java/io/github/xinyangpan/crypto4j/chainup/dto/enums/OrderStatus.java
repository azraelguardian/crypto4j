package io.github.xinyangpan.crypto4j.chainup.dto.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum OrderStatus {
	INIT(0, "初始订单，未成交未进入盘口"), //
	NEW(1, "新订单，未成交进入盘口"), //
	FILLED(2, "完全成交"), //
	PART_FILLED(3, "部分成交"), //
	CANCELED(4, "已撤单"), //
	PENDING_CANCEL(5, "待撤单"), //
	EXPIRED(6, "异常订单");

	@JsonValue
	private int code;
	private String desc;

	private OrderStatus(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	@JsonCreator
	public static OrderStatus of(int code) {
		switch (code) {
		case 0:
			return OrderStatus.INIT;
		case 1:
			return OrderStatus.NEW;
		case 2:
			return OrderStatus.FILLED;
		case 3:
			return OrderStatus.PART_FILLED;
		case 4:
			return OrderStatus.CANCELED;
		case 5:
			return OrderStatus.PENDING_CANCEL;
		case 6:
			return OrderStatus.EXPIRED;
		default:
			throw new IllegalArgumentException("Not supported code=" + code);
		}
	}

}
