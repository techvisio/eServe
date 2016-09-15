package com.techvisio.eserve.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techvisio.eserve.beans.Response;
import com.techvisio.eserve.manager.CacheManager;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CustomLogger;

@Component 
@RestController
@RequestMapping("/service/masterdata")
public class MasterDataService {
	private static CustomLogger logger = CustomLogger.getLogger(MasterDataService.class);

	@Autowired
	CacheManager cacheManager;

	//	@Autowired
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}


	@RequestMapping(value = "/customer", method = RequestMethod.GET)	
	public ResponseEntity<Response> getMasterDataforAdmission() {
		logger.info("{} Get MasterData call for customer ",this.getClass().getName());
		Response response=new Response();
		String[] masterEntity=new String[]{
				AppConstants.STATE,
				AppConstants.RESOLUTION,
				AppConstants.ISSUE,
				AppConstants.AGREEMENT_DURATION,
				AppConstants.UNIT_CATEGORY,
				AppConstants.CUSTOMER_TYPE,
				AppConstants.SERVICE_PROVIDER
		};

		try{
			Map<String,List> serverData=new HashMap<String, List>();

			for(String entity:masterEntity){
				serverData.put(entity, cacheManager.getEntityList(entity));
			}

			response.setResponseBody(serverData);
		}
		catch(Exception e){
			logger.error("Error while fetching master data for admssion", e);
			response.setError(e.getMessage());
		}
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}


	@RequestMapping(value = "/user", method = RequestMethod.GET)	
	public ResponseEntity<Response> getMasterDataforUser() {
		logger.info("{} Get MasterData call for user ",this.getClass().getName());
		Response response=new Response();
		String[] masterEntity=new String[]{
				AppConstants.QUESTION,
				AppConstants.DEPARTMENT,
				AppConstants.DESIGNATION
		};

		try{
			Map<String,List> serverData=new HashMap<String, List>();

			for(String entity:masterEntity){
				serverData.put(entity, cacheManager.getEntityList(entity));
			}

			response.setResponseBody(serverData);
		}
		catch(Exception e){
			logger.error("Error while fetching master data for user", e);
			response.setError(e.getMessage());
		}
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	
	@RequestMapping(value = "/workitem", method = RequestMethod.GET)	
	public ResponseEntity<Response> getMasterDataforWorkitem() {
		logger.info("{} Get MasterData call for workitem ",this.getClass().getName());
		Response response=new Response();
		String[] masterEntity=new String[]{
				AppConstants.USER,
		};

		try{
			Map<String,List> serverData=new HashMap<String, List>();

			for(String entity:masterEntity){
				serverData.put(entity, cacheManager.getEntityList(entity));
			}

			response.setResponseBody(serverData);
		}
		catch(Exception e){
			logger.error("Error while fetching master data for user", e);
			response.setError(e.getMessage());
		}
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
}
