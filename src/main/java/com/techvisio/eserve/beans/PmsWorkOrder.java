package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_PMS_WORK_ORDER")
public class PmsWorkOrder {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PMS_WORK_ORDER_ID")
	private Long pmsWorkOrderId;
	@Column(name="WORKITEM_ID")
	private Long workitemId;
	@Column(name="WORK_ORDER_ID")
	private Long workOrderId;
	
	public Long getPmsWorkOrderId() {
		return pmsWorkOrderId;
	}
	public void setPmsWorkOrderId(Long pmsWorkOrderId) {
		this.pmsWorkOrderId = pmsWorkOrderId;
	}
	public Long getWorkitemId() {
		return workitemId;
	}
	public void setWorkitemId(Long workitemId) {
		this.workitemId = workitemId;
	}
	public Long getWorkOrderId() {
		return workOrderId;
	}
	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
	}
	
}
