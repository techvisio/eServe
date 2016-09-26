package com.techvisio.eserve.service;

import java.text.ParseException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Comment;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.GenericRequest;
import com.techvisio.eserve.beans.SearchResultData;
import com.techvisio.eserve.beans.ServiceAgreement;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.UnitBasicInfo;
import com.techvisio.eserve.beans.WorkItem;
import com.techvisio.eserve.beans.WorkItemSearchCriteria;

@Component
@Transactional
public interface WorkItemService {

	public List<WorkItem> getWorkItemByUserId(Long userId);
	public List<WorkItem> getWorkItemByPrivilege(Long privilegeId);
	public List<WorkItem> getWorkItemOfServiceRenewal(WorkItemSearchCriteria criteria);
	public List<WorkItem> getWorkItembyUserandType(Long userId,String type, String status);
	public List<WorkItem> getUnitWorkItemsByEntityIdAndEntityType(Long entityId);
	public void createWorkItemForCustomerSave(String context,
			Customer customer, String comment);
	public void createWorkItemForUnitSave(String context, Long unitId, String comment);
	public void createWorkItemForServiceRenewal(Unit unit);
	public void workItemWorkForRejectApprovalChanges(Unit unit, String comment);
	public void closeAgreementApprovalWorkItem(Long unitId);
	public List<WorkItem> getWorkItemsByEntityIdAndEntityTypeAndWorkType(Long entityId,
			String entityType, String workType);
	public void saveWorkItem(WorkItem workItem);
	public void deleteWorkItemsByEntityIdAndWorkType(Long entityId, String workType);
	public UnitBasicInfo getUnitBasicInfo(Long unitId, String entityType);
	public WorkItem getWorkitemByWorkitemId(Long workitemId);
	public List<Comment> saveComment(GenericRequest<WorkItem> request);
	public List<Comment> getCommentList(Long workItemId);
	public Comment getLatestCommentBycommentType(Long entityId, String entityType,
			String commentType);
	public void createWorkItemForSalesRenewal(UnitBasicInfo unitInfo);
	public void updateWorkItemStatus(Long entityId, String status, String workType,
			String entityType);
	public SearchResultData getWorkItembySearchCriteria(
			WorkItemSearchCriteria workItemSearchCriteria) throws ParseException;
	public List<WorkItem> getActiveWorkItems(WorkItemSearchCriteria criteria,
			ServiceAgreement agreement);
	public WorkItemSearchCriteria getWorkitemCriteria(Long entityId,
			String entityType, String workType);
	SearchResultData getWorkItemForQueuebySearchCriteria(
			WorkItemSearchCriteria workItemSearchCriteria)
			throws ParseException;
}
