package com.techvisio.eserve.db.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Comment;
import com.techvisio.eserve.beans.WorkItem;
import com.techvisio.eserve.db.WorkItemDao;
@Component
public class WorkItemDaoImpl extends BaseDao implements WorkItemDao{

	@Override
	public List<WorkItem> getWorkItemByUserId(Long userId) {
		String queryString="FROM WorkItem wi WHERE wi.assigneeId = "+userId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<WorkItem> workItems= (List<WorkItem>)query.getResultList();

		return workItems;
	}

	@Override
	public List<WorkItem> getWorkItemByPrivilege(Long privilegeId) {
		String queryString="FROM WorkItem wi WHERE wi.privilegeId = "+privilegeId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<WorkItem> workItems= (List<WorkItem>)query.getResultList();

		return workItems;
	}

	@Override
	public List<WorkItem> getWorkItemByWorkType(String workType) {
		String queryString="FROM WorkItem wi WHERE wi.workType = "+workType;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<WorkItem> workItems= (List<WorkItem>)query.getResultList();

		return workItems;
	}

	@Override
	public void saveWorkItem(WorkItem workItem) {

		if(workItem.getWorkItemId()== null){
			getEntityManager().persist(workItem);
		}
		else
		{
			getEntityManager().merge(workItem);
		}
	}

	@Override
	public List<WorkItem> getWorkItembyUserandType(Long userId, String type, String status) {

		String queryString="Select wi.* from  TB_WORK_ITEM wi join TB_PRIVILEGE prv "
				+ "on wi.PRIVILEGE_ID=prv.PRIVILEGE_ID join TB_USER_PRIVILEGE usrpr on usrpr.PRIVILEGE_ID=prv.PRIVILEGE_ID "
				+ "where wi.WORKTYPE = coalesce(:type, WORKTYPE) and wi.STATUS = coalesce(:status, STATUS) and usrpr.USER_ID=:userId" ;
		Query query=getEntityManager().createNativeQuery(queryString,WorkItem.class);
		query.setParameter("type", type);
		query.setParameter("userId", userId);
		query.setParameter("status", status);
		@SuppressWarnings("unchecked")
		List<WorkItem> workItems= (List<WorkItem>)query.getResultList();
		return workItems;
	}

	@Override
	public void updateWorkItemStatus(Long entityId, String status, String workType, String entityType){

		String updateQuery = "UPDATE TB_WORK_ITEM SET STATUS= "+"'"+status+"'"+ " where ENTITY_ID = "+"'"+entityId+"'"+" and ENTITY_TYPE = "+"'"+entityType+"'"+" and WORKTYPE = "+"'"+workType+"'";

		Query query=(Query) getEntityManager().createNativeQuery(updateQuery);
		query.executeUpdate();	
	}

	@Override
	public List<WorkItem> getWorkItemsByEntityIdAndEntityType(Long entityId,String entityType){ 

		String queryString="FROM WorkItem wi WHERE wi.entityType = "+"'"+entityType+"'"+" and wi.entityId = "+entityId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<WorkItem> workItems= (List<WorkItem>)query.getResultList();

		return workItems;
	}

	@Override
	public List<WorkItem> getWorkItemsByEntityIdAndEntityTypeAndWorkType(Long entityId, String entityType, String workType){ 

		String queryString="FROM WorkItem wi WHERE wi.entityId = "+entityId +" and wi.entityType = " + "'" + entityType +"'" +" and wi.workType = " + "'" + workType +"'";
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<WorkItem> workItems= (List<WorkItem>)query.getResultList();

		return workItems;
	}

	@Override
	public void deleteWorkItemsByEntityIdAndWorkType(Long entityId, String workType) {
		String deleteQuery = "Delete from TB_WORK_ITEM where ENTITY_ID =:ENTITY_ID and WORKTYPE = :WORKTYPE";
		Query query=(Query) getEntityManager().createNativeQuery(deleteQuery).setParameter("ENTITY_ID", entityId).setParameter("WORKTYPE", workType);
		query.executeUpdate();	
	}

	@Override
	public WorkItem getWorkitemByWorkitemId(Long workitemId){
		String queryString="FROM WorkItem wi WHERE wi.workItemId = "+workitemId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<WorkItem> workItems= (List<WorkItem>)query.getResultList();
		if(workItems != null && workItems.size()>0){
			return workItems.get(0);
		}
		return null;
	}

	@Override
	public WorkItem getWorkItem(Long workItemId) {
		String queryString="FROM WorkItem wi WHERE wi.workItemId = "+workItemId; 
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<WorkItem> workItem= (List<WorkItem>)query.getResultList();
		if(workItem != null && workItem.size()>0){
			return workItem.get(0);
		}
		return null;

	}


	@Override
	public List<Comment> getCommentList(Long workItemId, Long clientId){
		String queryString="FROM Comment c WHERE c.workItemId = "+workItemId+" "+" and c.client.clientId = "+clientId +" ORDER BY c.createdOn desc"; 
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Comment> comment= (List<Comment>)query.getResultList();
		return comment;
	}

	@Override
	public Comment getLatestCommentBycommentType(Long entityId, String entityType, String commentType){

		Comment comment = null;
		String queryString="select cm.COMMENT_ID, cm.CREATED_BY, cm.CREATED_ON, cm.WORKITEM_ID, cm.Client_Id, cm.COMMENT_TYPE, cm.COMMENT "
				+ "from tb_work_item wi join tb_comment cm on wi.WORKITEM_ID = cm.WORKITEM_ID "
				+ "where wi.ENTITY_ID = " + entityId + " and wi.ENTITY_TYPE = "+ "'"+ entityType +"'" + " and cm.COMMENT_TYPE ="+ "'"+ commentType +"'"+ 
				" order by cm.CREATED_ON desc limit 1" ;
		Query query=getEntityManager().createNativeQuery(queryString, Comment.class);

		List<Comment> comments = (List<Comment>)query.getResultList();

		if(comments!=null && comments.size()>0){
			comment = comments.get(0);
		}
		return comment;
	}

}
