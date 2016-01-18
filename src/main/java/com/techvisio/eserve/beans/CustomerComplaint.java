package com.techvisio.eserve.beans;

import java.util.ArrayList;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.techvisio.eserve.util.AppConstants;

@Entity
@Table(name = "TB_CUSTOMER_COMPLAINT")
public class CustomerComplaint extends BasicEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="COMPLAINT_ID")
	private Long complaintId;
	@Column(name="COMPLAINT_CODE")
	private String complaintCode;
	@Column(name="PARENT_COMPLAINT_ID")
	private Long parentComplaintId;
	@Column(name="CUSTOMER_ID")
	private Long customerId;
	@Column(name="CUSTOMER_CODE")
	private String customerCode;
	@Column(name="CUSTOMER_NAME")
	private String customerName;
	@Column(name="EMAIL_ID")
	private String emailId;
	@Column(name="CONTACT_NO")
	private String contactNo;
	@OneToOne
	@JoinColumn(name="ISSUE_ID")
	private Issue issue;
	@Column(name="STATUS")
	private String status = AppConstants.complaintStatus.UNASSIGNED.name();
	@Column(name="DESCRIPTION")
	private String description;
	@Column(name="PRIORITY")
	private String priority;
	@Column(name="SLA_DATE")
	private Date slaDate;

	@Transient
	private String slaDateString;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "customerComplaint", fetch = FetchType.LAZY)
	private ComplaintResolution complaintResolution;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "customerComplaint", fetch = FetchType.LAZY)
	private ComplaintAssignment complaintAssignment;

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

	public Issue getIssue() {
		return issue;
	}
	public void setIssue(Issue issue) {
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
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@JsonProperty
	public ComplaintResolution getComplaintResolution() {
		return complaintResolution;
	}
	public void setComplaintResolution(ComplaintResolution complaintResolution) {
		this.complaintResolution = complaintResolution;
	}
	public Long getParentComplaintId() {
		return parentComplaintId;
	}
	public void setParentComplaintId(Long parentComplaintId) {
		this.parentComplaintId = parentComplaintId;
	}
	@JsonProperty
	public ComplaintAssignment getComplaintAssignment() {
		return complaintAssignment;
	}
	public void setComplaintAssignment(ComplaintAssignment complaintAssignment) {
		this.complaintAssignment = complaintAssignment;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}

	@JsonIgnore
	public Date getSlaDate() {
		return slaDate;
	}

	@JsonIgnore
	public void setSlaDate(Date slaDate) {
		this.slaDate = slaDate;
	}

	public String getSlaDateString() {
		if (this.slaDate == null)
			return null;

		try {
			DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
			return fmt.print(this.slaDate.getTime());

		} catch (Exception e) {

		}
		return null;

	}
	public void setSlaDateString(String slaDateString) {
		DateTimeFormatter parser2 = ISODateTimeFormat.dateTime().withZoneUTC();
		if(!StringUtils.isEmpty(slaDateString)){
			this.slaDate = parser2.parseDateTime(slaDateString).toDate();
		}
	}
	
	public String getComplaintCode() {
		return complaintCode;
	}
	
	public void setComplaintCode(String complaintCode) {
		this.complaintCode = complaintCode;
	}

}
