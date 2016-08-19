package com.techvisio.eserve.manager;

import java.util.List;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Comment;
import com.techvisio.eserve.beans.GenericRequest;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.WorkItem;

@Component
public interface WorkItemManager {

	public void saveWorkItem(WorkItem workItem);
	public List<WorkItem> getWorkItemByUserId(Long userId);
	public List<WorkItem> getWorkItemByPrivilege(Long privilegeId);
	public List<WorkItem> getWorkItemByWorkType(String workType);
	public List<WorkItem> getWorkItembyUserandType(Long userId, String type,
			String status);
	public void updateWorkItemStatus(Long entityId, String status,String workType, String entityType);
	public List<WorkItem> getWorkItemsByEntityIdAndEntityType(Long entityId, String entityType);
	public List<WorkItem> getWorkItemsByEntityIdAndEntityTypeAndWorkType(Long entityId,
			String entityType, String workType);
	public void deleteWorkItemsByEntityIdAndWorkType(Long entityId, String workType);
	public WorkItem getWorkitemByWorkitemId(Long workitemId);
	public List<Comment> saveComment(GenericRequest<WorkItem> request);
	public List<Comment> getCommentList(Long workItemId, Long clientId);
	public Comment getLatestCommentBycommentType(Long entityId, String entityType,
			String commentType);
}
