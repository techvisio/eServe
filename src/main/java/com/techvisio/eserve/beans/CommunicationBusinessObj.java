package com.techvisio.eserve.beans;

import java.util.List;

public class CommunicationBusinessObj {

	private Object businessObject;
	
	private List<String> recipients;

	public Object getBusinessObject() {
		return businessObject;
	}

	public void setBusinessObject(Object businessObject) {
		this.businessObject = businessObject;
	}

	public List<String> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}
	
}
