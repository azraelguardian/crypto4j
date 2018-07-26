package io.github.xinyangpan.crypto4j.exchange.huobi.dto.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.xinyangpan.crypto4j.exchange.huobi.dto.enums.Status;
import lombok.Data;

@Data
public class RestResponse<T> {

	private Status status;
	@JsonProperty("err-code")
	private String errCode;
	@JsonProperty("err-msg")
	private String errMsg;
	private T data;
	
	public RestResponse<T> throwExeceptionWhenError() {
		if (status == Status.ok) {
			return this;
		}
		throw new RuntimeException(String.format("errCode: %s. err-msg: %s", errCode, errMsg));
	}
	
	public T fethData() {
		return this.throwExeceptionWhenError().data;
	}
	
}
