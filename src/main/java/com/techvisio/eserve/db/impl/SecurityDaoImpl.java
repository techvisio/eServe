package com.techvisio.eserve.db.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
		String queryString = "FROM User u WHERE u.name = '" + user+"' and password='"+password+"' and active=1";
		Query query = getCurrentSession().createQuery(queryString);
		User result = (User) query.uniqueResult();
		return result;
	}

	@Override
	public Set<Privilege> getUserPrivilege(Long userId) {
		String queryString=" from Privilege as p , Role as r , User as u  where u.userId = "+userId;
		Query query=getCurrentSession().createQuery(queryString);
		List<Privilege> result= query.list();
		Set<Privilege> privileges = new HashSet<Privilege>(result);
		return privileges;
	}

}
