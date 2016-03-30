package com.techvisio.eserve.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.WorkItem;
import com.techvisio.eserve.factory.WorkItemFactory;
import com.techvisio.eserve.manager.WorkItemManager;
import com.techvisio.eserve.service.CustomerService;
import com.techvisio.eserve.service.WorkItemService;
import com.techvisio.eserve.util.AppConstants;

@Component
public class WorkItemServiceImpl implements WorkItemService{

	@Autowired
	WorkItemManager workItemManager;

	@Autowired
	CustomerService customerService;

	@Override
	public void saveWorkItem(WorkItem workItem) {
		workItemManager.saveWorkItem(workItem);
	}

	@Override
	public List<WorkItem> getWorkItemByUserId(Long userId) {
		List<WorkItem> workItems = workItemManager.getWorkItemByUserId(userId);
		return workItems;
	}

	@Override
	public List<WorkItem> getWorkItemByPrivilege(Long privilegeId) {
		List<WorkItem> workItems = workItemManager.getWorkItemByPrivilege(privilegeId);
		return workItems;
	}

	@Override
	public List<WorkItem> getWorkItemByWorkType(String workType) {
		List<WorkItem> workItems = workItemManager.getWorkItemByWorkType(workType);
		return workItems;
	}

	@Override
	public List<WorkItem> getWorkItembyUserandType(Long userId, String type, String status) {
		return workItemManager.getWorkItembyUserandType(userId, type, status);
	}

	@Override
	public void updateWorkItemStatus(Long entityId,String status) {
		workItemManager.updateWorkItemStatus(entityId,status);

	}

	@Override
	public List<WorkItem> getWorkItemsByEntityId(Long entityId) {
		List<WorkItem> workItems = workItemManager.getWorkItemsByEntityId(entityId);
		return workItems;
	}

	@Override
	public void createWorkItemForCustomer(Customer customer, String context,
			Long customerId) {



		if(context.equalsIgnoreCase(AppConstants.CUSTOMER_DRAFT)){
			WorkItem workItemFromDB = workItemManager.getWorkItemsByEntityIdAndEntityType(customerId, "CUSTOMER");

			if(workItemFromDB !=null){

				workItemFromDB.setStatus(AppConstants.WORK_ITEM_OPEN_STATUS);
				workItemManager.saveWorkItem(workItemFromDB);
			}

			else{
				WorkItemFactory factory = new WorkItemFactory();

				WorkItem workItem = factory.getWorkItem(context);
				workItem.setEntityId(customerId);
				workItemManager.saveWorkItem(workItem);
			}
		}

		if(context.equalsIgnoreCase(AppConstants.PUBLISH)){
			for(Unit unit:customer.getUnits()){
				WorkItem workItemFromDB = workItemManager.getWorkItemsByEntityIdAndEntityType(unit.getUnitId(), "UNIT");
				if(workItemFromDB !=null){

					workItemFromDB.setStatus(AppConstants.WORK_ITEM_OPEN_STATUS);
					workItemManager.saveWorkItem(workItemFromDB);
				}

				else{
					WorkItemFactory factory = new WorkItemFactory();
					WorkItem workItem = factory.getWorkItem(context);
					workItem.setEntityId(unit.getUnitId());
					workItemManager.saveWorkItem(workItem);
				}
			}
		}
	}

	@Override
	public void createWorkItemForUnit(String context, Long unitId) {
		Unit unitFromDB = customerService.getUnit(unitId);

		if(context.equalsIgnoreCase(AppConstants.CUSTOMER_DRAFT)){

			WorkItem workItemFromDB = workItemManager.getWorkItemsByEntityIdAndEntityType(unitId, "UNIT");

			if(workItemFromDB!=null){
				workItemFromDB.setStatus(AppConstants.WORK_ITEM_OPEN_STATUS);
				workItemManager.saveWorkItem(workItemFromDB);
			}
			else{
				WorkItemFactory factory = new WorkItemFactory();
				WorkItem workItem = factory.getWorkItem(context);
				workItem.setEntityId(unitFromDB.getCustomerId());
				workItemManager.saveWorkItem(workItem);
			}
		}

		if(context.equalsIgnoreCase(AppConstants.PUBLISH)){
			WorkItem workItemFromDB = workItemManager.getWorkItemsByEntityIdAndEntityType(unitId, "UNIT");

			if(workItemFromDB!=null){
				workItemFromDB.setStatus(AppConstants.WORK_ITEM_OPEN_STATUS);
				workItemManager.saveWorkItem(workItemFromDB);
			}
			else{
				WorkItemFactory factory = new WorkItemFactory();
				WorkItem workItem = factory.getWorkItem(context);
				workItem.setEntityId(unitId);
				workItemManager.saveWorkItem(workItem);
			}
		}
	}


	@Override
	public void createWorkItemForUnitDraft(String context,
			Long customerId) {

		if(context.equalsIgnoreCase(AppConstants.CUSTOMER_DRAFT)){

			WorkItem workItemFromDB = workItemManager.getWorkItemsByEntityIdAndEntityType(customerId, "CUSTOMER");

			if(workItemFromDB!=null){
				workItemFromDB.setStatus(AppConstants.WORK_ITEM_OPEN_STATUS);
				workItemManager.saveWorkItem(workItemFromDB);
			}
			else{
				WorkItemFactory factory = new WorkItemFactory();

				WorkItem workItem = factory.getWorkItem(context);
				workItem.setEntityId(customerId);
				workItemManager.saveWorkItem(workItem);
			}
		}
	}





}
