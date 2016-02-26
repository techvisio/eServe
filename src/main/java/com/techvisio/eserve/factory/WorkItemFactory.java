package com.techvisio.eserve.factory;

import com.techvisio.eserve.beans.WorkItem;
import com.techvisio.eserve.util.AppConstants;

public class WorkItemFactory {

	
	public WorkItem getWorkItem(String workType){

		WorkItem workItem = null;
		
		switch (workType) {
		case AppConstants.DRAFT:
			
		workItem = new WorkItem();
		workItem.setWorkType(AppConstants.PENDINGWORK);
		break;
		
		case AppConstants.PUBLISH:
			
			workItem = new WorkItem();
			workItem.setWorkType(AppConstants.APPROVALWORK);
			break;
		default:	
		
	}
		return workItem;
		
	}
	
	
}
