package com.techvisio.eserve.beans;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;

@Entity
@Table(name = "TB_UNIT_DETAIL")
public class Unit extends BasicEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="UNIT_ID")
	private Long unitId;
	@Column(name="CUSTOMER_ID") 
	private Long customerId;
	@Column(name="UNIT_CODE")
	private String unitCode;
	@Column(name="VERSION_ID")
	private Double versionId = 0.0;
	@Column(name="APPROVAL_STATUS")
	private char approvalStatus = AppConstants.PENDING.charAt(0);
	
	
	@Column(name="ASSET_NO")
	private String assetNo;
	
	@Column(name="MACHINE_SERIAL_NO")
	private String machineSerialNo;
	
	@Column(name="MODEL_NO")
	private String modelNo;
	
		
	@OneToOne
	@JoinColumn(name="UNIT_CATEGORY_ID")
	private UnitCategory unitCategory;

	@OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="ADDRESS_ID")
	private Address address;
	
	@OneToOne(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinColumn(name="UNIT_ID")
	private ServiceAgreement serviceAgreement;
	
	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinColumn(name="UNIT_ID")
	private List<EquipmentDetail> equipmentDetails;
	
	public String getAssetNo() {
		return assetNo;
	}
	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}
		public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	
	public UnitCategory getUnitCategory() {
		return unitCategory;
	}
	public void setUnitCategory(UnitCategory unitCategory) {
		this.unitCategory = unitCategory;
	}
	public List<EquipmentDetail> getEquipmentDetails() {
		return equipmentDetails;
	}
	public void setEquipmentDetails(List<EquipmentDetail> equipmentDetails) {
		this.equipmentDetails = equipmentDetails;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
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
	public ServiceAgreement getServiceAgreement() {
		return serviceAgreement;
	}
	public void setServiceAgreement(ServiceAgreement serviceAgreement) {
		this.serviceAgreement = serviceAgreement;
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

}
