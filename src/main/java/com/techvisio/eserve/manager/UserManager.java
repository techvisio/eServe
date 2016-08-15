package com.techvisio.eserve.manager;

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
public interface UserManager {

	public Map<String, Object> saveUser(User user, Long clientId);
	
	public User getUser(Long userId);

	public List<Role> getUserRole(Long userId);

	public void saveSecurityQuestion(SecurityQuestion securityQuestion);

	public SecurityQuestion getSecurityQuestion(Long questionId);

	public List<User> getUsers(Long clientId);

//	public List<Privilege> getUserPrivileges(Long userId);
	
	public void saveUserPrivileges(List<Privilege> privileges);

	public Map<String, Boolean> forcePasswordChange(User user);

	public SearchResultData getUserByCriteria(SearchCriteria searchCriteria);

	public User getCurrentPassword(Long userId);

	public User getUserWithUserPrivileges(Long userId);

	public List<UserPrivilege> getAllUserPrivileges(User user);
	
	public List<UserPrivilege> getUserPrivilegesSet();

	public void resetPassword(User user);

	public User getUserByUserName(String userName,Long clientId);

	public User getEmailId(String EmailId, Long clientId);

	public User getUserName(String UserName, Long clientId);

	
}
