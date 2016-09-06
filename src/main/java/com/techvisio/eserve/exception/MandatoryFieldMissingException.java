package com.techvisio.eserve.exception;

public class MandatoryFieldMissingException extends AbstractCustomException{

	public MandatoryFieldMissingException(Exception e) {
		super(e);
	}

	public MandatoryFieldMissingException() {
		super();
	}

	public MandatoryFieldMissingException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MandatoryFieldMissingException(String message, Throwable cause) {
		super(message, cause);
	}

	public MandatoryFieldMissingException(String message) {
		super(message);
	}

	public MandatoryFieldMissingException(Throwable cause) {
		super(cause);
	}

	
}
