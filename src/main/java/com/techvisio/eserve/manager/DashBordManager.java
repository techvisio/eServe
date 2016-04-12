package com.techvisio.eserve.manager;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.GraphData;
@Component
public interface DashBordManager {
	
	public Map<String, Long> getCount(Long clientId);
	
	public GraphData getComplaintCountByPriority(Long clientId);

	public GraphData getComplaintCountBySlaDate(Long clientId);
	
	public GraphData getComplaintCountByAssignment(Long clientId);
}
