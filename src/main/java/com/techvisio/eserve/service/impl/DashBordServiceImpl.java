package com.techvisio.eserve.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	}
