package io.github.xinyangpan.crypto4j.exchange.okex.dto.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OrderStatus {
	CANCELED, NEW, PARTIALLY_FILLED, FILLED, PENDING_CANCEL;
	
	@JsonCreator
	public static OrderStatus valueOf(int status) {
		switch (status) {
		case -1:
			return CANCELED;
		case 0:
			return NEW;
		case 1:
			return PARTIALLY_FILLED;
		case 2:
			return FILLED;
		case 3:
			return PENDING_CANCEL;
		default:
			throw new IllegalArgumentException(String.valueOf(status));
		}
	}

}
