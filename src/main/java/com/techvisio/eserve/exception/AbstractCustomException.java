package com.techvisio.eserve.exception;

public abstract class AbstractCustomException extends RuntimeException {

	public AbstractCustomException(Exception e) {
		super(e);
	}

	public AbstractCustomException() {
		super();
	}

	public AbstractCustomException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AbstractCustomException(String message, Throwable cause) {
		super(message, cause);
	}

	public AbstractCustomException(String message) {
		super(message);
	}

	public AbstractCustomException(Throwable cause) {
		super(cause);
	}

	
}
