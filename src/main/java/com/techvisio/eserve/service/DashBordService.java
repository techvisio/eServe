package com.techvisio.eserve.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.GraphData;

@Component
public interface DashBordService {

	public Map<String, Long> getCount();
	
	public GraphData getComplaintCountByPriority();

	public GraphData getComplaintCountBySlaDate();
	
	public GraphData getComplaintCountByAssignment();

}
