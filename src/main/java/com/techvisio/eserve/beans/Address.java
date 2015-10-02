package com.techvisio.eserve.beans;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name = "ADDRESS_DETAIL")    
public class Address extends BasicEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Address_Id")
	private Long addressId;
	@Column(name="House_No")
	private String houseNo;
	@Column(name="Locality")
	private String locality;
	@Column(name="Landmark")
	private String landmark;
	@Column(name="District")
	private String district;
	@Column(name="City")
	private String city;
	@Column(name="Pincode")
	private int pincode;
	@ManyToOne
	@JoinColumn(name="State_Id")
	private State State;
	@Column(name="File_No")
	private Long fileNo;
	@Column(name="Address_Type")
	private String addressType;

	
	public String getHouseNo() {
		return houseNo;
	}
	
	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}
	
	
	public String getLocality() {
		return locality;
	}
	
	public void setLocality(String locality) {
		this.locality = locality;
	}
	
	
	public String getLandmark() {
		return landmark;
	}
	
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}
	
	
	public String getDistrict() {
		return district;
	}
	
	public void setDistrict(String district) {
		this.district = district;
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
	
	public Long getFileNo() {
		return fileNo;
	}

	public void setFileNo(Long fileNo) {
		this.fileNo = fileNo;
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

}
