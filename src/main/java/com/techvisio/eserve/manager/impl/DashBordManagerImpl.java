package com.techvisio.eserve.manager.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.GraphData;
import com.techvisio.eserve.db.DashBordDao;
import com.techvisio.eserve.manager.DashBordManager;
@Component
public class DashBordManagerImpl implements DashBordManager{
	
	@Autowired
	DashBordDao dashBordDao;

	public Map<String,Long> getCount(Long clientId) {
		Map<String,Long> count = (Map<String,Long>) dashBordDao.getCount(clientId);
		return count;
	}

	@Override
	public GraphData getComplaintCountByPriority(Long clientId) {
		GraphData graphData = dashBordDao.getComplaintByPriority(clientId);
		return graphData;
	}

	@Override
	public GraphData getComplaintCountBySlaDate(Long clientId) {
		GraphData graphData = dashBordDao.getComplaintBySlaDate(clientId);
		return graphData;
	}

	@Override
	public GraphData getComplaintCountByAssignment(Long clientId) {
		GraphData graphData = dashBordDao.getComplaintByAssignment(clientId);
		return graphData;
	}

}
