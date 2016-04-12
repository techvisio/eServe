package com.techvisio.eserve.manager.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Config;
import com.techvisio.eserve.manager.CacheManager;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;

@Component
public class ConfigPreferences {

	@Autowired
	CacheManager cacheManager;

	public String getDefaultPassword(Long clientId){

		Map<Long, Map<String, Object>> configMap = cacheManager.getConfigMap(clientId);

		Map<String, Object> defaultMap = configMap.get(clientId);

		Config config = (Config) defaultMap.get(AppConstants.DEFAULT_PASSWORD);

		return config.getValue();
	}


	public Date getSlaDateForCriticalIssue(Long clientId){

		Map<Long, Map<String, Object>> configMap = cacheManager.getConfigMap(clientId);

		Map<String, Object> defaultMap = configMap.get(clientId);

		Config config = (Config) defaultMap.get(AppConstants.SLA_DAYS_CRITICAL);
		int countDays = Integer.parseInt(config.getValue());
		Date date = new Date();
		Date slaDate = CommonUtil.getDate(date, countDays, false, true);

		return slaDate;
	}

	public Date getSlaDateForHighIssue(Long clientId){

		Map<Long, Map<String, Object>> configMap = cacheManager.getConfigMap(clientId);

		Map<String, Object> defaultMap = configMap.get(clientId);

		Config config = (Config) defaultMap.get(AppConstants.SLA_DAYS_HIGH);
		int countDays = Integer.parseInt(config.getValue());
		Date date = new Date();
		Date slaDate = CommonUtil.getDate(date, countDays, false, true);

		return slaDate;
	}

	public Date getSlaDateForMediumIssue(Long clientId){

		Map<Long, Map<String, Object>> configMap = cacheManager.getConfigMap(clientId);

		Map<String, Object> defaultMap = configMap.get(clientId);

		Config config = (Config) defaultMap.get(AppConstants.SLA_DAYS_MEDIUM);
		int countDays = Integer.parseInt(config.getValue());
		Date date = new Date();
		Date slaDate = CommonUtil.getDate(date, countDays, false, true);

		return slaDate;
	}

	public Date getSlaDateForLowIssue(Long clientId){

		Map<Long, Map<String, Object>> configMap = cacheManager.getConfigMap(clientId);

		Map<String, Object> defaultMap = configMap.get(clientId);

		Config config = (Config) defaultMap.get(AppConstants.SLA_DAYS_LOW);
		int countDays = Integer.parseInt(config.getValue());
		Date date = new Date();
		Date slaDate = CommonUtil.getDate(date, countDays, false, true);

		return slaDate;
	}

}
