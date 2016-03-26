package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_EQUIPMENT_HISTORY")
public class EquipmentHistory {

	@EmbeddedId
	private CompositeKeyEquipmentHistory compositeKeyEquipmentHistory;

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

	public CompositeKeyEquipmentHistory getCompositeKeyEquipmentHistory() {
		return compositeKeyEquipmentHistory;
	}

	public void setCompositeKeyEquipmentHistory(
			CompositeKeyEquipmentHistory compositeKeyEquipmentHistory) {
		this.compositeKeyEquipmentHistory = compositeKeyEquipmentHistory;
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

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public boolean isUnderWarranty() {
		return underWarranty;
	}

	public void setUnderWarranty(boolean underWarranty) {
		this.underWarranty = underWarranty;
	}

}
