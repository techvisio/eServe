package com.techvisio.eserve.beans;

import javax.persistence.CascadeType;
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
@Table(name = "TB_COMPLAINT_ASSIGNMENT")
public class ComplaintAssignment extends BasicEntity{

	@OneToOne
	@JoinColumn(name="USER_ID")
	private User user;
	
	@Column(name="COMPLAINT_ID")
	@Id
	private Long complaintId;

	@JsonIgnore
	@MapsId
	@JoinColumn(name = "COMPLAINT_ID", referencedColumnName = "COMPLAINT_ID")
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	private CustomerComplaint customerComplaint;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

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

}
