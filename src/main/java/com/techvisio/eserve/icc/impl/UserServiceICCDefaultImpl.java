package com.techvisio.eserve.icc.impl;

import java.util.List;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techvisio.eserve.beans.EntityLocks;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.beans.UserPrivilege;
import com.techvisio.eserve.exception.EntityLockedException;
import com.techvisio.eserve.manager.UserManager;
import com.techvisio.eserve.service.EntityLockService;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.AppConstants.EntityType;
import com.techvisio.eserve.util.CommonUtil;

@Service
public class UserServiceICCDefaultImpl extends AbstractUserServiceICCImpl{
	@Autowired
	EntityLockService entityLockService;

	@Autowired
	UserManager userManager;

	@Override
	public void preRetrieveAllUsers(){
		super.preRetrieveAllUsers();
	}
	
	@Override
	public List<User> postRetrieveAllUsers(List<User> users){
		return super.postRetrieveAllUsers(users);
	}
	
	@Override
	public void preGetUserforEdit(Long userId) {
		super.preGetUserforEdit(userId);
		EntityLocks lockObject=new EntityLocks();
		lockObject.setEntityType(EntityType.USER.name());
		lockObject.setEntityId(userId);
		entityLockService.lockEntity(lockObject);
	}

	@Override
	public User postGetUserforEdit(User user) {
		user = super.postGetUserforEdit(user);
		List<UserPrivilege> privileges= userManager.getAllUserPrivileges(user);
		user.setPrivileges(privileges);
		user.setEdited(true);
		return user;
	}
	
	@Override
	public void preReleaseUserEntityLock(){
		super.preReleaseUserEntityLock();
	}
	
	@Override
	public User postReleaseUserEntityLock(User user){
		user = super.postReleaseUserEntityLock(user);
		entityLockService.unlockEntity(EntityType.USER.name(), user.getUserId());
		return user;
	}

	@Override
	public User preUserSave(User user) {
		user = super.preUserSave(user);
		String userName = CommonUtil.getCurrentUser().getUserName();
		if(user.getUserId()!=null){
			boolean isEntityLocked=entityLockService.isEntityLocked(user.getUserId(), AppConstants.EntityType.USER.toString(), userName);
			if(!isEntityLocked){
				throw new EntityLockedException("Current user does not hold lock for this user");
			}
		}

		return user;
	}

	@Override
	public User postUserSave(User user) {
		user=super.postUserSave(user);
		entityLockService.unlockEntity("USER", user.getUserId());
		return user;
	}

	@Override
	public void onErrorUserSave(User user) {
		// TODO Auto-generated method stub
		super.onErrorUserSave(user);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	@Override
	public void preGetUser() {
super.preGetUser();
	}

	@Override
	public User postGetUser(User user) {
		user = super.preUserSave(user);
		EntityLocks entityLocks  = entityLockService.getEntity(user.getUserId(), AppConstants.EntityType.USER.toString());
		if(entityLocks!=null){
			user.setEdited(true);
		}
		else{
			user.setEdited(false);
		}
		return user;
	}

	@Override
	public void preGetCurrentPassword() {
super.preGetCurrentPassword();		
	}

	@Override
	public User postGetCurrentPassword(User user) {
		return super.postGetCurrentPassword(user);
	}

	@Override
	public User preForcePasswordChange(User user) {
		return super.preForcePasswordChange(user);
	}

	@Override
	public User postForcePasswordChange(User user) {
		return super.postForcePasswordChange(user);
	}

	@Override
	public void preGetUserWithUserPrivileges() {
		super.preGetUserWithUserPrivileges();
	}

	@Override
	public User postGetUserWithUserPrivileges(User user) {
		return super.postGetUserWithUserPrivileges(user);
	}

	@Override
	public void preGetUserPrivilegesSet() {
		super.preGetUserPrivilegesSet();
	}

	@Override
	public List<UserPrivilege> postGetUserPrivilegesSet(
			List<UserPrivilege> userPrivileges) {
		return super.postGetUserPrivilegesSet(userPrivileges);
	}

	@Override
	public User preResetPassword(User user) {
		return super.preResetPassword(user);
	}

	@Override
	public User postResetPassword(User user) {
		return super.postResetPassword(user);
	}

	@Override
	public void preGetUserByUserName() {
		super.preGetUserByUserName();
	}

	@Override
	public User postGetUserByUserName(User user) {
		return super.postGetUserByUserName(user);
	}

	@Override
	public void preGetUserByEmailId() {
		super.preGetUserByEmailId();
	}

	@Override
	public User postGetUserByEmailId(User user) {
		return super.postGetUserByEmailId(user);
	}

}
