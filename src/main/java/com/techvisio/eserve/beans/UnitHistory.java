package com.techvisio.eserve.beans;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_UNIT_HISTORY")
public class UnitHistory {


	@EmbeddedId
	private CompositeKeyUnitHistory compositeKey;
	@Column(name="CUSTOMER_ID") 
	private Long customerId;
	@Column(name="UNIT_CODE")
	private String unitCode;

	@Column(name="APPROVAL_STATUS")
	private char approvalStatus;


	@Column(name="ASSET_NO")
	private String assetNo;

	@Column(name="MACHINE_SERIAL_NO")
	private String machineSerialNo;

	@Column(name="MODEL_NO")
	private String modelNo;


	@OneToOne
	@JoinColumn(name="UNIT_CATEGORY_ID")
	private UnitCategory unitCategory;

	@OneToOne
	@JoinColumn(name="ADDRESS_ID")
	private Address address;

	public CompositeKeyUnitHistory getCompositeKey() {
		return compositeKey;
	}

	public void setCompositeKey(CompositeKeyUnitHistory compositeKey) {
		this.compositeKey = compositeKey;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
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

	public UnitCategory getUnitCategory() {
		return unitCategory;
	}

	public void setUnitCategory(UnitCategory unitCategory) {
		this.unitCategory = unitCategory;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}


}
