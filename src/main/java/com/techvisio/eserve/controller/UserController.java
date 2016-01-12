package com.techvisio.eserve.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techvisio.eserve.beans.Response;
import com.techvisio.eserve.beans.Role;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.beans.UserPrivilege;
import com.techvisio.eserve.manager.UserManager;
import com.techvisio.eserve.service.UserService;
import com.techvisio.eserve.util.CommonUtil;

@RestController
@RequestMapping("service/user")
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(value="/loggedinuser", method = RequestMethod.GET)
	public ResponseEntity<Response> getLoggedInUser(){
		User user =null;
		user = CommonUtil.getCurrentUser();
		user.setPassword(null);
		Response response=new Response();
		response.setResponseBody(user);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public void logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	}

//	@RequestMapping(method = RequestMethod.POST)
//	public ResponseEntity<Response> saveUser(@RequestBody User user){
//		Response response = new Response();
//		userWorkflowManager.saveUser(user);
//		User userFromDB = userWorkflowManager.getUser(user.getUserId());
//		response.setResponseBody(userFromDB);
//		return new ResponseEntity<Response>(response,HttpStatus.OK);
//	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public ResponseEntity<Response> getUser(@PathVariable Long userId){
		Response response = new Response();
		User userFromDB=userService.getUser(userId);
		response.setResponseBody(userFromDB);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/currentpass/{userId}", method = RequestMethod.GET)
	public ResponseEntity<Response> getCurrentPassword(@PathVariable Long userId){
		Response response = new Response();
		User userPass=userService.getCurrentPassword(userId);
		response.setResponseBody(userPass);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping( method = RequestMethod.GET)
	public ResponseEntity<Response> getUser(){
		Response response = new Response();
		List<User> userFromDB=userService.getUsers();
		response.setResponseBody(userFromDB);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value = "/userRole/{userId}", method = RequestMethod.GET)
	public ResponseEntity<Response> getUserRoles(@PathVariable Long userId){
		Response response = new Response();
		List<Role> userRoles=userService.getUserRole(userId);
		response.setResponseBody(userRoles);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value = "/forcepasswordchange", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Response> saveManadatoryResetChanges(@RequestBody User user){

		User loggedinUser = CommonUtil.getCurrentUser();
		User userFrromDB = userService.getUser(user.getUserId());
		loggedinUser.setPassword(userFrromDB.getPassword());
		Map<String, Boolean> result = userService.forcePasswordChange(user);
		Response response=new Response();
		response.setResponseBody(result);
		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Response> saveUser(@RequestBody User user){

		Map<String, Object> result = userService.saveUser(user);
		user = (User) result.get("user");
		Response response=new Response();
		response.setResponseBody(result);
		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Response> updateUser(@RequestBody User user){

		Map<String, Object> result = userService.saveUser(user);
		user = (User) result.get("user");
		if(user!=null){
		List<UserPrivilege> userPrivileges = userService.getAllUserPrivileges(user);
		user.setPrivileges(userPrivileges);
		}
		Response response=new Response();
		response.setResponseBody(result);
		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}	
//	@RequestMapping(value = "/userprivileges/{userId}", method = RequestMethod.GET)
//	public ResponseEntity<Response> getUserPrivileges(@PathVariable Long userId){
//		Response response = new Response();
//		List<Privilege> userPrivileges=userManager.getUserPrivileges(userId);
//		response.setResponseBody(userPrivileges);
//		return new ResponseEntity<Response>(response,HttpStatus.OK);
//	}

	@RequestMapping(value = "/userwithprivileges/{userId}", method = RequestMethod.GET)
	public ResponseEntity<Response> getUserWithUserPrivileges(@PathVariable Long userId){
		Response response = new Response();

		User user = userService.getUser(userId);
		List<UserPrivilege> userPrivileges = userService.getAllUserPrivileges(user);
		user.setPrivileges(userPrivileges);
		response.setResponseBody(user);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value = "/userprivileges/", method = RequestMethod.GET)
	public ResponseEntity<Response> getUserPrivileges(){
		Response response = new Response();
		List<UserPrivilege> userPrivileges = userService.getUserPrivilegesSet();
		response.setResponseBody(userPrivileges);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value ="/search/", method = RequestMethod.POST)
	public ResponseEntity<Response> getUserByCriteria(@RequestBody SearchCriteria searchCriteria) {
		Response response=new Response();
		List<User> userByCriteria = userService.getUserByCriteria(searchCriteria);
		response.setResponseBody(userByCriteria);
		

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value ="/resetpassword/", method = RequestMethod.POST)
	public ResponseEntity<Response> resetPassword(@RequestBody User user) {
		Response response=new Response();
		userService.resetPassword(user);
		User userFromDB = userService.getUser(user.getUserId());
		response.setResponseBody(userFromDB);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
}
