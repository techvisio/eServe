package com.techvisio.eserve.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.techvisio.eserve.beans.Client;
import com.techvisio.eserve.beans.ClientConfig;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.manager.CacheManager;

public class CommonUtil {

	public static void propogateCustomerId(
			Customer customer) {

		Long customerId = customer.getCustomerId();
		if (customer.getUnits() != null) {
			for (Unit unit : customer.getUnits()) {
				unit.setCustomerId(customerId);
			}
		}
	}

	public static User getCurrentUser(){

		User user = null;
		if(SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() !=null){
			user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		return user;
	}

	public static Client getCurrentClient(){

		User user = null;
		if(SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() !=null){
			user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		return user.getClient();
	}

	public static Date getDate(Date date, int countDays, boolean includeWeekendDays, boolean isIncreaseOrDecrease){

		if(includeWeekendDays)
		{

		}

		if(isIncreaseOrDecrease)
		{
			Calendar c = Calendar.getInstance(); 
			c.setTime(date); 
			c.add(Calendar.DATE, (countDays));
			date = c.getTime();

		}
		else
		{
			Calendar c = Calendar.getInstance(); 
			c.setTime(date); 
			c.add(Calendar.DATE, -(countDays));
			date = c.getTime();
		}

		return date;

	}

	public static String getFieldValue(Class classType, String propertyName) throws NoSuchFieldException, SecurityException, ClassNotFoundException {

		Field field = classType.getDeclaredField(propertyName); 
		Annotation[] annotations = field.getDeclaredAnnotations();

		for(Annotation annotation : annotations){
			if(annotation instanceof javax.persistence.Column){
				javax.persistence.Column myAnnotation = (javax.persistence.Column) annotation;
				return myAnnotation.name();
			}
		}
		return null;
	}

	public static Date getDueDateByPmsFrequencyCalculator(Date date, int pmsFrequencyCount, String pmsFrequencyCalculator, int dueDateCount){

		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		if(pmsFrequencyCalculator.equalsIgnoreCase(AppConstants.CalculationFrequency.DAY.name()))
		{
			c.add(Calendar.DATE, (pmsFrequencyCount));
		}
		else if(pmsFrequencyCalculator.equalsIgnoreCase(AppConstants.CalculationFrequency.MONTH.name()))
		{
			c.add(Calendar.MONTH, (pmsFrequencyCount));	
		}
		else if(pmsFrequencyCalculator.equalsIgnoreCase(AppConstants.CalculationFrequency.YEAR.name()))
		{
			c.add(Calendar.YEAR, (pmsFrequencyCount));	
		}
		date = c.getTime();
		c.setTime(date);
		c.add(Calendar.DATE, -(dueDateCount));
		date = c.getTime();
		return date;
	}

	public static List<String> stringToStringArray(String value){

		String[] stringArray  = value.split(",");
		for (int i = 0; i < stringArray.length; i++){
			stringArray[i] = stringArray[i].trim();
		}
		
		List<String> stringArrayList = new ArrayList<String>(stringArray.length);
		for(String c : stringArray){
			stringArrayList.add(c);
		}
		return stringArrayList;
	}

	public static List<Integer> stringArrayToIntegerArray(List<String> stringArrayList){

		List<Integer> integerList = new ArrayList<Integer>();
		for(String s : stringArrayList) {
			integerList.add(Integer.valueOf(s));}
		return integerList;

	}
	
	public static String getUserImagePath(){
		String basePath=AppConstants.STATIC_RESOURCE_BASE_PATH;
		return basePath+getCurrentClient().getClientId()+"/"+"users";
	}

}