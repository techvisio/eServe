package com.techvisio.eserve.db.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Privilege;
import com.techvisio.eserve.beans.Role;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.SecurityQuestion;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.beans.UserPrivilege;
import com.techvisio.eserve.db.UserDao;
import com.techvisio.eserve.manager.CacheManager;
import com.techvisio.eserve.util.CommonUtil;

@Component
public class UserDaoImpl extends BaseDao implements UserDao{

	@Autowired
	CacheManager cacheManager;


	@Override
	public Long saveUser(User user){
		if(user.getUserId()==null){
			user.setForcePasswordChange(true);
			getEntityManager().persist(user);
		}
		else{
			deleteUserPrivilegeExclusion(user.getPrivileges(), user.getUserId());
			getEntityManager().merge(user);
			//saveUserPrivilege(user.getPrivileges(), user.getUserId());
		}

		//Explicitly flush session
		getEntityManager().flush();
		return user.getUserId();
	}

	@Override
	public User getUser(Long userId) {
		String queryString="FROM User u WHERE u.userId = "+userId;
		Query query= getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<User> result= (List<User>)query.getResultList();
		if(result != null && result.size()>0){
			return result.get(0);
		}
		return null;
	}

	@Override
	public User getCurrentPassword(Long userId) {
		String queryString="FROM User u WHERE u.userId = "+userId;
		Query query= getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<User> result= (List<User>)query.getResultList();
		if(result != null && result.size()>0){
			return result.get(0);
		}
		return null;
	}

	@Override
	public List<User> getUsers() {
		String queryString="FROM User";
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<User> result= (List<User>)query.getResultList();
		return result;
	}

	@Override
	public List<User> getUserByCriteria(SearchCriteria searchCriteria) {

		String queryString="from User WHERE client.clientId = coalesce(:clientId, client.clientId) and userId = coalesce(:userId, userId) and department.departmentId = coalesce(:departmentId, department.departmentId) and designation.designationId = coalesce(:designationId, designation.designationId) and lower(emailId) = coalesce(:emailId, emailId) and  lower(firstName) LIKE :firstName and lower(lastName) Like :lastName and lower(userName) Like :userName";
		Query query=getEntityManager().createQuery(queryString);

		String firstName = StringUtils.isEmpty(searchCriteria.getFirstName())?"":searchCriteria.getFirstName().toLowerCase();
		String lastName = StringUtils.isEmpty(searchCriteria.getLastName())?"":searchCriteria.getLastName().toLowerCase();
		String userName = StringUtils.isEmpty(searchCriteria.getUserName())?"":searchCriteria.getUserName().toLowerCase();
		String emailId = StringUtils.isEmpty(searchCriteria.getEmailId())?null:searchCriteria.getEmailId().toLowerCase();
		query.setParameter("firstName", "%"+firstName+"%");
		query.setParameter("lastName", "%"+lastName+"%");
		query.setParameter("userName", "%"+userName+"%");
		query.setParameter("userId", searchCriteria.getUserId());
		query.setParameter("clientId", searchCriteria.getClientId());
		query.setParameter("departmentId", searchCriteria.getDepartmentId());
		query.setParameter("designationId", searchCriteria.getDesignationId());
		query.setParameter("emailId", emailId);

		@SuppressWarnings("unchecked")
		List<User> result= (List<User>)query.getResultList();
		return result;

	}

	@Override
	public boolean isUserExists(User user) {

		String queryString="from User WHERE lower(emailId) = coalesce(:emailId, emailId) or lower(userName) = coalesce(:userName, userName)";
		Query query= getEntityManager().createQuery(queryString);

		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.setUserName(user.getUserName());
		searchCriteria.setEmailId(user.getEmailId());
		String emailId = StringUtils.isEmpty(searchCriteria.getEmailId())?null:searchCriteria.getEmailId().toLowerCase();
		String userName = StringUtils.isEmpty(searchCriteria.getUserName())?null:searchCriteria.getUserName().toLowerCase();

		query.setParameter("emailId", emailId);
		query.setParameter("userName", userName);

		@SuppressWarnings("unchecked")
		List<User> result= (List<User>)query.getResultList();
		if(result != null && result.size()>0){
			User existingUser = result.get(0);
			if(existingUser != null){
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Role> getUserRole(Long userId) {
		String queryString="select Role_Id,Role_Name,Description,created_By,created_on,updated_by,updated_on,case when exists(select 1 from tb_user_role ur where ur.role_id=rm.role_id and ur.user_id="+userId+") then 1 else 0 end as selected from tb_role_master rm" ;
		Query query=getEntityManager().createNativeQuery(queryString,Role.class);
		List<Role> userRoles = (List<Role>)query.getResultList();

		return userRoles;
	}

	@Override
	public void saveSecurityQuestion(SecurityQuestion securityQuestion){
		getEntityManager().merge(securityQuestion);
	}	

	@Override
	public SecurityQuestion getSecurityQuestion(Long questionId) {
		String queryString="FROM SecurityQuestion u WHERE u.securityQustnId = "+questionId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<SecurityQuestion> result= (List<SecurityQuestion>)query.getResultList();
		if(result != null && result.size()>0){
			return result.get(0);
		}
		return null;
	}

	//	@Override
	//	public List<Privilege> getPrivileges() {
	//		String queryString="FROM Privilege";
	//		Query query=getCurrentSession().createQuery(queryString);
	//		@SuppressWarnings("unchecked")
	//		List<Privilege> result= (List<Privilege>)query.list();
	//		return result;	}


	//	@Override
	//	public List<Privilege> getUserPrivileges(Long userId) {
	//		String queryString="select "+userId + " user_id,Privilege_Id,client_Id,Created_By,Created_On,Updated_By,Updated_On,Description,Privilege,Type, case when exists (select 1 from tb_user_privilege up where up.privilege_id=pm.privilege_id and up.User_Id="+userId+") then 1 else 0 end is_Granted from tb_privilege pm" ;
	//		SQLQuery query=getCurrentSession().createSQLQuery(queryString);
	//		query.addEntity(Privilege.class);
	//		List<Privilege> userPrivileges = (List<Privilege>)query.list();
	//
	//		return userPrivileges;
	//	}

	@Override
	public void saveUserPrivileges(List<Privilege> privileges) {

		if(privileges!=null && privileges.size()>0){
			for(Privilege privilege : privileges){
				savePrivilege(privilege);
			}
		}
	}

	@Override
	public void savePrivilege(Privilege privilege) {
		getEntityManager().merge(privilege);
	}	

	@Override
	public List<UserPrivilege> getAllUserPrivileges(User user){

		List<UserPrivilege> result = new ArrayList<UserPrivilege>();
		Map<Long, UserPrivilege> userPrivilegeMap = new HashMap<Long, UserPrivilege>();		
		List<UserPrivilege> existingUserPrivileges = user.getPrivileges();

		//creating map for existing privileges
		for(UserPrivilege userPrivilege : existingUserPrivileges){
			userPrivilegeMap.put(userPrivilege.getPrivilege().getPrivilegeId(), userPrivilege);
		}
		List<Privilege> allPrivileges = cacheManager.getPrivileges(user.getClient().getClientId());

		for(Privilege privilege : allPrivileges){

			UserPrivilege userPrivilege = (UserPrivilege) userPrivilegeMap.get(privilege.getPrivilegeId());

			if(userPrivilege==null){
				userPrivilege = new UserPrivilege();
				userPrivilege.setClient(user.getClient());
				userPrivilege.setPrivilege(privilege);
				userPrivilege.setUserId(user.getUserId());
			}
			else{
				userPrivilege.setGranted(true);
			}
			result.add(userPrivilege);				
		}
		return result;
	}

	@Override
	public User getUserWithUserPrivileges(Long userId){

		User user = getUser(userId);
		List<UserPrivilege> userPrivileges = getAllUserPrivileges(user);
		user.setPrivileges(userPrivileges);

		return user;
	}

	@Override
	public List<UserPrivilege> getUserPrivilegesSet(){

		List<UserPrivilege> result = new ArrayList<UserPrivilege>();
		List<Privilege> allPrivileges = cacheManager.getPrivileges(CommonUtil.getCurrentClient().getClientId());

		for(Privilege privilege : allPrivileges){

			UserPrivilege userPrivilege = new UserPrivilege();
			userPrivilege.setClient(CommonUtil.getCurrentClient());
			userPrivilege.setPrivilege(privilege);
			result.add(userPrivilege);	
		}

		return result;
	}

	@Override
	public void saveUserPrivilege(List<UserPrivilege> userPrivileges, Long userId) {

		if(userPrivileges!=null && userPrivileges.size()>0){
			deleteUserPrivilegeExclusion(userPrivileges, userId);
			for(UserPrivilege userPrivilege : userPrivileges){
				saveUserPrivilege(userPrivilege);
			}
		}
	}

	@Override
	public void saveUserPrivilege(UserPrivilege userPrivilege) {

		if(userPrivilege.getUserPrivilegeId() == null){
			getEntityManager().persist(userPrivilege);
		}		
	}


	public void deleteUserPrivilegeExclusion(
			List<UserPrivilege> userPrivileges, Long userId) {

		List<Long> userPrivilegeId = new ArrayList<Long>();
		if (userPrivileges != null) {
			for (UserPrivilege userPrivilege : userPrivileges) {
				if(userPrivilege.getUserPrivilegeId() != null){
					userPrivilegeId.add(userPrivilege.getUserPrivilegeId());
				}
			}

			if (userPrivilegeId.size() == 0) {
				userPrivilegeId.add(-1L);
			}
		}

		String deleteQuery = "Delete from tb_user_privilege where USER_ID =:USER_ID and USER_PRVLG_ID not in :USER_PRVLG_ID";

		Query query=(Query) getEntityManager().createNativeQuery(deleteQuery).setParameter("USER_ID", userId).setParameter("USER_PRVLG_ID", userPrivilegeId);
		query.executeUpdate();	}

}
