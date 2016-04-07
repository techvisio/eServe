package com.techvisio.eserve.db;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.GraphData;
@Component
public interface DashBordDao {


	public Map<String, Long> getCount(Long clientId);

	public GraphData getComplaintByPriority(Long clientId);

	public GraphData getComplaintBySlaDate(Long clientId);
}
