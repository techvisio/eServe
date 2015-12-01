package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_DEPARTMENT_MASTER")    
public class Department extends BasicEntity{

	@Id
	@Column(name = "DEPARTMENT_ID")
	private Long departmentId;
	@Column(name = "DEPARTMENT")
	private String department;
	@Column(name = "CLIENT_ID")
	private Long clientId;
	
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
}
