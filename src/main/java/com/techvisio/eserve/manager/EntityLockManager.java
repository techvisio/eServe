package com.techvisio.eserve.manager;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.EntityLocks;

@Component
public interface EntityLockManager {

	EntityLocks getEntityByUserName(Long entityId, String entityType,
			String userName);
	
	EntityLocks getEntity(Long entityId, String entityType);

	void saveEntityLock(EntityLocks entityLocks);

	void deleteEntityLock(Long entityId, String entityType, String lockedBy);
}
