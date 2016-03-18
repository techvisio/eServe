package com.techvisio.eserve.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.WorkItem;

@Component
public interface WorkItemService {

	public void saveWorkItem(WorkItem workItem);
	public List<WorkItem> getWorkItemByUserId(Long userId);
	public List<WorkItem> getWorkItemByPrivilege(Long privilegeId);
	public List<WorkItem>  getWorkItemByWorkType(String workType);
	public List<WorkItem> getWorkItembyUserandType(Long userId,String type, String status);
	public void updateWorkItemStatus(Long entityId,String status);
	public List<WorkItem> getWorkItemsByEntityId(Long entityId);
	public void createWorkItemForCustomer(Customer customer, String context,
			Long customerId);
	public void createWorkItemForUnit(String context, Long unitId);
	public void createWorkItemForUnitDraft(String context, Long customerId);
}
