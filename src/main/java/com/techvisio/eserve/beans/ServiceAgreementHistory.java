package com.techvisio.eserve.beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

@Entity
@Table(name = "TB_SERVICE_AGREEMENT_HISTORY")
public class ServiceAgreementHistory extends BasicEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="AGREEMENT_HISTORY_ID")
	private Long AgreementHistoryId;
	@Column(name="UNIT_ID")
	private Long UnitId;
	@Column(name="SERVICE_TYPE")
	private String serviceType;	
	@Column(name="START_DATE")
	private Date startDate;
	@Column(name="END_DATE")
	private Date endDate;

	@Transient
	private String startDateString;
	@Transient
	private String endDateString;
	
	public Long getAgreementHistoryId() {
		return AgreementHistoryId;
	}
	public void setAgreementHistoryId(Long agreementHistoryId) {
		AgreementHistoryId = agreementHistoryId;
	}
	public Long getUnitId() {
		return UnitId;
	}
	public void setUnitId(Long unitId) {
		UnitId = unitId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getStartDateString() {
		if (this.startDate == null)
			return null;

		try {
			
			DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");
			String startDateString = outputFormatter.format(this.startDate);
			return startDateString;

		} catch (Exception e) {

		}
		return null;
	}
	public void setStartDateString(String startDateString) {
		DateTimeFormatter parser2 = ISODateTimeFormat.dateTime().withZoneUTC();
		if(!StringUtils.isEmpty(startDateString)){
			this.startDate = parser2.parseDateTime(startDateString).toDate();
		}

	}
	public String getEndDateString() {
		if (this.endDate == null)
			return null;

		try {
			DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");
			String endDateString = outputFormatter.format(this.endDate);
			return endDateString;

		} catch (Exception e) {

		}
		return null;

	}
	public void setEndDateString(String endDateString) {
		DateTimeFormatter parser2 = ISODateTimeFormat.dateTime().withZoneUTC();
		if(!StringUtils.isEmpty(endDateString)){
			this.endDate = parser2.parseDateTime(endDateString).toDate();
		}
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	
}
