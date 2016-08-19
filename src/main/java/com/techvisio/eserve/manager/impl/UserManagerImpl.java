package com.techvisio.eserve.manager.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Config;
import com.techvisio.eserve.beans.Privilege;
import com.techvisio.eserve.beans.Role;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.SearchResultData;
import com.techvisio.eserve.beans.SecurityQuestion;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.beans.UserPrivilege;
import com.techvisio.eserve.db.CacheDao;
import com.techvisio.eserve.db.UserDao;
import com.techvisio.eserve.exception.DuplicateEntityException;
import com.techvisio.eserve.manager.UserManager;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;
@Component
public class UserManagerImpl implements UserManager{

	@Autowired
	UserDao userDao;

	@Autowired
	CacheDao cacheDao;

	@Autowired
	ClientConfiguration configPreferences;


	@Override
	public User getUser(Long userId) {
		User user = userDao.getUser(userId);
		return user;
	}

	@Override
	public User getCurrentPassword(Long userId){
		User user = userDao.getCurrentPassword(userId);
		return user;
	}

	@Override
	public List<User> getUsers(Long clientId){
		List<User> users = userDao.getUsers(clientId);
		return users;
	}

	@Override
	public User getUserWithUserPrivileges(Long userId){

		User user = userDao.getUserWithUserPrivileges(userId);
		return user;
	}



	@Override
	public SearchResultData getUserByCriteria(SearchCriteria searchCriteria){
		SearchResultData users = userDao.getUserByCriteria(searchCriteria);
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
	public void resetPassword(User user){

		List<Config> defaultValues = cacheDao.getDefalutValues(CommonUtil.getCurrentClient().getClientId());
		for(Config config : defaultValues){
			if(config.getProperty().equalsIgnoreCase(AppConstants.DEFAULT_PASSWORD)){
				user.setPassword(config.getValue().toCharArray());
			}
		}
		user.setForcePasswordChange(true);

		List<UserPrivilege> userPrivileges = user.getPrivileges();
		for(UserPrivilege privilege : userPrivileges){
			privilege.setGranted(true);
		}
		userDao.saveUser(user);
	}

	@Override
	public Long saveUser(User user, Long clientId) {

		if(user.getUserId()==null){
			User userByEmailId = userDao.getEmailId(user.getEmailId(), clientId);
			if(userByEmailId!=null){
				throw new DuplicateEntityException("This Email Id is Already Exists, Choose Different EmailId");
			}
			User userByUserName = userDao.getUserByUserName(user.getUserName(), clientId);
			if(userByUserName!=null){
				throw new DuplicateEntityException("This User Name is Already Exists, Choose Different User Name");
			}
		}

		else{
			User userByEmailId = userDao.getEmailId(user.getEmailId(), clientId);
			if(userByEmailId!=null && user.getUserId()!= userByEmailId.getUserId()){
				throw new DuplicateEntityException("This Email Id Already Exists, Choose Different EmailId");
			}
			User userByUserName = userDao.getUserByUserName(user.getUserName(), clientId);
			if(userByUserName!=null && user.getUserId()!= userByUserName.getUserId()){
				throw new DuplicateEntityException("This User Name Already Exists, Choose Different User Name");
			}
		}
		user.setClient(CommonUtil.getCurrentClient());		

		if(user.getUserId()==null){

			user.setPassword(configPreferences.getDefaultPassword(CommonUtil.getCurrentClient().getClientId()).toCharArray());
		}
		Iterator<UserPrivilege> itr = user.getPrivileges().iterator();

		while (itr.hasNext()) {
			UserPrivilege privilege=itr.next();
			if (!privilege.isGranted()) {
				itr.remove();
			}
		}

		Long userId = userDao.saveUser(user);	
		return userId;
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
			List<UserPrivilege> userPrivileges = user.getPrivileges();
			for(UserPrivilege privilege : userPrivileges){
				privilege.setGranted(true);
			}
			userDao.saveUser(user);

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

	@Override
	public User getUserByUserName(String userName, Long clientId){
		User user = userDao.getUserByUserName(userName, clientId); 
		return user;
	}

	@Override
	public User getEmailId(String EmailId, Long clientId) {
		User user = userDao.getEmailId(EmailId, clientId);
		return user;
	}

}
