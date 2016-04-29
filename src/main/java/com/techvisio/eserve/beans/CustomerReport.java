package com.techvisio.eserve.beans;

public class CustomerReport {
	
	//Customer
	private Long customerId;
	
	private String customerName;
	
	private String contactNo;
	
	private String customerCode;
	
	private String emailId;

	private String customerType;
	
	//Unit
	
	private Long unitId;
	 
	private String unitCode;
	
	private Double versionId;
	
	private char approvalStatus;
	
	private String assetNo;
	
	private String machineSerialNo;
	
	private String modelNo;
	
	private String lastApprovalDate;
	
	private String unitType;
	
	
	//Service Agreement
	
	private Long serviceAgreementId;
	
	private String serviceCategory;
	
	private String contractStartOn;
	
	private String contractExpireOn;
	
	private Long approvedBy;
	
	
	//Getter Setter
	
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

	public Double getVersionId() {
		return versionId;
	}

	public void setVersionId(Double versionId) {
		this.versionId = versionId;
	}

	public char getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(char approvalStatus) {
		this.approvalStatus = approvalStatus;
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
	
	
	
	public Long getServiceAgreementId() {
		return serviceAgreementId;
	}

	public void setServiceAgreementId(Long serviceAgreementId) {
		this.serviceAgreementId = serviceAgreementId;
	}

	public String getServiceCategory() {
		return serviceCategory;
	}

	public void setServiceCategory(String serviceCategory) {
		this.serviceCategory = serviceCategory;
	}

	
	public Long getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(Long approvedBy) {
		this.approvedBy = approvedBy;
	}
	
	


	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	
	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getContractStartOn() {
		return contractStartOn;
	}

	public void setContractStartOn(String contractStartOn) {
		this.contractStartOn = contractStartOn;
	}

	public String getContractExpireOn() {
		return contractExpireOn;
	}

	public void setContractExpireOn(String contractExpireOn) {
		this.contractExpireOn = contractExpireOn;
	}

	public String getLastApprovalDate() {
		return lastApprovalDate;
	}

	public void setLastApprovalDate(String lastApprovalDate) {
		this.lastApprovalDate = lastApprovalDate;
	}
	
	@Override
	public String toString() {
		return "CustomerReport [customerId=" + customerId + ", customerName=" + customerName + ", contactNo="
				+ contactNo + ", customerCode=" + customerCode + ", emailId=" + emailId + ", customerType="
				+ customerType + ", unitId=" + unitId + ", unitCode=" + unitCode + ", versionId=" + versionId
				+ ", approvalStatus=" + approvalStatus + ", assetNo=" + assetNo + ", machineSerialNo=" + machineSerialNo
				+ ", modelNo=" + modelNo + ", lastApprovalDate=" + lastApprovalDate + ", unitType=" + unitType
				+ ", serviceAgreementId=" + serviceAgreementId + ", serviceCategory=" + serviceCategory
				+ ", contractStartOn=" + contractStartOn + ", contractExpireOn=" + contractExpireOn + ", approvedBy="
				+ approvedBy + "]";
	}

	
}
