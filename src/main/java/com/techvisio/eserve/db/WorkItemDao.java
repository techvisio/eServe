package com.techvisio.eserve.db;

import java.util.List;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Comment;
import com.techvisio.eserve.beans.WorkItem;
@Component
public interface WorkItemDao {

	public void saveWorkItem(WorkItem workItem);
	public List<WorkItem> getWorkItemByUserId(Long userId);
	public List<WorkItem> getWorkItemByPrivilege(Long privilegeId);
	public List<WorkItem> getWorkItemByWorkType(String workType);
	public List<WorkItem> getWorkItembyUserandType(Long userId, String type,
			String status);
	public void updateWorkItemStatus(Long entityId,String status);
	public List<WorkItem> getWorkItemsByEntityId(Long entityId);
	public List<WorkItem> getWorkItemsByEntityIdAndEntityTypeAndWorkType(Long entityId,
			String entityType, String workType);
	public void deleteWorkItemsByEntityIdAndWorkType(Long entityId, String workType);
	public WorkItem getWorkitemByWorkitemId(Long workitemId);
	public WorkItem getWorkItem(Long workItemId);
	public List<Comment> getCommentList(Long workItemId, Long clientId);
}
