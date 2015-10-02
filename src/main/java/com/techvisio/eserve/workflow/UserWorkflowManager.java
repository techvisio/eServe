package com.techvisio.eserve.workflow;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.techvisio.eserve.beans.Role;
import com.techvisio.eserve.beans.SecurityQuestion;
import com.techvisio.eserve.beans.User;

@Component
@Transactional
public interface UserWorkflowManager {

	public void addUser(User user);

	User getUser(Long userId);

	List<Role> getUserRole(Long userId);

	void saveSecurityQuestion(SecurityQuestion securityQuestion);

	SecurityQuestion getSecurityQuestion(Long questionId);

}
