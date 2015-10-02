package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRIVILEGE")    
public class Privilege extends BasicEntity{

	private String privilege;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "Privilege_Id")
	private Long privilegeId;
	@Column(name = "PRIVILEGE_ID")
	private String description;
	@Column(name = "Type")
	private String type;

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
