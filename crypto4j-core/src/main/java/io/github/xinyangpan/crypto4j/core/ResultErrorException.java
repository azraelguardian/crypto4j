package io.github.xinyangpan.crypto4j.core;

public class ResultErrorException extends RuntimeException {
	private static final long serialVersionUID = -1962725212480052963L;

	public ResultErrorException() {
		super();
	}

	public ResultErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ResultErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResultErrorException(String message) {
		super(message);
	}

	public ResultErrorException(Throwable cause) {
		super(cause);
	}

}
