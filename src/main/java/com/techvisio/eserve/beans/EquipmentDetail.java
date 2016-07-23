package com.techvisio.eserve.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_EQUIPMENT_DETAIL")
public class EquipmentDetail extends BasicEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="EQUIPMENT_DTL_ID")
	private Long equipmentDtlId;
	@Column(name="UNIT_ID")
	private Long unitId;
	@Column(name="SERIAL_NO")
	private String serialNo;
	@Column(name="INVOICE_NO")
	private Long invoiceNo;
	@Column(name="WARRANTY_UNDER")
	private String warrantyUnder;
	@Column(name="TYPE")
	private String type;
	@OneToOne
	@JoinColumn(name="EQUIPMENT_ID")
	private Equipment equipment;

	@Column(name="IS_UNDER_WARRANTY")
	private boolean underWarranty;

	@Temporal(TemporalType.DATE)
	@Column(name="INSTALLATION_DATE")
	@JsonIgnore
	private Date installationDate;

	@Transient
	private boolean deleted;
	
	@Transient
	private String installationDateString;

	public Long getEquipmentDtlId() {
		return equipmentDtlId;
	}
	public void setEquipmentDtlId(Long equipmentDtlId) {
		this.equipmentDtlId = equipmentDtlId;
	}
	public Equipment getEquipment() {
		return equipment;
	}
	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public Long getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(Long invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getWarrantyUnder() {
		return warrantyUnder;
	}
	public void setWarrantyUnder(String warrantyUnder) {
		this.warrantyUnder = warrantyUnder;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	public boolean isUnderWarranty() {
		return underWarranty;
	}
	public void setUnderWarranty(boolean underWarranty) {
		this.underWarranty = underWarranty;
	}

	@JsonIgnore
	public Date getInstallationDate() {
		return installationDate;
	}
	@JsonIgnore
	public void setInstallationDate(Date installationDate) {
		this.installationDate = installationDate;
	}
	public String getInstallationDateString() {
		if (this.installationDate == null)
			return null;

		try {
			DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
			return fmt.print(this.installationDate.getTime());

		} catch (Exception e) {

		}
		return null;
	}
	public void setInstallationDateString(String installationDateString) {
		DateTimeFormatter parser2 = ISODateTimeFormat.dateTime().withZoneUTC();
		if(!StringUtils.isEmpty(installationDateString)){
			this.installationDate = parser2.parseDateTime(installationDateString).toDate();
		}
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}
