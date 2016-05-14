package com.techvisio.eserve.service;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.AgreementInvoice;
import com.techvisio.eserve.beans.InvoiceComponent;

@Component
public interface InvoiceService {

	public Long saveInvoice(Double amount);
	public void saveInvoiceComponent(InvoiceComponent invoiceComponent);
	public void saveInvoiceAgreement(AgreementInvoice agreementInvoice);

}
