package com.techvisio.eserve.manager;

import java.text.ParseException;
import java.util.List;

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
public interface WorkItemManager {

	public void saveWorkItem(WorkItem workItem);
	public List<WorkItem> getWorkItemByUserId(Long userId);
	public List<WorkItem> getWorkItemByPrivilege(Long privilegeId);
	public List<WorkItem> getWorkItemOfServiceRenewal(WorkItemSearchCriteria criteria);
	public List<WorkItem> getWorkItembyUserandType(Long userId, String type,
			String status);
	public void updateWorkItemStatus(Long entityId, String status,String workType, String entityType);
	public List<WorkItem> getWorkItemsByEntityIdAndEntityType(Long entityId, String entityType);
	public List<WorkItem> getWorkItemsByEntityIdAndEntityTypeAndWorkType(Long entityId,
			String entityType, String workType);
	public void deleteWorkItemsByEntityIdAndWorkType(Long entityId, String workType);
	public WorkItem getWorkitemByWorkitemId(Long workitemId);
	public List<Comment> saveComment(GenericRequest<WorkItem> request);
	public List<Comment> getCommentList(Long workItemId);
	public Comment getLatestCommentBycommentType(Long entityId, String entityType,
			String commentType);
	public void closeDraftWorkItem(Customer customer);
	public SearchResultData getWorkItembySearchCriteria(
			WorkItemSearchCriteria workItemSearchCriteria) throws ParseException;
	public void createWorkItemForServiceRenewal(Unit unit);
	public void closeAgreementApprovalWorkItem(Unit unit);
	public List<WorkItem> getActiveWorkItems(WorkItemSearchCriteria criteria,
			ServiceAgreement agreement);
	public WorkItemSearchCriteria getWorkitemCriteria(Long entityId,
			String entityType, String workType);
	public void createWorkItemForSalesRenewal(UnitBasicInfo unitInfo, Unit unit);
	public SearchResultData getWorkItemForQueuebySearchCriteria(
			WorkItemSearchCriteria workItemSearchCriteria)
			throws ParseException;
}
