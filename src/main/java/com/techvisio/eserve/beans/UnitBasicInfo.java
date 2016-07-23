package com.techvisio.eserve.beans;

public class UnitBasicInfo {

	private Long customerId;
	private String customerName;
	private String contactNo;
	private String customerCode;
	private String emailId;
	private String customerType;
	private Long unitId;
	private String unitCode;
	private String assetNo;
	private String machineSerialNo;
	private String modelNo;
	private String unitType;
	private String contractExpiredOn; 
	private String contractStartOn;
	private String serviceCategory;
	private String serviceProvider;
	private String address;
	private String city;
	private String AddresscontactNo;
	private int pincode;
	private String State;
	
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddresscontactNo() {
		return AddresscontactNo;
	}
	public void setAddresscontactNo(String addresscontactNo) {
		AddresscontactNo = addresscontactNo;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getPincode() {
		return pincode;
	}
	public void setPincode(int pincode) {
		this.pincode = pincode;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getUnitType() {
		return unitType;
	}
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	public String getContractExpiredOn() {
		return contractExpiredOn;
	}
	public void setContractExpiredOn(String contractExpiredOn) {
		this.contractExpiredOn = contractExpiredOn;
	}
	public String getContractStartOn() {
		return contractStartOn;
	}
	public void setContractStartOn(String contractStartOn) {
		this.contractStartOn = contractStartOn;
	}
	public String getServiceCategory() {
		return serviceCategory;
	}
	public void setServiceCategory(String serviceCategory) {
		this.serviceCategory = serviceCategory;
	}
	public String getServiceProvider() {
		return serviceProvider;
	}
	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	@Override
	public String toString() {
		return "UnitBasicInfo [customerId=" + customerId + ", customerName="
				+ customerName + ", contactNo=" + contactNo + ", customerCode="
				+ customerCode + ", emailId=" + emailId + ", customerType="
				+ customerType + ", unitId=" + unitId + ", unitCode="
				+ unitCode + ", assetNo=" + assetNo + ", machineSerialNo="
				+ machineSerialNo + ", modelNo=" + modelNo + ", unitType="
				+ unitType + ", contractExpiredOn=" + contractExpiredOn
				+ ", contractStartOn=" + contractStartOn + ", serviceCategory="
				+ serviceCategory + ", serviceProvider=" + serviceProvider
				+ ", address=" + address + ", city=" + city
				+ ", AddresscontactNo=" + AddresscontactNo + ", pincode="
				+ pincode + ", State=" + State + "]";
	}

}
