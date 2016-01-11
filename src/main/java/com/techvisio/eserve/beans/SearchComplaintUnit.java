package com.techvisio.eserve.beans;

import java.util.List;

public class SearchComplaintUnit {

	private Long unitId;
	private String unitCode;
	private String serviceCategory;
	private String externalId;
	private String serviceParty;
	private String unitCategory;
	private List<SearchComplaint> complaints;
	
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getServiceCategory() {
		return serviceCategory;
	}
	public void setServiceCategory(String serviceCategory) {
		this.serviceCategory = serviceCategory;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getServiceParty() {
		return serviceParty;
	}
	public void setServiceParty(String serviceParty) {
		this.serviceParty = serviceParty;
	}
	public String getUnitCategory() {
		return unitCategory;
	}
	public void setUnitCategory(String unitCategory) {
		this.unitCategory = unitCategory;
	}
	public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	public List<SearchComplaint> getComplaints() {
		return complaints;
	}
	public void setComplaints(List<SearchComplaint> complaints) {
		this.complaints = complaints;
	}

}
