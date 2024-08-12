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
import java.util.HashMap;
import java.util.StringTokenizer;

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
 * Reader for excel file.
 * 
 * @author $Author: kltan $<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name: $
 */
public class PoiExcel {

	public void readExcel(File fileUpload,ProcessDataFile dd) {

		try {

			FileInputStream fileIn = new FileInputStream(fileUpload);

			HSSFWorkbook wb = new HSSFWorkbook(fileIn);
			if(wb.getNumberOfSheets()>0){
				for(int sheetNo=0; sheetNo < wb.getNumberOfSheets(); sheetNo++){
					HSSFSheet sheet = wb.getSheetAt(sheetNo); // first sheet
					HSSFRow headerRow;
					HSSFRow row;
					HSSFCell headerCell;
					HSSFCell cell;
		
					int rowEndIndex;
					if (dd.rowEndIndex == 0) {
						rowEndIndex = sheet.getLastRowNum();
					}
					else {
						rowEndIndex = dd.rowEndIndex;
					}
					
					//In Excel File, there can be a possibility of data available in multiple sheets. If SheetNo = 0 then data should get read from 
					// 4th row else from 1st row as mentioned by HDFC in mail.
					
					if(sheetNo>0){
						 dd.rowStartIndex = 0;
					}
					
					// set row
					for (int j = dd.rowStartIndex; j <= rowEndIndex; j++) {
						row = sheet.getRow(j);
						if(sheetNo>0){
							headerRow = null;
						}else{
							headerRow = sheet.getRow(dd.rowHeaderIndex);
						}
						if (row == null) {
							continue; // skip blank row
						}
		
						// set column
						ArrayList cellArray = new ArrayList();
		
						HashMap hm = new HashMap();
		
						if (dd.columnsIndex == null) {
							for (int i = dd.columnStartIndex; i < dd.totalColumn; i++) {
		
								cell = row.getCell((short) i);
								/*if(headerRow!=null && !"".equals(headerRow)){
									headerCell = headerRow.getCell((short) i);
		
									if ((cell == null) || (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK)
											|| (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)) {
										hm.put(headerCell.getStringCellValue(), cell.getStringCellValue());
									}
									else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
										NumberFormat formatter = new DecimalFormat("##.##########");
										hm.put(headerCell.getStringCellValue(), formatter.format(cell.getNumericCellValue()));
									}
									else {
										// System.out.println("This cell type("+cell.
										// getCellType()+") not yet take care!");
									}
								}else{*/
									if ((cell == null) || (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK)
											|| (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)) {
										hm.put(dd.columnNameValuesMap.get(i), cell.getStringCellValue());
									}
									else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
										NumberFormat formatter = new DecimalFormat("##.##########");
										hm.put(dd.columnNameValuesMap.get(i), formatter.format(cell.getNumericCellValue()));
									}
								}
						//	}
						}
						else {
							StringTokenizer st = new StringTokenizer(dd.columnsIndex, ",");
							while (st.hasMoreTokens()) {
								int i = Integer.parseInt(st.nextToken());
								cell = row.getCell((short) i);
								
								if(headerRow!=null && !"".equals(headerRow)){
									headerCell = headerRow.getCell((short) i);
			
									if ((cell == null) || (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK)
											|| (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)) {
										hm.put(headerCell.getStringCellValue(), cell.getStringCellValue());
									}
									else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
										NumberFormat formatter = new DecimalFormat("##.##########");
										hm.put(headerCell.getStringCellValue(), formatter.format(cell.getNumericCellValue()));
									}
									else {
										// System.out.println("This cell type("+cell.
										// getCellType()+") not yet take care!");
									}
								}else{
									if ((cell == null) || (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK)
											|| (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)) {
										hm.put(dd.columnNameValuesMap.get(i), cell.getStringCellValue());
									}
									else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
										NumberFormat formatter = new DecimalFormat("##.##########");
										hm.put(dd.columnNameValuesMap.get(i), formatter.format(cell.getNumericCellValue()));
									}
								}
							}
						}
						dd.rowArray.add(hm);
					}
				}
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
		}
	}
	
	public void readExcel(ProcessDataFile dd) {

		try {

			FileInputStream fileIn = new FileInputStream(dd.dataFilePath);

			HSSFWorkbook wb = new HSSFWorkbook(fileIn);
			HSSFSheet sheet = wb.getSheetAt(0); // first sheet
			HSSFRow headerRow;
			HSSFRow row;
			HSSFCell headerCell;
			HSSFCell cell;

			int rowEndIndex;
			if (dd.rowEndIndex == 0) {
				rowEndIndex = sheet.getLastRowNum();
			}
			else {
				rowEndIndex = dd.rowEndIndex;
			}

			// set row
			for (int j = dd.rowStartIndex; j < rowEndIndex; j++) {
				row = sheet.getRow(j);
				headerRow = sheet.getRow(dd.rowHeaderIndex);
				if (row == null) {
					continue; // skip blank row
				}

				// set column
				ArrayList cellArray = new ArrayList();

				HashMap hm = new HashMap();

				if (dd.columnsIndex == null) {
					for (int i = dd.columnStartIndex; i < dd.totalColumn; i++) {

						cell = row.getCell((short) i);
						headerCell = headerRow.getCell((short) i);

						if ((cell == null) || (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK)
								|| (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)) {
							hm.put(headerCell.getStringCellValue(), cell.getStringCellValue());
						}
						else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							NumberFormat formatter = new DecimalFormat("##.##########");
							hm.put(headerCell.getStringCellValue(), formatter.format(cell.getNumericCellValue()));
						}
						else {
							// System.out.println("This cell type("+cell.
							// getCellType()+") not yet take care!");
						}
					}
				}
				else {
					StringTokenizer st = new StringTokenizer(dd.columnsIndex, ",");
					while (st.hasMoreTokens()) {
						int i = Integer.parseInt(st.nextToken());
						cell = row.getCell((short) i);
						headerCell = headerRow.getCell((short) i);

						if ((cell == null) || (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK)
								|| (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)) {
							hm.put(headerCell.getStringCellValue(), cell.getStringCellValue());
						}
						else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							NumberFormat formatter = new DecimalFormat("##.##########");
							hm.put(headerCell.getStringCellValue(), formatter.format(cell.getNumericCellValue()));
						}
						else {
							// System.out.println("This cell type("+cell.
							// getCellType()+") not yet take care!");
						}
					}
				}
				dd.rowArray.add(hm);
			}
			// System.out.println("successful insert excel data into array...");
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
		}
	}
	
	
	public void readXLSXFile(File fileUpload,ProcessDataFile dd) throws IOException
	{
		try{
			FileInputStream fileIn = new FileInputStream(fileUpload);
	
			XSSFWorkbook wb = new XSSFWorkbook(fileIn);
			
			if(wb.getNumberOfSheets()>0){
				for(int sheetNo=0; sheetNo < wb.getNumberOfSheets(); sheetNo++){
					XSSFSheet sheet = wb.getSheetAt(sheetNo); // first sheet
					XSSFRow headerRow;
					XSSFRow row;
					XSSFCell headerCell;
					XSSFCell cell;
		
					int rowEndIndex;
					if (dd.rowEndIndex == 0) {
						rowEndIndex = sheet.getLastRowNum();
					}
					else {
						rowEndIndex = dd.rowEndIndex;
					}
					
					//In Excel File, there can be a possibility of data available in multiple sheets. If SheetNo = 0 then data should get read from 
					// 4th row else from 1st row as mentioned by HDFC in mail.
					
					if(sheetNo>0){
						 dd.rowStartIndex = 0;
					}
					
					// set row
					for (int j = dd.rowStartIndex; j <= rowEndIndex; j++) {
						row = sheet.getRow(j);
						if(sheetNo>0){
							headerRow = null;
						}else{
							headerRow = sheet.getRow(dd.rowHeaderIndex);
						}
						if (row == null) {
							continue; // skip blank row
						}
		
						// set column
						ArrayList cellArray = new ArrayList();
						HashMap hm = new HashMap();
						if (dd.columnsIndex == null) {
							for (int i = dd.columnStartIndex; i < dd.totalColumn; i++) {
		
								cell = row.getCell((short) i);
								if ((cell == null) || (cell.getCellType() == XSSFCell.CELL_TYPE_BLANK)
										|| (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)) {
									hm.put(dd.columnNameValuesMap.get(i), cell.getStringCellValue());
								}
								else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
									NumberFormat formatter = new DecimalFormat("##.##########");
									hm.put(dd.columnNameValuesMap.get(i), formatter.format(cell.getNumericCellValue()));
								}
							}
						}else {
							StringTokenizer st = new StringTokenizer(dd.columnsIndex, ",");
							while (st.hasMoreTokens()) {
								int i = Integer.parseInt(st.nextToken());
								cell = row.getCell((short) i);
								
								if(headerRow!=null && !"".equals(headerRow)){
									headerCell = headerRow.getCell((short) i);
			
									if ((cell == null) || (cell.getCellType() == XSSFCell.CELL_TYPE_BLANK)
											|| (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)) {
										hm.put(headerCell.getStringCellValue(), cell.getStringCellValue());
									}
									else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
										NumberFormat formatter = new DecimalFormat("##.##########");
										hm.put(headerCell.getStringCellValue(), formatter.format(cell.getNumericCellValue()));
									}
									else {
										// System.out.println("This cell type("+cell.
										// getCellType()+") not yet take care!");
									}
								}else{
									if ((cell == null) || (cell.getCellType() == XSSFCell.CELL_TYPE_BLANK)
											|| (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)) {
										hm.put(dd.columnNameValuesMap.get(i), cell.getStringCellValue());
									}
									else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
										NumberFormat formatter = new DecimalFormat("##.##########");
										hm.put(dd.columnNameValuesMap.get(i), formatter.format(cell.getNumericCellValue()));
									}
								}
							}
						}
						dd.rowArray.add(hm);
					}
				}
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
		}
	}
	
	
}