package com.techvisio.eserve.beans;


public class CustomerUnitComplaint {

	private Long customerId;
	private String customerName;
	private String contactNo;
	private String customerCode;
	private String emailId;
	private String customerType;
	private Long unitId;
	private String unitCode;
	private Long unitCustomerId;
	private char approvalStatus;
	private String lastApprovedDate;
	private  String lastApprovedBy;
	private String assetNo;
	private String machineSerialNo;
	private String modelNo;
	private String unitCategory;
	private String serviceCategory;
	private String contractStartOnString;
	private String contractExpireOnString;
	private String agreementDuration;
	private String serviceProvider;
	private Long approvedBy;
	private Long srvcAgrmntUnitId;
	private Long workOrderId;
	private String workOrderNo;
	private String workOrderType;
	private String status;
	private String priority;
	private String contactPerson;
	private String slaDateString;
	private String woContactNo;
	private String alternateMobileNo;
	private String woCustomerCode;
	private Long woCustomerId;
	private String woCustomerName;
	private String woEmailId;
	private String woLandLineNo;
	private String woDescription;
	private String woIssue;
	private Long woUnitId;

	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getAssetNo() {
		return assetNo;
	}
	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}
	public String getMachineSerialNo() {
		return machineSerialNo;
	}
	public void setMachineSerialNo(String machineSerialNo) {
		this.machineSerialNo = machineSerialNo;
	}
	public String getModelNo() {
		return modelNo;
	}
	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getServiceCategory() {
		return serviceCategory;
	}
	public void setServiceCategory(String serviceCategory) {
		this.serviceCategory = serviceCategory;
	}
	public String getContractStartOnString() {
		return contractStartOnString;
	}
	public void setContractStartOnString(String contractStartOnString) {
		this.contractStartOnString = contractStartOnString;
	}
	public String getContractExpireOnString() {
		return contractExpireOnString;
	}
	public void setContractExpireOnString(String contractExpireOnString) {
		this.contractExpireOnString = contractExpireOnString;
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
	public String getWorkOrderType() {
		return workOrderType;
	}
	public void setWorkOrderType(String workOrderType) {
		this.workOrderType = workOrderType;
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
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getSlaDateString() {
		return slaDateString;
	}
	public void setSlaDateString(String slaDateString) {
		this.slaDateString = slaDateString;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public char getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(char approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String getUnitCategory() {
		return unitCategory;
	}
	public void setUnitCategory(String unitCategory) {
		this.unitCategory = unitCategory;
	}
	
	public String getAgreementDuration() {
		return agreementDuration;
	}
	public void setAgreementDuration(String agreementDuration) {
		this.agreementDuration = agreementDuration;
	}
	public String getServiceProvider() {
		return serviceProvider;
	}
	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
	public Long getUnitCustomerId() {
		return unitCustomerId;
	}
	public void setUnitCustomerId(Long unitCustomerId) {
		this.unitCustomerId = unitCustomerId;
	}
	public String getLastApprovedDate() {
		return lastApprovedDate;
	}
	public void setLastApprovedDate(String lastApprovedDate) {
		this.lastApprovedDate = lastApprovedDate;
	}
	public String getLastApprovedBy() {
		return lastApprovedBy;
	}
	public void setLastApprovedBy(String lastApprovedBy) {
		this.lastApprovedBy = lastApprovedBy;
	}
	public Long getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(Long approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Long getSrvcAgrmntUnitId() {
		return srvcAgrmntUnitId;
	}
	public void setSrvcAgrmntUnitId(Long srvcAgrmntUnitId) {
		this.srvcAgrmntUnitId = srvcAgrmntUnitId;
	}
	public String getWoContactNo() {
		return woContactNo;
	}
	public void setWoContactNo(String woContactNo) {
		this.woContactNo = woContactNo;
	}
	public String getAlternateMobileNo() {
		return alternateMobileNo;
	}
	public void setAlternateMobileNo(String alternateMobileNo) {
		this.alternateMobileNo = alternateMobileNo;
	}
	public String getWoCustomerCode() {
		return woCustomerCode;
	}
	public void setWoCustomerCode(String woCustomerCode) {
		this.woCustomerCode = woCustomerCode;
	}
	public Long getWoCustomerId() {
		return woCustomerId;
	}
	public void setWoCustomerId(Long woCustomerId) {
		this.woCustomerId = woCustomerId;
	}
	public String getWoCustomerName() {
		return woCustomerName;
	}
	public void setWoCustomerName(String woCustomerName) {
		this.woCustomerName = woCustomerName;
	}
	public String getWoEmailId() {
		return woEmailId;
	}
	public void setWoEmailId(String woEmailId) {
		this.woEmailId = woEmailId;
	}
	public String getWoLandLineNo() {
		return woLandLineNo;
	}
	public void setWoLandLineNo(String woLandLineNo) {
		this.woLandLineNo = woLandLineNo;
	}
	public String getWoDescription() {
		return woDescription;
	}
	public void setWoDescription(String woDescription) {
		this.woDescription = woDescription;
	}
	public String getWoIssue() {
		return woIssue;
	}
	public void setWoIssue(String woIssue) {
		this.woIssue = woIssue;
	}
	public Long getWoUnitId() {
		return woUnitId;
	}
	public void setWoUnitId(Long woUnitId) {
		this.woUnitId = woUnitId;
	}
	@Override
	public String toString() {
		return "CustomerUnitComplaint [customerId=" + customerId
				+ ", customerName=" + customerName + ", contactNo=" + contactNo
				+ ", customerCode=" + customerCode + ", emailId=" + emailId
				+ ", customerType=" + customerType + ", unitId=" + unitId
				+ ", unitCode=" + unitCode + ", unitCustomerId="
				+ unitCustomerId + ", approvalStatus=" + approvalStatus
				+ ", lastApprovedDate=" + lastApprovedDate
				+ ", lastApprovedBy=" + lastApprovedBy + ", assetNo=" + assetNo
				+ ", machineSerialNo=" + machineSerialNo + ", modelNo="
				+ modelNo + ", unitCategory=" + unitCategory
				+ ", serviceCategory=" + serviceCategory
				+ ", contractStartOnString=" + contractStartOnString
				+ ", contractExpireOnString=" + contractExpireOnString
				+ ", agreementDuration=" + agreementDuration
				+ ", serviceProvider=" + serviceProvider + ", approvedBy="
				+ approvedBy + ", srvcAgrmntUnitId=" + srvcAgrmntUnitId
				+ ", workOrderId=" + workOrderId + ", workOrderNo="
				+ workOrderNo + ", workOrderType=" + workOrderType
				+ ", status=" + status + ", priority=" + priority
				+ ", contactPerson=" + contactPerson + ", slaDateString="
				+ slaDateString + ", woContactNo=" + woContactNo
				+ ", alternateMobileNo=" + alternateMobileNo
				+ ", woCustomerCode=" + woCustomerCode + ", woCustomerId="
				+ woCustomerId + ", woCustomerName=" + woCustomerName
				+ ", woEmailId=" + woEmailId + ", woLandLineNo=" + woLandLineNo
				+ ", woDescription=" + woDescription + ", woIssue=" + woIssue
				+ ", woUnitId=" + woUnitId + "]";
	}

}
