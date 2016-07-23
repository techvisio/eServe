package com.techvisio.eserve.manager;

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
import com.techvisio.eserve.beans.QuestionMaster;
import com.techvisio.eserve.beans.Resolution;
import com.techvisio.eserve.beans.SearchResultData;
import com.techvisio.eserve.beans.ServiceProvider;
import com.techvisio.eserve.beans.State;
import com.techvisio.eserve.beans.UnitCategory;

@Component
public interface MasterDataManager {
	
	public SearchResultData getClientMasterData(Long clientId);

	Long saveClient(Client client);
	
	Client getClient(Long clientId); 

	SearchResultData getQuestionMasterData(Long clientId);

    void saveQuestion(QuestionMaster question);
    
    QuestionMaster getQuestion(String question);

	SearchResultData getStateMasterData(Long clientId);

	Long saveState(State state);
	
	State getState(Long stateId);

	SearchResultData getPrivilegeData(Long clientId);

	Long savePrivilege(Privilege privilege);
	
	Privilege getPrivilege(Long privilegeId);

	SearchResultData getDepartmentMasterData(Long clientId);

	Long saveDepartment(Department department);
	
	Department getDepartment(Long departmentId);

	SearchResultData getDesignationMasterData(Long clientId);

	Long saveDesignation(Designation designation);
	
	Designation getDesignation(Long designationId); 

	SearchResultData getEquipmentMasterData(Long clientId);

	Long saveEquipment(Equipment equipment);
	
	Equipment getEquipment(Long equipmentId);

	SearchResultData getConfigData(Long clientId);

	Long saveConfig(Config config);
	
	Config getConfig(Long configTBId);

	SearchResultData getIssueMasterData(Long clientId);

	Long saveIssue(Issue issue);
	
	Issue getIssue(Long issueId);

	SearchResultData getResolutionMasterData(Long clientId);

	Long saveResolution(Resolution resolution);
	
	Resolution getResolution(Long resolutionId);

	SearchResultData getUnitCategoryMasterData(Long clientId);

	Long saveUnitCategory(UnitCategory unitCategory);

	UnitCategory getUnitCategory(Long unitCategoryId);
	
	SearchResultData getServiceProviderMasterData(Long clientId);

	Long saveServiceProvider(ServiceProvider serviceProvider);

	ServiceProvider getServiceProvider(Long serviceProviderId);
	
	SearchResultData getCustomerTypeMasterData(Long clientId);

	Long saveCustomerType(CustomerType customerType);

	CustomerType getCustomerType(Long customerTypeId);
	
	SearchResultData getInvoiceTaxesMasterData(Long clientId);

	Long saveInvoiceTaxes(InvoiceTaxes invoiceTaxes);

	InvoiceTaxes getInvoiceTaxes(Long componentId);
}
