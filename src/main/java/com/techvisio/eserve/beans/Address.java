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
	@Column(name="Address_Id")
	private Long addressId;
	@Column(name="Address_1")
	private String address1;
	@Column(name="Address_2")
	private String address2;
	@Column(name="Telephone")
	private String telephone;
	@Column(name="Mobile")
	private String mobile;
	@Column(name="City")
	private String city;
	@Column(name="Pincode")
	private int pincode;
	@ManyToOne
	@JoinColumn(name="State_Id")
	private State State;
	@Column(name="Address_Type")
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

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


}
