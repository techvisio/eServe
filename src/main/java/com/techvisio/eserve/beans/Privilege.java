package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TB_PRIVILEGE")    
public class Privilege extends BasicEntity{

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "Privilege_Id")
	private Long privilegeId;
	@Column(name = "Privilege")
	private String privilege;
	@Column(name = "Description")
	private String description;
	@Column(name = "Type")
	private String type;
	@Transient
	private boolean granted;

	public boolean isGranted() {
		return granted;
	}

	public void setGranted(boolean granted) {
		this.granted = granted;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public Long getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(Long priviledgeId) {
		this.privilegeId = priviledgeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
