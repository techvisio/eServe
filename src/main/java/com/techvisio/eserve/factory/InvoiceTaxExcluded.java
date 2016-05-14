package com.techvisio.eserve.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.techvisio.eserve.beans.Invoice;
import com.techvisio.eserve.beans.InvoiceComponent;
import com.techvisio.eserve.beans.InvoiceTaxes;
import com.techvisio.eserve.manager.CacheManager;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;


public class InvoiceTaxExcluded implements InvoiceGenerator{


	CacheManager cacheManager;

	public InvoiceTaxExcluded(CacheManager cacheManager) {
		this.cacheManager=cacheManager;
	}

	public Invoice generateInvoice(Double amount){

		Double basePrice = amount;
		Double totalAmount = basePrice;

		List<InvoiceTaxes> invoiceTaxes = cacheManager.getInvoiceTaxes(CommonUtil.getCurrentClient().getClientId());

		Map<Long, Double> amountMap = new HashMap<Long, Double>();
		amountMap.put(AppConstants.BASE_AMOUNT_ID, basePrice);
		amountMap.put(AppConstants.TOTAL_AMOUNT_ID, totalAmount);

		totalAmount = calculateComponentAmount(basePrice, totalAmount, invoiceTaxes, amountMap);

		Invoice invoice = getInvoice(invoiceTaxes, amountMap);
		return invoice;
	}

	private Invoice getInvoice(List<InvoiceTaxes> invoiceTaxes,
			Map<Long, Double> amountMap) {

		List<InvoiceComponent> components = new ArrayList<InvoiceComponent>();
		Invoice invoice = new Invoice();

		for(InvoiceTaxes invoiceTaxes2 : invoiceTaxes ){
			InvoiceComponent invoiceComponent = new InvoiceComponent();

			Double componentAmount  = amountMap.get(invoiceTaxes2.getComponentId());

			invoiceComponent.setAmount(componentAmount);
			invoiceComponent.setComponentId(invoiceTaxes2.getComponentId());
			components.add(invoiceComponent);
		}

		Double invoiceAmount = amountMap.get(AppConstants.TOTAL_AMOUNT_ID);
		invoice.setInvoiceComponents(components);
		invoice.setAmount(invoiceAmount);

		return invoice;
	}

	private Double calculateComponentAmount(Double basePrice, Double totalAmount, List<InvoiceTaxes> invoiceTaxes,
			Map<Long, Double> amountMap) {

		for(InvoiceTaxes taxes : invoiceTaxes){

			if(taxes.isDocumentationOnly()){
				if(taxes.getComponentId()==AppConstants.BASE_AMOUNT_ID){
					amountMap.put(AppConstants.BASE_AMOUNT_ID, basePrice);

				}
				if(taxes.getComponentId()==AppConstants.TOTAL_AMOUNT_ID){
					amountMap.put(AppConstants.TOTAL_AMOUNT_ID, totalAmount);
				}

			}
			else{
				if(taxes.getFixed()!=null){
					Double componentAmount = taxes.getFixed();
					totalAmount = amountMap.get(AppConstants.TOTAL_AMOUNT_ID);
					totalAmount = totalAmount + componentAmount;
					amountMap.put(taxes.getComponentId(), componentAmount);
					amountMap.put(AppConstants.TOTAL_AMOUNT_ID, totalAmount);
				}
				else{
					Long dependentComponentId = taxes.getCalculatedOnId();
					Double componentAmount = amountMap.get(dependentComponentId);
					Double amountAfterTax = ((componentAmount*taxes.getPercentage())/100);
					amountAfterTax = Math.round(amountAfterTax*100.0)/100.0;
					totalAmount = amountMap.get(AppConstants.TOTAL_AMOUNT_ID);
					totalAmount = totalAmount + amountAfterTax;
					amountMap.put(taxes.getComponentId(), amountAfterTax);
					amountMap.put(AppConstants.TOTAL_AMOUNT_ID, totalAmount);
				}
			}
		}
		return totalAmount;
	}

}
