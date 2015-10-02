package com.techvisio.eserve.workflow.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.techvisio.einstitution.manager.UserManager;
import com.techvisio.eserve.beans.Role;
import com.techvisio.eserve.beans.SecurityQuestion;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.workflow.UserWorkflowManager;

@Component
@Transactional
public class UserWorkflowManagerImpl implements UserWorkflowManager {

	@Autowired
	UserManager userManager;

	@Override
	public void addUser(User user) {
         userManager.addUser(user);
	}

	@Override
	public User getUser(Long userId) {
		User user = userManager.getUser(userId);
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
}
