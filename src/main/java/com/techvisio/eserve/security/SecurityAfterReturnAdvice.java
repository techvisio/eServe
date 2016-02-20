package com.techvisio.eserve.security;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class SecurityAfterReturnAdvice {
	
	@AfterReturning(
		      pointcut = "execution(* com.techvisio.eserve.service.impl.*)",
		      returning= "result")
		   public void checkClientBeforeReturn(JoinPoint joinPoint, Object result) {

System.out.println(result);
		   }

}
