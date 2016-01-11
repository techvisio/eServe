package com.techvisio.eserve.beans;

import java.util.List;

public class SearchComplaintCustomer {

private Long customerId;
private String customerCode;
private String customerName;
private String contactNo;
private String emailId;
private String customerType;

private List<SearchComplaintUnit> units;

public Long getCustomerId() {
	return customerId;
}

public void setCustomerId(Long customerId) {
	this.customerId = customerId;
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

public String getContactNo() {
	return contactNo;
}

public void setContactNo(String contactNo) {
	this.contactNo = contactNo;
}

public String getEmailId() {
	return emailId;
}

public void setEmailId(String emailId) {
	this.emailId = emailId;
}

public String getCustomerType() {
	return customerType;
}

public void setCustomerType(String customerType) {
	this.customerType = customerType;
}

public List<SearchComplaintUnit> getUnits() {
	return units;
}

public void setUnits(List<SearchComplaintUnit> units) {
	this.units = units;
}


}
