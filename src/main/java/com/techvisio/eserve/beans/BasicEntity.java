package com.techvisio.eserve.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class BasicEntity implements Serializable {

	@Column(name="Created_By")
	private String createdBy;
	@Column(name="Updated_By")
	private String updatedBy;
	@Column(name="Created_On")
	private Date createdOn;
	@Column(name="Updated_On")
	private Date updatedOn;
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	@PrePersist
	  protected void onCreate() {
	    createdOn = new Date();
	  }

	  @PreUpdate
	  protected void onUpdate() {
	    updatedOn = new Date();
	  }
	
}
