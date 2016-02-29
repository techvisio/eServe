package com.techvisio.eserve.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.WorkItem;

@Component
public interface WorkItemService {

	public void saveWorkItem(WorkItem workItem);
	public WorkItem getWorkItemByUserId(Long userId);
	public WorkItem getWorkItemByPrivilege(Long privilegeId);
	public WorkItem getWorkItemByWorkType(String workType);
	public List<WorkItem> getWorkItembyUserandType(Long userId,String type);
	
}
