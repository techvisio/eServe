package com.techvisio.eserve.util;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ObjectExcelMapper {

	public static byte[] createExcel(List objectList,Map<String,String> columnsMap){
		 byte[] bytes=null;
		if(columnsMap != null && columnsMap.size()>0){
			Workbook workbook = new XSSFWorkbook();

	        Sheet reportSheet = workbook.createSheet("Report");
	        int rowIndex = 0;
	        
	        //create header row
	        Row row = reportSheet.createRow(rowIndex++);
            int cellIndex = 0;
            for(String s:columnsMap.keySet()){
            row.createCell(cellIndex++).setCellValue(columnsMap.get(s));
            }
	        
	        for(Object o : objectList){
	            Row dataRow = reportSheet.createRow(rowIndex++);
	            cellIndex = 0;
	            
	            for(String s:columnsMap.keySet()){
	                try {
	                	dataRow.createCell(cellIndex++).setCellValue( BeanUtils.getSimpleProperty(o, s));
					} catch (IllegalAccessException | InvocationTargetException
							| NoSuchMethodException e) {
						e.printStackTrace();
					}
	                }
	            
	        }

	        //write this workbook in excel file.
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        try {
	            try {
	            	
					workbook.write(bos);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        } finally {
	            try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        bytes = bos.toByteArray();
		}
		  return bytes;
	}
}