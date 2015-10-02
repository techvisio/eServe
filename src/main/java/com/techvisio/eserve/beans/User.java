package com.techvisio.eserve.beans;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "User_Id")
	private Long userId;
	@Column(name = "Name")
	private String name;
	@Column(name = "Email_Id")
	private String emailId;

	@ManyToMany(cascade = { CascadeType.ALL },fetch=FetchType.EAGER)
	@JoinTable(name="USER_ROLE", joinColumns = { 
			@JoinColumn(name = "User_Id") }, 
			inverseJoinColumns = { @JoinColumn(name = "Role_Id") })
	private List<Role> roles;
	@Column(name = "Password")
	private char[] password;
	@Column(name = "IS_ACTIVE")
	private boolean active;
	@Column(name = "Force_Password_Change")
	private boolean forcePasswordChange;
	@OneToMany(cascade = { CascadeType.ALL },fetch=FetchType.EAGER)
	@JoinColumn(name="User_Id")
	private List<SecurityQuestion> securityQuestions;
	@Column(name = "User_Name")
	private String userName;
	@Column(name = "Department")
	private String department;
	@Column(name = "Designation")
	private String designation;
	@Column(name = "DOB")
	private Date DOB; 
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isForcePasswordChange() {
		return forcePasswordChange;
	}

	public void setForcePasswordChange(boolean forcePasswordChange) {
		this.forcePasswordChange = forcePasswordChange;
	}

	public List<SecurityQuestion> getSecurityQuestions() {
		return securityQuestions;
	}

	public void setSecurityQuestions(List<SecurityQuestion> securityQuestions) {
		this.securityQuestions = securityQuestions;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Date getDOB() {
		return DOB;
	}

	public void setDOB(Date dOB) {
		DOB = dOB;
	}
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
}
