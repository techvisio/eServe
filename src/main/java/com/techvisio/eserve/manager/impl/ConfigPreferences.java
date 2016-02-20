package com.techvisio.eserve.manager.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.header.writers.frameoptions.StaticAllowFromStrategy;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Config;
import com.techvisio.eserve.manager.CacheManager;
import com.techvisio.eserve.util.AppConstants;

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

		Date date = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.add(Calendar.DATE, Integer.parseInt(config.getValue()));
		date = c.getTime();

		return date;

	}

	public Date getSlaDateForHighIssue(Long clientId){

		Map<Long, Map<String, Object>> configMap = cacheManager.getConfigMap(clientId);

		Map<String, Object> defaultMap = configMap.get(clientId);

		Config config = (Config) defaultMap.get(AppConstants.SLA_DAYS_HIGH);

		Date date = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.add(Calendar.DATE, Integer.parseInt(config.getValue()));
		date = c.getTime();

		return date;

	}

	public Date getSlaDateForMediumIssue(Long clientId){

		Map<Long, Map<String, Object>> configMap = cacheManager.getConfigMap(clientId);

		Map<String, Object> defaultMap = configMap.get(clientId);

		Config config = (Config) defaultMap.get(AppConstants.SLA_DAYS_MEDIUM);

		Date date = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.add(Calendar.DATE, Integer.parseInt(config.getValue()));
		date = c.getTime();

		return date;

	}
	
	public Date getSlaDateForLowIssue(Long clientId){

		Map<Long, Map<String, Object>> configMap = cacheManager.getConfigMap(clientId);

		Map<String, Object> defaultMap = configMap.get(clientId);

		Config config = (Config) defaultMap.get(AppConstants.SLA_DAYS_LOW);

		Date date = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.add(Calendar.DATE, Integer.parseInt(config.getValue()));
		date = c.getTime();

		return date;

	}

}
