package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_EQUIPMENT_DETAIL")
public class EquipmentDetail extends BasicEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Equipment_Dtl_Id")
	private Long equipmentDtlId;
	@Column(name="Unit_Id")
	private Long unitId;
	@Column(name="Serial_No")
	private String serialNo;
	@Column(name="Invoice_No")
	private Long invoiceNo;
	@Column(name="Warranty_Under")
	private String warrantyUnder;
	@Column(name="Type")
	private String type;
	@OneToOne
	@JoinColumn(name="Equipment_Id")
	private Equipment equipment;
	
	
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
	
	
}
