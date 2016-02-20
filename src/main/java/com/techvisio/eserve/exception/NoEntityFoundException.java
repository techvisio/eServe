package com.techvisio.eserve.exception;

public class NoEntityFoundException extends AbstractCustomException {

	public NoEntityFoundException(Exception e) {
		super(e);
	}

	public NoEntityFoundException() {
		super();
	}

	public NoEntityFoundException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoEntityFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoEntityFoundException(String message) {
		super(message);
	}

	public NoEntityFoundException(Throwable cause) {
		super(cause);
	}
	
	

}
