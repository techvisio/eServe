package com.techvisio.eserve.db.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Privilege;
import com.techvisio.eserve.beans.Role;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.SearchResultData;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.beans.UserPrivilege;
import com.techvisio.eserve.db.UserDao;
import com.techvisio.eserve.util.CommonUtil;

@Component
public class UserDaoImpl extends BaseDao implements UserDao{

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
			User clonedUser=SerializationUtils.clone(result.get(0));
			return clonedUser;
		}
		return null;
	}

	@Override
	public User getUserByUserName(String userName) {
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		String queryString="FROM User u WHERE u.userName = "+ " '"+userName +"' and u.client.clientId = "+clientId;
		Query query= getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<User> result= (List<User>)query.getResultList();
		if(result != null && result.size()>0){
			User clonedUser=SerializationUtils.clone(result.get(0));
			return clonedUser;
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
			User clonedUser=SerializationUtils.clone(result.get(0));
			return clonedUser;
		}
		return null;
	}

	@Override
	public List<User> getUsers( ) {
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		String queryString="FROM User u WHERE u.client.clientId = "+clientId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<User> result= (List<User>)query.getResultList();
		List<User> clonedUsers=new ArrayList<User>(result);
		return clonedUsers;
	}

	@Override
	public SearchResultData getUserByCriteria(SearchCriteria searchCriteria) {

		SearchResultData<User> searchResultData= new SearchResultData<User>();
		String ascOrDsc = searchCriteria.isAscending()?"ASC":"DESC";

		String sortBy=null;
		try {
			sortBy = CommonUtil.getFieldValue(User.class, searchCriteria.getSortBy());
		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String queryString="select USER_ID,CREATED_BY,CREATED_ON,UPDATED_BY,UPDATED_ON,DOB,IS_ACTIVE,EMAIL_ID,FIRST_NAME,FORCE_PASSWORD_CHANGE,LAST_NAME,PASSWORD,USER_NAME ,Client_Id,DEPARTMENT_ID,DESIGNATION_ID, PHOTO_PATH from TB_USER WHERE client_Id = coalesce(:client_Id, client_Id) and user_Id = coalesce(:user_Id, user_Id) and department_Id = coalesce(:department_Id, department_Id) and designation_Id = coalesce(:designation_Id, designation_Id) and lower(email_Id) = coalesce(:email_Id, email_Id) and  lower(first_Name) LIKE :first_Name and lower(last_Name) Like :last_Name and lower(user_Name) Like :user_Name ORDER BY  "+sortBy +" "+ascOrDsc+" limit :START_INDEX,:PAGE_SIZE";
		Query query=getEntityManager().createNativeQuery(queryString, User.class);

		String queryString1="SELECT count(*),'totalCount' FROM (select * from TB_USER WHERE client_Id = coalesce(:client_Id, client_Id) and user_Id = coalesce(:user_Id, user_Id) and department_Id = coalesce(:department_Id, department_Id) and designation_Id = coalesce(:designation_Id, designation_Id) and lower(email_Id) = coalesce(:email_Id, email_Id) and  lower(first_Name) LIKE :first_Name and lower(last_Name) Like :last_Name and lower(user_Name) Like :user_Name)a";
		Query query1=getEntityManager().createNativeQuery(queryString1);

		String firstName = StringUtils.isEmpty(searchCriteria.getFirstName())?"":searchCriteria.getFirstName().toLowerCase();
		String lastName = StringUtils.isEmpty(searchCriteria.getLastName())?"":searchCriteria.getLastName().toLowerCase();
		String userName = StringUtils.isEmpty(searchCriteria.getUserName())?"":searchCriteria.getUserName().toLowerCase();
		String emailId = StringUtils.isEmpty(searchCriteria.getEmailId())?null:searchCriteria.getEmailId().toLowerCase();

		int pageSize,pageNo;
		if(searchCriteria.getPageSize()==0)
		{
			pageSize = 3;
		}
		else
		{
			pageSize = searchCriteria.getPageSize();
		}
		if(searchCriteria.getPageNo() == 0)
		{
			pageNo = 1;
		}
		else
		{
			pageNo = searchCriteria.getPageNo();
		}

		int startIndex = (pageSize * pageNo) - pageSize;

		query.setParameter("first_Name", "%"+firstName+"%");
		query.setParameter("last_Name", "%"+lastName+"%");
		query.setParameter("user_Name", "%"+userName+"%");
		query.setParameter("user_Id", searchCriteria.getUserId());
		query.setParameter("client_Id", searchCriteria.getClientId());
		query.setParameter("department_Id", searchCriteria.getDepartmentId());
		query.setParameter("designation_Id", searchCriteria.getDesignationId());
		query.setParameter("email_Id", emailId);
		query.setParameter("PAGE_SIZE", pageSize);
		query.setParameter("START_INDEX", startIndex);


		query1.setParameter("first_Name", "%"+firstName+"%");
		query1.setParameter("last_Name", "%"+lastName+"%");
		query1.setParameter("user_Name", "%"+userName+"%");
		query1.setParameter("user_Id", searchCriteria.getUserId());
		query1.setParameter("client_Id", searchCriteria.getClientId());
		query1.setParameter("department_Id", searchCriteria.getDepartmentId());
		query1.setParameter("designation_Id", searchCriteria.getDesignationId());
		query1.setParameter("email_Id", emailId);

		@SuppressWarnings("unchecked")
		List<Object[]> counts = query1.getResultList();
		for (Object[] count : counts) {
			Long count1 = (long) ((Number) count[0]).intValue();
			searchResultData.setTotalCount(count1);
		}

		@SuppressWarnings("unchecked")
		List<User> result= (List<User>)query.getResultList();

		searchResultData.setObjectData(result);
		return searchResultData;

	}

	@Override
	public boolean isUserExists(User user) {
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		String queryString="from User u WHERE lower(emailId) = coalesce(:emailId, emailId) or lower(userName) = coalesce(:userName, userName) and u.client.clientId = "+clientId;
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
		String queryString="select Role_Id,Role_Name,Description,created_By,created_on,updated_by,updated_on,case when exists(select 1 from TB_USER_ROLE ur where ur.role_id=rm.role_id and ur.user_id="+userId+") then 1 else 0 end as selected from TB_ROLE_MASTER rm" ;
		Query query=getEntityManager().createNativeQuery(queryString,Role.class);
		List<Role> userRoles = (List<Role>)query.getResultList();

		return userRoles;
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
		getEntityManager().merge(privilege);
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

		String deleteQuery = "Delete from TB_USER_PRIVILEGE where USER_ID =:USER_ID and USER_PRVLG_ID not in :USER_PRVLG_ID";

		Query query=(Query) getEntityManager().createNativeQuery(deleteQuery).setParameter("USER_ID", userId).setParameter("USER_PRVLG_ID", userPrivilegeId);
		query.executeUpdate();	
		getEntityManager().flush();	
	}


	@Override
	public User getUserByEmailId(String EmailId) {
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		String queryString="FROM User u WHERE u.emailId = "+" '" + EmailId +" ' "+" and u.client.clientId = "+clientId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<User> users= (List<User>)query.getResultList();

		if(users != null && users.size()>0){
			User clonedUser=SerializationUtils.clone(users.get(0));
			return clonedUser;
		}
		return null;
	}

}