package com.techvisio.eserve.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.techvisio.eserve.beans.Comment;
import com.techvisio.eserve.beans.ClientConfig;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.GenericRequest;
import com.techvisio.eserve.beans.SearchResultData;
import com.techvisio.eserve.beans.ServiceAgreement;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.UnitBasicInfo;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.beans.WorkItem;
import com.techvisio.eserve.beans.WorkItemSearchCriteria;
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
@Transactional(propagation=Propagation.REQUIRED)
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
	public void updateWorkItemStatus(Long entityId, String status,String workType,String entityType) {
		workItemManager.updateWorkItemStatus(entityId, status,workType,entityType);

	}

	@Override
	public List<WorkItem> getUnitWorkItemsByEntityIdAndEntityType(Long entityId) {
		String entityType = AppConstants.EntityType.UNIT.name();
		List<WorkItem> workItems = workItemManager.getWorkItemsByEntityIdAndEntityType(entityId, entityType);
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
		Unit unitFromDB = customerService.getUnitById(unitId);
		Customer customer = customerService.getCustomerbyId(unitFromDB.getCustomerId());
		if (context.equalsIgnoreCase(AppConstants.CUSTOMER_DRAFT)) {


			List<WorkItem> workItems = getWorkItemsByEntityIdAndEntityTypeAndWorkType(unitId, AppConstants.EntityType.UNIT.name(), AppConstants.WorkItemType.AGREEMENT_APPROVAL.getWorkType());
			if(workItems != null && workItems.size()>0){
				workItemManager.updateWorkItemStatus(unitId, AppConstants.WORK_ITEM_CLOSE_STATUS, AppConstants.WorkItemType.AGREEMENT_APPROVAL.getWorkType(), AppConstants.EntityType.UNIT.name());
			}
			createWorkItemForCustomerDraft(context, unitFromDB.getCustomerId(), customer.getCustomerCode());
		}

		if (context.equalsIgnoreCase(AppConstants.PUBLISH)) {
			workItemManager.closeDraftWorkItem(customer);
			createWorkItemForPublishingUnit(context, comment,
					unitFromDB);
		}
	}

	private void createWorkItemForCustomerDraft(String context,Long customerId, String entityCode) {
		List<WorkItem> workItems = workItemManager.getWorkItemsByEntityIdAndEntityTypeAndWorkType(customerId,AppConstants.EntityType.CUSTOMER.name(),AppConstants.WorkItemType.CUSTOMER_DRAFT.getWorkType());
		WorkItem workItem = null;
		if(workItems != null && workItems.size()>0){
			workItem = workItems.get(0);
		}
		if (workItem == null) {
			WorkItemFactory factory = new WorkItemFactory();
			workItem = factory.getWorkItem(context);
		}
		workItem.setStatus(AppConstants.WORK_ITEM_OPEN_STATUS);
		workItem.setDueDate(new Date());
		workItem.setEntityId(customerId);
		workItem.setEntityCode(entityCode);
		workItemManager.saveWorkItem(workItem);
	}

	private void createWorkItemForPublishingUnit(String context,String comment, Unit unitFromDB) {
		List<WorkItem> workItems = workItemManager.getWorkItemsByEntityIdAndEntityTypeAndWorkType(unitFromDB.getUnitId(),AppConstants.EntityType.UNIT.name(),AppConstants.WorkItemType.AGREEMENT_APPROVAL.getWorkType()); 

		WorkItem workItem = null;
		if(workItems != null && workItems.size()>0){
			workItem = workItems.get(0);
		}
		if (workItem == null) {
			WorkItemFactory factory = new WorkItemFactory();
			workItem = factory.getWorkItem(context);
		}

		Comment commentFromDB = workItemManager.getLatestCommentBycommentType(unitFromDB.getUnitId(), AppConstants.EntityType.UNIT.name(), AppConstants.CommentType.REJECT.name());
		if(commentFromDB!=null){
			User user = userService	.getUserByUserName(commentFromDB.getCreatedBy());
			workItem.setAssigneeId(user.getUserId());
		}
		Comment unitPublishComment = new Comment();
		unitPublishComment.setCommentType(AppConstants.CommentType.PUBLISH.name());
		unitPublishComment.setComment(comment);
		List<Comment> comments = workItem.getComments();
		comments.add(unitPublishComment);
		workItem.setComments(comments);
		workItem.setStatus(AppConstants.WORK_ITEM_OPEN_STATUS);
		workItem.setEntityId(unitFromDB.getUnitId());
		workItem.setEntityCode(unitFromDB.getUnitCode());
		workItem.setDueDate(new Date());
		workItemManager.saveWorkItem(workItem);
	}

	@Override
	public void createWorkItemForServiceRenewal(Unit unit) {
		workItemManager.createWorkItemForServiceRenewal(unit);
	}

	@Override
	public void workItemWorkForRejectApprovalChanges(Unit unit, String comment) {

		List<WorkItem> workItems= workItemManager
				.getWorkItemsByEntityIdAndEntityTypeAndWorkType(unit
						.getUnitId(), AppConstants.EntityType.UNIT.name(),
						AppConstants.WorkItemType.AGREEMENT_APPROVAL
						.getWorkType());
		if(workItems != null && workItems.size()>0){
			WorkItem workItemFromDB = workItems.get(0);
			Comment commentFromDB = workItemManager.getLatestCommentBycommentType(unit.getUnitId(), AppConstants.EntityType.UNIT.name(), AppConstants.CommentType.PUBLISH.name());

			User user = userService	.getUserByUserName(commentFromDB.getCreatedBy());
			workItemFromDB.setAssigneeId(user.getUserId());
			workItemFromDB.setStatus(AppConstants.WORK_ITEM_OPEN_STATUS);
			Comment rejectionComment = new Comment();
			rejectionComment.setComment(comment);

			rejectionComment.setCommentType(AppConstants.CommentType.REJECT.name());
			List<Comment> comments = workItemFromDB.getComments();
			comments.add(rejectionComment);
			workItemFromDB.setDueDate(new Date());
			workItemFromDB.setComments(comments);
			workItemManager.saveWorkItem(workItemFromDB);
		}
	}

	@Override
	public void closeAgreementApprovalWorkItem(Long unitId){
		workItemManager.closeAgreementApprovalWorkItem(unitId);
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
			UnitBasicInfo basicInfo = customerService.getUnitBasicInfoById(entityId);
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

	@Override
	public List<Comment> getCommentList(Long workItemId) {
		List<Comment> comments = workItemManager.getCommentList(workItemId);
		return comments;
	}

	@Override
	public Comment getLatestCommentBycommentType(Long entityId,
			String entityType, String commentType) {
		Comment comment = workItemManager.getLatestCommentBycommentType(entityId, entityType, commentType);
		return comment;
	}

	@Override
	public void createWorkItemForSalesRenewal(UnitBasicInfo unitInfo) {
		workItemManager.createWorkItemForSalesRenewal(unitInfo);
	}

	@Override
	public SearchResultData getWorkItembySearchCriteria(
			WorkItemSearchCriteria workItemSearchCriteria) throws ParseException {
		SearchResultData searchResultData=workItemManager.getWorkItembySearchCriteria(workItemSearchCriteria);
		return searchResultData;
	}

	@Override
	public List<WorkItem> getActiveWorkItems(WorkItemSearchCriteria criteria,
			ServiceAgreement agreement) {
		List<WorkItem> workItems = workItemManager.getActiveWorkItems(criteria, agreement);
		return workItems;
	}

}
