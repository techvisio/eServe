package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name = "TB_ADDRESS_DETAIL")    
public class Address extends BasicEntity{


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ADDRESS_ID")
	private Long addressId;
	@Column(name="ADDRESS")
	private String address;
	@Column(name="CONTACT_NO")
	private String contactNo;
	@Column(name="CITY")
	private String city;
	@Column(name="PINCODE")
	private int pincode;
	@ManyToOne
	@JoinColumn(name="STATE_ID")
	private State State;
	@Column(name="ADDRESS_TYPE")
	private String addressType;


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

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public State getState() {
		return State;
	}

	public void setState(State state) {
		State = state;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

}
