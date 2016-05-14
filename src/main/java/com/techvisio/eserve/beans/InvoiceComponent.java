package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_INVOICE_COMPONENT")
public class InvoiceComponent extends BasicFileds{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="INVOICE_COMPONENT_ID")
	private Long invoiceComponentId;
	@Column(name="INVOICE_ID")
	private Long invoiceId;
	@Column(name="COMPONENT_ID")
	private Long componentId;
	@Column(name="AMOUNT")
	private Double amount;
	
	public Long getInvoiceComponentId() {
		return invoiceComponentId;
	}
	public void setInvoiceComponentId(Long invoiceComponentId) {
		this.invoiceComponentId = invoiceComponentId;
	}
	public Long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}
	public Long getComponentId() {
		return componentId;
	}
	public void setComponentId(Long componentId) {
		this.componentId = componentId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	

}
