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
	@OneToOne
	@JoinColumn(name="WARRANTY_UNDER_ID")
	private EquipmentWarrantyUnder warrantyUnder;
	@OneToOne
	@JoinColumn(name="EQUIPMENT_TYPE_ID")
	private EquipmentType equipmentType;
	@OneToOne
	@JoinColumn(name="EQUIPMENT_MODAL_NO_ID")
	private EquipmentModalNo modalNo;
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
