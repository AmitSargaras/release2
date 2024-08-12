/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.batch.common.filereader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.struts.upload.FormFile;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.integrosys.base.techinfra.logger.DefaultLogger;


/**
 * Reader for Releaselinedetails excel file.
 * 
 * @version $Revision:$
 * @since $Date: $ Tag: $Name: $
 */
public class ReleaselinedetailsExcel  {

	public ArrayList readExcel(FormFile fileUpload,ProcessDataFileReleaselinedetails dd) {
		ArrayList sheetData = new ArrayList();

		try {

            // Data will read for only one sheet of multiple rows   
			
			HSSFWorkbook workbook = new HSSFWorkbook(fileUpload.getInputStream());
			HSSFSheet sheet = workbook.getSheetAt(0);
			 Iterator rows = sheet.rowIterator();
			           while (rows.hasNext()) {
			               HSSFRow row = (HSSFRow) rows.next();
			               Iterator cells = row.iterator();
			
			               ArrayList data = new ArrayList();
			               boolean flag = cells.hasNext();
			               for(int i=0;i<9;i++) 
			               {
			               if (cells.hasNext()) {
			            	   if(i>=6 && i<9)
			            	   {
			            		   HSSFCell c = row.getCell(i, row.RETURN_BLANK_AS_NULL);
			            		   if(c==null)
			            		   {
			            			   data.add(c);
			            		   }
			            		   else
			            		   {
			            			   data.add(row.getCell(i));
			            		   }
			            	   }
			            	   else {
			                   HSSFCell cell = (HSSFCell) cells.next();   
			                    data.add(row.getCell(i));
			            	   }
			                    flag = cells.hasNext();
			                   
			                    DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd at row "+i+" ############:: " + row.getCell(i));
			                    DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd Flag value "+flag); 
			               }
			               else
			               {
			            	   HSSFCell cell = null;
			            	   data.add(cell);
			            	   flag = cells.hasNext();
			               }
			               }
			
			               sheetData.add(data);
			              
			               DefaultLogger.debug(this,"#####################In ReleaselinedetailsFileUploadCmd Data value "+data);
			            }
			        } catch (IOException e) {
			           e.printStackTrace();
			       } 
		return sheetData;
	}
	public ArrayList readExcel(ProcessDataFileReleaselinedetails dd) {
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
			               boolean flag = cells.hasNext();
			               for(int i=0;i<9;i++) 
			               {	   
			               if (cells.hasNext()) {
			                   HSSFCell cell = (HSSFCell) cells.next();   
			                    data.add(cell); 
			                    flag = cells.hasNext();
			               }
			               else
			               {
			            	   HSSFCell cell = null;
			            	   data.add(cell);
			            	   flag = cells.hasNext();
			               }
			               }
			               sheetData.add(data);
			            }
			        } catch (IOException e) {
			           e.printStackTrace();
			       } 
		return sheetData;
	}
	
	
	public ArrayList readXLSXFile(FormFile fileUpload,ProcessDataFileReleaselinedetails dd) throws IOException
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
			               boolean flag = cells.hasNext();
			               for(int i=0;i<9;i++) 
			               {	   
			               if (cells.hasNext()) {
			                   XSSFCell cell = (XSSFCell) cells.next();   
			                    data.add(cell); 
			                    flag = cells.hasNext();
			               }
			               else
			               {
			            	   XSSFCell cell = null;
			            	   data.add(cell);
			            	   flag = cells.hasNext();
			               }
			               }
			
			               sheetData.add(data);
			            }
			        } catch (IOException e) {
			           e.printStackTrace();
			        } 
		       return sheetData;
	  }
	
}
