package com.techvisio.eserve.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.GraphData;
import com.techvisio.eserve.manager.DashBordManager;
import com.techvisio.eserve.manager.impl.DashBordManagerImpl;
import com.techvisio.eserve.service.DashBordService;
import com.techvisio.eserve.util.CommonUtil;
@Component
public class DashBordServiceImpl implements DashBordService  {

	@Autowired
	DashBordManager dashBordManager;

	public Map<String, Long> getCount() {

		Long clientId = CommonUtil.getCurrentClient().getClientId();
		Map<String, Long> count= (Map<String, Long>) dashBordManager.getCount(clientId);
		return count;
	}

	@Override
	public GraphData getComplaintCountByPriority() {
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		GraphData graphData = dashBordManager.getComplaintCountByPriority(clientId);
		return graphData;
	}

	@Override
	public GraphData getComplaintCountBySlaDate() {
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		GraphData graphData = dashBordManager.getComplaintCountBySlaDate(clientId);
		return graphData;
	}

	@Override
	public GraphData getComplaintCountByAssignment() {
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		GraphData graphData = dashBordManager.getComplaintCountByAssignment(clientId);
		return graphData;
	}
}
