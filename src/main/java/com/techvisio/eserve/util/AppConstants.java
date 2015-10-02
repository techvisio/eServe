package com.techvisio.eserve.util;

public interface AppConstants {

	
	public static final String COURSE="COURSE";
	public static final String BRANCH="BRANCH";
	public static final String STATE="STATE";
	public static final String SUBJECT="SUBJECT";
	public static final String QUOTACODE="QUOTACODE";
	public static final String QUALIFICATION="QUALIFICATION";
	public static final String CATEGORY="CATEGORY";
	public static final String FEEHEAD="FEEHEAD";
	public static final String COUNSELLING="COUNSELLING";
	public static final String BLOODGROUP="BLOODGROUP";
	public static final String CONSULTANT="CONSULTANT";
	public static final String SEMESTER="SEMESTER";
	public static final String ENQUIRY="ENQUIRY";
	public static final String ADMISSION="ADMISSION";
	public static final String CODE_MAP="CODE_MAP";
	public static final String BATCH = "BATCH";
	public static final String SESSION = "SESSION";
	public static final String CENTRE = "CENTRE";
	public static final String SHIFT = "SHIFT";
	public static final String SECTION = "SECTION";
	public static final String WING = "WING";
	public static final String FLOOR = "FLOOR";
	public static final String BLOCK = "BLOCK";
	public static final String ROOMNO = "ROOMNO";
	public static final String VEHICLE = "VEHICLE";
	public static final String VEHICLETYPE = "VEHICLETYPE";
	public static final String ROOMTYPE = "ROOMTYPE";
	public static final String TRANSPORT = "TRANSPORT";
	public static final String ROUTE = "ROUTE";
	public static final String STOPPPAGE = "STOPPPAGE";
	public static final String AMENITIES = "AMENITIES";
	public static final String ADMISSION_WORKFLOW = "ADMISSION_WORKFLOW";
	public static final String QUESTION = "QUESTION";
	
	public static final String TASK="TASK";
	public static final Long HOSTEL_FEE_ID=9999L;
	public static final Long TRANSPORT_FEE_ID=9998L;
	public static final Long CASH_DEPOSITE_ID=9996L;
	public static final Long DD_DEPOSITE_ID=9995L;
	public static final Long CHQ_DEPOSITE_ID=9994L;
	public static final Long SCHOLARSHIP_HEAD_ID=9993L;
	public static final Long REVERSAL_ID=9992L;
	
	public static final String SCHOLARSHIP_ACTIVITY="SCHOLARSHIP_ADJUSTMENT";
	public static final String DISCOUNT_ACTIVITY="DISCOUNT_ADJUSTMENT";
	
	public enum AdmissionWorkFlowStatus{NEW,DOC_RECEIVED,DOC_VERIFIED,FEE_NEGOTIATED,MOVED_TO_MANAGEMENT,APPROVED,FEE_DEPOSITED,COMPLETE};
	
	public enum EnquiryStatus{OPEN,CLOSED,MOVED_TO_ADMISSION};
	
	public enum DocumentType{IDENTITY,ADDRESS,QUALIFICATION,DOB};
}
