package com.techvisio.eserve.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Comment;
import com.techvisio.eserve.beans.Config;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.beans.WorkItem;
import com.techvisio.eserve.factory.WorkItemFactory;
import com.techvisio.eserve.manager.CacheManager;
import com.techvisio.eserve.manager.WorkItemManager;
import com.techvisio.eserve.service.CustomerService;
import com.techvisio.eserve.service.UserService;
import com.techvisio.eserve.service.WorkItemService;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;

@Component
public class WorkItemServiceImpl implements WorkItemService {

	@Autowired
	WorkItemManager workItemManager;

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
			createWorkItemForCustomerDraft(context, customer.getCustomerId());
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

			createWorkItemForCustomerDraft(context, unitFromDB.getCustomerId());
		}

		if (context.equalsIgnoreCase(AppConstants.PUBLISH)) {
			createWorkItemForPublishingUnit(context, comment,
					unitFromDB);
		}
	}

	private void createWorkItemForCustomerDraft(String context,
			Long customerId) {
		WorkItem workItem = workItemManager
				.getWorkItemsByEntityIdAndEntityTypeAndWorkType(
						customerId,
						"CUSTOMER",
						AppConstants.DraftWorkItemTypeCustomer.CUSTOMER_DRAFT
						.getWorkType());

		if (workItem == null) {
			WorkItemFactory factory = new WorkItemFactory();
			workItem = factory.getWorkItem(context);

		}

		workItem.setStatus(AppConstants.WORK_ITEM_OPEN_STATUS);
		workItem.setEntityId(customerId);
		workItemManager.saveWorkItem(workItem);
	}

	private void createWorkItemForPublishingUnit(String context,
			String comment, Unit unitFromDB) {

		WorkItem workItem = workItemManager
				.getWorkItemsByEntityIdAndEntityTypeAndWorkType(
						unitFromDB.getUnitId(),
						"UNIT",
						AppConstants.ApprovalWorkItemType.AGREEMENT_APPROVAL
						.getWorkType());

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
		workItemManager.saveWorkItem(workItem);
	}

	@Override
	public void createWorkItemForServiceRenewal(Unit unit) {
		Map<Long, Map<String, Object>> configMap = cacheManager
				.getConfigMap(unit.getClient().getClientId());

		Map<String, Object> defaultMap = configMap.get(unit.getClient()
				.getClientId());

		Config config = (Config) defaultMap.get(AppConstants.SERVICE_REMINDER);
		int countDays = Integer.parseInt(config.getValue());

		Date dueDate = CommonUtil.getDate(unit.getServiceAgreement()
				.getContractExpireOn(), countDays, false, false);

		WorkItem workItem = workItemManager
				.getWorkItemsByEntityIdAndEntityTypeAndWorkType(
						unit.getUnitId(), "UNIT", "RENEW SERVICE AGREEMENT");

		if (workItem == null) {
			WorkItemFactory factory = new WorkItemFactory();
			workItem = factory.getWorkItem(AppConstants.RENEW_SERVICE_CALL);

		}
		workItem.setStatus(AppConstants.WORK_ITEM_OPEN_STATUS);
		workItem.setDueDate(dueDate);
		workItem.setEntityId(unit.getUnitId());
		workItem.setDueDate(dueDate);
		workItemManager.saveWorkItem(workItem);

	}

	@Override
	public void workItemWorkForRejectApprovalChanges(Unit unit, String comment) {
		WorkItem workItemFromDB = workItemManager
				.getWorkItemsByEntityIdAndEntityTypeAndWorkType(unit
						.getUnitId(), "UNIT",
						AppConstants.ApprovalWorkItemType.AGREEMENT_APPROVAL
						.getWorkType());
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

	@Override
	public void closeAgreementApprovalWorkItem(Long unitId){
		WorkItem workItemFromDB = workItemManager
				.getWorkItemsByEntityIdAndEntityTypeAndWorkType(unitId, "UNIT",
						AppConstants.ApprovalWorkItemType.AGREEMENT_APPROVAL
						.getWorkType());
		workItemFromDB.setStatus(AppConstants.WORK_ITEM_CLOSE_STATUS);
		workItemManager.saveWorkItem(workItemFromDB);
	}

}
