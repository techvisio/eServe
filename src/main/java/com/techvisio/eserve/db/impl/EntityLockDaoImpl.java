package com.techvisio.eserve.db.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.EntityLocks;
import com.techvisio.eserve.db.EntityLockDao;

@Component
public class EntityLockDaoImpl extends BaseDao implements EntityLockDao{

	@Override
	public EntityLocks getEntityByUserName(Long entityId, String entityType, String userName) {
		String queryString="FROM EntityLocks el WHERE el.entityId = "+entityId+" and el.entityType = " +" '"+ entityType +" '" +" and lockedBy = "+"'"+userName+"'";
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<EntityLocks> entityLocks= (List<EntityLocks>)query.getResultList();
		if(entityLocks != null && entityLocks.size()>0){
			return entityLocks.get(0);
		}
		return null;
	}

	@Override
	public EntityLocks getEntity(Long entityId, String entityType) {
		String queryString="FROM EntityLocks el WHERE el.entityId = "+entityId+" and el.entityType = " +" '"+ entityType +" '";
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<EntityLocks> entityLocks= (List<EntityLocks>)query.getResultList();
		if(entityLocks != null && entityLocks.size()>0){
			return entityLocks.get(0);
		}
		return null;
	}

	@Override
	public void saveEntityLock(EntityLocks entityLocks){
		getEntityManager().persist(entityLocks);
		getEntityManager().flush();
	}

	@Override
	public void deleteEntityLock(Long entityId, String entityType, String lockedBy){
		String deleteQuery = "Delete from TB_ENTITY_LOCKS where ENTITY_ID =:ENTITY_ID and ENTITY_TYPE=:ENTITY_TYPE and LOCKED_BY=:LOCKED_BY";

		Query query=(Query) getEntityManager().createNativeQuery(deleteQuery).setParameter("ENTITY_ID", entityId).setParameter("ENTITY_TYPE", entityType).setParameter("LOCKED_BY", lockedBy);;
		query.executeUpdate();
	}
}