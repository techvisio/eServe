package com.techvisio.eserve.workflow.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.techvisio.eserve.beans.Privilege;
import com.techvisio.eserve.beans.Role;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.SecurityQuestion;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.manager.UserManager;
import com.techvisio.eserve.util.CommonUtil;
import com.techvisio.eserve.workflow.UserWorkflowManager;

@Component
@Transactional
public class UserWorkflowManagerImpl implements UserWorkflowManager {

	@Autowired
	UserManager userManager;

	@Override
	public void saveUser(User user) {
		//saveUserPrivileges(user.getPrivileges());
		userManager.saveUser(user);
	}

	@Override
	public User getUser(Long userId) {
		User user = userManager.getUser(userId);
		return user;
	}

	@Override
	public List<User> getUsers(){
		List<User> users = userManager.getUsers();
		return users;
	}

	@Override
	public List<User> getUserByCriteria(SearchCriteria searchCriteria){
		List<User> users = userManager.getUserByCriteria(searchCriteria);
		return users;
	}

	@Override
	public User verifyUserNameAndEmialId(SearchCriteria searchCriteria){
		
		User user = userManager.verifyUserNameAndEmialId(searchCriteria);
		return user;
	}
	@Override
	public List<Role> getUserRole(Long userId) {

		List<Role> userRoles = userManager.getUserRole(userId);
		return userRoles;
	}

	@Override
	public void saveSecurityQuestion(SecurityQuestion securityQuestion){
		userManager.saveSecurityQuestion(securityQuestion);	
	}

	@Override
	public SecurityQuestion getSecurityQuestion(Long questionId){
		SecurityQuestion securityQuestion = userManager.getSecurityQuestion(questionId);
		return securityQuestion;
	}

	@Override
	public List<Privilege> getUserPrivileges(Long userId) {
		List<Privilege> privileges = userManager.getUserPrivileges(userId);
		return privileges;
	}

	@Override
	public void saveUserPrivileges(List<Privilege> privileges) {

		Iterator<Privilege> itr = privileges.iterator();


		while (itr.hasNext()) {
			Privilege privilege=itr.next();
			if (!privilege.isGranted()) {
				itr.remove();
			}
		}
		userManager.saveUserPrivileges(privileges);
	}

	@Override
	public Map<String, Boolean> forcePasswordChange(User user) {
		Map<String,Boolean> result=new HashMap<String, Boolean>();

		User currentLoginUser = CommonUtil.getCurrentUser();

		if(!java.util.Arrays.equals(user.getPassword(), currentLoginUser.getPassword())){
			result.put("passwordMatch", false);
			result.put("success", false);
		}
		else
		{
			user.setPassword(user.getNewPassword());
			user.setForcePasswordChange(false);
			saveUser(user);

			result.put("passwordMatch", true);
			result.put("success", true);
		}
		return result;
	}
}
