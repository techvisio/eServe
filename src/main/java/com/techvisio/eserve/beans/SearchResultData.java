package com.techvisio.eserve.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResultData<T> {

	private List<T> objectData =  new ArrayList<T>();
	private Long totalCount;
	private int pageSize;
	private int pageNo;
	private Map<String, String> contentTypeMap = new HashMap<String, String>();
	public List<T> getObjectData() {
		return objectData;
	}
	public void setObjectData(List<T> objectData) {
		this.objectData = objectData;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
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
	public Map<String, String> getContentTypeMap() {
		return contentTypeMap;
	}
	public void setContentTypeMap(Map<String, String> contentTypeMap) {
		this.contentTypeMap = contentTypeMap;
	}
	
}
