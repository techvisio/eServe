package com.techvisio.eserve.db.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.CustomerReport;
import com.techvisio.eserve.beans.EquipmentDetail;
import com.techvisio.eserve.beans.UnitBasicInfo;
import com.techvisio.eserve.beans.WorkItem;
import com.techvisio.eserve.db.WorkItemDao;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.DateUtil;
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
	public void updateWorkItemStatus(Long entityId, String status){

		String updateQuery = "UPDATE TB_WORK_ITEM SET STATUS=:STATUS where ENTITY_ID = :ENTITY_ID";

		Query query=(Query) getEntityManager().createNativeQuery(updateQuery).setParameter("STATUS", status).setParameter("ENTITY_ID", entityId);
		query.executeUpdate();	
	}

	@Override
	public List<WorkItem> getWorkItemsByEntityId(Long entityId){ 

		String queryString="FROM WorkItem wi WHERE wi.entityId = "+entityId;
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
		String deleteQuery = "Delete from tb_work_item where ENTITY_ID =:ENTITY_ID and WORKTYPE = :WORKTYPE";
		Query query=(Query) getEntityManager().createNativeQuery(deleteQuery).setParameter("ENTITY_ID", entityId).setParameter("WORKTYPE", workType);
		query.executeUpdate();	
	}

}
