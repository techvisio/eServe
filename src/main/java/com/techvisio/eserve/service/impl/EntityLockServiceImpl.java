package com.techvisio.eserve.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.CustomerComplaint;
import com.techvisio.eserve.beans.EntityLocks;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.exception.EntityLockedException;
import com.techvisio.eserve.interfaces.Lockable;
import com.techvisio.eserve.manager.EntityLockManager;
import com.techvisio.eserve.service.ComplaintService;
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

	@Autowired
	ComplaintService complaintService;


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

		Lockable lockedObject=null;
		if(entityLocksFromDB==null){
			entityLocks.setLockedBy(CommonUtil.getCurrentUser().getUserName());
			entityLocks.setLockedDate(new Date());
			entityLockManager.saveEntityLock(entityLocks);
		}
		else {
			if(!entityLocksFromDB.getLockedBy().equalsIgnoreCase(CommonUtil.getCurrentUser().getUserName())){
				throw new EntityLockedException("This entity is already being edited by " + entityLocksFromDB.getLockedBy());
			}
		}

		if(entityLocks.getEntityType().equalsIgnoreCase(AppConstants.EntityType.CUSTOMER.toString())){
			lockedObject = customerService.getCustomerbyId(entityLocks.getEntityId());
		}
		else if(entityLocks.getEntityType().equalsIgnoreCase(AppConstants.EntityType.UNIT.toString())){
			lockedObject = customerService.getUnitById(entityLocks.getEntityId());
		}
		else{
			User userFromDB = userService.getUser(entityLocks.getEntityId());
			return userFromDB;
		}	

		lockedObject.setEdited(true);


		return lockedObject;


	}

	@Override
	public Object unlockEntity(String entityType, Long entityId){

		EntityLocks entityLocksFromDB = entityLockManager.getEntity(entityId, entityType);

		String lockeBy = CommonUtil.getCurrentUser().getUserName();

		if(entityLocksFromDB!=null){
			if(entityLocksFromDB.getLockedBy().equalsIgnoreCase(lockeBy)){
				entityLockManager.deleteEntityLock(entityId, entityType, lockeBy);

				if(entityType.equalsIgnoreCase(AppConstants.EntityType.CUSTOMER.toString())){
					Customer customerFromDB = customerService.getCustomerbyId(entityId);
					return customerFromDB;
				}
				else if(entityType.equalsIgnoreCase(AppConstants.EntityType.UNIT.toString())){
					Unit unitFromDB = customerService.getUnitById(entityId);
					return unitFromDB;
				}
				else if(entityType.equalsIgnoreCase(AppConstants.EntityType.COMPLAINT.toString())){
					CustomerComplaint complaintFromDB = complaintService.getCustomerComplaint(entityId);
					return complaintFromDB;
				}

				else if(entityType.equalsIgnoreCase(AppConstants.EntityType.UNIT.toString())){
					User userFromDB = userService.getUser(entityId);
					return userFromDB;
				}

				else{
					return null;
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
		return false;
	}

}
