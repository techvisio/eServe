package com.techvisio.eserve.beans;

import java.text.SimpleDateFormat;
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
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_USER")
public class User extends BasicEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "User_Id")
	private Long userId;
	@Column(name = "First_Name")
	private String firstName;
	@Column(name = "Last_Name")
	private String lastName;
	@Column(name = "Email_Id")
	private String emailId;

	@ManyToMany(cascade = { CascadeType.ALL },fetch=FetchType.EAGER)
	@JoinTable(name="TB_USER_ROLE", joinColumns = { 
			@JoinColumn(name = "User_Id") }, 
			inverseJoinColumns = { @JoinColumn(name = "Role_Id") })
	private List<Role> roles;
	@Column(name = "Password")
	private char[] password;
	@Transient
	private char[] newPassword;
	@Column(name = "IS_ACTIVE")
	private boolean active;
	@Column(name = "Force_Password_Change")
	private boolean forcePasswordChange;
	
	@OneToOne(cascade=CascadeType.ALL)  
    @PrimaryKeyJoinColumn
	private SecurityQuestion securityQuestion=new SecurityQuestion();
	
	@Column(name = "User_Name")
	private String userName;
	@OneToOne
	@JoinColumn(name="Department_Id")
	private Department department;
	@OneToOne
	@JoinColumn(name="Designation_Id")
	private Designation designation;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DOB")
	@JsonIgnore
	private Date DOB;
	
	@Transient
	private String dobString;
	
	@JoinTable(name="TB_USER_PRIVILEGE",
		        joinColumns=
		            @JoinColumn(name="USER_ID"),
		        inverseJoinColumns=
		            @JoinColumn(name="Privilege_Id")
		        )
	@ManyToMany(fetch=FetchType.EAGER)
	private List<Privilege> privileges;
	
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

	public List<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
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
		if (this.DOB == null) return null;

        try {
            DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
            return fmt.print(this.DOB.getTime());

        } catch (Exception e) {
         
        }
        return null;
	}

	public void setDobString(String dobString) {
		DateTimeFormatter parser2 = ISODateTimeFormat.dateTime().withZoneUTC();
        this.DOB=parser2.parseDateTime(dobString).toDate();
	}


	
}
