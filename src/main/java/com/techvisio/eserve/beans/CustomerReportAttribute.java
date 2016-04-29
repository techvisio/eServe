package com.techvisio.eserve.beans;

import java.util.Date;

public class CustomerReportAttribute {



	private String customerType;

	private String approvalStatus;

	private  Date approvalDate;



	private String unitType;

	private Date fromDate;

	private Date toDate;

	private int pageSize;

	private int pageNo;

	private String toDateString;

	private String fromDateString;

	private boolean isAscending;

	private String sortBy;


	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}
	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}
	public String getUnitType() {
		return unitType;
	}
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public boolean isAscending() {
		return isAscending;
	}
	public void setAscending(boolean isAscending) {
		this.isAscending = isAscending;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public String getToDateString() {
		return toDateString;
	}
	public void setToDateString(String toDateString) {
		this.toDateString = toDateString;
	}
	public String getFromDateString() {
		return fromDateString;
	}
	public void setFromDateString(String fromDateString) {
		this.fromDateString = fromDateString;
	}

}
