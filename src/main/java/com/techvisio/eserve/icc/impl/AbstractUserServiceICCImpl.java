package com.techvisio.eserve.icc.impl;

import java.util.List;

import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.beans.UserPrivilege;
import com.techvisio.eserve.icc.UserServiceICC;

public abstract class AbstractUserServiceICCImpl implements UserServiceICC{

	@Override
	public void preRetrieveAllUsers(){
	}
	
	@Override
	public List<User> postRetrieveAllUsers(List<User> users){
		return users;
	}
	
	@Override
	public void preGetUserforEdit(Long userId) {
		
	}

	@Override
	public User postGetUserforEdit(User user) {
		return user;
	}

	@Override
	public void preReleaseUserEntityLock(){
		
	}
	
	@Override
	public User postReleaseUserEntityLock(User user){
		return user;
	}
	
	@Override
	public User preUserSave(User user) {
		return user;
	}

	@Override
	public User postUserSave(User user) {
		return user;
	}

	@Override
	public void preGetUser() {

	}

	@Override
	public User postGetUser(User user) {
		return user;
	}

	@Override
	public void preGetCurrentPassword() {
		
		
	}

	@Override
	public User postGetCurrentPassword(User user) {
		return user;
	}

	@Override
	public User preForcePasswordChange(User user) {
		return user;
	}

	@Override
	public User postForcePasswordChange(User user) {
		return user;
	}

	@Override
	public void preGetUserWithUserPrivileges() {
		
	}

	@Override
	public User postGetUserWithUserPrivileges(User user) {
		return user;
	}

	@Override
	public void preGetUserPrivilegesSet() {
		
	}

	@Override
	public List<UserPrivilege> postGetUserPrivilegesSet(
			List<UserPrivilege> userPrivileges) {
		return userPrivileges;
	}

	@Override
	public User preResetPassword(User user) {
		return user;
	}

	@Override
	public User postResetPassword(User user) {
		return user;
	}

	@Override
	public void preGetUserByUserName() {
		
	}

	@Override
	public User postGetUserByUserName(User user) {
		return user;
	}

	@Override
	public void preGetUserByEmailId() {
		
	}

	@Override
	public User postGetUserByEmailId(User user) {
		return user;
	}	
	
	@Override
	public void onErrorUserSave(User user) {
		
	}

}
