package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_CONFIG")

public class Config extends BasicEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CONFIG_TB_ID")
	private Long configTBId;
	@Column(name = "PROPERTY")
	private String property;
	@Column(name = "VALUE")
	private String value;

	public Long getConfigTBId() {
		return configTBId;
	}
	public void setConfigTBId(Long configTBId) {
		this.configTBId = configTBId;
	}

	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}