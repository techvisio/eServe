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
@Table(name = "TB_EQUIPMENT_MODAL_NO_MASTER")
public class EquipmentModalNo extends BasicEntity{

	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	@Column(name = "EQUIPMENT_MODAL_NO_ID")
	private Long equipmentModalNoId;
	@Column(name = "MODAL_NO")
	private String modalNo;
	@Column(name="PRICE")
	private Double price;
	@Column(name="BRAND")
	private String brand;
	@OneToOne
	@JoinColumn(name="EQUIPMENT_TYPE_ID")
	private EquipmentType equipmentType;
	
	public Long getEquipmentModalNoId() {
		return equipmentModalNoId;
	}
	public void setEquipmentModalNoId(Long equipmentModalNoId) {
		this.equipmentModalNoId = equipmentModalNoId;
	}
	public String getModalNo() {
		return modalNo;
	}
	public void setModalNo(String modalNo) {
		this.modalNo = modalNo;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
}
