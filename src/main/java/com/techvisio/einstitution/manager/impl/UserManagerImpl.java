package com.techvisio.einstitution.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.techvisio.einstitution.db.SecurityDao;
import com.techvisio.einstitution.db.UserDao;
import com.techvisio.einstitution.manager.UserManager;
import com.techvisio.eserve.beans.Role;
import com.techvisio.eserve.beans.SecurityQuestion;
import com.techvisio.eserve.beans.User;

public class UserManagerImpl implements UserManager{

	@Autowired
	UserDao userDao;
	
	@Override
	public void addUser(User user) {
      userDao.addUser(user);		
	}

	@Override
	public User getUser(Long userId) {
		User user = userDao.getUser(userId);
		return user;
	}

	@Override
	public List<Role> getUserRole(Long userId) {
		
		List<Role> userRoles = userDao.getUserRole(userId);
		return userRoles;
	}
	
	@Override
	public void saveSecurityQuestion(SecurityQuestion securityQuestion){
	    userDao.saveSecurityQuestion(securityQuestion);	
	}
	
	@Override
	public SecurityQuestion getSecurityQuestion(Long questionId){
		SecurityQuestion securityQuestion = userDao.getSecurityQuestion(questionId);
		return securityQuestion;
	}
}
