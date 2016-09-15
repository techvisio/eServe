package com.techvisio.eserve.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.velocity.runtime.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.CommunicationBusinessObj;
import com.techvisio.eserve.beans.CommunicationJob;
import com.techvisio.eserve.beans.EmailFormat;
import com.techvisio.eserve.manager.CommunicationManager;
import com.techvisio.eserve.service.CommunicationService;

@Transactional
@Component
public class CommunicationServiceImpl implements CommunicationService{

	@Autowired
	CommunicationManager communicationManager;
	
	@Override
	public List<String> getWhiteListedRecipietents(List<String> recipitents) {
		recipitents = communicationManager.getWhiteListedRecipietents(recipitents);
		return recipitents;
	}

	@Override
	public EmailFormat getEmailTemplateByCommunicationJob(CommunicationJob communicationJob) {
		EmailFormat emailFormat = communicationManager.getEmailTemplateByCommunicationJob(communicationJob);
		return emailFormat;
	}
	
	@Override
	public CommunicationBusinessObj getBusinessObjectByCommunicationJob(CommunicationJob communicationJobs) {
		CommunicationBusinessObj businessObj = communicationManager.getBusinessObjectByCommunicationJob(communicationJobs);
		return businessObj;
	}

	@Override
	public Long saveCommunicationJos(CommunicationJob communicationJob) {
		Long jobId = communicationManager.saveCommunicationJos(communicationJob);
		return jobId;
	}

	@Override
	public CommunicationJob getCommunicationJob(Long jobId) {
		CommunicationJob communicationJob = communicationManager.getCommunicationJob(jobId);
		return communicationJob;
	}

	@Override
	public void updateCommunicationJobStatus(CommunicationJob communicationJob) {
		communicationManager.updateCommunicationJobStatus(communicationJob);
	}

	@Override
	public EmailFormat getParsedCommunicationTemplate(EmailFormat emailFormat, CommunicationBusinessObj businessObj) throws ParseException {
		EmailFormat format=communicationManager.getParsedCommunicationTemplate(emailFormat, businessObj);
        return format;
	}

	@Override
	public List<CommunicationJob> getAllNonProcessedMsg() {
		List<CommunicationJob> communicationJobs = communicationManager.getAllNonProcessedMsg();
		return communicationJobs;
	}
}
