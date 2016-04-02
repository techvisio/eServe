package com.techvisio.eserve.db.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.db.DashBordDao;

@Component
public class DashBordDaoImpl extends BaseDao implements DashBordDao{

	@SuppressWarnings("unchecked")
	public Map<String, Long> getCount(Long clientId){
		Map<String, Long> countMap = new HashMap<String, Long>();
		String queryString="select 'pendingApp',count(*) from tb_unit_detail where APPROVAL_STATUS = 'P' and client_Id = " + clientId + " union select 'salesRenewal',count(*) from tb_customer_complaint WHERE (STATUS='ASSIGNED' OR STATUS='UNASSIGNED') and client_Id ="+clientId;
		Query query=getEntityManager().createNativeQuery(queryString);
		List<Object[]> results = query.getResultList();
		for (Object[] result : results) {
		String name = (String) result[0];
		Long count = (long) ((Number) result[1]).intValue();
		countMap.put(name, count)  ;  
		}
		return countMap;
	}

}
