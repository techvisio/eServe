package com.techvisio.eserve.db.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.techvisio.eserve.beans.Privilege;
import com.techvisio.eserve.beans.Role;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.SecurityQuestion;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.db.UserDao;
@Component
@Transactional
public class UserDaoImpl extends BaseDao implements UserDao{

	@Override
	public void saveUser(User user){
		if(user.getUserId()==null){
			user.setForcePasswordChange(true);
			getCurrentSession().persist(user);
		}
		else{
		getCurrentSession().update(user);
		}
	}

	@Override
	public User getUser(Long userId) {
		String queryString="FROM User u WHERE u.userId = "+userId;
		Query query=getCurrentSession().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<User> result= (List<User>)query.list();
		if(result != null && result.size()>0){
			return result.get(0);
		}
		return null;
	}

	@Override
	public List<User> getUsers() {
		String queryString="FROM User";
		Query query=getCurrentSession().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<User> result= (List<User>)query.list();
		return result;
	}

	@Override
	public List<User> getUserByCriteria(SearchCriteria searchCriteria) {

		String queryString="from User WHERE clientId = coalesce(:clientId, clientId) and userId = coalesce(:userId, userId) and department.departmentId = coalesce(:departmentId, department.departmentId) and designation.designationId = coalesce(:designationId, designation.designationId) and lower(emailId) = coalesce(:emailId, emailId) and  lower(firstName) LIKE :firstName and lower(lastName) Like :lastName and lower(userName) Like :userName";
	    Query query=getCurrentSession().createQuery(queryString);

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
		List<User> result= (List<User>)query.list();
		return result;
		
	}

	@Override
	public User verifyUserNameAndEmialId(SearchCriteria searchCriteria) {
	
		String queryString="from User WHERE lower(emailId) = coalesce(:emailId, emailId) or lower(userName) = coalesce(:userName, userName)";
	    Query query=getCurrentSession().createQuery(queryString);
	    
	    String emailId = StringUtils.isEmpty(searchCriteria.getEmailId())?null:searchCriteria.getEmailId().toLowerCase();
	    String userName = StringUtils.isEmpty(searchCriteria.getUserName())?null:searchCriteria.getUserName().toLowerCase();
	    
	    query.setParameter("emailId", emailId);
	    query.setParameter("userName", userName);
	
	    @SuppressWarnings("unchecked")
		List<User> result= (List<User>)query.list();
		if(result != null && result.size()>0){
			return result.get(0);
		}
		return null;
	}
	
	@Override
	public List<Role> getUserRole(Long userId) {
		String queryString="select Role_Id,Role_Name,Description,created_By,created_on,updated_by,updated_on,case when exists(select 1 from tb_user_role ur where ur.role_id=rm.role_id and ur.user_id="+userId+") then 1 else 0 end as selected from tb_role_master rm" ;
		SQLQuery query=getCurrentSession().createSQLQuery(queryString);
		query.addEntity(Role.class);
		List<Role> userRoles = (List<Role>)query.list();

		return userRoles;
	}

	@Override
	public void saveSecurityQuestion(SecurityQuestion securityQuestion){
		getCurrentSession().merge(securityQuestion);
	}	

	@Override
	public SecurityQuestion getSecurityQuestion(Long questionId) {
		String queryString="FROM SecurityQuestion u WHERE u.securityQustnId = "+questionId;
		Query query=getCurrentSession().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<SecurityQuestion> result= (List<SecurityQuestion>)query.list();
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


	@Override
	public List<Privilege> getUserPrivileges(Long userId) {
		String queryString="select "+userId + " user_id,Privilege_Id,client_Id,Created_By,Created_On,Updated_By,Updated_On,Description,Privilege,Type, case when exists (select 1 from tb_user_privilege up where up.privilege_id=pm.privilege_id and up.User_Id="+userId+") then 1 else 0 end is_Granted from tb_privilege pm" ;
		SQLQuery query=getCurrentSession().createSQLQuery(queryString);
		query.addEntity(Privilege.class);
		List<Privilege> userPrivileges = (List<Privilege>)query.list();

		return userPrivileges;
	}

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
		getCurrentSession().merge(privilege);
	}	

}
