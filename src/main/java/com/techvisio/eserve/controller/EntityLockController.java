package com.techvisio.eserve.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techvisio.eserve.beans.EntityLocks;
import com.techvisio.eserve.beans.Response;
import com.techvisio.eserve.service.EntityLockService;

@RestController
@RequestMapping("service/entityLock")
public class EntityLockController {

	@Autowired
	EntityLockService entityLockService;

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value ="/lockEntity", method = RequestMethod.POST)
	public void lockEntity(@RequestBody EntityLocks entityLocks) {
		entityLockService.lockEntity(entityLocks);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value ="/unlockEntity", method = RequestMethod.POST)
	public ResponseEntity<Response> unlockEntity(@RequestBody EntityLocks entityLocks) {
		Response response=new Response();
		Object object=entityLockService.unlockEntity(entityLocks.getEntityType(),entityLocks.getEntityId());
		response.setResponseBody(object);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

}
