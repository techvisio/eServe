package com.techvisio.eserve.async.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.util.ServiceLocator;

@Component
@Scope("prototype")
public class AsyncEmailMessage implements AsyncMessage{

	@Autowired
	ServiceLocator serviceLocator;
	
	private Long jobId;
	private Long entityId;
	private String entityType;
	private String emailType;
	@Override
	public void process() {
		//based on email type get bussiness object
		//get email template 
		//velocity conversion
		//send email
		//update table
	}

	
}
