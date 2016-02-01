package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_UNIT_CATEGORY_MASTER")
public class UnitCategory extends BasicEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="UNIT_CATEGORY_ID")
	private Long unitCategoryId;
	@Column(name="UNIT_TYPE")
	private String unitType;

	public Long getUnitCategoryId() {
		return unitCategoryId;
	}
	public void setUnitCategoryId(Long unitCategoryId) {
		this.unitCategoryId = unitCategoryId;
	}
	public String getUnitType() {
		return unitType;
	}
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	
}
