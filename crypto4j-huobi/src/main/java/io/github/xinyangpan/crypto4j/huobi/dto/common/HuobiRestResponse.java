package io.github.xinyangpan.crypto4j.huobi.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.xinyangpan.crypto4j.huobi.dto.enums.Status;
import lombok.Data;

/**
 * the real data either in data field or in tick field
 *
 * @param <T>
 */
@Data
public class HuobiRestResponse<T> {

	private Status status;
	@JsonProperty("err-code")
	private String errCode;
	@JsonProperty("err-msg")
	private String errMsg;
	private T data;
	private T tick;

	public HuobiRestResponse<T> throwExceptionWhenError() {
		if (this.isSuccessful()) {
			return this;
		}
		throw new RuntimeException(String.format("errCode: %s. err-msg: %s", errCode, errMsg));
	}

	public T fethData() {
		this.throwExceptionWhenError();
		if (data != null) {
			return data;
		}
		return tick;
	}

	public boolean isSuccessful() {
		return status == Status.ok;
	}

}
