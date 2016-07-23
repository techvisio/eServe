package com.techvisio.eserve.beans;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		
		String str = "31,231,312,31";
		String[] stra  = str.split(",");

		List<String> list = new ArrayList<String>(stra.length);
		List<Integer> integerList = new ArrayList<Integer>();

		for(String c : stra){
		    list.add(c);
		}

		for(String s : list) {
			
			integerList.add(Integer.valueOf(s));}
		
		System.out.println(integerList);
	}
}	
