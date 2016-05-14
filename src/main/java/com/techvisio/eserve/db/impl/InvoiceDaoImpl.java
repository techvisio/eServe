package com.techvisio.eserve.db.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.AgreementInvoice;
import com.techvisio.eserve.beans.Invoice;
import com.techvisio.eserve.beans.InvoiceComponent;
import com.techvisio.eserve.beans.InvoiceTaxes;
import com.techvisio.eserve.beans.ServiceAgreement;
import com.techvisio.eserve.db.InvoiceDao;

@Component
public class InvoiceDaoImpl extends BaseDao implements  InvoiceDao{

	@Override
	public List<InvoiceTaxes> getInvoiceTaxes(Long clientId) {
		String queryString="FROM InvoiceTaxes it where it.client.clientId = " + clientId;
		Query query= getEntityManager().createQuery(queryString);
		List<InvoiceTaxes> result= query.getResultList();
		return result;
	}	

	@Override
	public Long saveInvoice(Invoice invoice){

		if(invoice.getInvoiceId() == null){
			getEntityManager().persist(invoice);
		}
		else{
			getEntityManager().merge(invoice);
		}
		getEntityManager().flush();
		return invoice.getInvoiceId();
	}

	@Override
	public void saveInvoiceComponent(InvoiceComponent invoiceComponent){

		if(invoiceComponent.getInvoiceComponentId() == null){
			getEntityManager().persist(invoiceComponent);
		}
		else{
			getEntityManager().merge(invoiceComponent);
		}
		getEntityManager().flush();
	}

	@Override
	public void saveInvoiceAgreement(AgreementInvoice agreementInvoice){

		if(agreementInvoice.getAgreementInvoiceId() == null){
			getEntityManager().persist(agreementInvoice);
		}
		else{
			getEntityManager().merge(agreementInvoice);
		}
		getEntityManager().flush();
	}	
	
}
