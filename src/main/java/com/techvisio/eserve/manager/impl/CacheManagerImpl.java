package com.techvisio.eserve.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.techvisio.eserve.beans.Department;
import com.techvisio.eserve.beans.Designation;
import com.techvisio.eserve.beans.Privilege;
import com.techvisio.eserve.beans.QuestionMaster;
import com.techvisio.eserve.beans.State;
import com.techvisio.eserve.db.CacheDao;
import com.techvisio.eserve.db.impl.CacheDaoImpl;
import com.techvisio.eserve.manager.CacheManager;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CustomLogger;

@Transactional
@Component
public class CacheManagerImpl implements CacheManager {
	private static CustomLogger logger = CustomLogger.getLogger(CacheManagerImpl.class);
	@Autowired
	CacheDao cacheDao;


	public void setCacheDao(CacheDaoImpl cacheDao) {
		this.cacheDao = cacheDao;
	}

	@Autowired
	public CacheManagerImpl(CacheDao cacheDao){
		this.cacheDao = cacheDao;
		builtEntityListCache();
	}

	public CacheManagerImpl(){
	}

	private static Map<Long, Map<String,List>> clientEntityListMap= new HashMap<>();
	private static Map<String,List> entityListMap=new HashMap<String, List>();
	private static Map<Long, State> stateMap = new HashMap<Long, State>();
	private static Map<String, QuestionMaster> questionMap = new HashMap<String, QuestionMaster>();
	private static Map<Long, Department> departmentMap = new HashMap<Long, Department>();
	private static Map<Long, Designation> designationMap = new HashMap<Long, Designation>();

	public  List<State> getStates(Long clientId) {
		Map<String, List> clientMap = clientEntityListMap.get(clientId);
		if (clientMap != null){
			List data = clientMap.get(AppConstants.STATE); 
			if(data != null){
				return (List<State>)data;
			}
		}
		return new ArrayList<State>();
	}


	public List<QuestionMaster> getQuestion(Long clientId){

		Map<String, List> clientMap = clientEntityListMap.get(clientId);

		if(clientMap != null){
			List data = clientMap.get(AppConstants.QUESTION);
			if(data != null){
				return (List<QuestionMaster>) data;
			}
		}
		return new ArrayList<QuestionMaster>();
	}

	public List<Department> getDepartment(Long clientId){

		Map<String, List> clientMap = clientEntityListMap.get(clientId);

		if(clientMap != null){
			List data = clientMap.get(AppConstants.DEPARTMENT);
			if(data != null){
				return (List<Department>) data;
			}
		}
		return new ArrayList<Department>();
	}


	public List<Designation> getDesignations(Long clientId){

		Map<String, List> clientMap = clientEntityListMap.get(clientId);

		if(clientMap != null){
			List data = clientMap.get(AppConstants.DESIGNATION);
			if(data != null){
				return (List<Designation>) data;
			}
		}
		return new ArrayList<Designation>();
	}

	public void builtEntityListCache(){
		List<State> states=new ArrayList<State>();
		logger.info("{} : built entity list cache work for get state ",this.getClass().getName());
		states=cacheDao.getState();
		entityListMap.put(AppConstants.STATE, states);

		List<QuestionMaster> questions =new ArrayList<QuestionMaster>();
		questions=cacheDao.getQuestions();
		entityListMap.put(AppConstants.QUESTION, questions);

		List<Department> departments =new ArrayList<Department>();
		departments=cacheDao.getDepartments();
		entityListMap.put(AppConstants.DEPARTMENT, departments);

		List<Designation> designations =new ArrayList<Designation>();
		designations=cacheDao.getDesignations();
		entityListMap.put(AppConstants.DESIGNATION, designations);

		buildEntityMap();

	}



	@Override
	public void refreshCacheList(final String entity){
		switch (entity) {
		case AppConstants.STATE:
			logger.info("{} : refresh cache list work for get state ",this.getClass().getName());
			List<State> states=new ArrayList<State>();
			states=cacheDao.getState();
			entityListMap.put(AppConstants.STATE, states);
			break;

		case AppConstants.QUESTION:
			List<QuestionMaster> questions =new ArrayList<QuestionMaster>();
			questions=cacheDao.getQuestions();
			entityListMap.put(AppConstants.QUESTION, questions);
			break;

		case AppConstants.DEPARTMENT:
			List<Department> departments =new ArrayList<Department>();
			departments=cacheDao.getDepartments();
			entityListMap.put(AppConstants.DEPARTMENT, departments);
			break;

		case AppConstants.DESIGNATION:
			List<Designation> designations =new ArrayList<Designation>();
			designations=cacheDao.getDesignations();
			entityListMap.put(AppConstants.DESIGNATION, designations);
			break;

		default:

		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getEntityList(String entity){
		logger.info("{} : Get entity list by entity:{} ",this.getClass().getName(), entity);
		return (List<T>)entityListMap.get(entity);
	}

	private void buildEntityMap(){
		logger.info("{} : Build Entity Map ",this.getClass().getName());

		List<State> states = cacheDao.getState();
		if(states!=null){
			for(State state : states){

				Long clientId = state.getClientId();

				Map<String, List> subMap = clientEntityListMap.get(clientId);
				if(subMap==null){

					subMap = new HashMap<String, List>();
					clientEntityListMap.put(clientId, subMap);
				}

				List data = subMap.get(AppConstants.STATE);

				if(data==null){
					data = new ArrayList<State>();
					subMap.put(AppConstants.STATE,data);
				}
				data.add(state);
			}
		}

		List<QuestionMaster> questionMasters = cacheDao.getQuestions();
		if(questionMasters!=null){
			for(QuestionMaster questionMaster : questionMasters){

				Long clientId = questionMaster.getClientId();

				Map<String, List> subMap = clientEntityListMap.get(clientId);
				if(subMap==null){

					subMap = new HashMap<String, List>();
					clientEntityListMap.put(clientId, subMap);
				}

				List data = subMap.get(AppConstants.QUESTION);

				if(data==null){
					data = new ArrayList<State>();
					subMap.put(AppConstants.QUESTION,data);
				}
				data.add(questionMaster);
			}
		}

		List<Department> departments = cacheDao.getDepartments();
		if(departments!=null){
			for(Department department : departments){

				Long clientId = department.getClientId();

				Map<String, List> subMap = clientEntityListMap.get(clientId);
				if(subMap==null){

					subMap = new HashMap<String, List>();
					clientEntityListMap.put(clientId, subMap);
				}

				List data = subMap.get(AppConstants.DEPARTMENT);

				if(data==null){
					data = new ArrayList<State>();
					subMap.put(AppConstants.DEPARTMENT,data);
				}
				data.add(department);
			}
		}
		
		List<Designation> designations = cacheDao.getDesignations();
		if(designations!=null){
			for(Designation designation : designations){

				Long clientId = designation.getClientId();

				Map<String, List> subMap = clientEntityListMap.get(clientId);
				if(subMap==null){

					subMap = new HashMap<String, List>();
					clientEntityListMap.put(clientId, subMap);
				}

				List data = subMap.get(AppConstants.DESIGNATION);

				if(data==null){
					data = new ArrayList<State>();
					subMap.put(AppConstants.DESIGNATION,data);
				}
				data.add(designation);
			}
		}
	}

	@Override
	public State getStateId(Long id){
		return stateMap.get(id);
	}

	@Override
	public QuestionMaster getQuestionByQuestion(String question ){
		return questionMap.get(question);
	}

	@Override
	public Department getDepartmentById(Long departmentId){
		return departmentMap.get(departmentId);
	}

	@Override
	public Designation getDesignationByQuestion(Long designationId){
		return designationMap.get(designationId);
	}

	@Override
	public List<Privilege> getPrivileges(Long clientId) {
		List<Privilege> privileges = cacheDao.getPrivileges(clientId);
		return privileges;
	}
}

