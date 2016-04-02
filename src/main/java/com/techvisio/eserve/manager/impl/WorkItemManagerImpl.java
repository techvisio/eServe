package com.techvisio.eserve.manager.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Config;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.WorkItem;
import com.techvisio.eserve.db.WorkItemDao;
import com.techvisio.eserve.manager.CacheManager;
import com.techvisio.eserve.manager.WorkItemManager;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;

@Component
public class WorkItemManagerImpl implements WorkItemManager{

	@Autowired
	WorkItemDao workItemDao;

	@Autowired
	CacheManager cacheManager;
	
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

	@Override
	public WorkItem getWorkItemsByEntityIdAndEntityTypeAndWorkType(Long entityId,
			String entityType, String workType) {
		WorkItem workItem = workItemDao.getWorkItemsByEntityIdAndEntityTypeAndWorkType(entityId, entityType, workType);

		return workItem;
	}
	@Override
	public void createWorkItemForServiceRenewal(Unit unit) {
		WorkItem workItem = workItemDao.getWorkItemsByEntityIdAndEntityTypeAndWorkType(unit.getUnitId(),"UNIT", "AGREEMENT APPROVAL");

		Map<Long, Map<String, Object>> configMap = cacheManager.getConfigMap(unit.getClient().getClientId());

		Map<String, Object> defaultMap = configMap.get(unit.getClient().getClientId());

		Config config = (Config) defaultMap.get(AppConstants.SERVICE_REMINDER);
		int countDays = Integer.parseInt(config.getValue());

		Date dueDate = CommonUtil.getDate(unit.getServiceAgreement().getContractExpireOn(),  countDays, false, false);
		
		workItem.setDueDate(dueDate);
		workItem.setStatus(AppConstants.WORK_ITEM_OPEN_STATUS);
		workItem.setWorkType(AppConstants.RENEW_SERVICE_CALL);
		workItemDao.saveWorkItem(workItem);

	}

}
