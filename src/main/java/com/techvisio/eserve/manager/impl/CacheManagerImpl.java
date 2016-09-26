package com.techvisio.eserve.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.AgreementDuration;
import com.techvisio.eserve.beans.BasicEntity;
import com.techvisio.eserve.beans.ClientCommConfig;
import com.techvisio.eserve.beans.ClientConfig;
import com.techvisio.eserve.beans.CustomerType;
import com.techvisio.eserve.beans.Department;
import com.techvisio.eserve.beans.Designation;
import com.techvisio.eserve.beans.EquipmentModalNo;
import com.techvisio.eserve.beans.EquipmentType;
import com.techvisio.eserve.beans.EquipmentWarrantyUnder;
import com.techvisio.eserve.beans.InvoiceTaxes;
import com.techvisio.eserve.beans.Issue;
import com.techvisio.eserve.beans.Privilege;
import com.techvisio.eserve.beans.QuestionMaster;
import com.techvisio.eserve.beans.Resolution;
import com.techvisio.eserve.beans.ServiceProvider;
import com.techvisio.eserve.beans.State;
import com.techvisio.eserve.beans.UnitCategory;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.db.CacheDao;
import com.techvisio.eserve.db.impl.CacheDaoImpl;
import com.techvisio.eserve.manager.CacheManager;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;
import com.techvisio.eserve.util.CustomLogger;

@Transactional
@Component
public class CacheManagerImpl implements CacheManager {
	private static CustomLogger logger = CustomLogger
			.getLogger(CacheManagerImpl.class);

	@Autowired
	CacheDao cacheDao;

	public void setCacheDao(CacheDaoImpl cacheDao) {
		this.cacheDao = cacheDao;
	}

	@Autowired
	public CacheManagerImpl(CacheDao cacheDao) {
		this.cacheDao = cacheDao;
		builtEntityListCache();
		buildClientPreferenceCache();
		buildClientCommunicationConfig();
	}

	public CacheManagerImpl() {
	}

	private static Map<Long, Map<String, List>> clientEntityListMap = new HashMap<>();
	private static Map<String, List> entityListMap = new HashMap<String, List>();
	private static Map<Long, State> stateMap = new HashMap<Long, State>();
	private static Map<String, QuestionMaster> questionMap = new HashMap<String, QuestionMaster>();
	private static Map<Long, Department> departmentMap = new HashMap<Long, Department>();
	private static Map<Long, Designation> designationMap = new HashMap<Long, Designation>();
	private static Map<Long, Resolution> resolutionMap = new HashMap<Long, Resolution>();
	private static Map<Long, Issue> issueMap = new HashMap<Long, Issue>();
	private static Map<Long, AgreementDuration> agreementDurationMap = new HashMap<Long, AgreementDuration>();
	private static Map<Long, UnitCategory> unitCategoryMap = new HashMap<Long, UnitCategory>();
	private static Map<Long, CustomerType> customerTypeMap = new HashMap<Long, CustomerType>();
	private static Map<Long, ServiceProvider> serviceProviderMap = new HashMap<Long, ServiceProvider>();
	private static Map<Long, User> userMap = new HashMap<Long, User>();
	private static Map<Long, Map<String,ClientConfig>> clientConfigMap = new HashMap<Long, Map<String,ClientConfig>>();
	private static Map<Long, Map<String,ClientCommConfig>> clientComConfigMap = new HashMap<Long, Map<String,ClientCommConfig>>();

	@Override
	public Map<Long, Map<String, ClientConfig>> getConfigMap() {
		return clientConfigMap;
	}

	@Override
	public Map<Long, Map<String, ClientCommConfig>> getClientComConfigMap() {
		return  clientComConfigMap;
	}

	public List<State> getStates(Long clientId) {
		Map<String, List> clientMap = clientEntityListMap.get(clientId);
		if (clientMap != null) {
			List data = clientMap.get(AppConstants.STATE);
			if (data != null) {
				return (List<State>) data;
			}
		}
		return new ArrayList<State>();
	}

	public List<QuestionMaster> getQuestion(Long clientId) {

		Map<String, List> clientMap = clientEntityListMap.get(clientId);

		if (clientMap != null) {
			List data = clientMap.get(AppConstants.QUESTION);
			if (data != null) {
				return (List<QuestionMaster>) data;
			}
		}
		return new ArrayList<QuestionMaster>();
	}

	public List<Department> getDepartment(Long clientId) {

		Map<String, List> clientMap = clientEntityListMap.get(clientId);

		if (clientMap != null) {
			List data = clientMap.get(AppConstants.DEPARTMENT);
			if (data != null) {
				return (List<Department>) data;
			}
		}
		return new ArrayList<Department>();
	}

	public List<Designation> getDesignations(Long clientId) {

		Map<String, List> clientMap = clientEntityListMap.get(clientId);

		if (clientMap != null) {
			List data = clientMap.get(AppConstants.DESIGNATION);
			if (data != null) {
				return (List<Designation>) data;
			}
		}
		return new ArrayList<Designation>();
	}

	public List<Resolution> getComplaintResolutions(Long clientId) {
		Map<String, List> clientMap = clientEntityListMap.get(clientId);
		if (clientMap != null) {
			List data = clientMap.get(AppConstants.RESOLUTION);
			if (data != null) {
				return (List<Resolution>) data;
			}
		}
		return new ArrayList<Resolution>();
	}

	public List<Issue> getIssues(Long clientId) {
		Map<String, List> clientMap = clientEntityListMap.get(clientId);
		if (clientMap != null) {
			List data = clientMap.get(AppConstants.ISSUE);
			if (data != null) {
				return (List<Issue>) data;
			}
		}
		return new ArrayList<Issue>();
	}

	public List<AgreementDuration> getAgreementDurations(Long clientId) {
		Map<String, List> clientMap = clientEntityListMap.get(clientId);
		if (clientMap != null) {
			List data = clientMap.get(AppConstants.AGREEMENT_DURATION);
			if (data != null) {
				return (List<AgreementDuration>) data;
			}
		}
		return new ArrayList<AgreementDuration>();
	}

	public List<UnitCategory> getUnitCategories(Long clientId) {
		Map<String, List> clientMap = clientEntityListMap.get(clientId);
		if (clientMap != null) {
			List data = clientMap.get(AppConstants.UNIT_CATEGORY);
			if (data != null) {
				return (List<UnitCategory>) data;
			}
		}
		return new ArrayList<UnitCategory>();
	}

	public List<CustomerType> getCustomerTypes(Long clientId) {
		Map<String, List> clientMap = clientEntityListMap.get(clientId);
		if (clientMap != null) {
			List data = clientMap.get(AppConstants.CUSTOMER_TYPE);
			if (data != null) {
				return (List<CustomerType>) data;
			}
		}
		return new ArrayList<CustomerType>();
	}

	public List<ServiceProvider> getServiceProviders(Long clientId) {
		Map<String, List> clientMap = clientEntityListMap.get(clientId);
		if (clientMap != null) {
			List data = clientMap.get(AppConstants.SERVICE_PROVIDER);
			if (data != null) {
				return (List<ServiceProvider>) data;
			}
		}
		return new ArrayList<ServiceProvider>();
	}

	public List<User> getUsers(Long clientId) {
		Map<String, List> clientMap = clientEntityListMap.get(clientId);
		if (clientMap != null) {
			List data = clientMap.get(AppConstants.USER);
			if (data != null) {
				return (List<User>) data;
			}
		}
		return new ArrayList<User>();
	}

	public List<EquipmentWarrantyUnder> getEquipmentWarrantyUnder(Long clientId) {
		Map<String, List> clientMap = clientEntityListMap.get(clientId);
		if (clientMap != null) {
			List data = clientMap.get(AppConstants.EQUIPMENT_WARRANTY_UNDER);
			if (data != null) {
				return (List<EquipmentWarrantyUnder>) data;
			}
		}
		return new ArrayList<EquipmentWarrantyUnder>();
	}
	
	public List<EquipmentType> getEquipmentType(Long clientId) {
		Map<String, List> clientMap = clientEntityListMap.get(clientId);
		if (clientMap != null) {
			List data = clientMap.get(AppConstants.EQUIPMENT_TYPE);
			if (data != null) {
				return (List<EquipmentType>) data;
			}
		}
		return new ArrayList<EquipmentType>();
	}
	
	public List<EquipmentModalNo> getEquipmentModalNo(Long clientId) {
		Map<String, List> clientMap = clientEntityListMap.get(clientId);
		if (clientMap != null) {
			List data = clientMap.get(AppConstants.EQUIPMENT_MODAL_NO);
			if (data != null) {
				return (List<EquipmentModalNo>) data;
			}
		}
		return new ArrayList<EquipmentModalNo>();
	}
	public void builtEntityListCache() {
		List<State> states = new ArrayList<State>();
		logger.info("{} : built entity list cache work for get state ", this
				.getClass().getName());
		states = cacheDao.getState();
		entityListMap.put(AppConstants.STATE, states);

		List<QuestionMaster> questions = new ArrayList<QuestionMaster>();
		questions = cacheDao.getQuestions();
		entityListMap.put(AppConstants.QUESTION, questions);

		List<Department> departments = new ArrayList<Department>();
		departments = cacheDao.getDepartments();
		entityListMap.put(AppConstants.DEPARTMENT, departments);

		List<Designation> designations = new ArrayList<Designation>();
		designations = cacheDao.getDesignations();
		entityListMap.put(AppConstants.DESIGNATION, designations);

		List<Resolution> resolutions = new ArrayList<Resolution>();
		resolutions = cacheDao.getResolution();
		entityListMap.put(AppConstants.RESOLUTION, resolutions);

		List<Issue> issues = new ArrayList<Issue>();
		issues = cacheDao.getIssues();
		entityListMap.put(AppConstants.ISSUE, issues);

		List<AgreementDuration> agreementDurations = new ArrayList<AgreementDuration>();
		agreementDurations = cacheDao.getAgreementDuration();
		entityListMap.put(AppConstants.AGREEMENT_DURATION, agreementDurations);

		List<UnitCategory> unitCategories = new ArrayList<UnitCategory>();
		unitCategories = cacheDao.getUnitCategories();
		entityListMap.put(AppConstants.UNIT_CATEGORY, unitCategories);

		List<CustomerType> customerTypes = new ArrayList<CustomerType>();
		customerTypes = cacheDao.getCustomerTypes();
		entityListMap.put(AppConstants.CUSTOMER_TYPE, customerTypes);

		List<ServiceProvider> serviceProviders = new ArrayList<ServiceProvider>();
		serviceProviders = cacheDao.getServiceProviders();
		entityListMap.put(AppConstants.SERVICE_PROVIDER, serviceProviders);

		List<User> users = new ArrayList<User>();
		users = cacheDao.getUsers();
		entityListMap.put(AppConstants.USER, users);

		List<EquipmentWarrantyUnder> warrantyUnders = new ArrayList<EquipmentWarrantyUnder>();
		warrantyUnders = cacheDao.getEquipmentWarrantyUnder();
		entityListMap.put(AppConstants.EQUIPMENT_WARRANTY_UNDER, warrantyUnders);
		
		List<EquipmentType> equipmentTypes = new ArrayList<EquipmentType>();
		equipmentTypes = cacheDao.getEquipmentType();
		entityListMap.put(AppConstants.EQUIPMENT_TYPE, equipmentTypes);
		
		List<EquipmentModalNo> modalNo = new ArrayList<EquipmentModalNo>();
		modalNo = cacheDao.getEquipmentModalNo();
		entityListMap.put(AppConstants.EQUIPMENT_MODAL_NO, modalNo);
		
		buildClientCache();

	}

	private void buildClientCommunicationConfig() {
		List<ClientCommConfig> CommConfigList = new ArrayList<ClientCommConfig>();
		CommConfigList = cacheDao.getClientCommunicationConfig();
		for (ClientCommConfig commConfig : CommConfigList) {
			Long clientId=commConfig.getClient().getClientId();
			Map<String,ClientCommConfig> commConfigMap=clientComConfigMap.get(clientId);
			if(commConfigMap==null){
				commConfigMap=new HashMap<String, ClientCommConfig>();
				clientComConfigMap.put(clientId, commConfigMap);
			}
			commConfigMap.put(commConfig.getProperty(),commConfig);
		}
	}

	private void buildClientPreferenceCache() {
		List<ClientConfig> configList = new ArrayList<ClientConfig>();
		configList = cacheDao.getClientPreferences();
		for (ClientConfig config : configList) {
			Long clientId=config.getClient().getClientId();
			Map<String,ClientConfig> configMap=clientConfigMap.get(clientId);
			if(configMap==null){
				configMap=new HashMap<String, ClientConfig>();
				clientConfigMap.put(clientId, configMap);
			}
			configMap.put(config.getProperty(),config);
		}
	}

	@Override
	public void refreshCacheList(final String entity) {
		switch (entity) {
		case AppConstants.STATE:
			logger.info("{} : refresh cache list work for get state ", this
					.getClass().getName());
			List<State> states = new ArrayList<State>();
			states = cacheDao.getState();
			entityListMap.put(AppConstants.STATE, states);
			break;

		case AppConstants.QUESTION:
			List<QuestionMaster> questions = new ArrayList<QuestionMaster>();
			questions = cacheDao.getQuestions();
			entityListMap.put(AppConstants.QUESTION, questions);
			break;

		case AppConstants.DEPARTMENT:
			List<Department> departments = new ArrayList<Department>();
			departments = cacheDao.getDepartments();
			entityListMap.put(AppConstants.DEPARTMENT, departments);
			break;

		case AppConstants.DESIGNATION:
			List<Designation> designations = new ArrayList<Designation>();
			designations = cacheDao.getDesignations();
			entityListMap.put(AppConstants.DESIGNATION, designations);
			break;

		case AppConstants.RESOLUTION:
			List<Resolution> resolutions = new ArrayList<Resolution>();
			resolutions = cacheDao.getResolution();
			entityListMap.put(AppConstants.RESOLUTION, resolutions);
			break;

		case AppConstants.ISSUE:
			List<Issue> issues = new ArrayList<Issue>();
			issues = cacheDao.getIssues();
			entityListMap.put(AppConstants.ISSUE, issues);
			break;

		case AppConstants.AGREEMENT_DURATION:
			List<AgreementDuration> agreementDurations = new ArrayList<AgreementDuration>();
			agreementDurations = cacheDao.getAgreementDuration();
			entityListMap.put(AppConstants.AGREEMENT_DURATION,
					agreementDurations);
			break;

		case AppConstants.UNIT_CATEGORY:
			List<UnitCategory> unitCategories = new ArrayList<UnitCategory>();
			unitCategories = cacheDao.getUnitCategories();
			entityListMap.put(AppConstants.UNIT_CATEGORY, unitCategories);
			break;

		case AppConstants.CUSTOMER_TYPE:
			List<CustomerType> customerTypes = new ArrayList<CustomerType>();
			customerTypes = cacheDao.getCustomerTypes();
			entityListMap.put(AppConstants.CUSTOMER_TYPE, customerTypes);
			break;

		case AppConstants.SERVICE_PROVIDER:
			List<ServiceProvider> serviceProviders = new ArrayList<ServiceProvider>();
			serviceProviders = cacheDao.getServiceProviders();
			entityListMap.put(AppConstants.SERVICE_PROVIDER, serviceProviders);
			break;

		case AppConstants.USER:
			List<User> users = new ArrayList<User>();
			users = cacheDao.getUsers();
			entityListMap.put(AppConstants.USER, users);
			break;
			
		case AppConstants.EQUIPMENT_WARRANTY_UNDER:
			List<EquipmentWarrantyUnder> warrantyUnder = new ArrayList<EquipmentWarrantyUnder>();
			warrantyUnder = cacheDao.getEquipmentWarrantyUnder();
			entityListMap.put(AppConstants.EQUIPMENT_WARRANTY_UNDER, warrantyUnder);
			break;
			
		case AppConstants.EQUIPMENT_TYPE:
			List<EquipmentType> equipmentType = new ArrayList<EquipmentType>();
			equipmentType = cacheDao.getEquipmentType();
			entityListMap.put(AppConstants.EQUIPMENT_TYPE, equipmentType);
			break;
			
		case AppConstants.EQUIPMENT_MODAL_NO:
			List<EquipmentModalNo> modalNo = new ArrayList<EquipmentModalNo>();
			modalNo = cacheDao.getEquipmentModalNo();
			entityListMap.put(AppConstants.EQUIPMENT_MODAL_NO, modalNo);
			break;

		default:

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getEntityList(String entity) {
		logger.info("{} : Get entity list by entity:{} ", this.getClass()
				.getName(), entity);
		return (List<T>) entityListMap.get(entity);
	}

	private void buildClientCache() {
		for (String key : entityListMap.keySet()) {
			List allDataList = entityListMap.get(key);
			for (Object data : allDataList) {
				if (data instanceof BasicEntity) {
					Long clientId = ((BasicEntity) data).getClient()
							.getClientId();

					Map<String, List> clientDataList = clientEntityListMap
							.get(clientId);
					if (clientDataList == null) {
						clientDataList = new HashMap<String, List>();
						clientEntityListMap.put(clientId, clientDataList);
					}

					List clientEntityList = clientDataList.get(key);

					if (clientEntityList == null) {
						clientEntityList = new ArrayList();
						clientDataList.put(key, clientEntityList);
					}
					clientEntityList.add(data);
				}
			}
		}
	}

	// private void buildEntityMap(){
	// logger.info("{} : Build Entity Map ",this.getClass().getName());
	//
	// List<State> states = cacheDao.getState();
	// if(states!=null){
	// for(State state : states){
	//
	// Long clientId = state.getClient().getClientId();
	//
	// Map<String, List> subMap = clientEntityListMap.get(clientId);
	// if(subMap==null){
	//
	// subMap = new HashMap<String, List>();
	// clientEntityListMap.put(clientId, subMap);
	// }
	//
	// List data = subMap.get(AppConstants.STATE);
	//
	// if(data==null){
	// data = new ArrayList<State>();
	// subMap.put(AppConstants.STATE,data);
	// }
	// data.add(state);
	// }
	// }
	//
	// List<QuestionMaster> questionMasters = cacheDao.getQuestions();
	// if(questionMasters!=null){
	// for(QuestionMaster questionMaster : questionMasters){
	//
	// Long clientId = questionMaster.getClient().getClientId();
	//
	// Map<String, List> subMap = clientEntityListMap.get(clientId);
	// if(subMap==null){
	//
	// subMap = new HashMap<String, List>();
	// clientEntityListMap.put(clientId, subMap);
	// }
	//
	// List data = subMap.get(AppConstants.QUESTION);
	//
	// if(data==null){
	// data = new ArrayList<State>();
	// subMap.put(AppConstants.QUESTION,data);
	// }
	// data.add(questionMaster);
	// }
	// }
	//
	// List<Department> departments = cacheDao.getDepartments();
	// if(departments!=null){
	// for(Department department : departments){
	//
	// Long clientId = department.getClient().getClientId();
	//
	// Map<String, List> subMap = clientEntityListMap.get(clientId);
	// if(subMap==null){
	//
	// subMap = new HashMap<String, List>();
	// clientEntityListMap.put(clientId, subMap);
	// }
	//
	// List data = subMap.get(AppConstants.DEPARTMENT);
	//
	// if(data==null){
	// data = new ArrayList<State>();
	// subMap.put(AppConstants.DEPARTMENT,data);
	// }
	// data.add(department);
	// }
	// }
	//
	// List<Designation> designations = cacheDao.getDesignations();
	// if(designations!=null){
	// for(Designation designation : designations){
	//
	// Long clientId = designation.getClient().getClientId();
	//
	// Map<String, List> subMap = clientEntityListMap.get(clientId);
	// if(subMap==null){
	//
	// subMap = new HashMap<String, List>();
	// clientEntityListMap.put(clientId, subMap);
	// }
	//
	// List data = subMap.get(AppConstants.DESIGNATION);
	//
	// if(data==null){
	// data = new ArrayList<State>();
	// subMap.put(AppConstants.DESIGNATION,data);
	// }
	// data.add(designation);
	// }
	// }
	//
	// List<Resolution> resolutions = cacheDao.getResolution();
	// if(resolutions!=null){
	// for(Resolution resolutionMaster : resolutions){
	//
	// Long clientId = resolutionMaster.getClient().getClientId();
	//
	// Map<String, List> subMap = clientEntityListMap.get(clientId);
	// if(subMap==null){
	//
	// subMap = new HashMap<String, List>();
	// clientEntityListMap.put(clientId, subMap);
	// }
	//
	// List data = subMap.get(AppConstants.RESOLUTION);
	//
	// if(data==null){
	// data = new ArrayList<Resolution>();
	// subMap.put(AppConstants.RESOLUTION,data);
	// }
	// data.add(resolutionMaster);
	// }
	// }
	//
	// List<Issue> issues = cacheDao.getIssues();
	// if(issues!=null){
	// for(Issue issue : issues){
	//
	// Long clientId = issue.getClient().getClientId();
	//
	// Map<String, List> subMap = clientEntityListMap.get(clientId);
	// if(subMap==null){
	//
	// subMap = new HashMap<String, List>();
	// clientEntityListMap.put(clientId, subMap);
	// }
	//
	// List data = subMap.get(AppConstants.ISSUE);
	//
	// if(data==null){
	// data = new ArrayList<Issue>();
	// subMap.put(AppConstants.ISSUE,data);
	// }
	// data.add(issue);
	// }
	// }
	//
	// List<AgreementDuration> agreementDurations =
	// cacheDao.getAgreementDuration();
	// if(agreementDurations!=null){
	// for(AgreementDuration duration : agreementDurations){
	//
	// Long clientId = duration.getClient().getClientId();
	//
	// Map<String, List> subMap = clientEntityListMap.get(clientId);
	// if(subMap==null){
	//
	// subMap = new HashMap<String, List>();
	// clientEntityListMap.put(clientId, subMap);
	// }
	//
	// List data = subMap.get(AppConstants.AGREEMENT_DURATION);
	//
	// if(data==null){
	// data = new ArrayList<Issue>();
	// subMap.put(AppConstants.AGREEMENT_DURATION,data);
	// }
	// data.add(duration);
	// }
	// }
	//
	// List<UnitCategory> unitCategories = cacheDao.getUnitCategories();
	// if(unitCategories!=null){
	// for(UnitCategory unitCategory : unitCategories){
	//
	// Long clientId = unitCategory.getClient().getClientId();
	//
	// Map<String, List> subMap = clientEntityListMap.get(clientId);
	// if(subMap==null){
	//
	// subMap = new HashMap<String, List>();
	// clientEntityListMap.put(clientId, subMap);
	// }
	//
	// List data = subMap.get(AppConstants.UNIT_CATEGORY);
	//
	// if(data==null){
	// data = new ArrayList<UnitCategory>();
	// subMap.put(AppConstants.UNIT_CATEGORY,data);
	// }
	// data.add(unitCategory);
	// }
	// }
	//
	// List<CustomerType> customerTypes = cacheDao.getCustomerTypes();
	// if(customerTypes!=null){
	// for(CustomerType customerType: customerTypes){
	//
	// Long clientId = customerType.getClient().getClientId();
	//
	// Map<String, List> subMap = clientEntityListMap.get(clientId);
	// if(subMap==null){
	//
	// subMap = new HashMap<String, List>();
	// clientEntityListMap.put(clientId, subMap);
	// }
	//
	// List data = subMap.get(AppConstants.CUSTOMER_TYPE);
	//
	// if(data==null){
	// data = new ArrayList<CustomerType>();
	// subMap.put(AppConstants.CUSTOMER_TYPE,data);
	// }
	// data.add(customerType);
	// }
	// }
	//
	// List<ServiceProvider> serviceProviders = cacheDao.getServiceProviders();
	// if(serviceProviders!=null){
	// for(ServiceProvider serviceProvider: serviceProviders){
	//
	// Long clientId = serviceProvider.getClient().getClientId();
	//
	// Map<String, List> subMap = clientEntityListMap.get(clientId);
	// if(subMap==null){
	//
	// subMap = new HashMap<String, List>();
	// clientEntityListMap.put(clientId, subMap);
	// }
	//
	// List data = subMap.get(AppConstants.SERVICE_PROVIDER);
	//
	// if(data==null){
	// data = new ArrayList<ServiceProvider>();
	// subMap.put(AppConstants.SERVICE_PROVIDER,data);
	// }
	// data.add(serviceProvider);
	// }
	// }
	//
	//
	// List<User> users = cacheDao.getUsers();
	// if(users!=null){
	// for(User user: users){
	//
	// Long clientId = user.getClient().getClientId();
	//
	// Map<String, List> subMap = clientEntityListMap.get(clientId);
	// if(subMap==null){
	//
	// subMap = new HashMap<String, List>();
	// clientEntityListMap.put(clientId, subMap);
	// }
	//
	// List data = subMap.get(AppConstants.USER);
	//
	// if(data==null){
	// data = new ArrayList<User>();
	// subMap.put(AppConstants.USER,data);
	// }
	// data.add(user);
	// }
	// }
	//
	//
	// }

	@Override
	public State getStateId(Long id) {
		return stateMap.get(id);
	}

	@Override
	public QuestionMaster getQuestionByQuestion(String question) {
		return questionMap.get(question);
	}

	@Override
	public Department getDepartmentById(Long departmentId) {
		return departmentMap.get(departmentId);
	}

	@Override
	public Designation getDesignationByQuestion(Long designationId) {
		return designationMap.get(designationId);
	}

	@Override
	public Resolution getResolutionMasters(Long resolutionId) {
		return resolutionMap.get(resolutionId);
	}

	@Override
	public Issue getIssue(Long issueId) {
		return issueMap.get(issueId);
	}

	@Override
	public List<Privilege> getPrivileges(Long clientId) {
		List<Privilege> privileges = cacheDao.getPrivileges(clientId);
		return privileges;
	}

	@Override
	public List<InvoiceTaxes> getInvoiceTaxes(Long clientId) {
		List<InvoiceTaxes> invoiceTaxes = cacheDao.getInvoiceTaxes(clientId);
		return invoiceTaxes;
	}
}
