package com.techvisio.eserve.beans;

import java.util.Date;

public class ServiceRenewalBean {

	private String seriviceType;
	private int duration;
	private Date startDate;
	private String startDateString;
	
	public String getSeriviceType() {
		return seriviceType;
	}
	public void setSeriviceType(String seriviceType) {
		this.seriviceType = seriviceType;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getStartDateString() {
		return startDateString;
	}
	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
}
