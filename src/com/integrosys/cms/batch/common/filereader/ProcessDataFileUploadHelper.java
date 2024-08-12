package com.integrosys.cms.batch.common.filereader;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.upload.FormFile;

public class ProcessDataFileUploadHelper {

	public static List<List<Cell>> readExcel(FormFile fileUpload) {
		List<List<Cell>> sheetData = new ArrayList<List<Cell>>();

		try {
			HSSFWorkbook workbook = new HSSFWorkbook(fileUpload.getInputStream());
			HSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.rowIterator();
			while (rows.hasNext()) {
				HSSFRow row = (HSSFRow) rows.next();
				Iterator<Cell> cells = row.cellIterator();

				List<Cell> data = new ArrayList<Cell>();
				while(cells.hasNext()) {
					if (cells.hasNext()) {
						HSSFCell cell = (HSSFCell) cells.next();
						while(data.size() <= cell.getColumnIndex()) {
							data.add(null);
						}
						data.set(cell.getColumnIndex(),cell);
					}
				}
				sheetData.add(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return sheetData;
	}
	
	public static List<List<Cell>> readXLSXFile(FormFile fileUpload) throws IOException
	{
		List<List<Cell>> sheetData = new ArrayList<List<Cell>>();
		try {

			XSSFWorkbook workbook = new XSSFWorkbook(fileUpload.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.rowIterator();
			while (rows.hasNext()) {
				XSSFRow row = (XSSFRow) rows.next();
				Iterator<Cell> cells = row.cellIterator();

				List<Cell> data = new ArrayList<Cell>();
				while(cells.hasNext()) {
					XSSFCell cell = (XSSFCell) cells.next();
					while(data.size() <= cell.getColumnIndex()) {
						data.add(null);
					}
					data.set(cell.getColumnIndex(),cell);
				}
				sheetData.add(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return sheetData;
	}
	
	public static String getCellDataByType(Cell cell, boolean isTypeXlsx) {
		String returnValue = null;
		if(isTypeXlsx) {
			if (cell != null && cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
				return BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();
			}
			else if (cell != null && cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
				return cell.getStringCellValue().trim();
		} else {
			if (cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
				return BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();
			else if (cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
				return cell.getStringCellValue().trim();
		}
		
		return returnValue;
	}
	
}
