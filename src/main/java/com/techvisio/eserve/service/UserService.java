package com.techvisio.eserve.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Privilege;
import com.techvisio.eserve.beans.Role;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.SearchResultData;
import com.techvisio.eserve.beans.SecurityQuestion;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.beans.UserPrivilege;
@Component
@Transactional
public interface UserService {

	public Long saveUser(User user);
	
	public User getUser(Long userId);

	public List<Role> getUserRole(Long userId);

	public void saveSecurityQuestion(SecurityQuestion securityQuestion);

	public SecurityQuestion getSecurityQuestion(Long questionId);

	public List<User> getUsers();

//	public List<Privilege> getUserPrivileges(Long userId);
	
	public void saveUserPrivileges(List<Privilege> privileges);

	public Map<String, Boolean> forcePasswordChange(User user);

	public SearchResultData getUserByCriteria(SearchCriteria searchCriteria);

	public User getCurrentPassword(Long userId);

	public User getUserWithUserPrivileges(Long userId);

	public List<UserPrivilege> getAllUserPrivileges(User user);
	
	public List<UserPrivilege> getUserPrivilegesSet();

	public void resetPassword(User user);

	public User getUserByUserName(String userName);

	public User getEmailId(String emailId);
}
