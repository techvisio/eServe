package com.techvisio.eserve.exception;

public class EntityLockedException extends AbstractCustomException{

	public EntityLockedException(Exception e) {
		super(e);
	}

	public EntityLockedException() {
		super();
	}

	public EntityLockedException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EntityLockedException(String message, Throwable cause) {
		super(message, cause);
	}

	public EntityLockedException(String message) {
		super(message);
	}

	public EntityLockedException(Throwable cause) {
		super(cause);
	}
}
