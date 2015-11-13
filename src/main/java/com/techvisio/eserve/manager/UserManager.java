package com.techvisio.eserve.manager;

import java.util.List;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Privilege;
import com.techvisio.eserve.beans.Role;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.SecurityQuestion;
import com.techvisio.eserve.beans.User;
@Component
public interface UserManager {

	public void saveUser(User user);

	User getUser(Long userId);

	List<Role> getUserRole(Long userId);

	void saveSecurityQuestion(SecurityQuestion securityQuestion);

	SecurityQuestion getSecurityQuestion(Long questionId);
	
	public List<User> getUsers();

	public List<Privilege> getUserPrivileges(Long userId);
	
	public void saveUserPrivileges(List<Privilege> privileges);

	List<User> getUserByCriteria(SearchCriteria searchCriteria);

	User verifyUserNameAndEmialId(SearchCriteria searchCriteria);
}
