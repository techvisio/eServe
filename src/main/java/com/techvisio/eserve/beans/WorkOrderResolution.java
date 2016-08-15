package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_WORK_ORDER_RESOLUTION")
public class WorkOrderResolution extends BasicEntity{

	
	@Column(name="WORK_ORDER_ID")
	@Id
	private Long workOrderId;

	@JsonIgnore
	@MapsId
	@JoinColumn(name = "WORK_ORDER_ID", referencedColumnName = "WORK_ORDER_ID")
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	private WorkOrder workOrder;


	@OneToOne
	@JoinColumn(name="RESOLUTION_ID")
	private Resolution resolution;
	@Column(name="DESCRIPTION")
	private String description;
	public Long getWorkOrderId() {
		return workOrderId;
	}
	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
	}
	public WorkOrder getWorkOrder() {
		return workOrder;
	}
	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}
	public Resolution getResolution() {
		return resolution;
	}
	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
