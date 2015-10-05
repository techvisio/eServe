package com.techvisio.eserve.db.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;

import com.techvisio.eserve.beans.Role;
import com.techvisio.eserve.beans.SecurityQuestion;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.db.UserDao;

public class UserDaoImpl extends BaseDao implements UserDao{

	@Override
	public void addUser(User user){
		getCurrentSession().merge(user);
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
	public List<Role> getUserRole(Long userId) {
		String queryString="select Role_Id,Role_Name,Description,created_By,created_on,updated_by,updated_on,case when exists(select 1 from user_role ur where ur.role_id=rm.role_id and ur.user_id="+userId+") then 1 else 0 end as selected from role_master rm" ;
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

}
