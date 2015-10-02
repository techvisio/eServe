package com.techvisio.eserve.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**This class will used to provide the spring application context to
 * Resources that are not registered with Spring Context
 * */
public class ContextProvider {
	
	public static ApplicationContext getContext(){
		ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-config/Application-context.xml");
		return ctx;
	}

}
