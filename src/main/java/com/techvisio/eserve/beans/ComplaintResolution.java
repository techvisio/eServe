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
@Table(name = "TB_COMPLAINT_RESOLUTION")
public class ComplaintResolution extends BasicEntity{

	
	@Column(name="COMPLAINT_ID")
	@Id
	private Long complaintId;

	@JsonIgnore
	@MapsId
	@JoinColumn(name = "COMPLAINT_ID", referencedColumnName = "COMPLAINT_ID")
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	private CustomerComplaint customerComplaint;


	@OneToOne
	@JoinColumn(name="RESOLUTION_ID")
	private Resolution resolution;
	@Column(name="DESCRIPTION")
	private String description;
	public Long getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(Long complaintId) {
		this.complaintId = complaintId;
	}
	public CustomerComplaint getCustomerComplaint() {
		return customerComplaint;
	}
	public void setCustomerComplaint(CustomerComplaint customerComplaint) {
		this.customerComplaint = customerComplaint;
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
