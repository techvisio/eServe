package com.techvisio.eserve.db.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.techvisio.eserve.beans.Department;
import com.techvisio.eserve.beans.Designation;
import com.techvisio.eserve.beans.Privilege;
import com.techvisio.eserve.beans.QuestionMaster;
import com.techvisio.eserve.beans.State;
import com.techvisio.eserve.db.CacheDao;
import com.techvisio.eserve.util.CustomLogger;

@Transactional
@Component
public class CacheDaoImpl extends BaseDao implements CacheDao {
	private static CustomLogger logger = CustomLogger.getLogger(CacheDaoImpl.class);


	public List<State> getState(){
		String queryString="FROM State";
		Query query=getCurrentSession().createQuery(queryString);
		List<State> result= query.list();
		return result;
	}

	@Override
	public List<QuestionMaster> getQuestions() {
		String queryString="FROM QuestionMaster";
		Query query=getCurrentSession().createQuery(queryString);
		List<QuestionMaster> result= query.list();
		return result;
	}

	@Override
	public List<Department> getDepartments() {
		String queryString="FROM Department";
		Query query=getCurrentSession().createQuery(queryString);
		List<Department> result= query.list();
		return result;
	}

	@Override
	public List<Designation> getDesignations() {
		String queryString="FROM Designation";
		Query query=getCurrentSession().createQuery(queryString);
		List<Designation> result= query.list();
		return result;
	}
	
	@Override
	public List<State> getState(Long clientId){
		String queryString="FROM State s where s.clientId = "+ clientId ;
		Query query=getCurrentSession().createQuery(queryString);
		List<State> result= query.list();
		return result;
	}

	@Override
	public List<QuestionMaster> getQuestions(Long clientId) {
		String queryString="FROM QuestionMaster qm where qm.clientId = "+clientId;
		Query query=getCurrentSession().createQuery(queryString);
		List<QuestionMaster> result= query.list();
		return result;
	}

	@Override
	public List<Department> getDepartments(Long clientId) {
		String queryString="FROM Department d where d.clientId = " + clientId;
		Query query=getCurrentSession().createQuery(queryString);
		List<Department> result= query.list();
		return result;
	}

	@Override
	public List<Designation> getDesignations(Long clientId) {
		String queryString="FROM Designation d where d.clientId = " + clientId;
		Query query=getCurrentSession().createQuery(queryString);
		List<Designation> result= query.list();
		return result;
	}

	@Override
	public List<Privilege> getPrivileges(Long clientId) {
		String queryString="FROM Privilege p where p.client.clientId = " + clientId;
		Query query=getCurrentSession().createQuery(queryString);
		List<Privilege> result= query.list();
		return result;
	}
}
