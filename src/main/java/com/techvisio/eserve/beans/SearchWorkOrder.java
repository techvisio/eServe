package com.techvisio.eserve.beans;


public class SearchWorkOrder {

	private Long workOrderId;
	private String workOrderNo;
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
	public Long getWorkOrderId() {
		return workOrderId;
	}
	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
	}
	public String getWorkOrderNo() {
		return workOrderNo;
	}
	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
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
