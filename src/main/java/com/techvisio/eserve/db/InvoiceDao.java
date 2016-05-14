package com.techvisio.eserve.db;

import java.util.List;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.AgreementInvoice;
import com.techvisio.eserve.beans.Invoice;
import com.techvisio.eserve.beans.InvoiceComponent;
import com.techvisio.eserve.beans.InvoiceTaxes;
@Component
public interface InvoiceDao {

	List<InvoiceTaxes> getInvoiceTaxes(Long clientId);

	public Long saveInvoice(Invoice invoice);

	void saveInvoiceComponent(InvoiceComponent invoiceComponent);

	void saveInvoiceAgreement(AgreementInvoice agreementInvoice);

}
