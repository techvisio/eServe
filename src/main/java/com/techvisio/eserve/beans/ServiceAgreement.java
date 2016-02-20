package com.techvisio.eserve.beans;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "TB_SERVICE_AGREEMENT")
public class ServiceAgreement extends BasicEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SERVICE_AGREEMENT_ID")
	private Long serviceAgreementId;
	@Column(name="UNIT_ID")
	private Long unitId;
	@Column(name="SERVICE_CATEGORY")
	private String serviceCategory = "Paid";
	
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
	
	@ManyToOne
	@JoinColumn(name="AGREEMENT_DURATION_ID")
	private AgreementDuration agreementDuration;
	
	@Column(name="APPROVED_BY")
	private Long approvedBy;
	
	@OneToOne(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinColumn(name="SERVICE_AGREEMENT_FINANCE_ID")
	private ServiceAgreementFinance serviceAgreementFinance;
	
	@OneToOne
	@JoinColumn(name="SERVICE_PROVIDER_ID")
	private ServiceProvider serviceProvider;
	
	
	public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}
	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
	public String getServiceCategory() {
		return serviceCategory;
	}
	public void setServiceCategory(String serviceCategory) {
		this.serviceCategory = serviceCategory;
	}
	public Long getServiceAgreementId() {
		return serviceAgreementId;
	}
	public void setServiceAgreementId(Long serviceAgreementId) {
		this.serviceAgreementId = serviceAgreementId;
	}
	public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	
	public AgreementDuration getAgreementDuration() {
		return agreementDuration;
	}
	public void setAgreementDuration(AgreementDuration agreementDuration) {
		this.agreementDuration = agreementDuration;
	}
	public Long getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(Long approvedBy) {
		this.approvedBy = approvedBy;
	}
	public ServiceAgreementFinance getServiceAgreementFinance() {
		return serviceAgreementFinance;
	}
	public void setServiceAgreementFinance(
			ServiceAgreementFinance serviceAgreementFinance) {
		this.serviceAgreementFinance = serviceAgreementFinance;
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
}
