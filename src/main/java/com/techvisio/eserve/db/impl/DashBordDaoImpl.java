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
		String queryString="select 'pendingApp',count(*) from tb_unit_detail where APPROVAL_STATUS = 'P' and client_Id = " + clientId + " union select 'openComplaints',count(*) from tb_customer_complaint WHERE STATUS != 'CLOSE' and client_Id ="+clientId +" union select 'salesRenewal',count(*) from tb_work_item where STATUS != 'CLOSE' and WORKTYPE='RENEW SERVICE CALL'  and client_Id= " +clientId ;
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

		String queryString="select PRIORITY, Count(*) From (select Case when PRIORITY = 'C' then 'CRITICAL' when PRIORITY = 'H' then 'HIGH' when PRIORITY = 'M' then 'MEDIUM' else 'LOW' End PRIORITY from tb_customer_complaint where client_id = "+clientId+") as t group by t.PRIORITY";
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

		String queryString="select SLAstatus, Count(*) From (select Case when Date(SLA_DATE) > CURDATE() then 'DUE' when Date(SLA_DATE) < CURDATE() then 'PAST' else 'TODAY' End SLAstatus from tb_customer_complaint where client_id ="+clientId+") as t group by t.SLAstatus";
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

		String queryString="select STATUS, Count(STATUS) from tb_customer_complaint where Client_Id = "+clientId+" group by STATUS;";
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

