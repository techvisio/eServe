package com.techvisio.eserve.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TB_ENTITY_LOCKS")
public class EntityLocks {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ENTITY_LOCK_ID")
	private Long entityLockId;
	@Column(name = "ENTITY_ID")	
	private Long entityId;
	@Column(name = "ENTITY_TYPE")	
	private String entityType;
	@Column(name = "LOCKED_BY")	
	private String lockedBy;
	@Column(name = "LOCKED_DATE")	
	private Date lockedDate;

	@Transient
	private boolean isEdited;

	public Long getEntityId() {
		return entityId;
	}
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public Date getLockedDate() {
		return lockedDate;
	}
	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}
	public Long getEntityLockId() {
		return entityLockId;
	}
	public void setEntityLockId(Long entityLockId) {
		this.entityLockId = entityLockId;
	}
	public String getLockedBy() {
		return lockedBy;
	}
	public void setLockedBy(String lockedBy) {
		this.lockedBy = lockedBy;
	}
	public boolean isEdited() {
		return isEdited;
	}
	public void setEdited(boolean isEdited) {
		this.isEdited = isEdited;
	}

}
