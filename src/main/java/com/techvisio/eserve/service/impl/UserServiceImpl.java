package com.techvisio.eserve.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.EntityLocks;
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
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		if(user.getUserId()!=null){
			boolean isEntityLocked=entityLockService.isEntityLocked(user.getUserId(), AppConstants.EntityType.USER.toString(), userName);
			if(!isEntityLocked){
				throw new EntityLockedException("Current user does not hold lock for this user");
			}
		}

		Map<String, Object> userMap = userManager.saveUser(user,clientId);
		entityLockService.unlockEntity("USER", user.getUserId());
		return userMap;
	}

	@Override
	public User getUser(Long userId) {
		User user = userManager.getUser(userId);
		EntityLocks entityLocks  = entityLockService.getEntity(userId, AppConstants.EntityType.USER.toString());
		if(entityLocks!=null){
			user.setEdited(true);
		}
		else{
			user.setEdited(false);
		}
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
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		List<User> users = userManager.getUsers(clientId);
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
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		User user = userManager.getUserByUserName(userName, clientId);
		return user;
	}

	@Override
	public User getEmailId(String emailId) {
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		User user = userManager.getEmailId(emailId, clientId);
		return user;
	}

	@Override
	public User getUserName(String userName)  {
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		User user = userManager.getUserName(userName, clientId);
		return user;
	}

}
