package com.techvisio.eserve.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.AgreementInvoice;
import com.techvisio.eserve.beans.InvoiceComponent;
import com.techvisio.eserve.manager.InvoiceManager;
import com.techvisio.eserve.service.InvoiceService;

@Component
public class InvoiceServiceImpl implements InvoiceService{
	
	@Autowired
	InvoiceManager invoiceManager; 
	
	
	@Override
	public Long saveInvoice(Double amount){
		Long invoiceId=invoiceManager.saveInvoice(amount);
		return invoiceId;
	}


	@Override
	public void saveInvoiceComponent(InvoiceComponent invoiceComponent) {
		invoiceManager.saveInvoiceComponent(invoiceComponent);
		
	}


	@Override
	public void saveInvoiceAgreement(AgreementInvoice agreementInvoice) {
		invoiceManager.saveInvoiceAgreement(agreementInvoice);
		
	}

}
