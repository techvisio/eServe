package com.techvisio.eserve.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TB_INVOICE")
public class Invoice extends BasicFileds{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="INVOICE_ID")
	private Long invoiceId;
	@Column(name="INVOICE_NO")
	private Long invoiceNo;
	@Column(name="AMOUNT")
	private Double amount;
	
	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER )
	@JoinColumn(name="INVOICE_ID")
	private List<InvoiceComponent> invoiceComponents=new ArrayList<InvoiceComponent>();
	
	public Long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}
	public Long getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(Long invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public List<InvoiceComponent> getInvoiceComponents() {
		return invoiceComponents;
	}
	public void setInvoiceComponents(List<InvoiceComponent> invoiceComponents) {
		this.invoiceComponents = invoiceComponents;
	}
	
}
