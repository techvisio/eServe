package com.techvisio.eserve.beans;

public class WorkItemSearchCriteria {

	
	private String sortBy;

	private int pageSize;

	private int pageNo;

	private boolean isAscending;

	private int startIndex;
	
	private Long userId;
	
	private String type;
	
	private String status;

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
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
		this.pageNo = pgeNo;
	}

	public boolean getIsAscending() {
		return isAscending;
	}

	public void setIsAscending(boolean isAscending) {
		this.isAscending = isAscending;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
