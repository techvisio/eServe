package com.techvisio.eserve.beans;


public class SearchComplaint {

	private Long complaintId;
	private String complaintCode;
	private String status;
	private String priority;
	private String slaDate;
	private String assignTo;
	private Long customerId;
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(Long complaintId) {
		this.complaintId = complaintId;
	}
	public String getComplaintCode() {
		return complaintCode;
	}
	public void setComplaintCode(String complaintCode) {
		this.complaintCode = complaintCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getAssignTo() {
		return assignTo;
	}
	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}
	public String getSlaDate() {
		return slaDate;
	}
	public void setSlaDate(String slaDate) {
		this.slaDate = slaDate;
	}
	
}
