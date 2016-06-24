package com.techvisio.eserve.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.EntityLocks;

@Component
@Transactional
public interface EntityLockService {

	EntityLocks getEntity(Long entityId, String entityType);

	void saveEntityLock(EntityLocks entityLocks);

	void lockEntity(EntityLocks entityLocks);

	Object unlockEntity(String entityType, Long entityId);

	boolean isEntityLocked(Long entityId, String entityType, String userName);
}
