package io.github.xinyangpan.crypto4j.huobi.dto.common;

import lombok.Data;

@Data
public class HuobiWsResponse<T> {

	private String id;
	private String rep;
	private String status;
	private T data;
	private T tick;

	public T fethData() {
		if (data != null) {
			return data;
		}
		return tick;
	}

}