package com.techvisio.eserve.util;

import org.springframework.beans.factory.annotation.Autowired;

public class ServiceLocator {
	
	@Autowired
	ApplicationContextProvider contextProvider;
	
	
	public <T> T getService(Class clazz) {
		//TODO:based on interface provided as argument get client implementation and then get that class from context
		//if not found fetch default implementation
		Object o = contextProvider.getApplicationContext().getBean(clazz);
		if (o != null) {
			return (T) o;
		}
		return null;
	}

	
}
