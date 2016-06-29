package com.techvisio.eserve.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Privilege;
import com.techvisio.eserve.beans.Role;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.SearchResultData;
import com.techvisio.eserve.beans.SecurityQuestion;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.beans.UserPrivilege;
import com.techvisio.eserve.exception.EntityLockedException;
import com.techvisio.eserve.manager.UserManager;
import com.techvisio.eserve.service.EntityLockService;
import com.techvisio.eserve.service.UserService;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;

@Component
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	UserManager userManager;

	@Autowired
	EntityLockService entityLockService;

	@Override
	public Map<String, Object> saveUser(User user) {
		String userName = CommonUtil.getCurrentUser().getUserName();

		if(user.getUserId()!=null){
			boolean isEntityLocked=entityLockService.isEntityLocked(user.getUserId(), AppConstants.entityType.USER.toString(), userName);
			if(isEntityLocked){
				throw new EntityLockedException("Current user does not hold lock for this customer");
			}
		}

		Map<String, Object> userMap = userManager.saveUser(user);
		return userMap;
	}

	@Override
	public User getUser(Long userId) {
		User user = userManager.getUser(userId);
		return user;
	}

	@Override
	public List<Role> getUserRole(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveSecurityQuestion(SecurityQuestion securityQuestion) {
		userManager.saveSecurityQuestion(securityQuestion);
	}

	@Override
	public SecurityQuestion getSecurityQuestion(Long questionId) {
		SecurityQuestion securityQuestion = userManager.getSecurityQuestion(questionId);
		return securityQuestion;
	}

	@Override
	public List<User> getUsers() {
		List<User> users = userManager.getUsers();
		return users;
	}

	@Override
	public void saveUserPrivileges(List<Privilege> privileges) {
		userManager.saveUserPrivileges(privileges);
	}

	@Override
	public Map<String, Boolean> forcePasswordChange(User user) {
		Map<String, Boolean> userMap = userManager.forcePasswordChange(user);
		return userMap;
	}

	@Override
	public SearchResultData getUserByCriteria(SearchCriteria searchCriteria) {
		SearchResultData users = userManager.getUserByCriteria(searchCriteria);
		return users;
	}

	@Override
	public User getCurrentPassword(Long userId) {
		User user = userManager.getCurrentPassword(userId);
		return user;
	}

	@Override
	public User getUserWithUserPrivileges(Long userId) {
		User user = userManager.getUserWithUserPrivileges(userId);
		return user;
	}

	@Override
	public List<UserPrivilege> getAllUserPrivileges(User user) {
		List<UserPrivilege> privileges = userManager.getAllUserPrivileges(user);
		return privileges;
	}

	@Override
	public List<UserPrivilege> getUserPrivilegesSet() {
		List<UserPrivilege> userPrivileges = userManager.getUserPrivilegesSet();
		return userPrivileges;
	}

	@Override
	public void resetPassword(User user) {
		userManager.resetPassword(user);		
	}

	@Override
	public User getUserByUserName(String userName) {
		User user = userManager.getUserByUserName(userName);
		return user;
	}

}
