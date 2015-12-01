package com.techvisio.eserve.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_USER")
public class User extends BasicEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -378250549629261597L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "EMAIL_ID")
	private String emailId;

	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinTable(name = "TB_USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	private List<Role> roles;
	
	@Column(name = "PASSWORD")
	private char[] password;
	
	@Transient
	private char[] newPassword;
	
	@Column(name = "IS_ACTIVE")
	private boolean active;
	
	@Column(name = "FORCE_PASSWORD_CHANGE")
	private boolean forcePasswordChange;

	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private SecurityQuestion securityQuestion = new SecurityQuestion();

	@Column(name = "USER_NAME")
	private String userName;
	
	@ManyToOne
	@JoinColumn(name = "DEPARTMENT_ID")
	private Department department;
	
	@ManyToOne
	@JoinColumn(name = "DESIGNATION_ID")
	private Designation designation;

	@Temporal(TemporalType.DATE)
	@Column(name = "DOB")
	@JsonIgnore
	private Date DOB;

	@Transient
	private String dobString;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name="USER_ID")
	private List<UserPrivilege> privileges = new ArrayList<UserPrivilege>();

	
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

	public char[] getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(char[] newPassword) {
		this.newPassword = newPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public SecurityQuestion getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(SecurityQuestion securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	@JsonIgnore
	public Date getDOB() {
		return DOB;
	}

	@JsonIgnore
	public void setDOB(Date dOB) {
		DOB = dOB;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getDobString() {
		if (this.DOB == null)
			return null;

		try {
			DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
			return fmt.print(this.DOB.getTime());

		} catch (Exception e) {

		}
		return null;
	}

	public void setDobString(String dobString) {
		DateTimeFormatter parser2 = ISODateTimeFormat.dateTime().withZoneUTC();
		if(!StringUtils.isEmpty(dobString)){
		this.DOB = parser2.parseDateTime(dobString).toDate();
		}
	}

	public List<UserPrivilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<UserPrivilege> privileges) {
		this.privileges = privileges;
	}

	
}
