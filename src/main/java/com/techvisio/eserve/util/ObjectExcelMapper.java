package com.techvisio.eserve.util;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ObjectExcelMapper {

	public static void createExcel(String path,List objectList,String[] columns){
		if(columns != null && columns.length>0){
			Workbook workbook = new XSSFWorkbook();

	        Sheet reportSheet = workbook.createSheet("Report");
	        int rowIndex = 0;
	        
	        //create header row
	        Row row = reportSheet.createRow(rowIndex++);
            int cellIndex = 0;
            for(String s:columns){
            row.createCell(cellIndex++).setCellValue(s.toUpperCase());
            }
	        
	        for(Object o : objectList){
	            Row dataRow = reportSheet.createRow(rowIndex++);
	            cellIndex = 0;
	            
	            for(String s:columns){
	                try {
	                	dataRow.createCell(cellIndex++).setCellValue( BeanUtils.getSimpleProperty(o, s));
					} catch (IllegalAccessException | InvocationTargetException
							| NoSuchMethodException e) {
						e.printStackTrace();
					}
	                }
	            
	        }

	        //write this workbook in excel file.
	        try {
	        	File file=new File(path);
	        	if(!file.exists()){
	        		file.createNewFile();
	        	}
	            FileOutputStream fos = new FileOutputStream(path);
	            workbook.write(fos);
	            fos.close();

	            System.out.println(path + " is successfully written");
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

		}
	}
}