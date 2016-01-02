package com.techvisio.eserve.manager;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.techvisio.eserve.beans.Privilege;
import com.techvisio.eserve.beans.Role;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.SecurityQuestion;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.beans.UserPrivilege;
@Component
@Transactional
public interface UserManager {

	Map<String, Object> saveUser(User user);
	
	User getUser(Long userId);

	List<Role> getUserRole(Long userId);

	void saveSecurityQuestion(SecurityQuestion securityQuestion);

	SecurityQuestion getSecurityQuestion(Long questionId);

	public List<User> getUsers();

//	public List<Privilege> getUserPrivileges(Long userId);
	
	public void saveUserPrivileges(List<Privilege> privileges);

	Map<String, Boolean> forcePasswordChange(User user);

	List<User> getUserByCriteria(SearchCriteria searchCriteria);

	User getCurrentPassword(Long userId);

	User getUserWithUserPrivileges(Long userId);

	public List<UserPrivilege> getAllUserPrivileges(User user);
	
	public List<UserPrivilege> getUserPrivilegesSet();

	public void resetPassword(User user);
}
