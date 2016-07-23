package com.techvisio.eserve.factory;

import com.techvisio.eserve.beans.WorkItem;
import com.techvisio.eserve.util.AppConstants;

public class WorkItemFactory {


	public WorkItem getWorkItem(String workType){

		WorkItem workItem = null;

		switch (workType) {
		case AppConstants.CUSTOMER_DRAFT:

			workItem = new WorkItem();
			workItem.setWorkType(AppConstants.WorkItemType.CUSTOMER_DRAFT.getWorkType());
			workItem.setEntityUrl(AppConstants.WorkItemType.CUSTOMER_DRAFT.getUrl());
			workItem.setStatus(AppConstants.WORK_ITEM_OPEN_STATUS); 
			workItem.setEntityType(AppConstants.WorkItemType.CUSTOMER_DRAFT.getEntityType());
			//TODO:Change with privilege from Enum
			workItem.setPrivilegeId(5L);
			break;

		case AppConstants.PUBLISH:

			workItem = new WorkItem();
			workItem.setWorkType(AppConstants.WorkItemType.AGREEMENT_APPROVAL.getWorkType());
			workItem.setEntityUrl(AppConstants.WorkItemType.AGREEMENT_APPROVAL.getUrl());
			workItem.setStatus(AppConstants.WORK_ITEM_OPEN_STATUS);
			workItem.setEntityType(AppConstants.WorkItemType.AGREEMENT_APPROVAL.getEntityType());
			//TODO:Change with privilege from Enum
			workItem.setPrivilegeId(9L);
			break;

		case AppConstants.RENEW_SERVICE_CALL:

			workItem = new WorkItem();
			workItem.setWorkType(AppConstants.WorkItemType.RENEW_SERVICE_AGREEMENT.getWorkType());
			workItem.setEntityUrl(AppConstants.WorkItemType.RENEW_SERVICE_AGREEMENT.getUrl());
			workItem.setStatus(AppConstants.WORK_ITEM_OPEN_STATUS); 
			workItem.setEntityType(AppConstants.WorkItemType.RENEW_SERVICE_AGREEMENT.getEntityType());
			//TODO:Change with privilege from Enum
			workItem.setPrivilegeId(10L);
			break;
			
		case AppConstants.PMS:

			workItem = new WorkItem();
			workItem.setWorkType(AppConstants.WorkItemType.PMS.getWorkType());
			workItem.setEntityUrl(AppConstants.WorkItemType.PMS.getUrl());
			workItem.setStatus(AppConstants.WORK_ITEM_OPEN_STATUS); 
			workItem.setEntityType(AppConstants.WorkItemType.PMS.getEntityType());
			//TODO:Change with privilege from Enum
			workItem.setPrivilegeId(7L);
			break;

		default:	

		}
		return workItem;
	}


}
