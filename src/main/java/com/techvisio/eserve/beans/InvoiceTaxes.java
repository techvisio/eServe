package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_INVOICE_TAXES_MASTER")
public class InvoiceTaxes extends BasicEntity{
	
	@Id
	@Column(name="COMPONENT_ID")
	private Long componentId;
	@Column(name="DESCRIPTION")
	private String description;
	@Column(name="PERCENTAGE")
	private Double percentage;
	@Column(name="FIXED")
	private Double fixed;
	@Column(name="CALCULATED_ON_ID")
	private Long calculatedOnId;
	@Column(name="ACTIVE")
	private Boolean active;
	@Column(name="SEQUENCE_NO")
	private Long sequenceNo;
	@Column(name="IS_DOCUMENTATION_ONLY")
	private boolean documentationOnly;
	
	public Long getComponentId() {
		return componentId;
	}
	public void setComponentId(Long componentId) {
		this.componentId = componentId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPercentage() {
		return percentage;
	}
	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
	public Double getFixed() {
		return fixed;
	}
	public void setFixed(Double fixed) {
		this.fixed = fixed;
	}
	public Long getCalculatedOnId() {
		return calculatedOnId;
	}
	public void setCalculatedOnId(Long calculatedOnId) {
		this.calculatedOnId = calculatedOnId;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Long getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(Long sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	public boolean isDocumentationOnly() {
		return documentationOnly;
	}
	public void setDocumentationOnly(boolean documentationOnly) {
		this.documentationOnly = documentationOnly;
	}

}
