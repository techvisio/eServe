package com.techvisio.eserve.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Comment;
import com.techvisio.eserve.beans.Config;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.GenericRequest;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.UnitBasicInfo;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.beans.WorkItem;
import com.techvisio.eserve.factory.WorkItemFactory;
import com.techvisio.eserve.manager.CacheManager;
import com.techvisio.eserve.manager.WorkItemManager;
import com.techvisio.eserve.manager.impl.ClientConfiguration;
import com.techvisio.eserve.service.CustomerService;
import com.techvisio.eserve.service.UserService;
import com.techvisio.eserve.service.WorkItemService;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;

@Component
@Transactional
public class WorkItemServiceImpl implements WorkItemService {

	@Autowired
	WorkItemManager workItemManager;

	@Autowired
	ClientConfiguration configPreferences;

	@Autowired
	CacheManager cacheManager;

	@Autowired
	UserService userService;

	@Autowired
	CustomerService customerService;

	@Override
	public List<WorkItem> getWorkItemByUserId(Long userId) {
		List<WorkItem> workItems = workItemManager.getWorkItemByUserId(userId);
		return workItems;
	}

	@Override
	public List<WorkItem> getWorkItemByPrivilege(Long privilegeId) {
		List<WorkItem> workItems = workItemManager
				.getWorkItemByPrivilege(privilegeId);
		return workItems;
	}

	@Override
	public List<WorkItem> getWorkItemByWorkType(String workType) {
		List<WorkItem> workItems = workItemManager
				.getWorkItemByWorkType(workType);
		return workItems;
	}

	@Override
	public List<WorkItem> getWorkItembyUserandType(Long userId, String type,
			String status) {
		return workItemManager.getWorkItembyUserandType(userId, type, status);
	}

	@Override
	public void updateWorkItemStatus(Long entityId, String status) {
		workItemManager.updateWorkItemStatus(entityId, status);

	}

	@Override
	public List<WorkItem> getWorkItemsByEntityId(Long entityId) {
		List<WorkItem> workItems = workItemManager
				.getWorkItemsByEntityId(entityId);
		return workItems;
	}

	@Override
	public void createWorkItemForCustomerSave(String context,
			Customer customer, String comment) {

		if (context.equalsIgnoreCase(AppConstants.CUSTOMER_DRAFT)) {
			createWorkItemForCustomerDraft(context, customer.getCustomerId(), customer.getCustomerCode());
		}

		if (context.equalsIgnoreCase(AppConstants.PUBLISH)) {
			for (Unit unit : customer.getUnits()) {
				createWorkItemForPublishingUnit(context, comment, unit);
			}
		}
	}

	@Override
	public void createWorkItemForUnitSave(String context, Long unitId,
			String comment) {
		Unit unitFromDB = customerService.getUnit(unitId);

		if (context.equalsIgnoreCase(AppConstants.CUSTOMER_DRAFT)) {

			Customer customer = customerService.getCustomer(unitFromDB.getCustomerId());
			createWorkItemForCustomerDraft(context, unitFromDB.getCustomerId(), customer.getCustomerCode());
		}

		if (context.equalsIgnoreCase(AppConstants.PUBLISH)) {
			createWorkItemForPublishingUnit(context, comment,
					unitFromDB);
		}
	}

	private void createWorkItemForCustomerDraft(String context,Long customerId, String entityCode) {
		List<WorkItem> workItems = workItemManager.getWorkItemsByEntityIdAndEntityTypeAndWorkType(customerId,"CUSTOMER",AppConstants.WorkItemType.CUSTOMER_DRAFT.getWorkType());
		WorkItem workItem = null;
		if(workItems != null && workItems.size()>0){
			workItem = workItems.get(0);
		}
		if (workItem == null) {
			WorkItemFactory factory = new WorkItemFactory();
			workItem = factory.getWorkItem(context);
		}
		workItem.setStatus(AppConstants.WORK_ITEM_OPEN_STATUS);
		workItem.setEntityId(customerId);
		workItem.setEntityCode(entityCode);
		workItemManager.saveWorkItem(workItem);
	}

	private void createWorkItemForPublishingUnit(String context,String comment, Unit unitFromDB) {
		List<WorkItem> workItems = workItemManager.getWorkItemsByEntityIdAndEntityTypeAndWorkType(unitFromDB.getUnitId(),"UNIT",AppConstants.WorkItemType.AGREEMENT_APPROVAL.getWorkType()); 

		WorkItem workItem = null;
		if(workItems != null && workItems.size()>0){
			workItem = workItems.get(0);
		}
		if (workItem == null) {
			WorkItemFactory factory = new WorkItemFactory();
			workItem = factory.getWorkItem(context);
		}
		Comment unitPublishComment = new Comment();
		unitPublishComment.setComment(comment);
		List<Comment> comments = workItem.getComments();
		comments.add(unitPublishComment);
		workItem.setComments(comments);
		workItem.setStatus(AppConstants.WORK_ITEM_OPEN_STATUS);
		workItem.setEntityId(unitFromDB.getUnitId());
		workItem.setEntityCode(unitFromDB.getUnitCode());
		workItemManager.saveWorkItem(workItem);
	}

	@Override
	public void createWorkItemForServiceRenewal(Unit unit) {
		Config config = configPreferences.getConfigObject(AppConstants.SERVICE_REMINDER);
		int countDays = Integer.parseInt(config.getValue());
		Date dueDate = CommonUtil.getDate(unit.getServiceAgreement().getContractExpireOn(), countDays, false, false);
		List<WorkItem> workItems = workItemManager.getWorkItemsByEntityIdAndEntityTypeAndWorkType(unit.getUnitId(), "UNIT", "RENEW SERVICE AGREEMENT");
		WorkItem workItem = null;
		if(workItems != null && workItems.size()>0){
			workItem = workItems.get(0);
		}
		if (workItem == null) {
			WorkItemFactory factory = new WorkItemFactory();
			workItem = factory.getWorkItem(AppConstants.RENEW_SERVICE_CALL);
		}
		workItem.setStatus(AppConstants.WORK_ITEM_OPEN_STATUS);
		workItem.setDueDate(dueDate);
		workItem.setEntityId(unit.getUnitId());
		workItem.setEntityCode(unit.getUnitCode());
		workItem.setDueDate(dueDate);
		workItemManager.saveWorkItem(workItem);
	}

	@Override
	public void workItemWorkForRejectApprovalChanges(Unit unit, String comment) {

		List<WorkItem> workItems= workItemManager
				.getWorkItemsByEntityIdAndEntityTypeAndWorkType(unit
						.getUnitId(), "UNIT",
						AppConstants.WorkItemType.AGREEMENT_APPROVAL
						.getWorkType());
		if(workItems != null && workItems.size()>0){
			WorkItem workItemFromDB = workItems.get(0);
			User user = userService
					.getUserByUserName(workItemFromDB.getUpdatedBy());
			workItemFromDB.setAssigneeId(user.getUserId());
			workItemFromDB.setStatus(AppConstants.WORK_ITEM_OPEN_STATUS);
			Comment rejectionComment = new Comment();
			rejectionComment.setComment(comment);
			List<Comment> comments = workItemFromDB.getComments();
			comments.add(rejectionComment);
			workItemFromDB.setComments(comments);
			workItemManager.saveWorkItem(workItemFromDB);
		}
	}

	@Override
	public void closeAgreementApprovalWorkItem(Long unitId){
		List<WorkItem> workItems= workItemManager
				.getWorkItemsByEntityIdAndEntityTypeAndWorkType(unitId, "UNIT",
						AppConstants.WorkItemType.AGREEMENT_APPROVAL
						.getWorkType());

		if(workItems != null && workItems.size()>0){
			WorkItem workItemFromDB = workItems.get(0);		
			workItemFromDB.setStatus(AppConstants.WORK_ITEM_CLOSE_STATUS);
			workItemManager.saveWorkItem(workItemFromDB);
		}	

	}

	@Override
	public List<WorkItem> getWorkItemsByEntityIdAndEntityTypeAndWorkType(Long entityId,
			String entityType, String workType) {
		List<WorkItem> workItem = workItemManager.getWorkItemsByEntityIdAndEntityTypeAndWorkType(entityId, entityType, workType);

		return workItem;
	}

	@Override
	public void saveWorkItem(WorkItem workItem) {
		workItemManager.saveWorkItem(workItem);
	}

	@Override
	public void deleteWorkItemsByEntityIdAndWorkType(Long entityId,
			String workType) {
		workItemManager.deleteWorkItemsByEntityIdAndWorkType(entityId, workType);
	}

	@Override
	public UnitBasicInfo getUnitBasicInfo(Long entityId, String entityType) {
		if(entityType.equalsIgnoreCase(AppConstants.WorkItemType.PMS.getEntityType())){
			UnitBasicInfo basicInfo = customerService.getUnitBasicInfo(entityId);
			return basicInfo;
		}

		return null;
	}

	@Override
	public WorkItem getWorkitemByWorkitemId(Long workitemId) {
		WorkItem workItem = workItemManager.getWorkitemByWorkitemId(workitemId);
		return workItem;
	}
	
	@Override
	public List<Comment> saveComment(GenericRequest<WorkItem> request){
		List<Comment> commentList = workItemManager.saveComment(request);
		return commentList;
	}
}
