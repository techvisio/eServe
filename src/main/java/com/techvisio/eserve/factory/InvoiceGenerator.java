package com.techvisio.eserve.factory;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Invoice;

@Component
public interface InvoiceGenerator {

	public Invoice generateInvoice(Double amount);
	
}
