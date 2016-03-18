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
	public List<WorkItem> getWorkItemByUserId(Long userId) {
		List<WorkItem> workItems = workItemDao.getWorkItemByUserId(userId);
		return workItems;
	}

	@Override
	public List<WorkItem> getWorkItemByPrivilege(Long privilegeId) {
		List<WorkItem> workItems = workItemDao.getWorkItemByPrivilege(privilegeId);
		return workItems;
	}

	@Override
	public List<WorkItem> getWorkItemByWorkType(String workType) {
		List<WorkItem> workItems = workItemDao.getWorkItemByWorkType(workType);
		return workItems;
	}

	@Override
	public List<WorkItem> getWorkItembyUserandType(Long userId, String type, String status) {

		if(type.equalsIgnoreCase("")){
			type = null;
		}
		if(status.equalsIgnoreCase("")){
			status = null;
		}

		return workItemDao.getWorkItembyUserandType(userId, type,status);
	}

	@Override
	public void updateWorkItemStatus(Long entityId, String status) {
	workItemDao.updateWorkItemStatus(entityId, status);
		
	}

	@Override
	public List<WorkItem> getWorkItemsByEntityId(Long entityId) {
		List<WorkItem> workItems = workItemDao.getWorkItemsByEntityId(entityId);
		return workItems;
	}

}