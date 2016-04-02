package com.techvisio.eserve.manager.impl;

import java.util.Map;

import com.techvisio.eserve.db.DashBordDao;
import com.techvisio.eserve.manager.DashBordManager;

public class DashBordManagerImpl implements DashBordManager{
	
	DashBordDao dashborddaoDao;

	public Map<String,Long> getcount(Long clientId) {
		Map<String,Long> count = (Map<String,Long>) dashborddaoDao.getCount(clientId);
		return count;
	}

}
