package com.techvisio.eserve.db;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.EntityLocks;

@Component
public interface EntityLockDao {

	EntityLocks getEntity(Long entityId, String entityType);

	void saveEntityLock(EntityLocks entityLocks);

	void deleteEntityLock(Long entityId, String entityType, String lockedBy);

	EntityLocks getEntityByUserName(Long entityId, String entityType,
			String userName);

}
