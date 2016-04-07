package com.techvisio.eserve.beans;

import java.util.List;

public class GraphData {

	private List<String> label;
	private List<Long> data;

	public List<String> getLabel() {
		return label;
	}
	public void setLabel(List<String> label) {
		this.label = label;
	}
	public List<Long> getData() {
		return data;
	}
	public void setData(List<Long> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "GraphData [label=" + label + ", data=" + data + "]";
	}

	

}
