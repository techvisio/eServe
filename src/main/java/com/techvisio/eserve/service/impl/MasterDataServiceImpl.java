package com.techvisio.eserve.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Client;
import com.techvisio.eserve.beans.Config;
import com.techvisio.eserve.beans.CustomerType;
import com.techvisio.eserve.beans.Department;
import com.techvisio.eserve.beans.Designation;
import com.techvisio.eserve.beans.Equipment;
import com.techvisio.eserve.beans.InvoiceTaxes;
import com.techvisio.eserve.beans.Issue;
import com.techvisio.eserve.beans.Privilege;
import com.techvisio.eserve.beans.Resolution;
import com.techvisio.eserve.beans.SearchResultData;
import com.techvisio.eserve.beans.ServiceProvider;
import com.techvisio.eserve.beans.State;
import com.techvisio.eserve.beans.UnitCategory;
import com.techvisio.eserve.manager.MasterDataManager;
import com.techvisio.eserve.service.MasterDataService;
import com.techvisio.eserve.util.CommonUtil;

@Component
public class MasterDataServiceImpl implements MasterDataService{

	@Autowired
	MasterDataManager masterDataManager;

	public Object  getEntity(Object object,String type){
		
		switch(type){
		
			case "CLIENT":
				Long  clientId = masterDataManager.saveClient((Client) object);
				Client client = masterDataManager.getClient(clientId);
				return client;
				
				
//		
//			case "QUESTION":
//				masterDataManager.saveQuestion((QuestionMaster) object);
//				QuestionMaster Question = masterDataManager.getQuestion(question);
//				return Question;
			
				
			case "STATE":
				Long stateId = masterDataManager.saveState((State) object);
				State state = masterDataManager.getState(stateId);
				return state;
				
			case "PRIVILEGE":
				Long  privilegeId = masterDataManager.savePrivilege((Privilege) object);
				Privilege privilege = masterDataManager.getPrivilege(privilegeId);
				return privilege;
				
			case "DEPARTMENT":
				Long  departmentId = masterDataManager.saveDepartment((Department) object);
				Department department= masterDataManager.getDepartment(departmentId);
				return department;
				
			case "DESIGNATION":
				Long  designationId = masterDataManager.saveDesignation((Designation) object);
				Designation designation= masterDataManager.getDesignation(designationId);
				return designation;
				
			case "EQUIPMENT":
				Long  equipmentId = masterDataManager.saveEquipment((Equipment) object);
				Equipment equipment= masterDataManager.getEquipment(equipmentId);
				return equipment;
				
			case "CONFIG":
				Long  configTBId = masterDataManager.saveConfig((Config) object);
				Config config= masterDataManager.getConfig(configTBId);
				return config;
				
			case "ISSUE":
				Long  issueId = masterDataManager.saveIssue((Issue) object);
				Issue issue= masterDataManager.getIssue(issueId);
				return issue;
				
			case "RESOLUTION":
				Long  resolutionId= masterDataManager.saveResolution((Resolution) object);
				Resolution resolution = masterDataManager.getResolution(resolutionId);
				return resolution;
				
			case "UNIT_CATEGORY":
				Long  unitCategoryId= masterDataManager.saveUnitCategory((UnitCategory) object);
				UnitCategory unitCategory = masterDataManager.getUnitCategory(unitCategoryId);
				return unitCategory;
				
			case "SERVICE_PROVIDER":
				Long  serviceProviderId = masterDataManager.saveServiceProvider((ServiceProvider) object);
				ServiceProvider serviceProvider = masterDataManager.getServiceProvider(serviceProviderId);
				return serviceProvider;
				
			case "CUSTOMER_TYPE":
				Long  customerTypeId = masterDataManager.saveCustomerType((CustomerType) object);
				CustomerType customerType = masterDataManager.getCustomerType(customerTypeId);
				return customerType;
				
			case "INVOICE_TAXES":
				Long  componentId= masterDataManager.saveInvoiceTaxes((InvoiceTaxes) object);
				InvoiceTaxes invoiceTaxes = masterDataManager.getInvoiceTaxes(componentId);
				return invoiceTaxes;
		}
		return null;
		}
	
	@Override
	public SearchResultData getClientMasterData() {
		// TODO Auto-generated method stub
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		SearchResultData  clientMasterData = masterDataManager.getClientMasterData(clientId);
		return clientMasterData;
	}

	

	@Override
	public SearchResultData getQuestionMasterData() {
		// TODO Auto-generated method stub
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		SearchResultData  questionMasterData = masterDataManager.getQuestionMasterData(clientId);
		return questionMasterData ;
	}


	

	@Override
	public SearchResultData getStateMasterData() {
		// TODO Auto-generated method stub
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		SearchResultData  stateMasterData = masterDataManager.getStateMasterData(clientId);
		return stateMasterData;
	}

	

	@Override
	public SearchResultData getPrivilegeData() {
		// TODO Auto-generated method stub
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		SearchResultData  privilegeData = masterDataManager.getPrivilegeData(clientId);
		return privilegeData;
	}

	

	@Override
	public SearchResultData getDepartmentMasterData() {
		// TODO Auto-generated method stub
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		SearchResultData  departmentMasterData = masterDataManager.getDepartmentMasterData(clientId);
		return departmentMasterData;
	}

	

	@Override
	public SearchResultData getDesignationMasterData() {
		// TODO Auto-generated method stub
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		SearchResultData  designationMasterData = masterDataManager.getDesignationMasterData(clientId);
		return designationMasterData;
		
	}

	

	@Override
	public SearchResultData getEquipmentMasterData() {
		// TODO Auto-generated method stub
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		SearchResultData  equipmentMasterData = masterDataManager.getEquipmentMasterData(clientId);
		return equipmentMasterData;
	}

	

	@Override
	public SearchResultData getConfigData() {
		// TODO Auto-generated method stub
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		SearchResultData  configData = masterDataManager.getConfigData(clientId);
		return configData;
	}

	

	@Override
	public SearchResultData getIssueMasterData() {
		// TODO Auto-generated method stub
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		SearchResultData  issueMasterData = masterDataManager.getIssueMasterData(clientId);
		return issueMasterData;
	}



	@Override
	public SearchResultData getResolutionMasterData() {
		// TODO Auto-generated method stub
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		SearchResultData  resolutionMasterData = masterDataManager.getResolutionMasterData(clientId);
		return resolutionMasterData;
	}

	

	@Override
	public SearchResultData getUnitCategoryMasterData() {
		// TODO Auto-generated method stub
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		SearchResultData  unitCategoryMasterData = masterDataManager.getUnitCategoryMasterData(clientId);
		return unitCategoryMasterData;
	}



	@Override
	public SearchResultData getServiceProviderMasterData() {
		// TODO Auto-generated method stub
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		SearchResultData  serviceProviderMasterData = masterDataManager.getServiceProviderMasterData(clientId);
		return serviceProviderMasterData;
	}

	

	@Override
	public SearchResultData getCustomerTypeMasterData() {
		// TODO Auto-generated method stub
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		SearchResultData  customerTypeMasterData = masterDataManager.getCustomerTypeMasterData(clientId);
		return customerTypeMasterData;
	}

	

	@Override
	public SearchResultData getInvoiceTaxesMasterData() {
		// TODO Auto-generated method stub
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		SearchResultData  invoiceTaxesMasterData = masterDataManager.getInvoiceTaxesMasterData(clientId);
		return invoiceTaxesMasterData;
	}

}
