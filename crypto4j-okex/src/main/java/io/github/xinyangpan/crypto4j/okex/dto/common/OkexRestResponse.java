package io.github.xinyangpan.crypto4j.okex.dto.common;

import io.github.xinyangpan.crypto4j.core.ResultErrorException;

public class OkexRestResponse {
	private boolean result;

	public boolean isSuccessful() {
		return result;
	}

	public OkexRestResponse throwExceptionWhenError() {
		return this.throwExceptionWhenError(null);
	}

	public OkexRestResponse throwExceptionWhenError(String errMsg) {
		if (result) {
			return this;
		}
		throw new ResultErrorException(errMsg);
	}

}
