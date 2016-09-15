package com.techvisio.eserve.db.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.CommunicationJob;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.EmailFormat;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.UnitBasicCustomer;
import com.techvisio.eserve.beans.WorkItem;
import com.techvisio.eserve.db.CommunicationDao;
import com.techvisio.eserve.util.AppConstants;

@Component
public class CommunicationDaoImpl extends BaseDao implements CommunicationDao{

	@Override
	public Customer getCustomer(Long customerId) {
		String queryString="FROM Customer cus WHERE cus.customerId = "+customerId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Customer> customers= (List<Customer>)query.getResultList();
		if(customers != null && customers.size()>0){
			Customer clonedCustomer=SerializationUtils.clone(customers.get(0));
			return clonedCustomer;
		}
		return null;
	}

	@Override
	public Unit getUnit(Long unitId) {
		String queryString="FROM Unit u WHERE u.unitId = "+unitId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Unit> units= (List<Unit>)query.getResultList();
		if(units != null && units.size()>0){
			Unit clonedUnit=SerializationUtils.clone(units.get(0));
			return clonedUnit;
		}
		return null;
	}

	@Override
	public EmailFormat getEmailTemplateByCommunicationJob(CommunicationJob communicationJobs) {
		String queryString="FROM EmailFormat ef WHERE ef.communicationType = "+"'"+communicationJobs.getCommunicationType()+"'"+" and ef.eventType = "+"'"+communicationJobs.getEventType()+"'"
				+" and ef.client.clientId = "+communicationJobs.getClient().getClientId();
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<EmailFormat> formats= (List<EmailFormat>)query.getResultList();
		if(formats != null && formats.size()>0){
			EmailFormat clonedEmailFormat=SerializationUtils.clone(formats.get(0));
			return clonedEmailFormat;
		}
		return null;
	}

	@Override
	public CommunicationJob getCommunicationJob(Long jobId){
		String queryString="FROM CommunicationJob cj WHERE cj.jobId = "+jobId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<CommunicationJob> jobs= (List<CommunicationJob>)query.getResultList();
		if(jobs != null && jobs.size()>0){

			return jobs.get(0);
		}
		return null;
	}

	@Override
	public Long saveCommunicationJos(CommunicationJob communicationJob){
		if(communicationJob.getJobId()== null){
			getEntityManager().persist(communicationJob);
		}
		else
		{
			getEntityManager().merge(communicationJob);
		}
		return communicationJob.getJobId();
	}

	@Override
	public List<CommunicationJob> getAllNonProcessedMsg(){
		String queryString="FROM CommunicationJob cj WHERE cj.status =" + null;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<CommunicationJob> jobs= (List<CommunicationJob>)query.getResultList();
		return jobs;
	}
}