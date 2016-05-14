package com.techvisio.eserve.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoiceComponentAmountBean {

	private Map<Long, Double> amountMap = new HashMap<Long, Double>();
	private List<InvoiceComponent> components = new ArrayList<InvoiceComponent>();
	public Map<Long, Double> getAmountMap() {
		return amountMap;
	}
	public void setAmountMap(Map<Long, Double> amountMap) {
		this.amountMap = amountMap;
	}
	public List<InvoiceComponent> getComponents() {
		return components;
	}
	public void setComponents(List<InvoiceComponent> components) {
		this.components = components;
	}
	
}
