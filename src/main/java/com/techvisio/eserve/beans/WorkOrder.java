package com.techvisio.eserve.beans;

import java.util.Date;

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
import com.techvisio.eserve.interfaces.Lockable;
import com.techvisio.eserve.util.AppConstants;

@Entity
@Table(name = "TB_WORK_ORDER")
public class WorkOrder extends BasicEntity implements Lockable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="WORK_ORDER_ID")
	private Long workOrderId;
	@Column(name="WORK_ORDER_NO")
	private String workOrderNo;
	@Column(name="WORK_ORDER_TYPE")
	private String workOrderType;
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
	private String status = AppConstants.ComplaintStatus.UNASSIGNED.name();
	@Column(name="DESCRIPTION")
	private String description;
	@Column(name="PRIORITY")
	private String priority;
	@Column(name="SLA_DATE")
	private Date slaDate;
	@Column(name="CONTACT_PERSON")
	private String contactPerson;
	@Column(name="ALTERNATE_MOBILE_NO")
	private String alternateMobileNo;
	@Column(name="LANDLINE_NO")
	private String landlineNo;
	@Transient
	private String slaDateString;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "workOrder", fetch = FetchType.LAZY)
	private WorkOrderResolution workOrderResolution;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "workOrder", fetch = FetchType.LAZY)
	private WorkOrderAssignment workOrderAssignment;

	@OneToOne(fetch=FetchType.EAGER )
	@JoinColumn(name="UNIT_ID")
	private Unit unit;

	@Transient
	private boolean edited = false;
	
	public Long getWorkOrderId() {
		return workOrderId;
	}
	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
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
	public WorkOrderResolution getWorkOrderResolution() {
		return workOrderResolution;
	}
	public void setWorkOrderResolution(WorkOrderResolution workOrderResolution) {
		this.workOrderResolution = workOrderResolution;
	}
	public Long getParentComplaintId() {
		return parentComplaintId;
	}
	public void setParentComplaintId(Long parentComplaintId) {
		this.parentComplaintId = parentComplaintId;
	}
	@JsonProperty
	public WorkOrderAssignment getWorkOrderAssignment() {
		return workOrderAssignment;
	}
	public void setWorkOrderAssignment(WorkOrderAssignment workOrderAssignment) {
		this.workOrderAssignment = workOrderAssignment;
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
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getAlternateMobileNo() {
		return alternateMobileNo;
	}
	public void setAlternateMobileNo(String alternateMobileNo) {
		this.alternateMobileNo = alternateMobileNo;
	}
	public String getLandlineNo() {
		return landlineNo;
	}
	public void setLandlineNo(String landlineNo) {
		this.landlineNo = landlineNo;
	}
	public boolean isEdited() {
		return edited;
	}
	public void setEdited(boolean edited) {
		this.edited = edited;
	}
	public String getWorkOrderType() {
		return workOrderType;
	}
	public void setWorkOrderType(String workOrderType) {
		this.workOrderType = workOrderType;
	}
	public String getWorkOrderNo() {
		return workOrderNo;
	}
	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}

}
