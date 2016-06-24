package com.techvisio.eserve.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.EntityLocks;
import com.techvisio.eserve.db.EntityLockDao;
import com.techvisio.eserve.manager.EntityLockManager;

@Component
public class EntityLockManagerImpl implements EntityLockManager{

	@Autowired 
	EntityLockDao entityLockDao;

	@Override
	public EntityLocks getEntity(Long entityId, String entityType) {
		EntityLocks entityLocks = entityLockDao.getEntity(entityId,entityType);
		return entityLocks;
	}

	@Override
	public void saveEntityLock(EntityLocks entityLocks){
		entityLockDao.saveEntityLock(entityLocks);
	}

	@Override
	public void deleteEntityLock(Long entityId, String entityType, String lockedBy) {
		entityLockDao.deleteEntityLock(entityId, entityType, lockedBy);
	}

	@Override
	public EntityLocks getEntityByUserName(Long entityId, String entityType,
			String userName) {
		EntityLocks entityLocks = entityLockDao.getEntityByUserName(entityId, entityType, userName);
		return entityLocks;
	}
}
