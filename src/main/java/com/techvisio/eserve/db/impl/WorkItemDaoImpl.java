package com.techvisio.eserve.db.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.WorkItem;
import com.techvisio.eserve.db.WorkItemDao;
@Component
public class WorkItemDaoImpl extends BaseDao implements WorkItemDao{

	@Override
	public WorkItem getWorkItemByUserId(Long userId) {
		String queryString="FROM WorkItem wi WHERE wi.assigneeId = "+userId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<WorkItem> workItems= (List<WorkItem>)query.getResultList();
		if(workItems != null && workItems.size()>0){
			return workItems.get(0);
		}
		return null;
	}

	@Override
	public WorkItem getWorkItemByPrivilege(Long privilegeId) {
		String queryString="FROM WorkItem wi WHERE wi.privilegeId = "+privilegeId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<WorkItem> workItems= (List<WorkItem>)query.getResultList();
		if(workItems != null && workItems.size()>0){
			return workItems.get(0);
		}
		return null;
	}

	@Override
	public WorkItem getWorkItemByWorkType(String workType) {
		String queryString="FROM WorkItem wi WHERE wi.workType = "+workType;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<WorkItem> workItems= (List<WorkItem>)query.getResultList();
		if(workItems != null && workItems.size()>0){
			return workItems.get(0);
		}
		return null;
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

}
