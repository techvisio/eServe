package com.techvisio.eserve.exception;

public class DuplicateEntityException extends AbstractCustomException {

	public DuplicateEntityException(Exception e) {
		super(e);
	}

	public DuplicateEntityException() {
		super();
	}

	public DuplicateEntityException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DuplicateEntityException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateEntityException(String message) {
		super(message);
	}

	public DuplicateEntityException(Throwable cause) {
		super(cause);
	}
	
	

}
