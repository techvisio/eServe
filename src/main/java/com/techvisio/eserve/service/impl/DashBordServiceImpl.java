package com.techvisio.eserve.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.techvisio.eserve.service.DashBordService;

public class DashBordServiceImpl implements DashBordService  {
	
	@Autowired
	DashBordService dashbordService;
	
		//private Map<String, Long> items = new HashMap<String, Long>();
		public Map<String, Long> getcount(Long clientId) {
			Map<String, Long> count= (Map<String, Long>) dashbordService.getcount(clientId);
			return count;
		}
	}
