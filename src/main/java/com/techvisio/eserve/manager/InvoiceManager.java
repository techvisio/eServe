package com.techvisio.eserve.manager;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.AgreementInvoice;
import com.techvisio.eserve.beans.InvoiceComponent;

@Component
public interface InvoiceManager {

	public Long saveInvoice(Double amount);
	public void saveInvoiceComponent(InvoiceComponent invoiceComponent);
	public void saveInvoiceAgreement(AgreementInvoice agreementInvoice);
	public void saveInvoiceAndInvoiceAgreement(Long agreementId, Double agreementAmount);

}
