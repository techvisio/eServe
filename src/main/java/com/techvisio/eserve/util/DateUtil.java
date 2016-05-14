package com.techvisio.eserve.util;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class DateUtil {

	public static String convertDateToString(java.util.Date date, String pattern) {

		DateFormat format = new SimpleDateFormat(pattern);
		String stringDate = format.format(date);

		return stringDate;
	}

	public static java.util.Date convertStringToUtilDate(String dateString, String pattern) throws ParseException {
		DateFormat format = new SimpleDateFormat(pattern);
		java.util.Date date = null;
		date = format.parse(dateString);
		return date;
	}
	
	public static java.sql.Date convertStringToSqlDate(String dateString, String pattern) throws ParseException {
		DateFormat format = new SimpleDateFormat(pattern);
		java.util.Date javaUtilDate = null;
		javaUtilDate = format.parse(dateString);
		java.sql.Date javeSqlDate=new java.sql.Date(javaUtilDate.getTime());
		return javeSqlDate;
	}
	
	public static String convertDateToString(java.util.Date date){

		DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
		String stringDate = format.format(date);

		return stringDate; 
	}

	public static Date convertStringToSqlDate(String date){

		java.util.Date javaUtilDate = null;
		DateTimeFormatter parser2 = ISODateTimeFormat.dateTime().withZoneUTC();
		if(!StringUtils.isEmpty(date)){
			javaUtilDate= parser2.parseDateTime(date).toDate();
		}
		java.sql.Date javeSqlDate=new java.sql.Date(javaUtilDate.getTime());

		return javeSqlDate;
	}

	

}