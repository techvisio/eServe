package com.techvisio.eserve.service;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.SearchResultData;

@Component
public interface MasterDataService {
	
	public Object  getEntity(Object object,String type);

	public SearchResultData getClientMasterData();

	SearchResultData getQuestionMasterData();

	SearchResultData getStateMasterData();

	SearchResultData getPrivilegeData();

	SearchResultData getDepartmentMasterData();

	SearchResultData getDesignationMasterData();

	SearchResultData getConfigData();
	
	SearchResultData getIssueMasterData();
	
	SearchResultData getResolutionMasterData();

	SearchResultData getUnitCategoryMasterData();
	
	SearchResultData getServiceProviderMasterData();
	
	SearchResultData getCustomerTypeMasterData();
	
	SearchResultData getInvoiceTaxesMasterData();

}
