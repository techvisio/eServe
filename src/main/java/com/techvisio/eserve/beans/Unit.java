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

	@Temporal(TemporalType.DATE)
	@Column(name="CONTRACT_START_ON")
	@JsonIgnore
	private Date contractStartOn;
	
	@Transient
	private String contractStartOnString;
	
	@Temporal(TemporalType.DATE)
	@Column(name="CONTRACT_EXPIRE_ON")
	@JsonIgnore
	private Date contractExpireOn;
	
	@Transient
	private String contractExpireOnString;
	
	@Column(name="SERVICE_CATEGORY")
	private String serviceCategory = "Paid";
	@Column(name="EXTERNAL_ID")
	private String externalId;
	@Column(name="LENGTH")
	private Long length;
	@Column(name="WIDTH")
	private Long width;
	@Column(name="HEIGHT")
	private Long height;
	@Column(name="IS_AGREEMENT_APPROVED")
	private boolean agreementApproval;
	
	@OneToOne
	@JoinColumn(name="UNIT_CATEGORY_ID")
	private UnitCategory unitCategory;

	@OneToOne
	@JoinColumn(name="SERVICE_PROVIDER_ID")
	private ServiceProvider serviceProvider;
	
	@OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="ADDRESS_ID")
	private Address address;
	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinColumn(name="UNIT_ID")
	private List<EquipmentDetail> equipmentDetails;
	
	public String getServiceCategory() {
		return serviceCategory;
	}
	public void setServiceCategory(String serviceCategory) {
		this.serviceCategory = serviceCategory;
	}
	
	public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}
	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public Long getLength() {
		return length;
	}
	public void setLength(Long length) {
		this.length = length;
	}
	public Long getWidth() {
		return width;
	}
	public void setWidth(Long width) {
		this.width = width;
	}
	
	public Long getHeight() {
		return height;
	}
	public void setHeight(Long height) {
		this.height = height;
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
	
	public String getContractStartOnString() {
		if (this.contractStartOn == null)
			return null;

		try {
			DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
			return fmt.print(this.contractStartOn.getTime());

		} catch (Exception e) {

		}
		return null;
	}

	public void setContractStartOnString(String contractStartOnString) {
		DateTimeFormatter parser2 = ISODateTimeFormat.dateTime().withZoneUTC();
		if(!StringUtils.isEmpty(contractStartOnString)){
		this.contractStartOn = parser2.parseDateTime(contractStartOnString).toDate();
		}
	}

	
	public String getContractExpireOnString() {
		if (this.contractExpireOn == null)
			return null;

		try {
			DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
			return fmt.print(this.contractExpireOn.getTime());

		} catch (Exception e) {

		}
		return null;
	}

	public void setContractExpireOnString(String contractExpireOnString) {
		DateTimeFormatter parser2 = ISODateTimeFormat.dateTime().withZoneUTC();
		if(!StringUtils.isEmpty(contractExpireOnString)){
		this.contractExpireOn = parser2.parseDateTime(contractExpireOnString).toDate();
		}
	}
	public boolean isAgreementApproval() {
		return agreementApproval;
	}
	public void setAgreementApproval(boolean agreementApproval) {
		this.agreementApproval = agreementApproval;
	}
	
}
