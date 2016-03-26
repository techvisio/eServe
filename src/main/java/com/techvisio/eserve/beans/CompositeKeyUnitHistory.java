package com.techvisio.eserve.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CompositeKeyUnitHistory implements Serializable{

	@Column(name = "UNITID")
	private Long unitId;

	@Column(name = "VERSION")
	private Double version;

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


}
