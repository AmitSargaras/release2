package com.integrosys.cms.batch.common.filereader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.upload.FormFile;


/**
 * Reader for createfacilityline excel file.
 * 
 * @version $Revision:$
 * @since $Date: $ Tag: $Name: $
 */
public class CreatefacilitylineExcel  {

	public ArrayList readExcel(FormFile fileUpload,ProcessDataFileCreatefacilityline dd) {
		ArrayList sheetData = new ArrayList();

		try {

            // Data will read for only one sheet of multiple rows   
			
			HSSFWorkbook workbook = new HSSFWorkbook(fileUpload.getInputStream());
			HSSFSheet sheet = workbook.getSheetAt(0);
			 Iterator rows = sheet.rowIterator();
			           while (rows.hasNext()) {
			               HSSFRow row = (HSSFRow) rows.next();
			               Iterator cells = row.cellIterator();
			
			               ArrayList data = new ArrayList();
			             //  commenting below line as 'flag' is not used anywhere
			            //   boolean flag = cells.hasNext();
			               for(int i=0;i<36;i++) 
			               {	  
			            	   HSSFCell cell = row.getCell(i);
			                    data.add(cell); 
			              /* if (cells.hasNext()) {
			                   HSSFCell cell = (HSSFCell) cells.next();   
			                    data.add(cell); 
			                    flag = cells.hasNext();
			               }
			               else
			               {
			            	   HSSFCell cell = null;
			            	   data.add(cell);
			            	   flag = cells.hasNext();
			               }*/
			               }
			
			               sheetData.add(data);
			            }
			        } catch (IOException e) {
			           e.printStackTrace();
			       } 
		return sheetData;
	}
	public ArrayList readExcel(ProcessDataFileCreatefacilityline dd) {
		ArrayList sheetData = new ArrayList();
		try {

			// Data will read for only one sheet of multiple rows 
			
			FileInputStream fileIn = new FileInputStream(dd.dataFilePath);

			HSSFWorkbook workbook = new HSSFWorkbook(fileIn);
			HSSFSheet sheet = workbook.getSheetAt(0);
			 Iterator rows = sheet.rowIterator();
			           while (rows.hasNext()) {
			               HSSFRow row = (HSSFRow) rows.next();
			               Iterator cells = row.cellIterator();
			
			               ArrayList data = new ArrayList();
			               //  commenting below line as 'flag' is not used anywhere
			            //   boolean flag = cells.hasNext();
			               for(int i=0;i<36;i++) 
			               {	  
			            	   
			            	   HSSFCell cell  =row.getCell(i);
			            	   data.add(cell); 
			            	   
			              /* if (cells.hasNext()) {
			                   HSSFCell cell = (HSSFCell) cells.next();   
			                    data.add(cell); 
			                    flag = cells.hasNext();
			               }
			               else
			               {
			            	   HSSFCell cell = null;
			            	   data.add(cell);
			            	   flag = cells.hasNext();
			               }*/
			               }
			               sheetData.add(data);
			            }
			        } catch (IOException e) {
			           e.printStackTrace();
			       } 
		return sheetData;
	}
	
	
	public ArrayList readXLSXFile(FormFile fileUpload,ProcessDataFileCreatefacilityline dd) throws IOException
	{
		ArrayList sheetData = new ArrayList();
		try{
			
			// Data will read for only one sheet of multiple rows 
			
			XSSFWorkbook workbook = new XSSFWorkbook(fileUpload.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			 Iterator rows = sheet.rowIterator();
			           while (rows.hasNext()) {
			               XSSFRow row = (XSSFRow) rows.next();
			               Iterator cells = row.cellIterator();
			
			               ArrayList data = new ArrayList();
			               //  commenting below line as 'flag' is not used anywhere
			             //  boolean flag = cells.hasNext();
			               for(int i=0;i<36;i++) 
			               {
			            	   XSSFCell cell  =row.getCell(i);
			            	   data.add(cell); 
			              /* if (cells.hasNext()) {
			                   XSSFCell cell = (XSSFCell) cells.next();   
			                    data.add(cell); 
			                   // flag = cells.hasNext();
			               }
			               else
			               {
			            	   XSSFCell cell = null;
			            	   data.add(cell);
			            	 //  flag = cells.hasNext();
			               }*/
			               }
			
			               sheetData.add(data);
			            }
			        } catch (IOException e) {
			           e.printStackTrace();
			        } 
		       return sheetData;
	  }
	
}
