package com.techvisio.eserve.manager.impl;

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
import com.techvisio.eserve.beans.UserPrivilege;
import com.techvisio.eserve.db.SecurityDao;
import com.techvisio.eserve.db.UserDao;
import com.techvisio.eserve.manager.UserManager;
import com.techvisio.eserve.util.CommonUtil;
@Component
@Transactional
public class UserManagerImpl implements UserManager{

	@Autowired
	UserDao userDao;

	@Override
	public User getUser(Long userId) {
		User user = userDao.getUser(userId);
		return user;
	}

	@Override
	public User getCurrentPassword(Long userId){
		User user = userDao.getUser(userId);
		return user;
	}

	@Override
	public List<User> getUsers(){
		List<User> users = userDao.getUsers();
		return users;
	}

	@Override
	public User getUserWithUserPrivileges(Long userId){

		User user = userDao.getUserWithUserPrivileges(userId);
		return user;
	}

	
	@Override
	public List<User> getUserByCriteria(SearchCriteria searchCriteria){
		List<User> users = userDao.getUserByCriteria(searchCriteria);
		return users;
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

//	@Override
//	public List<Privilege> getUserPrivileges(Long userId) {
//		List<Privilege> privileges = userDao.getUserPrivileges(userId);
//		return privileges;
//	}

	@Override
	public void saveUserPrivileges(List<Privilege> privileges) {

		Iterator<Privilege> itr = privileges.iterator();


//		while (itr.hasNext()) {
//			Privilege privilege=itr.next();
//			if (!privilege.isGranted()) {
//				itr.remove();
//			}
//		}
		userDao.saveUserPrivileges(privileges);
	}

	@Override
	public Map<String, Object> saveUser(User user) {
		Map<String,Object> result=new HashMap<String, Object>();

		if(user.getUserId()==null){
			
			boolean isUserExists = userDao.isUserExists(user);
			if(isUserExists){
				result.put("existingUser", null);
				result.put("success", false);
				return result;
			}
		}
		
            user.setClient(CommonUtil.getCurrentClient());		
    		Iterator<UserPrivilege> itr = user.getPrivileges().iterator();

    		while (itr.hasNext()) {
    			UserPrivilege privilege=itr.next();
    			if (!privilege.isGranted()) {
    				itr.remove();
    			}
    		}
            
            Long userId = userDao.saveUser(user);	
			
			User UserFromDB = userDao.getUser(userId);
			result.put("user", UserFromDB);
			result.put("success", true);
			return result;
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

	@Override
	public List<UserPrivilege> getAllUserPrivileges(User user) {
		
		List<UserPrivilege> userPrivileges =  userDao.getAllUserPrivileges(user);
		return userPrivileges;
	}

	@Override
	public List<UserPrivilege> getUserPrivilegesSet() {
		List<UserPrivilege> userPrivileges =  userDao.getUserPrivilegesSet();
		return userPrivileges;
	}

}
