package com.techvisio.eserve.async.process;

import org.apache.velocity.runtime.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Client;
import com.techvisio.eserve.beans.CommunicationBusinessObj;
import com.techvisio.eserve.beans.CommunicationJob;
import com.techvisio.eserve.beans.EmailFormat;
import com.techvisio.eserve.beans.EmailMessage;
import com.techvisio.eserve.communication.EmailSender;
import com.techvisio.eserve.service.CommunicationService;
import com.techvisio.eserve.util.ServiceLocator;

@Component
@Scope("prototype")
public class AsyncEmailMessage implements AsyncMessage{

	@Autowired
	ServiceLocator serviceLocator;
	
	
	
	private CommunicationJob communicationJob;
	
	public CommunicationJob getCommunicationJob() {
		return communicationJob;
	}

	public void setCommunicationJob(CommunicationJob communicationJob) {
		this.communicationJob = communicationJob;
	}

	@Override
	public void process() {
	 
		EmailSender emailSender = serviceLocator.getService(EmailSender.class);
		CommunicationService communicationService = serviceLocator.getService(CommunicationService.class);
		
		CommunicationBusinessObj businessObj = communicationService.getBusinessObjectByCommunicationJob(communicationJob);
		EmailFormat emailFormat = communicationService.getEmailTemplateByCommunicationJob(communicationJob);
		try {
			emailFormat=communicationService.getParsedCommunicationTemplate(emailFormat, businessObj);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EmailMessage emailMessage = getEmailMessage(emailFormat, businessObj);
		emailSender.sendEmail(emailMessage);
		
		communicationService.updateCommunicationJobStatus(communicationJob);

	}

	private EmailMessage getEmailMessage(EmailFormat emailFormat, CommunicationBusinessObj businessObj){
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setRecipients(businessObj.getRecipients());
		emailMessage.setSubject(emailFormat.getSubject());
		emailMessage.setText(emailFormat.getParsedTemplate());
		emailMessage.setClientId(emailFormat.getClient().getClientId());
		return emailMessage;
	}
	
}
