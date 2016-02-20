package com.techvisio.eserve.db;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.WorkItem;
@Component
public interface WorkItemDao {

	public void saveWorkItem(WorkItem workItem);
	public WorkItem getWorkItemByUserId(Long userId);
	public WorkItem getWorkItemByPrivilege(Long privilegeId);
	public WorkItem getWorkItemByWorkType(String workType);
	
	
	
}
