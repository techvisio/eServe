package com.techvisio.eserve.service;

import java.util.List;

import org.apache.velocity.runtime.parser.ParseException;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.CommunicationBusinessObj;
import com.techvisio.eserve.beans.CommunicationJob;
import com.techvisio.eserve.beans.EmailFormat;
@Component
public interface CommunicationService {

	public List<String> getWhiteListedRecipietents(List<String> recipitents);
	public CommunicationBusinessObj getBusinessObjectByCommunicationJob(CommunicationJob communicationJobs);
	public Long saveCommunicationJos(CommunicationJob communicationJob);
	public CommunicationJob getCommunicationJob(Long jobId);
	public void updateCommunicationJobStatus(CommunicationJob communicationJob);
	public EmailFormat getParsedCommunicationTemplate(EmailFormat emailFormat, CommunicationBusinessObj businessObj) throws ParseException;
	public EmailFormat getEmailTemplateByCommunicationJob(CommunicationJob communicationJob);
	public List<CommunicationJob> getAllNonProcessedMsg();
}
