package io.github.xinyangpan.crypto4j.core.dto;

import io.github.xinyangpan.crypto4j.core.ResultErrorException;

public interface ThrowExceptionWhenError {

	boolean isSuccessful();

	default void throwExceptionWhenError() {
		this.throwExceptionWhenError(null);
	}

	default void throwExceptionWhenError(String errMsg) {
		if (isSuccessful()) {
			return;
		}
		throw new ResultErrorException(errMsg);
	}

}
