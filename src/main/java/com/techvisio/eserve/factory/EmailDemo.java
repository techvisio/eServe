package com.techvisio.eserve.factory;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class EmailDemo {

	
	public static void main( String[] args )  
	        throws Exception  
	    {  
	        /* 
	         *   first, get and initialize an engine 
	         */  
	  
	        VelocityEngine ve = new VelocityEngine();  
	        ve.init();  
	  
	  
	        /* 
	         *  add element to a VelocityContext 
	         */  
	  
	        VelocityContext context = new VelocityContext();  
	        context.put("clientCompany", "techVisio Pvt Ltd");  
	  
	        /* 
	         *   get the Template   
	         */  
	  
	        Template t = ve.getTemplate( "src/main/webapp/home/Invoice.html.vm" );  
	  
	        /* 
	         *  now render the template into a Writer, here  
	         *  a StringWriter  
	         */  
	  
	        StringWriter writer = new StringWriter();  
	  
	        t.merge( context, writer );  
	  
	        /* 
	         *  use the output in the body of your emails 
	         */  
	  
	        System.out.println( writer.toString() );  
	    }  
}
