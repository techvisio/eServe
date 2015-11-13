package com.techvisio.eserve.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.techvisio.eserve.util.CommonUtil;

@Entity
@Table(name = "TB_CUSTOMER_DETAIL")    
public class Customer extends BasicEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Customer_Id")
	private Long customerId;
	@Column(name="Customer_Name")
	private String customerName;
	@Column(name="Contact_No")
	private String contactNo;
	@Column(name="Email_Id")
	private String emailId;
	@Column(name="Customer_Type")
	private String customerType;
	@OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="Address_Id")
	private Address address;
	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER )
	@JoinColumn(name="Customer_Id")
	private List<Unit> units=new ArrayList<Unit>();
	
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
	
	public List<Unit> getUnits() {
		return units;
	}
	public void setUnits(List<Unit> units) {
		this.units = units;
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
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
}
