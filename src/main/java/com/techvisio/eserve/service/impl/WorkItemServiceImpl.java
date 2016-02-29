package com.techvisio.eserve.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.WorkItem;
import com.techvisio.eserve.manager.WorkItemManager;
import com.techvisio.eserve.service.WorkItemService;

@Component
public class WorkItemServiceImpl implements WorkItemService{

	@Autowired
	WorkItemManager workItemManager;
	
	@Override
	public void saveWorkItem(WorkItem workItem) {
		workItemManager.saveWorkItem(workItem);
	}

	@Override
	public WorkItem getWorkItemByUserId(Long userId) {
		WorkItem workItem = workItemManager.getWorkItemByUserId(userId);
		return workItem;
	}

	@Override
	public WorkItem getWorkItemByPrivilege(Long privilegeId) {
		WorkItem workItem = workItemManager.getWorkItemByPrivilege(privilegeId);
		return workItem;
	}

	@Override
	public WorkItem getWorkItemByWorkType(String workType) {
		WorkItem workItem = workItemManager.getWorkItemByWorkType(workType);
		return workItem;
	}

	@Override
	public List<WorkItem> getWorkItembyUserandType(Long userId, String type) {
		return workItemManager.getWorkItembyUserandType(userId, type);
	}


}
