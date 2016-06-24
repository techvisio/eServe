package com.techvisio.eserve.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;

import com.techvisio.eserve.beans.Client;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.User;

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
}