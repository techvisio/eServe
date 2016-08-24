package com.techvisio.eserve.icc;

import java.util.List;

import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.beans.UserPrivilege;

public interface UserServiceICC {

	public void preRetrieveAllUsers();
	public List<User> postRetrieveAllUsers(List<User> users);

	public void preGetUserforEdit(Long userId);
	public User postGetUserforEdit(User user);

	public void preReleaseUserEntityLock();
	public User postReleaseUserEntityLock(User user);
	
	public User preUserSave(User user);
	public User postUserSave(User user);

	public void preGetUser();
	public User postGetUser(User user);

	public void preGetCurrentPassword();
	public User postGetCurrentPassword(User user);

	public User preForcePasswordChange(User user);
	public User postForcePasswordChange(User user);
	
	public void preGetUserWithUserPrivileges();
	public User postGetUserWithUserPrivileges(User user);
	
	public void preGetUserPrivilegesSet();
	public List<UserPrivilege> postGetUserPrivilegesSet(List<UserPrivilege> userPrivileges);
	
	public User preResetPassword(User user);
	public User postResetPassword(User user);
	
	public void preGetUserByUserName();
	public User postGetUserByUserName(User user);
	
	public void preGetUserByEmailId();
	public User postGetUserByEmailId(User user);
	
	void onErrorUserSave(User user);
	
}
