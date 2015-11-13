package com.techvisio.eserve.util;

import org.springframework.security.core.context.SecurityContextHolder;

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
	
public static Long getClientId(){
		
		User user = null;
		if(SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() !=null){
		user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			}
		return user.getClientId();
	}
}