package com.techvisio.eserve.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Privilege;
import com.techvisio.eserve.beans.Role;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.SecurityQuestion;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.db.SecurityDao;
import com.techvisio.eserve.db.UserDao;
import com.techvisio.eserve.manager.UserManager;
@Component
public class UserManagerImpl implements UserManager{

	@Autowired
	UserDao userDao;
	
	@Override
	public void saveUser(User user) {
      userDao.saveUser(user);		
	}

	@Override
	public User getUser(Long userId) {
		User user = userDao.getUser(userId);
		return user;
	}

	@Override
	public List<User> getUsers(){
		List<User> users = userDao.getUsers();
		return users;
	}

	@Override
	public List<User> getUserByCriteria(SearchCriteria searchCriteria){
		
		List<User> users = userDao.getUserByCriteria(searchCriteria);
		return users;
	}

	@Override
	public User verifyUserNameAndEmialId(SearchCriteria searchCriteria){
		
		User user = userDao.verifyUserNameAndEmialId(searchCriteria);
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

	@Override
	public List<Privilege> getUserPrivileges(Long userId) {
		List<Privilege> privileges = userDao.getUserPrivileges(userId);
		return privileges;
	}

	@Override
	public void saveUserPrivileges(List<Privilege> privileges) {
		userDao.saveUserPrivileges(privileges);
	}
}
