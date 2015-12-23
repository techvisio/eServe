package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_RESOLUTION_MASTER")
public class Resolution extends BasicEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RESOLUTION_ID")
	private Long resolutionId;
	@Column(name="VALUE")
	private String value;

	public Long getResolutionId() {
		return resolutionId;
	}
	public void setResolutionId(Long resolutionId) {
		this.resolutionId = resolutionId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
