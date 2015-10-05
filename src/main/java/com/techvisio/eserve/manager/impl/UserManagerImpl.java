package com.techvisio.eserve.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.techvisio.eserve.beans.Role;
import com.techvisio.eserve.beans.SecurityQuestion;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.db.SecurityDao;
import com.techvisio.eserve.db.UserDao;
import com.techvisio.eserve.manager.UserManager;

public class UserManagerImpl implements UserManager{

	
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
