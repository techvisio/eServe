package com.techvisio.eserve.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_CUSTOMER_COMPLAINT")
public class CustomerComplaint {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="COMPLAINT_ID")
	private Long complaintId;
	@Column(name="CUSTOMER_CODE")
	private String customerCode;
	@Column(name="CUSTOMER_NAME")
	private String customerName;
	@Column(name="EMAIL_ID")
	private String emailId;
	@Column(name="CONTACT_NO")
	private String contactNo;
	@Column(name="ISSUE")
	private String issue;
	@Column(name="DESCRIPTION")
	private String description;
	
	@OneToOne(fetch=FetchType.EAGER )
	@JoinColumn(name="UNIT_ID")
	private Unit unit;
	
	public Long getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(Long complaintId) {
		this.complaintId = complaintId;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
}
