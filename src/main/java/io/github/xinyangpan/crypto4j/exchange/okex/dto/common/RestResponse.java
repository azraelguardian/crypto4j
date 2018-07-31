package io.github.xinyangpan.crypto4j.exchange.okex.dto.common;

import lombok.Data;

@Data
public class RestResponse {

	private boolean result;

	public RestResponse throwExceptionWhenError() {
		return this.throwExceptionWhenError(null);
	}

	public RestResponse throwExceptionWhenError(String errMsg) {
		if (result) {
			return this;
		}
		throw new RuntimeException(errMsg);
	}

}
