package io.github.xinyangpan.crypto4j.exchange.huobi.dto.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {
	ok, error;
	
	@JsonCreator
	public static Status create(String a) {
		System.out.println(a);
		return ok;
	}
}
