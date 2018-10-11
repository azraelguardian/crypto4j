package io.github.xinyangpan.crypto4j.core;

import lombok.Getter;

@Getter
public class UnknownOrderException extends RuntimeException {
	private static final long serialVersionUID = 7571240489543203957L;
	private String orderId;

	public UnknownOrderException() {
		super();
	}

	public UnknownOrderException(String message) {
		super(message);
	}

	public UnknownOrderException(Throwable cause) {
		super(cause);
	}

	public UnknownOrderException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownOrderException(String orderId, String message, Throwable cause) {
		this(message, cause);
		this.orderId = orderId;
	}

	public UnknownOrderException(String orderId, String message) {
		this(message);
		this.orderId = orderId;
	}

}
