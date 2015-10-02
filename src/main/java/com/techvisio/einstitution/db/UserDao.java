package com.techvisio.einstitution.db;

import java.util.List;

import com.techvisio.eserve.beans.Role;
import com.techvisio.eserve.beans.SecurityQuestion;
import com.techvisio.eserve.beans.User;

public interface UserDao {

	public void addUser(User user);

	User getUser(Long userId);

	List<Role> getUserRole(Long userId);

	void saveSecurityQuestion(SecurityQuestion securityQuestion);

	SecurityQuestion getSecurityQuestion(Long questionId);

}
