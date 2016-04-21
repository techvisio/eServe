package com.techvisio.eserve.beans;

import java.util.Date;

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
	
	private Date lastApprovalDate;
	
	private String unitType;
	
	
	//Service Agreement
	
	private Long serviceAgreementId;
	
	private String serviceCategory;
	
	private Date contractStartOn;
	
	private Date contractExpireOn;
	
	private Long approvedBy;
	
	//Service Agreement Finance
	
	private Long serviceAgreementFinanceId;
	
	private Double agreementAmount;

	
	//Customer Getter Setter
	
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

	
	
	//Unit Getter Setter
	
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
	
	
	//Service Agreement Getter Setter
	
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

	public Date getContractStartOn() {
		return contractStartOn;
	}

	public void setContractStartOn(Date contractStartOn) {
		this.contractStartOn = contractStartOn;
	}

	public Date getContractExpireOn() {
		return contractExpireOn;
	}

	public void setContractExpireOn(Date contractExpireOn) {
		this.contractExpireOn = contractExpireOn;
	}

	public Long getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(Long approvedBy) {
		this.approvedBy = approvedBy;
	}
	
	
	//Service Agreement Finance Getter Setter
	
	public Long getServiceAgreementFinanceId() {
		return serviceAgreementFinanceId;
	}

	public void setServiceAgreementFinanceId(Long serviceAgreementFinanceId) {
		this.serviceAgreementFinanceId = serviceAgreementFinanceId;
	}

	public Double getAgreementAmount() {
		return agreementAmount;
	}

	public void setAgreementAmount(Double agreementAmount) {
		this.agreementAmount = agreementAmount;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public Date getLastApprovalDate() {
		return lastApprovalDate;
	}

	public void setLastApprovalDate(Date lastApprovalDate) {
		this.lastApprovalDate = lastApprovalDate;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	
    
	
	


}
