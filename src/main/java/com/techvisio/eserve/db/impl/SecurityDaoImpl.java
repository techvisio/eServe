package com.techvisio.eserve.db.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Privilege;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.db.SecurityDao;

@Transactional
@Component
public class SecurityDaoImpl extends BaseDao implements SecurityDao {


	@Override
	public User authenticateNgetUser(Authentication authentication) {
		String user=authentication.getName();
		String password=authentication.getCredentials().toString();
		String queryString = "FROM User u WHERE (u.userName = '" + user+"' or u.emailId = '"+ user +"') and password='"+password+"'";
		Query query = getEntityManager().createQuery(queryString);
		List<User> result = (List<User>) query.getResultList();
		if(result!=null){
			return result.get(0);
		}
		
		return null;
	}

	@Override
	public Set<Privilege> getUserPrivilege(Long userId) {
		String queryString=" from Privilege as p , Role as r , User as u  where u.userId = "+userId;
		Query query=getEntityManager().createQuery(queryString);
		List<Privilege> result= query.getResultList();
		Set<Privilege> privileges = new HashSet<Privilege>(result);
		return privileges;
	}

}
