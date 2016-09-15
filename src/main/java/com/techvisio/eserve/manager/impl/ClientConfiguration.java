package com.techvisio.eserve.manager.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.ClientCommConfig;
import com.techvisio.eserve.beans.ClientConfig;
import com.techvisio.eserve.manager.CacheManager;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;

@Component
public class ClientConfiguration {

	@Autowired
	CacheManager cacheManager;

	public String getDefaultPassword(Long clientId) {

		ClientConfig config = getConfigObject(AppConstants.DEFAULT_PASSWORD);

		return config.getValue();
	}

	public Date getSlaDateForCriticalIssue(Long clientId) {

		ClientConfig config = getConfigObject(AppConstants.SLA_DAYS_CRITICAL);
		int countDays = Integer.parseInt(config.getValue());
		Date date = new Date();
		Date slaDate = CommonUtil.getDate(date, countDays, false, true);

		return slaDate;
	}

	public Date getSlaDateForHighIssue(Long clientId) {

		ClientConfig config = getConfigObject(AppConstants.SLA_DAYS_HIGH);
		int countDays = Integer.parseInt(config.getValue());
		Date date = new Date();
		Date slaDate = CommonUtil.getDate(date, countDays, false, true);

		return slaDate;
	}

	public Date getSlaDateForMediumIssue(Long clientId) {
		ClientConfig config = getConfigObject(AppConstants.SLA_DAYS_MEDIUM);
		int countDays = Integer.parseInt(config.getValue());
		Date date = new Date();
		Date slaDate = CommonUtil.getDate(date, countDays, false, true);

		return slaDate;
	}

	public Date getSlaDateForLowIssue(Long clientId) {
		ClientConfig config = getConfigObject(AppConstants.SLA_DAYS_LOW);
		int countDays = Integer.parseInt(config.getValue());
		Date date = new Date();
		Date slaDate = CommonUtil.getDate(date, countDays, false, true);

		return slaDate;
	}

	public ClientConfig getConfigObject(String property) {
		return getConfigObject(property, CommonUtil.getCurrentClient().getClientId());
	}

	public ClientConfig getConfigObject(String property, Long clientId) {

		Map<Long, Map<String, ClientConfig>> configMap = cacheManager.getConfigMap();
		Map<String, ClientConfig> defaultMap = configMap.get(clientId);
		ClientConfig config = (ClientConfig) defaultMap.get(property);
		return config;
	}

	
	public ClientCommConfig getClientComConfigObject(String property) {
		return getClientComConfigObject(property, CommonUtil.getCurrentClient().getClientId());
	}
	
	public ClientCommConfig getClientComConfigObject(String property,Long clientId) {

		Map<Long, Map<String, ClientCommConfig>> clientComConfigMap = cacheManager.getClientComConfigMap();
		Map<String, ClientCommConfig> defaultMap = clientComConfigMap.get(clientId);
		ClientCommConfig clientComConfig = (ClientCommConfig) defaultMap.get(property);
		return clientComConfig;
	}
}
