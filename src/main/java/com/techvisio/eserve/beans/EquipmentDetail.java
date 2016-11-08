package com.techvisio.eserve.beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.techvisio.eserve.util.AppConstants;

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
	@OneToOne
	@JoinColumn(name="WARRANTY_UNDER_ID")
	private EquipmentWarrantyUnder warrantyUnder;
	@OneToOne
	@JoinColumn(name="EQUIPMENT_TYPE_ID")
	private EquipmentType equipmentType;
	@OneToOne
	@JoinColumn(name="EQUIPMENT_MODAL_NO_ID")
	private EquipmentModalNo modalNo;

	@Column(name="IS_UNDER_WARRANTY")
	private boolean underWarranty;

	@Column(name="INSTALLATION_DATE")
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
	public EquipmentWarrantyUnder getWarrantyUnder() {
		return warrantyUnder;
	}
	public void setWarrantyUnder(EquipmentWarrantyUnder warrantyUnder) {
		this.warrantyUnder = warrantyUnder;
	}
	public EquipmentType getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(EquipmentType equipmentType) {
		this.equipmentType = equipmentType;
	}
	public EquipmentModalNo getModalNo() {
		return modalNo;
	}
	public void setModalNo(EquipmentModalNo modalNo) {
		this.modalNo = modalNo;
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

	public Date getInstallationDate() {
		return installationDate;
	}
	public void setInstallationDate(Date installationDate) {
		this.installationDate = installationDate;
	}
	public String getInstallationDateString() {
		if (this.installationDate == null)
			return null;

		try {
			DateFormat outputFormatter = new SimpleDateFormat(AppConstants.DateFormat.MM_dd_yyyy.getPattern());
			String endDateString = outputFormatter.format(this.installationDate);
			return endDateString;

		} catch (Exception e) {

		}
		return null;

	}
//	public void setInstallationDateString(String installationDateString) {
//		DateTimeFormatter parser2 = ISODateTimeFormat.dateTime().withZoneUTC();
//		if(!StringUtils.isEmpty(installationDateString)){
//			this.installationDate = parser2.parseDateTime(installationDateString).toDate();
//		}
//	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}
