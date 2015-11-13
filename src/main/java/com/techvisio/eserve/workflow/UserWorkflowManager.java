package com.techvisio.eserve.workflow;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.techvisio.eserve.beans.Privilege;
import com.techvisio.eserve.beans.Role;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.SecurityQuestion;
import com.techvisio.eserve.beans.User;

@Component
@Transactional
public interface UserWorkflowManager {

	public void saveUser(User user);

	User getUser(Long userId);

	List<Role> getUserRole(Long userId);

	void saveSecurityQuestion(SecurityQuestion securityQuestion);

	SecurityQuestion getSecurityQuestion(Long questionId);

	public List<User> getUsers();

	public List<Privilege> getUserPrivileges(Long userId);
	
	public void saveUserPrivileges(List<Privilege> privileges);

	Map<String, Boolean> forcePasswordChange(User user);

	List<User> getUserByCriteria(SearchCriteria searchCriteria);

	User verifyUserNameAndEmialId(SearchCriteria searchCriteria);
}
