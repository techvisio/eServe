package com.techvisio.eserve.beans;

public class Response {
	private Object responseBody;
	private String error;
	public Object getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(Object data) {
		this.responseBody = data;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

}
