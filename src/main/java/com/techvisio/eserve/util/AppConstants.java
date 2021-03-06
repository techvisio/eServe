package com.techvisio.eserve.util;

public interface AppConstants {

	public static final String STATE = "STATE";
	public static final String QUESTION = "QUESTION";
	public static final String DESIGNATION = "DESIGNATION";
	public static final String DEPARTMENT = "DEPARTMENT";
	public static final String RESOLUTION = "RESOLUTION";
	public static final String ISSUE = "ISSUE";
	public static final String AGREEMENT_DURATION = "AGREEMENT_DURATION";
	public static final String UNIT_CATEGORY = "UNIT_CATEGORY";
	public static final String CUSTOMER_TYPE = "CUSTOMER_TYPE";
	public static final String SERVICE_PROVIDER = "SERVICE_PROVIDER";
	public static final String CONFIG = "CONFIG";
	public static final String CLIENT_CONFIG = "CLIENT_CONFIG";
	public static final String EQUIPMENT_MODAL_NO = "EQUIPMENT_MODAL_NO";
	public static final String EQUIPMENT_WARRANTY_UNDER = "EQUIPMENT_WARRANTY_UNDER";
	public static final String EQUIPMENT_TYPE = "EQUIPMENT_TYPE";
	
	
	
	
	public static final char APPROVED = 'A';
	public static final char REJECTED = 'R';
	public static final char PENDING = 'P';
	public static final char DRAFTSTATUS = 'D';

	
	
	public static final String CRITICAL = "C";
	public static final String MEDIUM = "M";
	public static final String HIGH = "H";
	public static final String LOW = "L";
	public static final String DEFAULT_PASSWORD = "DEFAULT_PASSWORD";
	public static final String SLA_DAYS_HIGH = "SLA_DAYS_HIGH";
	public static final String SLA_DAYS_LOW = "SLA_DAYS_LOW";
	public static final String SLA_DAYS_CRITICAL = "SLA_DAYS_CRITICAL";
	public static final String SLA_DAYS_MEDIUM = "SLA_DAYS_MEDIUM";
	public static final String SERVICE_REMINDER = "SERVICE_REMINDER";
	public static final String IS_TAX_INCLUDED = "IS_TAX_INCLUDED";
	public static final String PMS_FREQUENCY = "PMS_FREQUENCY";
	public static final String PMS_CACULATION_FREQUENCY = "PMS_CACULATION_FREQUENCY";
	public static final String PMS_CALCULATION_DATA = "PMS_CALCULATION_DATA";
	public static final String PMS_DUE_DATE_REMINDER = "PMS_DUE_DATE_REMINDER";

	public static final String USER = "USER";

	public static final String PROCESSED = "P";
//	public static final String CONFIG = "CONFIG";
	
	public static final Long BASE_AMOUNT_ID=9998L;
	public static final Long TOTAL_AMOUNT_ID=9999L;

	public static final String WORK_ITEM_OPEN_STATUS = "OPEN";
	public static final String WORK_ITEM_CLOSE_STATUS = "CLOSE";
	public static final String CUSTOMER_DRAFT = "CUSTOMER_DRAFT";
	public static final String FOLLOWUP_RENEWAL_SERVICE = "FOLLOWUP RENEWAL SERVICE";
	public static final String SALES_RENEWAL_AGREEMENT = "SALES RENEWAL AGREEMENT";
	public static final String PMS = "PMS";
	public static final String UNIT_DRAFT = "UNIT_DRAFT";
	public static final String PUBLISH = "PUBLISH";
	public static final String APPROVALWORK = "APPROVAL";
	public static final String PENDINGWORK = "AGREEMENT_APPROVAL";

	public static final String ENVIRONMENT = "ENVIRONMENT";
	public static final String WHITE_LISTED_EMAIL_ID = "WHITE_LISTED_EMAIL_ID";
	public static final String WHITE_LSITED_SMS_NO = "WHITE_LSITED_SMS_NO";
	
	public static final String MONTH = "MONTH";
	public static final String DAY = "DAY";
	public static final String YEAR = "YEAR";

	public static final String STATIC_RESOURCE_BASE_PATH = "/static/resources/";

	public enum EventType {
		APPROVAL, RENEW_SERVICE
	};
	
	public enum ComplaintStatus {
		ASSIGNED, UNASSIGNED, CLOSED
	};

	public enum processEnvironment {
		LIVE, TESTING, DEVELOPMENT
	};
	
	public enum CommentType {
		REJECT, PUBLISH, OPERATIONAL
	};
	
	public enum EntityType {
		CUSTOMER, UNIT, USER, COMPLAINT
	};

	public enum CalculationFrequency {
		MONTH,DAY,YEAR
	};

	public enum WorkItemType {
		AGREEMENT_APPROVAL("unit", "APPROVE_SERVICE_AGREEMENT",
				"AGREEMENT APPROVAL", "UNIT"), CUSTOMER_DRAFT("customer",
						"CREATE_CUSTOMER", "CUSTOMER AS DRAFT", "CUSTOMER"), FOLLOWUP_RENEWAL_SERVICE(
								"unit", "RENEW_SERVICE_AGREEMENT", "FOLLOWUP RENEWAL SERVICE",
								"UNIT"), PMS("unit", "CREATE_COMPLAINT", "PMS","UNIT"),SALES_RENEWAL_AGREEMENT("unit", "ACCESS_SALES_RENEW", "SALES RENEWAL AGREEMENT","UNIT");;
		String url;
		String privilege;
		String workType;
		String entityType;

		WorkItemType(String url, String privilege, String workType,
				String entityType) {
			this.url = url;
			this.privilege = privilege;
			this.workType = workType;
			this.entityType = entityType;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getPrivilege() {
			return privilege;
		}

		public void setPrivilege(String privilege) {
			this.privilege = privilege;
		}

		public String getWorkType() {
			return workType;
		}

		public void setWorkType(String workType) {
			this.workType = workType;
		}
		public String getEntityType() {
			return entityType;
		}

		public void setEntityType(String entityType) {
			this.entityType = entityType;
		}

	};

	enum DateFormat{MM_dd_yyyy("MMM dd, yyyy");
	String pattern;
	DateFormat(String pattern){
		this.pattern=pattern;
	}

	public String getPattern(){
		return this.pattern;
	}
	};

	
}
