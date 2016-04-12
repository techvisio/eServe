package com.techvisio.eserve.factory;

import com.techvisio.eserve.beans.WorkItem;
import com.techvisio.eserve.util.AppConstants;

public class WorkItemFactory {


	public WorkItem getWorkItem(String workType){

		WorkItem workItem = null;

		switch (workType) {
		case AppConstants.CUSTOMER_DRAFT:

			workItem = new WorkItem();
			workItem.setWorkType(AppConstants.DraftWorkItemTypeCustomer.CUSTOMER_DRAFT.getWorkType());
			workItem.setEntityUrl(AppConstants.DraftWorkItemTypeCustomer.CUSTOMER_DRAFT.getUrl());
			workItem.setStatus(AppConstants.WORK_ITEM_OPEN_STATUS); 
			workItem.setEntityType("CUSTOMER");
			//TODO:Change with privilege from Enum
			workItem.setPrivilegeId(5L);
			break;

		case AppConstants.PUBLISH:

			workItem = new WorkItem();
			workItem.setWorkType(AppConstants.ApprovalWorkItemType.AGREEMENT_APPROVAL.getWorkType());
			workItem.setEntityUrl(AppConstants.ApprovalWorkItemType.AGREEMENT_APPROVAL.getUrl());
			workItem.setStatus(AppConstants.WORK_ITEM_OPEN_STATUS);
			workItem.setEntityType("UNIT");
			//TODO:Change with privilege from Enum
			workItem.setPrivilegeId(9L);
			break;

		case AppConstants.RENEW_SERVICE_CALL:

			workItem = new WorkItem();
			workItem.setWorkType(AppConstants.RenewAgreementWorkItemType.RENEW_SERVICE_AGREEMENT.getWorkType());
			workItem.setEntityUrl(AppConstants.RenewAgreementWorkItemType.RENEW_SERVICE_AGREEMENT.getUrl());
			workItem.setStatus(AppConstants.WORK_ITEM_OPEN_STATUS); 
			workItem.setEntityType("UNIT");
			//TODO:Change with privilege from Enum
			workItem.setPrivilegeId(10L);
			break;

		default:	

		}
		return workItem;
	}


}
