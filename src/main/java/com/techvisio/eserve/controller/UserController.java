package com.techvisio.eserve.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techvisio.eserve.beans.Privilege;
import com.techvisio.eserve.beans.Response;
import com.techvisio.eserve.beans.Role;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.util.CommonUtil;
import com.techvisio.eserve.workflow.UserWorkflowManager;

@RestController
@RequestMapping("service/user")
public class UserController {

	@Autowired
	UserWorkflowManager userWorkflowManager;

	@RequestMapping(value="/loggedinuser", method = RequestMethod.GET)
	public ResponseEntity<Response> getLoggedInUser(){
		User user =null;
		user = CommonUtil.getCurrentUser();
		Response response=new Response();
		response.setResponseBody(user);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Response> saveUser(@RequestBody User user){
		Response response = new Response();
		userWorkflowManager.saveUser(user);
		User userFromDB = userWorkflowManager.getUser(user.getUserId());
		response.setResponseBody(userFromDB);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public ResponseEntity<Response> getUser(@PathVariable Long userId){
		Response response = new Response();
		User userFromDB=userWorkflowManager.getUser(userId);
		response.setResponseBody(userFromDB);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping( method = RequestMethod.GET)
	public ResponseEntity<Response> getUser(){
		Response response = new Response();
		List<User> userFromDB=userWorkflowManager.getUsers();
		response.setResponseBody(userFromDB);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value = "/userRole/{userId}", method = RequestMethod.GET)
	public ResponseEntity<Response> getUserRoles(@PathVariable Long userId){
		Response response = new Response();
		List<Role> userRoles=userWorkflowManager.getUserRole(userId);
		response.setResponseBody(userRoles);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value = "/forcepasswordchange", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Response> saveManadatoryResetChanges(@RequestBody User user){

		Map<String, Boolean> result = userWorkflowManager.forcePasswordChange(user);
		Response response=new Response();
		response.setResponseBody(result);
		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/userprivileges/{userId}", method = RequestMethod.GET)
	public ResponseEntity<Response> getUserPrivileges(@PathVariable Long userId){
		Response response = new Response();
		List<Privilege> userPrivileges=userWorkflowManager.getUserPrivileges(userId);
		response.setResponseBody(userPrivileges);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value ="/search/", method = RequestMethod.POST)
	public ResponseEntity<Response> getStudentDtlByCriteria(@RequestBody SearchCriteria searchCriteria) {
		Response response=new Response();
		List<User> userByCriteria = userWorkflowManager.getUserByCriteria(searchCriteria);
		response.setResponseBody(userByCriteria);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value ="/verify/", method = RequestMethod.POST)
	public ResponseEntity<Response> verifyUserNameAndEmailId(@RequestBody SearchCriteria searchCriteria) {
		Response response=new Response();
		User user = userWorkflowManager.verifyUserNameAndEmialId(searchCriteria);
		response.setResponseBody(user);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

}
