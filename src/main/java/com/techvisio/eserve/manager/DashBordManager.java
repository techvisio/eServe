package com.techvisio.eserve.manager;

import java.util.Map;

import org.springframework.stereotype.Component;
@Component
public interface DashBordManager {
	
	public Map<String, Long> getCount(Long clientId);

}
