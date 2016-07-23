package com.techvisio.eserve.service;

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
public interface MasterDataService {
	
	public Object  getEntity(Object object,String type);

	public SearchResultData getClientMasterData();

	SearchResultData getQuestionMasterData();

	SearchResultData getStateMasterData();

	SearchResultData getPrivilegeData();

	SearchResultData getDepartmentMasterData();

	SearchResultData getDesignationMasterData();

	SearchResultData getEquipmentMasterData();

	SearchResultData getConfigData();
	
	SearchResultData getIssueMasterData();
	
	SearchResultData getResolutionMasterData();

	SearchResultData getUnitCategoryMasterData();
	
	SearchResultData getServiceProviderMasterData();
	
	SearchResultData getCustomerTypeMasterData();
	
	SearchResultData getInvoiceTaxesMasterData();

}
