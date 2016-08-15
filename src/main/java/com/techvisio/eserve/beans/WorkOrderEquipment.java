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
@Table(name = "TB_WORK_ORDER_EQUIPMENT")
public class WorkOrderEquipment {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="WORK_ORDER_EQUIPMENT_ID")
	private Long workOrderEquipmentId;
	@Column(name="WORK_ORDER_ID")
	private Long workOrderId;
	@Column(name="IS_DELETED")
	private boolean deleted;
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

			DateFormat outputFormatter = new SimpleDateFormat(AppConstants.DateFormat.MM_dd_yyyy.getPattern());
			String startDateString = outputFormatter.format(this.installationDate);
			return startDateString;

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
	public Long getWorkOrderEquipmentId() {
		return workOrderEquipmentId;
	}
	public void setWorkOrderEquipmentId(Long workOrderEquipmentId) {
		this.workOrderEquipmentId = workOrderEquipmentId;
	}
	public Long getWorkOrderId() {
		return workOrderId;
	}
	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}
