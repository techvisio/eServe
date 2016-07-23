package com.techvisio.eserve.db.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Client;
import com.techvisio.eserve.beans.Config;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.CustomerType;
import com.techvisio.eserve.beans.Department;
import com.techvisio.eserve.beans.Designation;
import com.techvisio.eserve.beans.Equipment;
import com.techvisio.eserve.beans.InvoiceTaxes;
import com.techvisio.eserve.beans.Issue;
import com.techvisio.eserve.beans.Privilege;
import com.techvisio.eserve.beans.QuestionMaster;
import com.techvisio.eserve.beans.Resolution;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.SearchResultData;
import com.techvisio.eserve.beans.ServiceProvider;
import com.techvisio.eserve.beans.State;
import com.techvisio.eserve.beans.UnitCategory;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.db.MasterDataDao;

@Component
public class MasterDataDaoImpl extends BaseDao implements MasterDataDao{
	
	@Override
	public SearchResultData getClientMasterData(Long clientId){
		
		SearchResultData<Client> searchResultData= new SearchResultData<Client>();
		
		String queryString="SELECT * FROM Client c WHERE c.clientId ="+clientId;
		Query query= getEntityManager().createQuery(queryString);
		
		String queryStringforCount="SELECT COUNT(*) FROM tb_client_master";
		Query countQuery= getEntityManager().createNativeQuery(queryStringforCount);
		
		@SuppressWarnings("unchecked")
		List<Object[]> counts = countQuery.getResultList();
		for (Object[] count : counts) {
			Long count1 = (long) ((Number) count[0]).intValue();
			searchResultData.setTotalCount(count1);
		}
		
		@SuppressWarnings("unchecked")
		List<Client> result= (List<Client>)query.getResultList();
		searchResultData.setObjectData(result);
		return searchResultData;
		
	}
	
	@Override
	public Long saveClient(Client client){
		if(client.getClientId() == null){
			getEntityManager().persist(client);
		}
		else{
			
			getEntityManager().merge(client);
			//saveUserPrivilege(user.getPrivileges(), user.getUserId());
		}

		//Explicitly flush session
		getEntityManager().flush();
		return client.getClientId();
	}
	
	@Override
	public Client getClient(Long clientId) {
		String queryString="FROM Client c WHERE c.clientId = "+clientId;
		Query query= getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Client> result= (List<Client>)query.getResultList();
		if(result != null && result.size()>0){
			return result.get(0);
		}
		return null;
	}

	
	@Override
	public SearchResultData getQuestionMasterData(Long clientId){
		
		SearchResultData<QuestionMaster> searchResultData= new SearchResultData<QuestionMaster>();
		
		String queryString="SELECT * FROM QuestionMaster qm WHERE qm.clientId ="+clientId;
		Query query= getEntityManager().createQuery(queryString);
		
		String queryString1="SELECT COUNT(*) FROM tb_question_master";
		Query query1= getEntityManager().createNativeQuery(queryString1);
		
		@SuppressWarnings("unchecked")
		List<Object[]> counts = query1.getResultList();
		for (Object[] count : counts) {
			Long count1 = (long) ((Number) count[0]).intValue();
			searchResultData.setTotalCount(count1);
		}
		
		@SuppressWarnings("unchecked")
		List<QuestionMaster> result= (List<QuestionMaster>)query.getResultList();
		searchResultData.setObjectData(result);
		return searchResultData;
		
	}
	
	@Override
	public void saveQuestion(QuestionMaster question){
		if(question.getQuestion() == null){
			getEntityManager().persist(question);
		}
		else{
			
			getEntityManager().merge(question);
			//saveUserPrivilege(user.getPrivileges(), user.getUserId());
		}

		//Explicitly flush session
		getEntityManager().flush();
		
	}
	
	@Override
	public QuestionMaster getQuestion(String question) {
		String queryString="FROM QuestionMaster qm WHERE qm.question= "+question;
		Query query= getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<QuestionMaster> result= (List<QuestionMaster>)query.getResultList();
		if(result != null && result.size()>0){
			return result.get(0);
		}
		return null;
	}
	
	@Override
	public SearchResultData getStateMasterData(Long clientId){
		
		SearchResultData<State> searchResultData= new SearchResultData<State>();
		
		String queryString="SELECT * FROM State s WHERE s.clientId ="+clientId;
		Query query= getEntityManager().createQuery(queryString);
		
		String queryString1="SELECT COUNT(*) FROM tb_state_master";
		Query query1= getEntityManager().createNativeQuery(queryString1);
		
		@SuppressWarnings("unchecked")
		List<Object[]> counts = query1.getResultList();
		for (Object[] count : counts) {
			Long count1 = (long) ((Number) count[0]).intValue();
			searchResultData.setTotalCount(count1);
		}
		
		@SuppressWarnings("unchecked")
		List<State> result= (List<State>)query.getResultList();
		searchResultData.setObjectData(result);
		return searchResultData;
		
	}

	@Override
	public Long saveState(State state){
		if(state.getStateId() == null){
			getEntityManager().persist(state);
		}
		else{
			
			getEntityManager().merge(state);
			//saveUserPrivilege(user.getPrivileges(), user.getUserId());
		}

		//Explicitly flush session
		getEntityManager().flush();
		return state.getStateId();
	}
	
	@Override
	public State getState(Long stateId) {
		String queryString="FROM State s WHERE s.stateId= "+stateId;
		Query query= getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<State> result= (List<State>)query.getResultList();
		if(result != null && result.size()>0){
			return result.get(0);
		}
		return null;
	}
	
	@Override
	public SearchResultData getPrivilegeData(Long clientId){
		
		SearchResultData<Privilege> searchResultData= new SearchResultData<Privilege>();
		
		String queryString="SELECT * FROM Privilege p WHERE p.clientId ="+clientId;
		Query query= getEntityManager().createQuery(queryString);
		
		String queryString1="SELECT COUNT(*) FROM tb_privilege";
		Query query1= getEntityManager().createNativeQuery(queryString1);
		
		@SuppressWarnings("unchecked")
		List<Object[]> counts = query1.getResultList();
		for (Object[] count : counts) {
			Long count1 = (long) ((Number) count[0]).intValue();
			searchResultData.setTotalCount(count1);
		}
		
		@SuppressWarnings("unchecked")
		List<Privilege> result= (List<Privilege>)query.getResultList();
		searchResultData.setObjectData(result);
		return searchResultData;
		
	}
	
	@Override
	public Long savePrivilege(Privilege privilege){
		if(privilege.getPrivilegeId() == null){
			getEntityManager().persist(privilege);
		}
		else{
			
			getEntityManager().merge(privilege);
			//saveUserPrivilege(user.getPrivileges(), user.getUserId());
		}

		//Explicitly flush session
		getEntityManager().flush();
		return privilege.getPrivilegeId();
	}
	
	@Override
	public Privilege getPrivilege(Long privilegeId) {
		String queryString="FROM Privilege p WHERE p.privilegeId= "+privilegeId;
		Query query= getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Privilege> result= (List<Privilege>)query.getResultList();
		if(result != null && result.size()>0){
			return result.get(0);
		}
		return null;
	}
	
	@Override
	public SearchResultData getDepartmentMasterData(Long clientId){
		
		SearchResultData<Department> searchResultData= new SearchResultData<Department>();
		
		String queryString="SELECT * FROM Department d WHERE d.clientId ="+clientId;
		Query query= getEntityManager().createQuery(queryString);
		
		String queryString1="SELECT COUNT(*) FROM tb_department_master";
		Query query1= getEntityManager().createNativeQuery(queryString1);
		
		@SuppressWarnings("unchecked")
		List<Object[]> counts = query1.getResultList();
		for (Object[] count : counts) {
			Long count1 = (long) ((Number) count[0]).intValue();
			searchResultData.setTotalCount(count1);
		}
		
		@SuppressWarnings("unchecked")
		List<Department> result= (List<Department>)query.getResultList();
		searchResultData.setObjectData(result);
		return searchResultData;
		
	}
	
	@Override
	public Long saveDepartment(Department department){
		if(department.getDepartmentId() == null){
			getEntityManager().persist(department);
		}
		else{
			
			getEntityManager().merge(department);
			//saveUserPrivilege(user.getPrivileges(), user.getUserId());
		}

		//Explicitly flush session
		getEntityManager().flush();
		return department.getDepartmentId();
	}
	
	@Override
	public Department getDepartment(Long departmentId) {
		String queryString="FROM Department d WHERE d.departmentId= "+departmentId;
		Query query= getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Department> result= (List<Department>)query.getResultList();
		if(result != null && result.size()>0){
			return result.get(0);
		}
		return null;
	}

	@Override
	public SearchResultData getDesignationMasterData(Long clientId){
		
		SearchResultData<Designation> searchResultData= new SearchResultData<Designation>();
		
		String queryString="SELECT * FROM Designation d WHERE d.clientId ="+clientId;
		Query query= getEntityManager().createQuery(queryString);
		
		String queryString1="SELECT COUNT(*) FROM tb_designation_master";
		Query query1= getEntityManager().createNativeQuery(queryString1);
		
		@SuppressWarnings("unchecked")
		List<Object[]> counts = query1.getResultList();
		for (Object[] count : counts) {
			Long count1 = (long) ((Number) count[0]).intValue();
			searchResultData.setTotalCount(count1);
		}
		
		@SuppressWarnings("unchecked")
		List<Designation> result= (List<Designation>)query.getResultList();
		searchResultData.setObjectData(result);
		return searchResultData;
		
	}
	
	@Override
	public Long saveDesignation(Designation designation){
		if(designation.getDesignationId() == null){
			getEntityManager().persist(designation);
		}
		else{
			
			getEntityManager().merge(designation);
			//saveUserPrivilege(user.getPrivileges(), user.getUserId());
		}

		//Explicitly flush session
		getEntityManager().flush();
		return designation.getDesignationId();
	}
	
	@Override
	public Designation getDesignation(Long designationId) {
		String queryString="FROM Designation d WHERE d.designationId= "+designationId;
		Query query= getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Designation> result= (List<Designation>)query.getResultList();
		if(result != null && result.size()>0){
			return result.get(0);
		}
		return null;
	}

	@Override
	public SearchResultData getEquipmentMasterData(Long clientId){
		
		SearchResultData<Equipment> searchResultData= new SearchResultData<Equipment>();
		
		String queryString="SELECT * FROM Equipment e WHERE e.clientId ="+clientId;
		Query query= getEntityManager().createQuery(queryString);
		
		String queryString1="SELECT COUNT(*) FROM tb_equipment_master";
		Query query1= getEntityManager().createNativeQuery(queryString1);
		
		@SuppressWarnings("unchecked")
		List<Object[]> counts = query1.getResultList();
		for (Object[] count : counts) {
			Long count1 = (long) ((Number) count[0]).intValue();
			searchResultData.setTotalCount(count1);
		}
		
		@SuppressWarnings("unchecked")
		List<Equipment> result= (List<Equipment>)query.getResultList();
		searchResultData.setObjectData(result);
		return searchResultData;
		
	}
	
	@Override
	public Long saveEquipment(Equipment equipment){
		if(equipment.getEquipmentId() == null){
			getEntityManager().persist(equipment);
		}
		else{
			
			getEntityManager().merge(equipment);
			//saveUserPrivilege(user.getPrivileges(), user.getUserId());
		}

		//Explicitly flush session
		getEntityManager().flush();
		return equipment.getEquipmentId();
	}
	
	@Override
	public Equipment getEquipment(Long equipmentId) {
		String queryString="FROM Equipment e WHERE e.equipmentId= "+equipmentId;
		Query query= getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Equipment> result= (List<Equipment>)query.getResultList();
		if(result != null && result.size()>0){
			return result.get(0);
		}
		return null;
	}

	
	@Override
	public SearchResultData getConfigData(Long clientId){
		
		SearchResultData<Config> searchResultData= new SearchResultData<Config>();
		
		String queryString="SELECT * FROM Config c WHERE c.clientId ="+clientId;
		Query query= getEntityManager().createQuery(queryString);
		
		String queryString1="SELECT COUNT(*) FROM tb_config";
		Query query1= getEntityManager().createNativeQuery(queryString1);
		
		@SuppressWarnings("unchecked")
		List<Object[]> counts = query1.getResultList();
		for (Object[] count : counts) {
			Long count1 = (long) ((Number) count[0]).intValue();
			searchResultData.setTotalCount(count1);
		}
		
		@SuppressWarnings("unchecked")
		List<Config> result= (List<Config>)query.getResultList();
		searchResultData.setObjectData(result);
		return searchResultData;
		
	}
	
	@Override
	public Long saveConfig(Config config){
		if(config.getConfigTBId() == null){
			getEntityManager().persist(config);
		}
		else{
			
			getEntityManager().merge(config);
			//saveUserPrivilege(user.getPrivileges(), user.getUserId());
		}

		//Explicitly flush session
		getEntityManager().flush();
		return config.getConfigTBId();
	}
	
	@Override
	public Config getConfig(Long configTBId) {
		String queryString="FROM Config c WHERE c.configTBId= "+configTBId;
		Query query= getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Config> result= (List<Config>)query.getResultList();
		if(result != null && result.size()>0){
			return result.get(0);
		}
		return null;
	}
	
	@Override
	public SearchResultData getIssueMasterData(Long clientId){
		
		SearchResultData<Issue> searchResultData= new SearchResultData<Issue>();
		
		String queryString="SELECT * FROM Issue s WHERE s.clientId ="+clientId;
		Query query= getEntityManager().createQuery(queryString);
		
		String queryString1="SELECT COUNT(*) FROM tb_issue_master";
		Query query1= getEntityManager().createNativeQuery(queryString1);
		
		@SuppressWarnings("unchecked")
		List<Object[]> counts = query1.getResultList();
		for (Object[] count : counts) {
			Long count1 = (long) ((Number) count[0]).intValue();
			searchResultData.setTotalCount(count1);
		}
		
		@SuppressWarnings("unchecked")
		List<Issue> result= (List<Issue>)query.getResultList();
		searchResultData.setObjectData(result);
		return searchResultData;
		
	}
	
	@Override
	public Long saveIssue(Issue issue){
		if(issue.getIssueId() == null){
			getEntityManager().persist(issue);
		}
		else{
			
			getEntityManager().merge(issue);
			//saveUserPrivilege(user.getPrivileges(), user.getUserId());
		}

		//Explicitly flush session
		getEntityManager().flush();
		return issue.getIssueId();
	}
	
	@Override
	public Issue getIssue(Long issueId) {
		String queryString="FROM Issue i WHERE i.issueId= "+issueId;
		Query query= getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Issue> result= (List<Issue>)query.getResultList();
		if(result != null && result.size()>0){
			return result.get(0);
		}
		return null;
	}

	@Override
	public SearchResultData getResolutionMasterData(Long clientId){
		
		SearchResultData<Resolution> searchResultData= new SearchResultData<Resolution>();
		
		String queryString="SELECT * FROM Resolution r WHERE r.clientId ="+clientId;
		Query query= getEntityManager().createQuery(queryString);
		
		String queryString1="SELECT COUNT(*) FROM tb_resolution_master";
		Query query1= getEntityManager().createNativeQuery(queryString1);
		
		@SuppressWarnings("unchecked")
		List<Object[]> counts = query1.getResultList();
		for (Object[] count : counts) {
			Long count1 = (long) ((Number) count[0]).intValue();
			searchResultData.setTotalCount(count1);
		}
		
		@SuppressWarnings("unchecked")
		List<Resolution> result= (List<Resolution>)query.getResultList();
		searchResultData.setObjectData(result);
		return searchResultData;
		
	}
	
	@Override
	public Long saveResolution(Resolution resolution){
		if(resolution.getResolutionId() == null){
			getEntityManager().persist(resolution);
		}
		else{
			
			getEntityManager().merge(resolution);
			//saveUserPrivilege(user.getPrivileges(), user.getUserId());
		}

		//Explicitly flush session
		getEntityManager().flush();
		return resolution.getResolutionId();
	}
	
	@Override
	public Resolution getResolution(Long resolutionId) {
		String queryString="FROM Resolution r WHERE r.resolutionId= "+resolutionId;
		Query query= getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Resolution> result= (List<Resolution>)query.getResultList();
		if(result != null && result.size()>0){
			return result.get(0);
		}
		return null;
	}

	@Override
	public SearchResultData getUnitCategoryMasterData(Long clientId){
		
		SearchResultData<UnitCategory> searchResultData= new SearchResultData<UnitCategory>();
		
		String queryString="SELECT * FROM  UnitCategory uc WHERE uc.clientId ="+clientId;
		Query query= getEntityManager().createQuery(queryString);
		
		String queryString1="SELECT COUNT(*) FROM  tb_unit_category_master";
		Query query1= getEntityManager().createNativeQuery(queryString1);
		
		@SuppressWarnings("unchecked")
		List<Object[]> counts = query1.getResultList();
		for (Object[] count : counts) {
			Long count1 = (long) ((Number) count[0]).intValue();
			searchResultData.setTotalCount(count1);
		}
		
		@SuppressWarnings("unchecked")
		List<UnitCategory> result= (List<UnitCategory>)query.getResultList();
		searchResultData.setObjectData(result);
		return searchResultData;
		
	}
	
	@Override
	public Long saveUnitCategory(UnitCategory unitCategory){
		if(unitCategory.getUnitCategoryId() == null){
			getEntityManager().persist(unitCategory);
		}
		else{
			
			getEntityManager().merge(unitCategory);
			//saveUserPrivilege(user.getPrivileges(), user.getUserId());
		}

		//Explicitly flush session
		getEntityManager().flush();
		return unitCategory.getUnitCategoryId();
	}
	
	@Override
	public UnitCategory getUnitCategory(Long unitCategoryId) {
		String queryString="FROM UnitCategory uc WHERE uc.unitCategoryId= "+unitCategoryId;
		Query query= getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<UnitCategory> result= (List<UnitCategory>)query.getResultList();
		if(result != null && result.size()>0){
			return result.get(0);
		}
		return null;
	}
	
	@Override
	public SearchResultData getServiceProviderMasterData(Long clientId){
		
		SearchResultData<ServiceProvider> searchResultData= new SearchResultData<ServiceProvider>();
		
		String queryString="SELECT * FROM  ServiceProvider sp WHERE sp.clientId ="+clientId;
		Query query= getEntityManager().createQuery(queryString);
		
		String queryString1="SELECT COUNT(*) FROM  tb_service_provider_master";
		Query query1= getEntityManager().createNativeQuery(queryString1);
		
		@SuppressWarnings("unchecked")
		List<Object[]> counts = query1.getResultList();
		for (Object[] count : counts) {
			Long count1 = (long) ((Number) count[0]).intValue();
			searchResultData.setTotalCount(count1);
		}
		
		@SuppressWarnings("unchecked")
		List<ServiceProvider> result= (List<ServiceProvider>)query.getResultList();
		searchResultData.setObjectData(result);
		return searchResultData;
		
	}
	
	@Override
	public Long saveServiceProvider(ServiceProvider serviceProvider){
		if(serviceProvider.getServiceProviderId() == null){
			getEntityManager().persist(serviceProvider);
		}
		else{
			
			getEntityManager().merge(serviceProvider);
			//saveUserPrivilege(user.getPrivileges(), user.getUserId());
		}

		//Explicitly flush session
		getEntityManager().flush();
		return serviceProvider.getServiceProviderId();
	}
	
	@Override
	public ServiceProvider getServiceProvider(Long serviceProviderId) {
		String queryString="FROM ServiceProvider sp WHERE sp.serviceProviderId= "+serviceProviderId;
		Query query= getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<ServiceProvider> result= (List<ServiceProvider>)query.getResultList();
		if(result != null && result.size()>0){
			return result.get(0);
		}
		return null;
	}
	
	@Override
	public SearchResultData getCustomerTypeMasterData(Long clientId){
		
		SearchResultData<CustomerType> searchResultData= new SearchResultData<CustomerType>();
		
		String queryString="SELECT * FROM  CustomerType ct WHERE ct.clientId ="+clientId;
		Query query= getEntityManager().createQuery(queryString);
		
		String queryString1="SELECT COUNT(*) FROM  tb_customer_type_master";
		Query query1= getEntityManager().createNativeQuery(queryString1);
		
		@SuppressWarnings("unchecked")
		List<Object[]> counts = query1.getResultList();
		for (Object[] count : counts) {
			Long count1 = (long) ((Number) count[0]).intValue();
			searchResultData.setTotalCount(count1);
		}
		
		@SuppressWarnings("unchecked")
		List<CustomerType> result= (List<CustomerType>)query.getResultList();
		searchResultData.setObjectData(result);
		return searchResultData;
		
	}
	
	@Override
	public Long saveCustomerType(CustomerType customerType){
		if(customerType.getCustomerTypeId() == null){
			getEntityManager().persist(customerType);
		}
		else{
			
			getEntityManager().merge(customerType);
			//saveUserPrivilege(user.getPrivileges(), user.getUserId());
		}

		//Explicitly flush session
		getEntityManager().flush();
		return customerType.getCustomerTypeId();
	}
	
	@Override
	public CustomerType getCustomerType(Long customerTypeId) {
		String queryString="FROM CustomerType ct WHERE ct.customerTypeId= "+customerTypeId;
		Query query= getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<CustomerType> result= (List<CustomerType>)query.getResultList();
		if(result != null && result.size()>0){
			return result.get(0);
		}
		return null;
	}
	
	@Override
	public SearchResultData getInvoiceTaxesMasterData(Long clientId){
		
		SearchResultData<InvoiceTaxes> searchResultData= new SearchResultData<InvoiceTaxes>();
		
		String queryString="SELECT * FROM  InvoiceTaxes it WHERE it.clientId ="+clientId;
		Query query= getEntityManager().createQuery(queryString);
		
		String queryString1="SELECT COUNT(*) FROM  tb_invoice_taxes_master";
		Query query1= getEntityManager().createNativeQuery(queryString1);
		
		@SuppressWarnings("unchecked")
		List<Object[]> counts = query1.getResultList();
		for (Object[] count : counts) {
			Long count1 = (long) ((Number) count[0]).intValue();
			searchResultData.setTotalCount(count1);
		}
		
		@SuppressWarnings("unchecked")
		List<InvoiceTaxes> result= (List<InvoiceTaxes>)query.getResultList();
		searchResultData.setObjectData(result);
		return searchResultData;
		
	}
	
	@Override
	public Long saveInvoiceTaxes(InvoiceTaxes invoiceTaxes){
		if(invoiceTaxes.getComponentId() == null){
			getEntityManager().persist(invoiceTaxes);
		}
		else{
			
			getEntityManager().merge(invoiceTaxes);
			//saveUserPrivilege(user.getPrivileges(), user.getUserId());
		}

		//Explicitly flush session
		getEntityManager().flush();
		return invoiceTaxes.getComponentId();
	}
	
	@Override
	public InvoiceTaxes getInvoiceTaxes(Long componentId) {
		String queryString="FROM InvoiceTaxes it WHERE it.componentId= "+componentId;
		Query query= getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<InvoiceTaxes> result= (List<InvoiceTaxes>)query.getResultList();
		if(result != null && result.size()>0){
			return result.get(0);
		}
		return null;
	}

}
