package com.techvisio.eserve.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

@MappedSuperclass
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@EntityListeners({BasicFieldsCallBack.class})
public class BasicFileds implements Serializable{

	@Column(name="CREATED_BY")
	private String createdBy;
	@Column(name="CREATED_ON")
	private Date createdOn;
	@ManyToOne
	@JoinColumn(name = "Client_Id")
	private Client client;
	
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
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	
	@PrePersist
	protected void onCreate() {
		createdOn = new Date();
	}

}
