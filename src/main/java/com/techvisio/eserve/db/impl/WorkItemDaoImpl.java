package com.techvisio.eserve.db.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Comment;
import com.techvisio.eserve.beans.SearchResultData;
import com.techvisio.eserve.beans.ServiceAgreement;
import com.techvisio.eserve.beans.WorkItem;
import com.techvisio.eserve.beans.WorkItemSearchCriteria;
import com.techvisio.eserve.db.WorkItemDao;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;
import com.techvisio.eserve.util.DateUtil;
import com.techvisio.eserve.util.StringUtilities;
@Component
public class WorkItemDaoImpl extends BaseDao implements WorkItemDao{

	@Override
	public List<WorkItem> getWorkItemByUserId(Long userId) {
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		String queryString="FROM WorkItem wi WHERE wi.assigneeId = "+userId+" and wi.client.clientId = "+clientId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<WorkItem> workItems= (List<WorkItem>)query.getResultList();
		List<WorkItem> clonedWorkItems = new ArrayList<WorkItem>(workItems);
		return clonedWorkItems;
	}

	@Override
	public List<WorkItem> getWorkItemByPrivilege(Long privilegeId) {
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		String queryString="FROM WorkItem wi WHERE wi.privilegeId = "+privilegeId+" and wi.client.clientId = "+clientId;;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<WorkItem> workItems= (List<WorkItem>)query.getResultList();
		List<WorkItem> clonedWorkItems = new ArrayList<WorkItem>(workItems);
		return clonedWorkItems;
	}

	@Override
	public List<WorkItem> getWorkItemByWorkType(String workType) {
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		String queryString="FROM WorkItem wi WHERE wi.workType = "+" ' "+workType+" ' "+" and wi.client.clientId = "+clientId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<WorkItem> workItems= (List<WorkItem>)query.getResultList();
		List<WorkItem> clonedWorkItems = new ArrayList<WorkItem>(workItems);
		return clonedWorkItems;
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
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		String queryString="Select wi.* from  TB_WORK_ITEM wi join TB_PRIVILEGE prv "
				+ "on wi.PRIVILEGE_ID=prv.PRIVILEGE_ID join TB_USER_PRIVILEGE usrpr on usrpr.PRIVILEGE_ID=prv.PRIVILEGE_ID "
				+ "where wi.WORKTYPE = coalesce(:type, WORKTYPE) and wi.STATUS = coalesce(:status, STATUS) and usrpr.USER_ID=:userId and wi.client_Id = "+clientId ;
		Query query=getEntityManager().createNativeQuery(queryString,WorkItem.class);
		query.setParameter("type", type);
		query.setParameter("userId", userId);
		query.setParameter("status", status);
		@SuppressWarnings("unchecked")
		List<WorkItem> workItems= (List<WorkItem>)query.getResultList();
		List<WorkItem> clonedWorkItems = new ArrayList<WorkItem>(workItems);
		return clonedWorkItems;
	}

	@Override
	public void updateWorkItemStatus(Long entityId, String status, String workType, String entityType){

		String updateQuery = "UPDATE TB_WORK_ITEM SET STATUS= "+"'"+status+"'"+ " where ENTITY_ID = "+entityId+" and ENTITY_TYPE = "+"'"+entityType+"'"+" and WORKTYPE = "+"'"+workType+"'";

		Query query=(Query) getEntityManager().createNativeQuery(updateQuery);
		query.executeUpdate();	
	}

	@Override
	public List<WorkItem> getWorkItemsByEntityIdAndEntityType(Long entityId,String entityType){ 
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		String queryString="FROM WorkItem wi WHERE wi.entityType = "+"'"+entityType+"'"+" and wi.entityId = "+entityId+" and wi.client.clientId = "+clientId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<WorkItem> workItems= (List<WorkItem>)query.getResultList();
		List<WorkItem> clonedWorkItems = new ArrayList<WorkItem>(workItems);
		return clonedWorkItems;
	}

	@Override
	public List<WorkItem> getWorkItemsByEntityIdAndEntityTypeAndWorkType(Long entityId, String entityType, String workType){ 
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		String queryString="FROM WorkItem wi WHERE wi.entityId = "+entityId +" and wi.entityType = " + "'" + entityType +"'" +" and wi.workType = " + "'" + workType +"'"+" and wi.client.clientId = "+clientId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<WorkItem> workItems= (List<WorkItem>)query.getResultList();
		List<WorkItem> clonedWorkItems = new ArrayList<WorkItem>(workItems);
		return clonedWorkItems;
	}

	@Override
	public void deleteWorkItemsByEntityIdAndWorkType(Long entityId, String workType) {
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		String deleteQuery = "Delete from WorkItem where entityId =:ENTITY_ID and workType = :WORKTYPE and clientId = "+clientId ;;
		Query query=(Query) getEntityManager().createQuery(deleteQuery).setParameter("ENTITY_ID", entityId).setParameter("WORKTYPE", workType);
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
			WorkItem clonedWorkItem=SerializationUtils.clone(workItem.get(0));
			return clonedWorkItem;
		}
		return null;

	}


	@Override
	public List<Comment> getCommentList(Long workItemId){
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		String queryString="FROM Comment c WHERE c.workItemId = "+workItemId+" "+" and c.client.clientId = "+clientId +" ORDER BY c.createdOn desc"; 
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Comment> comment= (List<Comment>)query.getResultList();
		List<Comment> clonedComments = new ArrayList<Comment>(comment);
		return clonedComments;
	}

	@Override
	public Comment getLatestCommentBycommentType(Long entityId, String entityType, String commentType){
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		String queryString="select cm.COMMENT_ID, cm.CREATED_BY, cm.CREATED_ON, cm.WORKITEM_ID, cm.Client_Id, cm.COMMENT_TYPE, cm.COMMENT "
				+ "from tb_work_item wi join tb_comment cm on wi.WORKITEM_ID = cm.WORKITEM_ID "
				+ "where wi.ENTITY_ID = " + entityId + " and wi.ENTITY_TYPE = "+ "'"+ entityType +"'" + " and cm.COMMENT_TYPE ="+ "'"+ commentType +"'"+ " and wi.client_Id = "+clientId 
				+" order by cm.CREATED_ON desc limit 1";
		Query query=getEntityManager().createNativeQuery(queryString, Comment.class);

		List<Comment> comments = (List<Comment>)query.getResultList();

		if(comments!=null && comments.size()>0){
			return comments.get(0);
		}

		return null;
	}

	@Override
	public List<WorkItem> getActiveWorkItems(WorkItemSearchCriteria criteria, ServiceAgreement agreement){

		java.sql.Date startDateSql = DateUtil.convertStringToSqlDate(agreement.getContractStartOnString());
		java.sql.Date expireDateSql = DateUtil.convertStringToSqlDate(agreement.getContractExpireOnString());

		String queryString="Select wi.WORKITEM_ID	,wi.CREATED_BY	,wi.CREATED_ON	,wi.UPDATED_BY	,wi.UPDATED_ON	,wi.ASSIGNEE_ID	,wi.DESCRIPTION	,wi.DUE_DATE	,wi.ENTITY_CODE	,wi.ENTITY_ID	,wi.ENTITY_TYPE	,wi.ENTITY_URL	,wi.PRIORITY	,wi.PRIVILEGE_ID,wi.STATUS,wi.WORKTYPE	,"
				+ "wi.Client_Id	 from  TB_WORK_ITEM " 
				+ "where wi.entityId ="+criteria.getEntityId()+" and wi.WORKTYPE = "+"'"+criteria.getType()+"'"+" and wi.ENTITY_TYPE = "+"'"+criteria.getEntityType() +"'"+" and wi.DUE_DATE BETWEEN "+startDateSql+" AND " + expireDateSql;
		Query query=getEntityManager().createNativeQuery(queryString,WorkItem.class);
		@SuppressWarnings("unchecked")
		List<WorkItem> workItems= (List<WorkItem>)query.getResultList();
		List<WorkItem> clonedWorkItems = new ArrayList<WorkItem>(workItems);
		return clonedWorkItems;
	}	

	@Override
	public SearchResultData getWorkItembySearchCriteria(WorkItemSearchCriteria workItemSearchCriteria) throws ParseException {

		SearchResultData<WorkItem> searchResultData= new SearchResultData<WorkItem>();
		String ascOrDsc = workItemSearchCriteria.getIsAscending()?"ASC":"DESC";
		
		String sortBy=null;
		try {
			sortBy = CommonUtil.getFieldValue(WorkItem.class, workItemSearchCriteria.getSortBy());
		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String queryString="Select wi.WORKITEM_ID	,wi.CREATED_BY	,wi.CREATED_ON	,wi.UPDATED_BY	,wi.UPDATED_ON	,wi.ASSIGNEE_ID	,wi.DESCRIPTION	,wi.DUE_DATE	,wi.ENTITY_CODE	,wi.ENTITY_ID	,wi.ENTITY_TYPE	,wi.ENTITY_URL	,wi.PRIORITY	,wi.PRIVILEGE_ID,wi.STATUS,wi.WORKTYPE	,"
				+ "wi.Client_Id	 from  TB_WORK_ITEM wi join TB_PRIVILEGE prv "
				+ "on wi.PRIVILEGE_ID=prv.PRIVILEGE_ID join TB_USER_PRIVILEGE usrpr on usrpr.PRIVILEGE_ID=prv.PRIVILEGE_ID "
				+ "where wi.WORKTYPE = coalesce(:type, wi.WORKTYPE) and wi.STATUS = coalesce(:status, wi.STATUS) and wi.ENTITY_TYPE = coalesce(:entityType, wi.ENTITY_TYPE) and coalesce(wi.ASSIGNEE_ID, '-1') = coalesce(:assigneeId, coalesce(wi.ASSIGNEE_ID, '-1')) and usrpr.USER_ID=:userId and (coalesce(DATE(wi.DUE_DATE),now()) BETWEEN :DATE_FROM AND :DATE_TO) ORDER BY  "+sortBy +" "+ascOrDsc+" limit :START_INDEX,:PAGE_SIZE";
		Query query=getEntityManager().createNativeQuery(queryString,WorkItem.class);
		
		String queryString1="SELECT count(*),'totalCount' FROM (select wi.* from  TB_WORK_ITEM wi join TB_PRIVILEGE prv on wi.PRIVILEGE_ID=prv.PRIVILEGE_ID join TB_USER_PRIVILEGE usrpr on usrpr.PRIVILEGE_ID=prv.PRIVILEGE_ID where wi.WORKTYPE = coalesce(:type, WORKTYPE) and wi.STATUS = coalesce(:status, STATUS) and wi.ENTITY_TYPE = coalesce(:entityType, wi.ENTITY_TYPE) and coalesce(wi.ASSIGNEE_ID, '-1') = coalesce(:assigneeId, coalesce(wi.ASSIGNEE_ID, '-1')) and usrpr.USER_ID=:userId and (coalesce(DATE(wi.DUE_DATE),now()) BETWEEN :DATE_FROM AND :DATE_TO))a";
		Query query1=getEntityManager().createNativeQuery(queryString1);
		
		
		String entityType = StringUtilities.getDefaultOrBlankValueOfString(workItemSearchCriteria.getEntityType());
		String type = StringUtilities.getDefaultOrBlankValueOfString(workItemSearchCriteria.getType());
		String status = StringUtilities.getDefaultOrBlankValueOfString(workItemSearchCriteria.getStatus());
		Long userId = workItemSearchCriteria.getUserId();
		Long assigneeId = null;
		if(workItemSearchCriteria.getAssigneeId() != null){
			 assigneeId = workItemSearchCriteria.getAssigneeId();
		}
		String dateFromString,dateToString = null;
		java.sql.Date dateToSql;
		java.sql.Date dateFromSql;
		if(workItemSearchCriteria.getFromDate() == null)
		{
			dateFromString = "March 01, 1986";
			dateFromSql = DateUtil.convertStringToSqlDate(dateFromString, AppConstants.DateFormat.MM_dd_yyyy.getPattern());
		}
		else
		{
			dateFromString = workItemSearchCriteria.getFromDate();
			dateFromSql = DateUtil.convertStringToSqlDate(dateFromString);
		}

		if(workItemSearchCriteria.getToDate() == null)
		{
			dateToString = "March 01, 9999";
			dateToSql = DateUtil.convertStringToSqlDate(dateToString, AppConstants.DateFormat.MM_dd_yyyy.getPattern()); 
		}
		else
		{
			dateToString = workItemSearchCriteria.getToDate();
			dateToSql = DateUtil.convertStringToSqlDate(dateToString);
		}

		
		int pageSize,pageNo;
		if(workItemSearchCriteria.getPageSize()==0)
		{
			pageSize = 3;
		}
		else
		{
			pageSize = workItemSearchCriteria.getPageSize();
		}
		if(workItemSearchCriteria.getPageNo() == 0)
		{
			pageNo = 1;
		}
		else
		{
			pageNo = workItemSearchCriteria.getPageNo();
		}

		int startIndex = (pageSize * pageNo) - pageSize;
		
		query.setParameter("type", type);
		query.setParameter("userId", userId);
		query.setParameter("status", status);
		query.setParameter("entityType", entityType);
		query.setParameter("assigneeId", assigneeId);
		query.setParameter("DATE_FROM", dateFromSql);
		query.setParameter("DATE_TO", dateToSql);
		query.setParameter("PAGE_SIZE", pageSize);
		query.setParameter("START_INDEX", startIndex);
		
		query1.setParameter("type", type);
		query1.setParameter("userId", userId);
		query1.setParameter("status", status);
		query1.setParameter("entityType", entityType);
		query1.setParameter("assigneeId", assigneeId);
		query1.setParameter("DATE_FROM", dateFromSql);
		query1.setParameter("DATE_TO", dateToSql);
		
		@SuppressWarnings("unchecked")
		List<Object[]> counts = query1.getResultList();
		for (Object[] count : counts) {
			Long count1 = (long) ((Number) count[0]).intValue();
			searchResultData.setTotalCount(count1);
		}
		
		@SuppressWarnings("unchecked")
		List<WorkItem> result= (List<WorkItem>)query.getResultList();
		searchResultData.setObjectData(result);
		return searchResultData;
	}

}
