package com.techvisio.eserve.beans;

import java.util.HashMap;
import java.util.Map;

public class GenericRequest<T> {

	T bussinessObject;
	Map<String,String> contextInfo=new HashMap<String,String>();
	public T getBussinessObject() {
		return bussinessObject;
	}
	public void setBussinessObject(T bussinessObject) {
		this.bussinessObject = bussinessObject;
	}
	public Map<String, String> getContextInfo() {
		return contextInfo;
	}
	public void setContextInfo(Map<String, String> contextInfo) {
		this.contextInfo = contextInfo;
	}
	
}
