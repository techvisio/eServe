package com.techvisio.eserve.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techvisio.eserve.beans.Response;
import com.techvisio.eserve.beans.Role;
import com.techvisio.eserve.beans.SecurityQuestion;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.workflow.UserWorkflowManager;

@RestController
@RequestMapping("/service/user")
public class UserController {
	
	UserWorkflowManager userWorkflowManager;

	@RequestMapping(value="/loggedinuser", method = RequestMethod.GET)
	public ResponseEntity<Response> getLoggedInUser(){
		User user =null;
		if(SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() !=null){
		user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		Response response=new Response();
		response.setResponseBody(user);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/adduser/", method = RequestMethod.POST)
	public ResponseEntity<Response> addUser(@RequestBody User user){
		Response response = new Response();
		userWorkflowManager.addUser(user);
        User userFromDB = userWorkflowManager.getUser(user.getUserId());
		response.setResponseBody(userFromDB);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value = "/getuser/{userId}", method = RequestMethod.GET)
	public ResponseEntity<Response> getUser(@PathVariable Long userId){
		Response response = new Response();
		User userFromDB=userWorkflowManager.getUser(userId);
		response.setResponseBody(userFromDB);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getuserRole/{userId}", method = RequestMethod.GET)
	public ResponseEntity<Response> getUserRoles(@PathVariable Long userId){
		Response response = new Response();
		List<Role> userRoles=userWorkflowManager.getUserRole(userId);
		response.setResponseBody(userRoles);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveQuestion/", method = RequestMethod.POST)
	public ResponseEntity<Response> saveSecurityQuestion(@RequestBody SecurityQuestion securityQuestion){
		Response response = new Response();
		userWorkflowManager.saveSecurityQuestion(securityQuestion);
		SecurityQuestion questionFromDB = userWorkflowManager.getSecurityQuestion(securityQuestion.getSecurityQustnId());
		response.setResponseBody(questionFromDB);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
}
