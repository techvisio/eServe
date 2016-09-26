package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_EQUIPMENT_WARRANTY_UNDER_MASTER")
public class EquipmentWarrantyUnder extends BasicEntity{

	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	@Column(name = "WARRANTY_UNDER_ID")
	private Long warrantyUnderId;
	@Column(name = "WARRANTY_UNDER")
	private String warrantyUnder;

	public Long getWarrantyUnderId() {
		return warrantyUnderId;
	}
	public void setWarrantyUnderId(Long warrantyUnderId) {
		this.warrantyUnderId = warrantyUnderId;
	}
	public String getWarrantyUnder() {
		return warrantyUnder;
	}
	public void setWarrantyUnder(String warrantyUnder) {
		this.warrantyUnder = warrantyUnder;
	}
}
