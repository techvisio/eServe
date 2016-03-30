package com.techvisio.eserve.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

@MappedSuperclass
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@EntityListeners({BasicEntityCallback.class})
public class BasicFileds {

	@Column(name="CREATED_BY")
	private String createdBy;
	@Column(name="CREATED_ON")
	private Date createdOn;

	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@PrePersist
	protected void onCreate() {
		createdOn = new Date();
	}

}
