package com.techvisio.eserve.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CompositeKeyEquipmentHistory implements Serializable{

	@Column(name="EQUIPMENT_DTL_ID")
	private Long equipmentDtlId;
	@Column(name="UNIT_ID")
	private Long unitId;
	@Column(name = "VERSION")
	private Double version;
	public Long getEquipmentDtlId() {
		return equipmentDtlId;
	}
	public void setEquipmentDtlId(Long equipmentDtlId) {
		this.equipmentDtlId = equipmentDtlId;
	}
	public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	public Double getVersion() {
		return version;
	}
	public void setVersion(Double version) {
		this.version = version;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((equipmentDtlId == null) ? 0 : equipmentDtlId.hashCode());
		result = prime * result + ((unitId == null) ? 0 : unitId.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompositeKeyEquipmentHistory other = (CompositeKeyEquipmentHistory) obj;
		if (equipmentDtlId == null) {
			if (other.equipmentDtlId != null)
				return false;
		} else if (!equipmentDtlId.equals(other.equipmentDtlId))
			return false;
		if (unitId == null) {
			if (other.unitId != null)
				return false;
		} else if (!unitId.equals(other.unitId))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}
	
	
}
