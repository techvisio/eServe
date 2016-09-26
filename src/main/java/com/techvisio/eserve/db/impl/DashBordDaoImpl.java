package com.techvisio.eserve.db.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.GraphData;
import com.techvisio.eserve.db.DashBordDao;

@Component
public class DashBordDaoImpl extends BaseDao implements DashBordDao{

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Long> getCount(Long clientId){
		Map<String, Long> countMap = new HashMap<String, Long>();
		String queryString="select 'pendingApp',count(*) from TB_UNIT_DETAIL where APPROVAL_STATUS = 'P' and client_Id = " + clientId + " union select 'openComplaints',count(*) from TB_WORK_ORDER WHERE STATUS != 'CLOSE' and client_Id ="+clientId +" and work_order_type='Complaint' union select 'salesRenewal',count(*) from TB_WORK_ITEM where STATUS != 'CLOSE' and WORKTYPE='FOLLOWUP RENEWAL SERVICE'  and client_Id= " +clientId+" union select 'pmsCall',count(*) from TB_WORK_ITEM where STATUS != 'CLOSE' and WORKTYPE='PMS'  and client_Id= " +clientId ;
		Query query=getEntityManager().createNativeQuery(queryString);
		List<Object[]> results = query.getResultList();
		for (Object[] result : results) {
			String name = (String) result[0];
			Long count = (long) ((Number) result[1]).intValue();
			countMap.put(name, count)  ;  
		}
		return countMap;
	}

	@Override
	@SuppressWarnings("unchecked")
	public GraphData getComplaintByPriority(Long clientId){
		GraphData graphData = new GraphData();
		List<String> label = new LinkedList<String>();
		List<Long> data = new LinkedList<Long>();

		String queryString="select PRIORITY, Count(*) From (select Case when PRIORITY = 'C' then 'CRITICAL' when PRIORITY = 'H' then 'HIGH' when PRIORITY = 'M' then 'MEDIUM' else 'LOW' End PRIORITY from TB_WORK_ORDER where work_order_type='Complaint' and client_id = "+clientId+") as t group by t.PRIORITY";
		Query query=getEntityManager().createNativeQuery(queryString);
		List<Object[]> results = query.getResultList();
		for (Object[] result : results) {
			
			Long count = (long) ((Number) result[1]).intValue();
			data.add(count);
			label.add((String) result[0]);
			graphData.setLabel(label);
			graphData.setData(data);
		}
		return graphData;
	}

	@Override
	@SuppressWarnings("unchecked")
	public GraphData getComplaintBySlaDate(Long clientId){
		GraphData graphData = new GraphData();
		List<String> label = new LinkedList<String>();
		List<Long> data = new LinkedList<Long>();

		String queryString="select SLAstatus, Count(*) From (select Case when Date(SLA_DATE) > CURDATE() then 'DUE' when Date(SLA_DATE) < CURDATE() then 'PAST' else 'TODAY' End SLAstatus from TB_WORK_ORDER where work_order_type='Complaint' and  client_id ="+clientId+") as t group by t.SLAstatus";
		Query query=getEntityManager().createNativeQuery(queryString);
		List<Object[]> results = query.getResultList();
		for (Object[] result : results) {
			
			Long count = (long) ((Number) result[1]).intValue();
			data.add(count);
			label.add((String) result[0]);
			graphData.setLabel(label);
			graphData.setData(data);
		}
		return graphData;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public GraphData getComplaintByAssignment(Long clientId){
		GraphData graphData = new GraphData();
		List<String> label = new LinkedList<String>();
		List<Long> data = new LinkedList<Long>();

		String queryString="select STATUS, Count(STATUS) from TB_WORK_ORDER where work_order_type='Complaint' and  Client_Id = "+clientId+" group by STATUS;";
		Query query=getEntityManager().createNativeQuery(queryString);
		List<Object[]> results = query.getResultList();
		for (Object[] result : results) {
			
			Long count = (long) ((Number) result[1]).intValue();
			data.add(count);
			label.add((String) result[0]);
			graphData.setLabel(label);
			graphData.setData(data);
		}
		return graphData;
	}	
}

