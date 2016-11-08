package com.techvisio.eserve.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Client;
import com.techvisio.eserve.beans.ClientConfig;
import com.techvisio.eserve.beans.CustomerType;
import com.techvisio.eserve.beans.Department;
import com.techvisio.eserve.beans.Designation;
import com.techvisio.eserve.beans.InvoiceTaxes;
import com.techvisio.eserve.beans.Issue;
import com.techvisio.eserve.beans.Privilege;
import com.techvisio.eserve.beans.QuestionMaster;
import com.techvisio.eserve.beans.Resolution;
import com.techvisio.eserve.beans.SearchResultData;
import com.techvisio.eserve.beans.ServiceProvider;
import com.techvisio.eserve.beans.State;
import com.techvisio.eserve.beans.UnitCategory;
import com.techvisio.eserve.db.MasterDataDao;
import com.techvisio.eserve.manager.MasterDataManager;

@Component
public class MasterDataManagerImpl implements MasterDataManager {

	@Autowired
	MasterDataDao masterDataDao;
	
	public SearchResultData getClientMasterData(Long clientId){
		
		SearchResultData  clientMasterData = masterDataDao.getClientMasterData(clientId);
		return clientMasterData;
	}

	@Override
	public Long saveClient(Client client) {
		// TODO Auto-generated method stub
		Long  saveClient = masterDataDao.saveClient(client);
		return saveClient;
	}
	
	@Override
	public Client getClient(Long clientId) {
		Client client = masterDataDao.getClient(clientId);
		return client;
	}

	@Override
	public SearchResultData getQuestionMasterData(Long clientId) {
		// TODO Auto-generated method stub
		SearchResultData  questionMasterData = masterDataDao.getQuestionMasterData(clientId);
		return questionMasterData;
	}

	@Override
	public void saveQuestion(QuestionMaster question) {
		// TODO Auto-generated method stub
		 masterDataDao.saveQuestion(question);
		
		
	}
	
	@Override
	public QuestionMaster getQuestion(String question) {
		QuestionMaster Question = masterDataDao.getQuestion(question);
		return Question;
	}

	@Override
	public SearchResultData getStateMasterData(Long clientId) {
		// TODO Auto-generated method stub
		SearchResultData  stateMasterData = masterDataDao.getStateMasterData(clientId);
		return stateMasterData;
	}

	@Override
	public Long saveState(State state) {
		// TODO Auto-generated method stub
		Long  saveState = masterDataDao.saveState(state);
		return saveState;
	}
	
	@Override
	public State getState(Long stateId) {
		State state = masterDataDao.getState(stateId);
		return state;
	}

	@Override
	public SearchResultData getPrivilegeData(Long clientId) {
		// TODO Auto-generated method stub
		SearchResultData  stateMasterData = masterDataDao.getPrivilegeData(clientId);
		return stateMasterData;
	}

	@Override
	public Long savePrivilege(Privilege privilege) {
		// TODO Auto-generated method stub
		Long  savePrivilege = masterDataDao.savePrivilege(privilege);
		return savePrivilege;
	}
	
	@Override
	public Privilege getPrivilege(Long privilegeId) {
		Privilege  privilege  = masterDataDao.getPrivilege(privilegeId);
		return privilege ;
	}

	@Override
	public SearchResultData getDepartmentMasterData(Long clientId) {
		// TODO Auto-generated method stub
		SearchResultData  departmentMasterData = masterDataDao.getDepartmentMasterData(clientId);
		return departmentMasterData;
	}

	@Override
	public Long saveDepartment(Department department) {
		// TODO Auto-generated method stub
		Long  saveDepartment = masterDataDao.saveDepartment(department);
		return saveDepartment;
	}
	
	@Override
	public Department getDepartment(Long departmentId) {
		Department  department  = masterDataDao.getDepartment(departmentId);
		return department ;
	}

	@Override
	public SearchResultData getDesignationMasterData(Long clientId) {
		// TODO Auto-generated method stub
		SearchResultData  designationMasterData = masterDataDao.getDesignationMasterData(clientId);
		return designationMasterData;
	}

	@Override
	public Long saveDesignation(Designation designation) {
		// TODO Auto-generated method stub
		Long  saveDesignation = masterDataDao.saveDesignation(designation);
		return saveDesignation;
	}
	
	@Override
	public Designation getDesignation(Long designationId) {
		Designation  designation = masterDataDao.getDesignation(designationId);
		return designation ;
	}


	@Override
	public SearchResultData getConfigData(Long clientId) {
		// TODO Auto-generated method stub
		SearchResultData  configData = masterDataDao.getConfigData(clientId);
		return configData;
	}

	@Override
	public Long saveConfig(ClientConfig config) {
		// TODO Auto-generated method stub
		Long  saveConfig = masterDataDao.saveConfig(config);
		return saveConfig;
	}
	
	@Override
	public ClientConfig getConfig(Long configTBId) {
		ClientConfig  config = masterDataDao.getConfig(configTBId);
		return config ;
	}

	@Override
	public SearchResultData getIssueMasterData(Long clientId) {
		// TODO Auto-generated method stub
		SearchResultData  issueMasterData = masterDataDao.getIssueMasterData(clientId);
		return issueMasterData;
	}

	@Override
	public Long saveIssue(Issue issue) {
		// TODO Auto-generated method stub
		Long  saveIssue = masterDataDao.saveIssue(issue);
		return saveIssue;
	}
	
	@Override
	public Issue getIssue(Long issueId) {
		Issue  issue = masterDataDao.getIssue(issueId);
		return issue ;
	}

	@Override
	public SearchResultData getResolutionMasterData(Long clientId) {
		// TODO Auto-generated method stub
		SearchResultData  resolutionMasterData = masterDataDao.getResolutionMasterData(clientId);
		return resolutionMasterData ;
	}

	@Override
	public Long saveResolution(Resolution resolution) {
		// TODO Auto-generated method stub
		Long  saveResolution = masterDataDao.saveResolution(resolution);
		return saveResolution;
	}
	
	@Override
	public Resolution getResolution(Long resolutionId) {
		Resolution  resolution = masterDataDao.getResolution(resolutionId);
		return resolution ;
	}

	@Override
	public SearchResultData getUnitCategoryMasterData(Long clientId) {
		// TODO Auto-generated method stub
		SearchResultData  unitCategoryMasterData = masterDataDao.getUnitCategoryMasterData(clientId);
		return unitCategoryMasterData;
	}

	@Override
	public Long saveUnitCategory(UnitCategory unitCategory) {
		// TODO Auto-generated method stub
		Long  saveUnitCategory = masterDataDao.saveUnitCategory(unitCategory);
		return saveUnitCategory;
	}
	
	@Override
	public UnitCategory getUnitCategory(Long unitCategoryId) {
		UnitCategory  unitCategory = masterDataDao.getUnitCategory(unitCategoryId);
		return unitCategory ;
	}

	@Override
	public SearchResultData getServiceProviderMasterData(Long clientId) {
		// TODO Auto-generated method stub
		SearchResultData  serviceProviderMasterData = masterDataDao.getServiceProviderMasterData(clientId);
		return serviceProviderMasterData;
	}

	@Override
	public Long saveServiceProvider(ServiceProvider serviceProvider) {
		// TODO Auto-generated method stub
		Long  saveServiceProvider = masterDataDao.saveServiceProvider(serviceProvider);
		return saveServiceProvider;
	}
	
	@Override
	public ServiceProvider getServiceProvider(Long serviceProviderId) {
		ServiceProvider  serviceProvider = masterDataDao.getServiceProvider(serviceProviderId);
		return serviceProvider ;
	}

	@Override
	public SearchResultData getCustomerTypeMasterData(Long clientId) {
		// TODO Auto-generated method stub
		SearchResultData  customerTypeMaster = masterDataDao.getCustomerTypeMasterData(clientId);
		return customerTypeMaster;
	}

	@Override
	public Long saveCustomerType(CustomerType customerType) {
		// TODO Auto-generated method stub
		Long  saveCustomerType = masterDataDao.saveCustomerType(customerType);
		return saveCustomerType;
	}
	
	@Override
	public CustomerType getCustomerType(Long customerTypeId) {
		CustomerType  customerType = masterDataDao.getCustomerType(customerTypeId);
		return customerType  ;
	}

	@Override
	public SearchResultData getInvoiceTaxesMasterData(Long clientId) {
		// TODO Auto-generated method stub
		SearchResultData  invoiceTaxesMasterData= masterDataDao.getInvoiceTaxesMasterData(clientId);
		return invoiceTaxesMasterData;
	}

	@Override
	public Long saveInvoiceTaxes(InvoiceTaxes invoiceTaxes) {
		// TODO Auto-generated method stub
		Long  saveInvoiceTaxes = masterDataDao.saveInvoiceTaxes(invoiceTaxes);
		return saveInvoiceTaxes;
	}
	
	@Override
	public InvoiceTaxes getInvoiceTaxes(Long componentId) {
		InvoiceTaxes  invoiceTaxes = masterDataDao.getInvoiceTaxes(componentId);
		return invoiceTaxes ;
	}
	
	
}
