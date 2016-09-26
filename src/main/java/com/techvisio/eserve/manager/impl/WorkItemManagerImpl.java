package com.techvisio.eserve.manager.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Comment;
import com.techvisio.eserve.beans.ClientConfig;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.GenericRequest;
import com.techvisio.eserve.beans.SearchResultData;
import com.techvisio.eserve.beans.ServiceAgreement;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.UnitBasicInfo;
import com.techvisio.eserve.beans.WorkItem;
import com.techvisio.eserve.beans.WorkItemSearchCriteria;
import com.techvisio.eserve.db.WorkItemDao;
import com.techvisio.eserve.factory.WorkItemFactory;
import com.techvisio.eserve.manager.WorkItemManager;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;

@Component
public class WorkItemManagerImpl implements WorkItemManager{

	@Autowired
	WorkItemDao workItemDao;

	@Autowired
	ClientConfiguration configPreferences;

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
	public List<WorkItem> getWorkItemOfServiceRenewal(WorkItemSearchCriteria criteria) {
		List<WorkItem> workItems = workItemDao.getWorkItemOfServiceRenewal(criteria);
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
	public void updateWorkItemStatus(Long entityId, String status, String workType, String entityType) {
		workItemDao.updateWorkItemStatus(entityId, status, workType, entityType);

	}

	@Override
	public List<WorkItem> getWorkItemsByEntityIdAndEntityType(Long entityId, String entityType){
		List<WorkItem> workItems = workItemDao.getWorkItemsByEntityIdAndEntityType(entityId, entityType);
		return workItems;
	}

	@Override
	public List<WorkItem> getWorkItemsByEntityIdAndEntityTypeAndWorkType(Long entityId,
			String entityType, String workType) {
		List<WorkItem> workItem = workItemDao.getWorkItemsByEntityIdAndEntityTypeAndWorkType(entityId, entityType, workType);
		return workItem;
	}

	@Override
	public void deleteWorkItemsByEntityIdAndWorkType(Long entityId,
			String workType) {
		workItemDao.deleteWorkItemsByEntityIdAndWorkType(entityId, workType);

	}

	@Override
	public WorkItem getWorkitemByWorkitemId(Long workitemId) {
		WorkItem workItem = workItemDao.getWorkitemByWorkitemId(workitemId);
		return workItem;
	}


	@Override
	public List<Comment> saveComment(GenericRequest<WorkItem> request){
		WorkItem frontWorkItem = request.getBussinessObject();

		Long workItemId = frontWorkItem.getWorkItemId();
		String stringComment = request.getContextInfo().get("comment");
		WorkItem workItem = workItemDao.getWorkItem(workItemId);
		List<Comment> commentList= workItem.getComments();
		if(commentList != null && commentList.size()>0){
			Comment comment = new Comment();
			comment.setComment(stringComment);
			List<Comment> clonedCommntList = new ArrayList<Comment>(commentList);
			clonedCommntList.add(comment);
			frontWorkItem.setComments(clonedCommntList);
			workItemDao.saveWorkItem(frontWorkItem);
			return getCommentList(workItemId);
		}

		List<Comment> newCommentList = new ArrayList<Comment>();
		Comment comment = new Comment();
		comment.setComment(stringComment);
		newCommentList.add(comment);
		frontWorkItem.setComments(newCommentList);
		workItemDao.saveWorkItem(frontWorkItem);
		return getCommentList(workItemId);
	}

	@Override
	public List<Comment> getCommentList(Long workItemId) {
		List<Comment> comments = workItemDao.getCommentList(workItemId);
		return comments;
	}

	@Override
	public Comment getLatestCommentBycommentType(Long entityId,
			String entityType, String commentType) {
		Comment comment = workItemDao.getLatestCommentBycommentType(entityId, entityType, commentType);
		return comment;
	};

	@Override
	public void closeDraftWorkItem(Customer customer){

		String entityType = AppConstants.WorkItemType.CUSTOMER_DRAFT.getEntityType();
		String workType = AppConstants.WorkItemType.CUSTOMER_DRAFT.getWorkType();
		for(Unit unit : customer.getUnits()){

			if(unit.getApprovalStatus()=='D'){
				updateWorkItemStatus(customer.getCustomerId(),AppConstants.WORK_ITEM_OPEN_STATUS, workType, entityType);
				return;
			}
			else{
				updateWorkItemStatus(customer.getCustomerId(),AppConstants.WORK_ITEM_CLOSE_STATUS,workType, entityType);
			}
		}
	}

	@Override
	public SearchResultData getWorkItembySearchCriteria(
			WorkItemSearchCriteria workItemSearchCriteria) throws ParseException {
		SearchResultData searchResultData = workItemDao.getWorkItembySearchCriteria(workItemSearchCriteria);
		return searchResultData;
	}

	@Override
	public void createWorkItemForServiceRenewal(Unit unit) {
		
		String entityType = AppConstants.EntityType.UNIT.name();
		String workType = AppConstants.WorkItemType.FOLLOWUP_RENEWAL_SERVICE
				.getWorkType();
		ClientConfig config = configPreferences.getConfigObject(AppConstants.SERVICE_REMINDER);
		int countDays = Integer.parseInt(config.getValue());
		Date dueDate = CommonUtil.getDate(unit.getServiceAgreement().getContractExpireOn(), countDays, false, false);
		
		WorkItemSearchCriteria criteria = getWorkitemCriteria(unit.getUnitId(), entityType, workType);
		List<WorkItem> workItems = workItemDao.getActiveWorkItems(criteria, unit.getServiceAgreement());
		WorkItem workItem = null;
		if(workItems != null && workItems.size()>0){
			workItem = workItems.get(0);
		}
		if (workItem == null) {
			WorkItemFactory factory = new WorkItemFactory();
			workItem = factory.getWorkItem(workType);
		}
		workItem.setStatus(AppConstants.WORK_ITEM_OPEN_STATUS);
		workItem.setEntityId(unit.getUnitId());
		workItem.setEntityCode(unit.getUnitCode());
		workItem.setDueDate(dueDate);
		workItemDao.saveWorkItem(workItem);
	}

	@Override
	public void closeAgreementApprovalWorkItem(Unit unit){
		
		String entityType = AppConstants.EntityType.UNIT.name();
		String workType = AppConstants.WorkItemType.AGREEMENT_APPROVAL
				.getWorkType();
		WorkItemSearchCriteria criteriaForApproval = getWorkitemCriteria(unit.getUnitId(), entityType, workType);
		List<WorkItem> workItems= workItemDao.getActiveWorkItems(criteriaForApproval, unit.getServiceAgreement());

		if(workItems != null && workItems.size()>0){
			WorkItem workItemFromDB = workItems.get(0);		
			workItemFromDB.setStatus(AppConstants.WORK_ITEM_CLOSE_STATUS);
			workItemDao.saveWorkItem(workItemFromDB);
		}	
	}

	@Override
	public void createWorkItemForSalesRenewal(UnitBasicInfo unitInfo, Unit unit) {
		String entityType = AppConstants.EntityType.UNIT.name();
		String workType = AppConstants.WorkItemType.SALES_RENEWAL_AGREEMENT.getWorkType();
		WorkItemSearchCriteria criteriaForSalesRenewal = getWorkitemCriteria(unitInfo.getUnitId(), entityType, workType);
		List<WorkItem> workItems= workItemDao.getActiveWorkItems(criteriaForSalesRenewal, unit.getServiceAgreement());
		WorkItem workItem = null;
		
		if(workItems != null && workItems.size()>0){
			workItem = workItems.get(0);
		}
		if (workItem == null) {
			WorkItemFactory factory = new WorkItemFactory();
			workItem = factory.getWorkItem(AppConstants.SALES_RENEWAL_AGREEMENT);
		}
		workItem.setStatus(AppConstants.WORK_ITEM_OPEN_STATUS);
		workItem.setEntityId(unitInfo.getUnitId());
		workItem.setDueDate(new Date());
		workItem.setEntityCode(unitInfo.getUnitCode());
		workItemDao.saveWorkItem(workItem);
		workItemDao.updateWorkItemStatus(unitInfo.getUnitId(),AppConstants.WORK_ITEM_CLOSE_STATUS,AppConstants.FOLLOWUP_RENEWAL_SERVICE ,entityType);
	}

	@Override
	public List<WorkItem> getActiveWorkItems(WorkItemSearchCriteria criteria, ServiceAgreement agreement) {
		List<WorkItem> workItems = workItemDao.getActiveWorkItems(criteria, agreement);
		return workItems;
	}
	
	@Override
	public WorkItemSearchCriteria getWorkitemCriteria(Long entityId,String entityType, String workType) {
		WorkItemSearchCriteria criteria = new WorkItemSearchCriteria();
		criteria.setType(workType);
		criteria.setEntityType(entityType);
		criteria.setEntityId(entityId);
		return criteria;
	}
	@Override
	public SearchResultData getWorkItemForQueuebySearchCriteria(WorkItemSearchCriteria workItemSearchCriteria) throws ParseException{
		SearchResultData searchResultData = workItemDao.getWorkItemForQueuebySearchCriteria(workItemSearchCriteria);
		return searchResultData;
	}
	
}
