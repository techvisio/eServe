package com.techvisio.eserve.util;

import org.springframework.util.StringUtils;

public class StringUtilities {

	public static String getDefaultOrBlankValueOfString(String property){
	
		if(StringUtils.isEmpty(property)){
			property=null;
		}
		return property;
	}

}
