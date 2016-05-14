package com.techvisio.eserve.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.AgreementInvoice;
import com.techvisio.eserve.beans.Invoice;
import com.techvisio.eserve.beans.InvoiceComponent;
import com.techvisio.eserve.db.InvoiceDao;
import com.techvisio.eserve.factory.InvoiceGenerator;
import com.techvisio.eserve.factory.InvoiceGeneratorFactory;
import com.techvisio.eserve.manager.InvoiceManager;
@Component
public class InvoiceManagerImpl implements InvoiceManager{

	@Autowired
	InvoiceDao invoiceDao;
	
	@Autowired
	InvoiceGeneratorFactory invoiceGeneratorFactory;
	
	@Override
	public Long saveInvoice(Double amount){
		
		
		InvoiceGenerator invoiceGenerator=invoiceGeneratorFactory.getInstance();
		Invoice invoice=invoiceGenerator.generateInvoice(amount);
		Long invoiceId=invoiceDao.saveInvoice(invoice);
		return invoiceId;
	}

	@Override
	public void saveInvoiceComponent(InvoiceComponent invoiceComponent) {
		invoiceDao.saveInvoiceComponent(invoiceComponent);
	}

	@Override
	public void saveInvoiceAgreement(AgreementInvoice agreementInvoice) {
		invoiceDao.saveInvoiceAgreement(agreementInvoice);
		
	}

	@Override
	public void saveInvoiceAndInvoiceAgreement(Long agreementId,
			Double agreementAmount) {
		Long invoiceId = saveInvoice(agreementAmount);

		AgreementInvoice agreementInvoice = new AgreementInvoice();
		agreementInvoice.setAgreementId(agreementId);
		agreementInvoice.setInvoiceId(invoiceId);
		saveInvoiceAgreement(agreementInvoice);
	}
	
}
