package com.techvisio.eserve.db;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.techvisio.eserve.beans.Privilege;
import com.techvisio.eserve.beans.Role;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.SearchResultData;
import com.techvisio.eserve.beans.SecurityQuestion;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.beans.UserPrivilege;
@Component
public interface UserDao {

	public Long saveUser(User user);

	User getUser(Long userId);

	List<Role> getUserRole(Long userId);

	void saveSecurityQuestion(SecurityQuestion securityQuestion);

	SecurityQuestion getSecurityQuestion(Long questionId);

	public List<User> getUsers();
	
//	public List<Privilege> getPrivileges();

//	List<Privilege> getUserPrivileges(Long userId);

	void saveUserPrivileges(List<Privilege> privileges);

	void savePrivilege(Privilege privilege);

	SearchResultData getUserByCriteria(SearchCriteria searchCriteria);

	User getCurrentPassword(Long userId);

	boolean isUserExists(User user);

	List<UserPrivilege> getAllUserPrivileges(User user);

	void saveUserPrivilege(UserPrivilege userPrivilege);

	void saveUserPrivilege(List<UserPrivilege> userPrivileges, Long userId);

	User getUserWithUserPrivileges(Long userId);

	public List<UserPrivilege> getUserPrivilegesSet();

	public User getUserByUserName(String userName);

}
