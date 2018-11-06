package io.github.xinyangpan.crypto4j.chainup.dto;

import lombok.Data;

@Data
public class ChainupResponse<T> {

	private Integer code;
	private String msg;
	private T data;

	public boolean isSuccessful() {
		return code != null && code == 0;
	}

}
