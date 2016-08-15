package com.techvisio.eserve.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Comment;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.GenericRequest;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.UnitBasicInfo;
import com.techvisio.eserve.beans.WorkItem;

@Component
@Transactional
public interface WorkItemService {

	public List<WorkItem> getWorkItemByUserId(Long userId);
	public List<WorkItem> getWorkItemByPrivilege(Long privilegeId);
	public List<WorkItem>  getWorkItemByWorkType(String workType);
	public List<WorkItem> getWorkItembyUserandType(Long userId,String type, String status);
	public void updateWorkItemStatus(Long entityId,String status);
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
}
