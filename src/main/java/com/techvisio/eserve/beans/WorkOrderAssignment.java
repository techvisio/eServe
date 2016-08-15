package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_WORK_ORDER_ASSIGNMENT")
public class WorkOrderAssignment extends BasicEntity{

	@OneToOne
	@JoinColumn(name="USER_ID")
	private User user;
	
	@Column(name="WORK_ORDER_ID")
	@Id
	private Long workOrderId;

	@JsonIgnore
	@MapsId
	@JoinColumn(name = "WORK_ORDER_ID", referencedColumnName = "WORK_ORDER_ID")
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	private WorkOrder workOrder;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

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

}
