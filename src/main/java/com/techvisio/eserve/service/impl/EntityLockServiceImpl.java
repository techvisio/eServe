package com.techvisio.eserve.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.EntityLocks;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.exception.EntityLockedException;
import com.techvisio.eserve.manager.EntityLockManager;
import com.techvisio.eserve.service.CustomerService;
import com.techvisio.eserve.service.EntityLockService;
import com.techvisio.eserve.service.UserService;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;
@Component
@Transactional
public class EntityLockServiceImpl implements EntityLockService{

	@Autowired
	CustomerService customerService;

	@Autowired 
	EntityLockManager entityLockManager;

	@Autowired
	UserService userService;

	@Override
	public EntityLocks getEntity(Long entityId, String entityType) {
		EntityLocks entityLocks = entityLockManager.getEntity(entityId, entityType);
		return entityLocks;
	}

	@Override
	public void saveEntityLock(EntityLocks entityLocks){
		entityLockManager.saveEntityLock(entityLocks);
	}

	@Override
	public Object lockEntity(EntityLocks entityLocks){

		EntityLocks entityLocksFromDB = entityLockManager.getEntity(entityLocks.getEntityId(), entityLocks.getEntityType());

		boolean entityLockBySameUser = isEntityLocked(entityLocks.getEntityId(), entityLocks.getEntityType(), CommonUtil.getCurrentUser().getUserName());

		//		temprory work
		if(entityLockBySameUser){
			unlockEntity(entityLocks.getEntityType(), entityLocks.getEntityId());
		}
		//

		else{	
			if(entityLocksFromDB==null){
				entityLocks.setLockedBy(CommonUtil.getCurrentUser().getUserName());
				entityLocks.setLockedDate(new Date());
				entityLockManager.saveEntityLock(entityLocks);
				if(entityLocks.getEntityType().equalsIgnoreCase(AppConstants.entityType.CUSTOMER.toString())){
					Customer customerFromDB = customerService.getCustomer(entityLocks.getEntityId());
					return customerFromDB;
				}
				else if(entityLocks.getEntityType().equalsIgnoreCase(AppConstants.entityType.UNIT.toString())){
					Unit unitFromDB = customerService.getUnit(entityLocks.getEntityId());
					return unitFromDB;
				}
				else{
					User userFromDB = userService.getUser(entityLocks.getEntityId());
					return userFromDB;
				}	
			}
			else {
				throw new EntityLockedException("This entity is already being edited by " + entityLocksFromDB.getLockedBy());
			}
		}
		return entityLockBySameUser;
	}

	@Override
	public Object unlockEntity(String entityType, Long entityId){

		EntityLocks entityLocksFromDB = entityLockManager.getEntity(entityId, entityType);

		String lockeBy = CommonUtil.getCurrentUser().getUserName();

		if(entityLocksFromDB!=null){
			if(entityLocksFromDB.getLockedBy().equalsIgnoreCase(lockeBy)){
				entityLockManager.deleteEntityLock(entityId, entityType, lockeBy);

				if(entityType.equalsIgnoreCase(AppConstants.entityType.CUSTOMER.toString())){
					Customer customerFromDB = customerService.getCustomer(entityId);
					return customerFromDB;
				}
				else if(entityType.equalsIgnoreCase(AppConstants.entityType.UNIT.toString())){
					Unit unitFromDB = customerService.getUnit(entityId);
					return unitFromDB;
				}
				else{
					User userFromDB = userService.getUser(entityId);
					return userFromDB;
				}	
			}

			else{
				throw new EntityLockedException("current user does not hold lock for this entity. \n This entity is locked by " + entityLocksFromDB.getLockedBy() +" and same person can unlock this entity");
			}
		}
		return null;
	}	

	@Override
	public boolean isEntityLocked(Long entityId, String entityType, String userName){

		EntityLocks entityLocksFromDB = entityLockManager.getEntity(entityId, entityType);
		if(entityLocksFromDB!=null && entityLocksFromDB.getLockedBy().equalsIgnoreCase(userName)){
			return true; 
		}
		else{
			return false;
		}
	}

}
