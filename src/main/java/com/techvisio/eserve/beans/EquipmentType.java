package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_EQUIPMENT_TYPE_MASTER")
public class EquipmentType extends BasicEntity{

	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	@Column(name = "EQUIPMENT_TYPE_ID")
	private Long equipmentTypeId;
	@Column(name = "EQUIPMENT_TYPE")
	private String equipmentType;
	
	public Long getEquipmentTypeId() {
		return equipmentTypeId;
	}
	public void setEquipmentTypeId(Long equipmentTypeId) {
		this.equipmentTypeId = equipmentTypeId;
	}
	public String getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}
	
	
}
