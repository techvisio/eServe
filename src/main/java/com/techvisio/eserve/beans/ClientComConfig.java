package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_CLIENT_COMMUNICATION_CONFIG")
public class ClientComConfig extends BasicEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CLIENT_COM_CONFIG_ID")
	private Long clientComConfigId;
	@Column(name = "PROPERTY")
	private String property;
	@Column(name = "VALUE")
	private String value;

	public Long getClientComConfigId() {
		return clientComConfigId;
	}
	public void setClientComConfigId(Long clientComConfigId) {
		this.clientComConfigId = clientComConfigId;
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
