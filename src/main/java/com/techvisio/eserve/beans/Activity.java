package com.techvisio.eserve.beans;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_ACTIVITY")
public class Activity extends BasicFileds {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ACTIVITY_ID")
	private Long activityId;
	@Column(name="ACTIVITY")
	private String activity;
	@Column(name="DESCRIPTION")
	private String description;
	@Column(name="ACTIVITY_DATE")
	private Date activityDate;
	@Column(name="USERNAME")
	private String username;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="ACTIVITY_ID")
	private ActivityParam activityParam;
	
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getActivityDate() {
		return activityDate;
	}
	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public ActivityParam getActivityParam() {
		return activityParam;
	}
	public void setActivityParam(ActivityParam activityParam) {
		this.activityParam = activityParam;
	}
	@Override
	public String toString() {
		return "Activity [activityId=" + activityId + ", activity=" + activity
				+ ", description=" + description + ", activityDate="
				+ activityDate + ", username=" + username + ", activityParam="
				+ activityParam + ", getActivityId()=" + getActivityId()
				+ ", getActivity()=" + getActivity() + ", getDescription()="
				+ getDescription() + ", getActivityDate()=" + getActivityDate()
				+ ", getUsername()=" + getUsername() + ", getActivityParam()="
				+ getActivityParam() + ", getCreatedBy()=" + getCreatedBy()
				+ ", getCreatedOn()=" + getCreatedOn() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	
}


