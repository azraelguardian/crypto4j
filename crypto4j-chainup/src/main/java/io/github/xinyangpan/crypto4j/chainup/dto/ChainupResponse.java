package io.github.xinyangpan.crypto4j.chainup.dto;

import lombok.Data;

@Data
public class ChainupResponse<T> {

	private Integer code;
	private String msg;
	private T data;

}
