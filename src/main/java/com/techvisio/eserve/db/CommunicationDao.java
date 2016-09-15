package com.techvisio.eserve.db;

import java.util.List;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.CommunicationJob;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.EmailFormat;
import com.techvisio.eserve.beans.Unit;
@Component
public interface CommunicationDao {

	public Customer getCustomer(Long customerId);
	public Unit getUnit(Long unitId);
	public EmailFormat getEmailTemplateByCommunicationJob(CommunicationJob communicationJobs);
	public Long saveCommunicationJos(CommunicationJob communicationJob);
	public CommunicationJob getCommunicationJob(Long jobId);
	public List<CommunicationJob> getAllNonProcessedMsg();

}
