package com.techvisio.eserve.db.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.AgreementDuration;
import com.techvisio.eserve.beans.Client;
import com.techvisio.eserve.beans.ClientCommConfig;
import com.techvisio.eserve.beans.ClientConfig;
import com.techvisio.eserve.beans.CustomerType;
import com.techvisio.eserve.beans.Department;
import com.techvisio.eserve.beans.Designation;
import com.techvisio.eserve.beans.InvoiceTaxes;
import com.techvisio.eserve.beans.Issue;
import com.techvisio.eserve.beans.Privilege;
import com.techvisio.eserve.beans.QuestionMaster;
import com.techvisio.eserve.beans.Resolution;
import com.techvisio.eserve.beans.ServiceProvider;
import com.techvisio.eserve.beans.State;
import com.techvisio.eserve.beans.UnitCategory;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.db.CacheDao;
import com.techvisio.eserve.util.CustomLogger;

@Transactional
@Component
public class CacheDaoImpl extends BaseDao implements CacheDao {
	private static CustomLogger logger = CustomLogger.getLogger(CacheDaoImpl.class);

	public List<State> getState(){
		String queryString="FROM State";
		Query query= getEntityManager().createQuery(queryString);
		List<State> result= query.getResultList();
		return result;
	}

	@Override
	public List<QuestionMaster> getQuestions() {
		String queryString="FROM QuestionMaster";
		Query query=getEntityManager().createQuery(queryString);
		List<QuestionMaster> result= query.getResultList();
		return result;
	}

	@Override
	public List<Department> getDepartments() {
		String queryString="FROM Department";
		Query query=getEntityManager().createQuery(queryString);
		List<Department> result= query.getResultList();
		return result;
	}

	@Override
	public List<Designation> getDesignations() {
		String queryString="FROM Designation";
		Query query=getEntityManager().createQuery(queryString);
		List<Designation> result= query.getResultList();
		return result;
	}
	
	@Override
	public List<ClientConfig> getDefaultValues() {
		String queryString="FROM Config";
		Query query=getEntityManager().createQuery(queryString);
		List<ClientConfig> result= query.getResultList();
		return result;
	}
	
	@Override
	public List<AgreementDuration> getAgreementDuration() {
		String queryString="FROM AgreementDuration";
		Query query=getEntityManager().createQuery(queryString);
		List<AgreementDuration> result= query.getResultList();
		return result;
	}
	
	@Override
	public List<UnitCategory> getUnitCategories() {
		String queryString="FROM UnitCategory";
		Query query=getEntityManager().createQuery(queryString);
		List<UnitCategory> result= query.getResultList();
		return result;
	}
	
	@Override
	public List<CustomerType> getCustomerTypes() {
		String queryString="FROM CustomerType";
		Query query=getEntityManager().createQuery(queryString);
		List<CustomerType> result= query.getResultList();
		return result;
	}
	
	@Override
	public List<ServiceProvider> getServiceProviders() {
		String queryString="FROM ServiceProvider";
		Query query=getEntityManager().createQuery(queryString);
		List<ServiceProvider> result= query.getResultList();
		return result;
	}
	
	@Override
	public List<Resolution> getResolution(){
		String queryString="FROM Resolution";
		Query query= getEntityManager().createQuery(queryString);
		List<Resolution> result= query.getResultList();
		return result;
	}

	@Override
	public List<User> getUsers() {
		String queryString="FROM User";
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<User> result= (List<User>)query.getResultList();
		return result;
	}
	
	@Override
	public List<State> getState(Long clientId){
		String queryString="FROM State s where s.clientId = "+ clientId ;
		Query query=getEntityManager().createQuery(queryString);
		List<State> result= query.getResultList();
		return result;
	}

	@Override
	public Client getClient(Long clientId){
		String queryString="FROM Client s where s.clientId = "+ clientId ;
		Query query=getEntityManager().createQuery(queryString);
		List<Client> result= query.getResultList();
		if(result != null && result.size()>0){
			return result.get(0);
		}
		return null;
	}
	
	@Override
	public List<QuestionMaster> getQuestions(Long clientId) {
		String queryString="FROM QuestionMaster qm where qm.clientId = "+clientId;
		Query query=getEntityManager().createQuery(queryString);
		List<QuestionMaster> result= query.getResultList();
		return result;
	}

	@Override
	public List<Department> getDepartments(Long clientId) {
		String queryString="FROM Department d where d.clientId = " + clientId;
		Query query= getEntityManager().createQuery(queryString);
		List<Department> result= query.getResultList();
		return result;
	}

	@Override
	public List<Designation> getDesignations(Long clientId) {
		String queryString="FROM Designation d where d.clientId = " + clientId;
		Query query= getEntityManager().createQuery(queryString);
		List<Designation> result= query.getResultList();
		return result;
	}

	@Override
	public List<Privilege> getPrivileges(Long clientId) {
		String queryString="FROM Privilege p where p.client.clientId = " + clientId;
		Query query= getEntityManager().createQuery(queryString);
		List<Privilege> result= query.getResultList();
		return result;
	}
	
	@Override
	public List<ClientConfig> getDefalutValues(Long clientId) {
		String queryString="FROM Config c where c.client.clientId = " + clientId;
		Query query= getEntityManager().createQuery(queryString);
		List<ClientConfig> result= query.getResultList();
		return result;
	}
	
	@Override
	public List<ClientCommConfig> getClientComConfigValues(Long clientId) {
		String queryString="FROM ClientComConfig c where c.client.clientId = " + clientId;
		Query query= getEntityManager().createQuery(queryString);
		List<ClientCommConfig> result= query.getResultList();
		return result;
	}

	@Override
	public List<Issue> getIssues(){
		String queryString="FROM Issue";
		Query query=getEntityManager().createQuery(queryString);
		List<Issue> result= query.getResultList();
		return result;
	}
	
	@Override
	public AgreementDuration getAgreementDuration(Long durationId){
		String queryString="FROM AgreementDuration ad WHERE ad.agreementDurationId = "+durationId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<AgreementDuration> agreementDurations= (List<AgreementDuration>)query.getResultList();
		if(agreementDurations != null && agreementDurations.size()>0){
			return agreementDurations.get(0);
		}
		return null;
		}
	
	
	@Override
	public List<InvoiceTaxes> getInvoiceTaxes(Long clientId) {
		String queryString="FROM InvoiceTaxes it where it.client.clientId = " + clientId;
		Query query= getEntityManager().createQuery(queryString);
		List<InvoiceTaxes> result= query.getResultList();
		return result;
	}

	@Override
	public List<ClientConfig> getClientPreferences() {
		String queryString="FROM ClientConfig";
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<ClientConfig> configs= (List<ClientConfig>)query.getResultList();
		return configs;
	}

	@Override
	public List<ClientCommConfig> getClientCommunicationConfig() {
		String queryString="FROM ClientCommConfig c";
		Query query= getEntityManager().createQuery(queryString);
		List<ClientCommConfig> result= query.getResultList();
		return result;
	}

}
