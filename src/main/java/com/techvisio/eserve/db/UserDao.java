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

	public User getUser(Long userId);

	public List<Role> getUserRole(Long userId);

	public List<User> getUsers();

	public void saveUserPrivileges(List<Privilege> privileges);

	public void savePrivilege(Privilege privilege);

	public SearchResultData getUserByCriteria(SearchCriteria searchCriteria);

	public User getCurrentPassword(Long userId);

	public boolean isUserExists(User user);

	public void saveUserPrivilege(UserPrivilege userPrivilege);

	public void saveUserPrivilege(List<UserPrivilege> userPrivileges, Long userId);

	public User getUserByUserName(String userName);

	public User getUserByEmailId(String EmailId);

}
