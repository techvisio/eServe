package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB__AGREEMENT_INVOICE")
public class AgreementInvoice extends BasicEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="AGREEMENT_INVOICE_ID")
	private Long agreementInvoiceId;
	@Column(name="AGREEMENT_ID")
	private Long agreementId;
	@Column(name="INVOICE_ID")
	private Long invoiceId;
	
	public Long getAgreementInvoiceId() {
		return agreementInvoiceId;
	}
	public void setAgreementInvoiceId(Long agreementInvoiceId) {
		this.agreementInvoiceId = agreementInvoiceId;
	}
	public Long getAgreementId() {
		return agreementId;
	}
	public void setAgreementId(Long agreementId) {
		this.agreementId = agreementId;
	}
	public Long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}
	
}
