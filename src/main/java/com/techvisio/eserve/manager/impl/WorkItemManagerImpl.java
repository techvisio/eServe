package com.techvisio.eserve.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Comment;
import com.techvisio.eserve.beans.Config;
import com.techvisio.eserve.beans.GenericRequest;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.WorkItem;
import com.techvisio.eserve.db.WorkItemDao;
import com.techvisio.eserve.manager.CacheManager;
import com.techvisio.eserve.manager.WorkItemManager;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;

@Component
public class WorkItemManagerImpl implements WorkItemManager{

	@Autowired
	WorkItemDao workItemDao;

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
	public List<WorkItem> getWorkItemByWorkType(String workType) {
		List<WorkItem> workItems = workItemDao.getWorkItemByWorkType(workType);
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
	public void updateWorkItemStatus(Long entityId, String status) {
		workItemDao.updateWorkItemStatus(entityId, status);

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
			commentList.add(comment);
			frontWorkItem.setComments(commentList);
			workItemDao.saveWorkItem(frontWorkItem);
			return commentList;
		}

		List<Comment> newCommentList = new ArrayList<Comment>();
		Comment comment = new Comment();
		comment.setComment(stringComment);
		newCommentList.add(comment);
		frontWorkItem.setComments(newCommentList);
		workItemDao.saveWorkItem(frontWorkItem);
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		return getCommentList(workItemId, clientId);
	}

	@Override
	public List<Comment> getCommentList(Long workItemId, Long clientId) {
		List<Comment> comments = workItemDao.getCommentList(workItemId, clientId);
		return comments;
	}

	@Override
	public Comment getLatestCommentBycommentType(Long entityId,
			String entityType, String commentType) {
		Comment comment = workItemDao.getLatestCommentBycommentType(entityId, entityType, commentType);
		return comment;
	};
}
