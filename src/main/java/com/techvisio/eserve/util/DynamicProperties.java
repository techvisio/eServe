package com.techvisio.eserve.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DynamicProperties {
	public String title();
	public String id();
	public String type() default "text";
	public String[] validValues() default {""};
	public boolean isMandatory() default false;
	public boolean readOnly() default false;
	public String masterDataCode() default "";
	}
