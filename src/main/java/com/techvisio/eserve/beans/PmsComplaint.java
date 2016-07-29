package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_PMS_COMPLAINT")
public class PmsComplaint {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PMS_COMPLAINT_ID")
	private Long pmsComplaintId;
	@Column(name="WORKITEM_ID")
	private Long workitemId;
	@Column(name="COMPLAINT_ID")
	private Long complaintId;
	
	public Long getPmsComplaintId() {
		return pmsComplaintId;
	}
	public void setPmsComplaintId(Long pmsComplaintId) {
		this.pmsComplaintId = pmsComplaintId;
	}
	public Long getWorkitemId() {
		return workitemId;
	}
	public void setWorkitemId(Long workitemId) {
		this.workitemId = workitemId;
	}
	public Long getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(Long complaintId) {
		this.complaintId = complaintId;
	}
	
}
