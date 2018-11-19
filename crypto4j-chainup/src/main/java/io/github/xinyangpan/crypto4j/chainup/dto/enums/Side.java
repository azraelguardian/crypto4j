package io.github.xinyangpan.crypto4j.chainup.dto.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Side {
	BUY, SELL;

	@JsonCreator
	public static Side of(String side) {
		switch (side) {
		case "buy":
		case "BUY":
			return Side.BUY;
		case "sell":
		case "SELL":
			return Side.SELL;
		default:
			throw new IllegalArgumentException("Not supported side=" + side);
		}
	}

}
