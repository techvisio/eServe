package com.techvisio.eserve.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Role;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.SearchResultData;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.beans.UserPrivilege;
import com.techvisio.eserve.icc.UserServiceICC;
import com.techvisio.eserve.manager.UserManager;
import com.techvisio.eserve.service.UserService;
import com.techvisio.eserve.util.CommonUtil;
import com.techvisio.eserve.util.ServiceLocator;

@Component
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	UserManager userManager;

	@Autowired
	ServiceLocator servicelocator;

	@Override
	public Long saveUser(User user) {
		UserServiceICC userServiceICC=servicelocator.getService(UserServiceICC.class);
		user = userServiceICC.preUserSave(user);
		Long userId = userManager.saveUser(user);
		user = userServiceICC.postUserSave(user);
		return userId;
	}

	@Override
	public User getUser(Long userId) {
		UserServiceICC userServiceICC=servicelocator.getService(UserServiceICC.class);
		userServiceICC.preGetUser();
		User user = userManager.getUser(userId);
		user = userServiceICC.postGetUser(user);
		return user;
	}

	@Override
	public List<Role> getUserRole(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}
//
//	@Override
//	public void saveSecurityQuestion(SecurityQuestion securityQuestion) {
//		userManager.saveSecurityQuestion(securityQuestion);
//	}

//	@Override
//	public SecurityQuestion getSecurityQuestion(Long questionId) {
//		SecurityQuestion securityQuestion = userManager.getSecurityQuestion(questionId);
//		return securityQuestion;
//	}

	@Override
	public List<User> getUsers() {
		UserServiceICC userServiceICC=servicelocator.getService(UserServiceICC.class);
		userServiceICC.preRetrieveAllUsers();
		List<User> users = userManager.getUsers();
		users = userServiceICC.postRetrieveAllUsers(users);
		return users;
	}

//
//	@Override
//	public void saveUserPrivileges(List<Privilege> privileges) {
//		userManager.saveUserPrivileges(privileges);
//	}

	@Override
	public Map<String, Boolean> forcePasswordChange(User user) {
		UserServiceICC userServiceICC=servicelocator.getService(UserServiceICC.class);
		user=userServiceICC.preForcePasswordChange(user);
		Map<String, Boolean> userMap = userManager.forcePasswordChange(user);
		user=userServiceICC.postForcePasswordChange(user);
		return userMap;
	}

	@Override
	public SearchResultData getUserByCriteria(SearchCriteria searchCriteria) {
		SearchResultData users = userManager.getUserByCriteria(searchCriteria);
		return users;
	}

	@Override
	public User getCurrentPassword(Long userId) {
		UserServiceICC userServiceICC=servicelocator.getService(UserServiceICC.class);
		userServiceICC.preGetCurrentPassword();
		User user = userManager.getCurrentPassword(userId);
		user = userServiceICC.postGetCurrentPassword(user);
		return user;
	}

	@Override
	public User getUserWithUserPrivileges(Long userId) {
		UserServiceICC userServiceICC=servicelocator.getService(UserServiceICC.class);
		userServiceICC.preGetUserWithUserPrivileges();
		User user = userManager.getUserWithUserPrivileges(userId);
		user = userServiceICC.postGetUserWithUserPrivileges(user);
		return user;
	}

	@Override
	public List<UserPrivilege> getAllUserPrivileges(User user) {
		List<UserPrivilege> privileges = userManager.getAllUserPrivileges(user);
		return privileges;
	}

	@Override
	public List<UserPrivilege> getUserPrivilegesSet() {
		UserServiceICC userServiceICC=servicelocator.getService(UserServiceICC.class);
		userServiceICC.preGetUserPrivilegesSet();
		List<UserPrivilege> userPrivileges = userManager.getUserPrivilegesSet();
		userPrivileges = userServiceICC.postGetUserPrivilegesSet(userPrivileges);
		return userPrivileges;
	}

	@Override
	public void resetPassword(User user) {
		UserServiceICC userServiceICC=servicelocator.getService(UserServiceICC.class);
		user=userServiceICC.preResetPassword(user);
		userManager.resetPassword(user);		
		user = userServiceICC.postResetPassword(user);
	}

	@Override
	public User getUserByUserName(String userName) {
		UserServiceICC userServiceICC=servicelocator.getService(UserServiceICC.class);
		userServiceICC.preGetUserByUserName();
		User user = userManager.getUserByUserName(userName);
		user = userServiceICC.postGetUserByUserName(user);
		return user;
	}

	@Override
	public User getUserByEmailId(String emailId) {
		UserServiceICC userServiceICC=servicelocator.getService(UserServiceICC.class);
		userServiceICC.preGetUserByEmailId();
		User user = userManager.getUserByEmailId(emailId);
		user = userServiceICC.postGetUserByEmailId(user);
		return user;
	}

	@Override
	public User getUserForEdit(Long userId) {
		UserServiceICC userServiceICC=servicelocator.getService(UserServiceICC.class);
		userServiceICC.preGetUserforEdit(userId);
		User user=userManager.getUser(userId);
		user=userServiceICC.postGetUserforEdit(user);
		return user;
	}

	@Override
	public User releaseUserEntityLock(Long userId) {
		UserServiceICC userServiceICC=servicelocator.getService(UserServiceICC.class);
		userServiceICC.preReleaseUserEntityLock();
		User user = userManager.getUser(userId);
		user=userServiceICC.postReleaseUserEntityLock(user);
		return user;
	}
}
