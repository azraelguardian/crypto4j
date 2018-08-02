package io.github.xinyangpan.crypto4j.exchange.huobi.dto.common;

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

	public RestResponse<T> throwExceptionWhenError() {
		if (this.isSuccessful()) {
			return this;
		}
		throw new RuntimeException(String.format("errCode: %s. err-msg: %s", errCode, errMsg));
	}

	public T fethData() {
		return this.throwExceptionWhenError().data;
	}

	public boolean isSuccessful() {
		return status == Status.ok;
	}

}
