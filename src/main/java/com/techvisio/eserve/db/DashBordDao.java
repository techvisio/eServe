package com.techvisio.eserve.db;

import java.util.Map;

public interface DashBordDao {


	public Map<String, Long> getCount(Long clientId);
}
