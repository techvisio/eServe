package com.techvisio.eserve.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.WorkItem;
import com.techvisio.eserve.db.WorkItemDao;
import com.techvisio.eserve.manager.WorkItemManager;

@Component
public class WorkItemManagerImpl implements WorkItemManager{

	@Autowired
	WorkItemDao workItemDao;
	
	@Override
	public void saveWorkItem(WorkItem workItem) {
		workItemDao.saveWorkItem(workItem);
	}

	@Override
	public WorkItem getWorkItemByUserId(Long userId) {
		WorkItem workItem = workItemDao.getWorkItemByUserId(userId);
		return workItem;
	}

	@Override
	public WorkItem getWorkItemByPrivilege(Long privilegeId) {
		WorkItem workItem = workItemDao.getWorkItemByPrivilege(privilegeId);
		return workItem;
	}

	@Override
	public WorkItem getWorkItemByWorkType(String workType) {
		WorkItem workItem = workItemDao.getWorkItemByWorkType(workType);
		return workItem;
	}

	@Override
	public List<WorkItem> getWorkItembyUserandType(Long userId, String type) {
		return workItemDao.getWorkItembyUserandType(userId, type);
	}

}
