package com.techvisio.eserve.util;

public interface AppConstants {

	public static final String STATE="STATE";
	public static final String QUESTION = "QUESTION";
	public static final String DESIGNATION="DESIGNATION";
	public static final String DEPARTMENT = "DEPARTMENT";
	public static final String RESOLUTION = "RESOLUTION";
	public static final String ISSUE = "ISSUE";
	public enum DefaultValues{DEFAULT_PASSWORD, SLA_DAYS_HIGH, SLA_DAYS_LOW, SLA_DAYS_CRITICAL, SLA_DAYS_MEDIUM};
	public enum complaintStatus{ASSIGNED, UNASSIGNED, CLOSED};
}
