package com.techvisio.eserve.exception;

public class AuthorizationException extends AbstractCustomException {

	public AuthorizationException() {
		super();
	}

	public AuthorizationException(Exception e) {
		super(e);
	}

	public AuthorizationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AuthorizationException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthorizationException(String message) {
		super(message);
	}

	public AuthorizationException(Throwable cause) {
		super(cause);
	}

}
