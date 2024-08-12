package com.integrosys.cms.ui.diaryitem;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.upload.FormFile;
import org.apache.poi.xssf.*;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.diary.bus.IDiaryItem;
import com.integrosys.cms.app.diary.bus.IDiaryItemJdbc;
import com.integrosys.cms.app.ftp.CMSFtpClient;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.batch.common.filereader.PoiExcel;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;
 
public class ODScheduleUploadExcelToDB{
	
	ODScheduleUploadExcelToDB(){
		
	}
	
 
public String readExcelData(FormFile formFile,long diaryNumber,long itemId,String segment,IDiaryItem item) throws IOException
{
	
	String uploadFileError = "N";
	
	try{
		ResourceBundle resbundle = ResourceBundle.getBundle("ofa");
		IDiaryItemJdbc diaryItemJdbc=(IDiaryItemJdbc)BeanHouse.get("diaryItemJdbc");
		//File myFile = new File("C://temp/Employee.xlsx");
		
		//FileInputStream fis = new FileInputStream(formFile.getInputStream());

		//Read more: http://www.java67.com/2014/09/how-to-read-write-xlsx-file-in-java-apache-poi-example.html#ixzz5OnBu8L7y
		
		XSSFWorkbook myWorkBook = new XSSFWorkbook (formFile.getInputStream()); 


	// Return first sheet from the XLSX workbook 

	XSSFSheet mySheet = myWorkBook.getSheetAt(0); 


	// Get iterator to all the rows in current sheet 

	Iterator<Row> rowIterator = mySheet.iterator(); 

	// Traversing over each row of XLSX file 
	
	String loanAmount = null;
	String intrestRate = null;
	String reducedOn = null;
	StringBuffer sqlInsertQuery = new StringBuffer() ;
	
	
	
	 IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
		IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
		IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
		Date dateApplication=new Date();
		long ladGenIndicator=0l;
		for(int i=0;i<generalParamEntries.length;i++){
			if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
				dateApplication=new Date(generalParamEntries[i].getParamValue());
			}
		}
		
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateApplication);
		int dayOfMonth = 0 ;
		int date = cal.get(Calendar.DATE);
		String reduceDate = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		
	while (rowIterator.hasNext()) { 
		
		Row row = rowIterator.next(); 

		sqlInsertQuery =sqlInsertQuery.append("INSERT INTO CMS_DIARY_SCHEDULE_DATA VALUES("+diaryNumber+",");
	// For each row, iterate through each columns 

	Iterator<Cell> cellIterator = row.cellIterator();
	
	FormulaEvaluator evaluator = myWorkBook.getCreationHelper().createFormulaEvaluator();
//	Cell cell = row.getCell(cellReference.getCol());
	 

	 DecimalFormat f = new DecimalFormat();
	 f.setMaximumFractionDigits(2);
	 int rowNum = row.getRowNum();
	 

	 while (cellIterator.hasNext()) {
		 Cell cell = cellIterator.next();
		 CellValue cellValue = evaluator.evaluate(cell);
		/* String cellValueInString = new BigDecimal(cell.getNumericCellValue()).toPlainString();
		 System.out.println(cellValueInString);
		*/ 
		 int columnNum = cell.getColumnIndex();
		
		 if(rowNum == 0 && columnNum == 1  ) {
			 loanAmount = new BigDecimal(cell.getNumericCellValue()).toPlainString();
			 if(!"".equals(loanAmount) && loanAmount != null) {
			 sqlInsertQuery.append("'"+loanAmount+"'");
			 }else {
				 uploadFileError = "Y";
				 break;
			 }
		 }
		 if(rowNum == 1 && columnNum == 1  ) {
			 intrestRate = new BigDecimal(f.format(cell.getNumericCellValue()*100)).toPlainString();
			 if(!"".equals(intrestRate) && intrestRate != null) {
			 sqlInsertQuery.append(",'"+intrestRate+"'");
			 }else {
				 uploadFileError = "Y";
				 break;
			 }
			 
		 }
		 if(rowNum == 2 && columnNum == 1  ) {
			 reducedOn = cell.getStringCellValue();
			 if(!"".equals(reducedOn) && reducedOn != null) {
			 sqlInsertQuery.append(",'"+reducedOn+"'");
		
			 reduceDate = reducedOn.replaceAll("[^0-9]", "");
			 dayOfMonth = Integer.parseInt(reduceDate);				
				
				
			 }else {
				 uploadFileError = "Y";
				 break;
			 }
		 }
		 
		 
		 
		 if(rowNum > 5) {
		
	     if(rowNum > 5 && columnNum == 0) {
	    	 
	    	 if (rowNum == 6) {	
	    		 
	    		 if(dayOfMonth < date) {
					cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH) + 1));
					cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
					dateApplication = cal.getTime();
					reduceDate = dateFormat.format(dateApplication).toString();
	    		 }else {
	    			cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH)));
					cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
					dateApplication = cal.getTime();
	    			reduceDate = dateFormat.format(dateApplication).toString();
	    		 }
			}
	    	 
					if (rowNum > 6) {	
							cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH) + 1));
							cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
							dateApplication = cal.getTime();
							reduceDate = dateFormat.format(dateApplication).toString();
					}
				
				
				
		 sqlInsertQuery.append("'"+loanAmount+"'"+",'"+intrestRate+"'"+",'"+reduceDate+"'");
	     }
		 switch (cellValue.getCellType()) {
		 
		 
		 case Cell.CELL_TYPE_STRING: 
			 if(cell.getStringCellValue() != null && !"".equals(cell.getStringCellValue())) {
		 sqlInsertQuery.append(",'"+cell.getStringCellValue()+"'");
			 }else {
				 uploadFileError = "Y";
			 }
		 break; 
		 
		 
		 case Cell.CELL_TYPE_NUMERIC:
			 if(new BigDecimal(cell.getNumericCellValue()).setScale(2, BigDecimal.ROUND_UP).toPlainString() != null && !"".equals(new BigDecimal(cell.getNumericCellValue()).setScale(2, BigDecimal.ROUND_UP).toPlainString())) {
		 sqlInsertQuery.append(",'"+new BigDecimal(cell.getNumericCellValue()).setScale(2, BigDecimal.ROUND_UP).toPlainString()+"'");
			 }else {
				 uploadFileError = "Y";
			 }
		 break;
		 
		 
		 default : 
			 if(new BigDecimal(cell.getNumericCellValue()).setScale(2, BigDecimal.ROUND_UP).toPlainString() != null && !"".equals(new BigDecimal(cell.getNumericCellValue()).setScale(2, BigDecimal.ROUND_UP).toPlainString())) {
			 sqlInsertQuery.append(",'"+new BigDecimal(cell.getNumericCellValue()).setScale(2, BigDecimal.ROUND_UP).toPlainString()+"'");
			 }else {
				 uploadFileError = "Y";
			 }
			 
			
		 }
	
		 }
		 
		 
	/*switch (cellValue.getCellType()) {
	 case Cell.CELL_TYPE_STRING: 
	 System.out.print(cellValue.getStringValue() + "\t");
	 break; 
	 case Cell.CELL_TYPE_NUMERIC: 
		 
	 System.out.print(cellValue.getNumberValue() + "\t"); 
	 break; 
	 case Cell.CELL_TYPE_BOOLEAN: System.out.print(cellValue.getBooleanValue() + "\t");
	 break; 
	 
	 default : 
		 System.out.println(NumberToTextConverter.toText(Double.parseDouble(cell.toString())));
		 
		String cellValueInString = new BigDecimal(cell.getNumericCellValue()).toPlainString();
	 } */
		 
		// sqlInsertQuery.append(")");
	 } 
	 if("N".equals(uploadFileError)) {
	 if(rowNum == 5) {
		 sqlInsertQuery.append("'"+loanAmount+"'"+",'"+intrestRate+"'"+",'"+reducedOn+"'"+",'','','','',''"+",'"+loanAmount+"','','','','','','','','','','')");
		 System.out.println("SQLQUERY>>"+sqlInsertQuery);
		 diaryItemJdbc.insertODScheduleData(sqlInsertQuery);
		 System.out.println("");
	 }
	 if(rowNum > 5) {
	 sqlInsertQuery.append(",'N',to_date(TO_CHAR('"+reduceDate+"'),'DD-MM-YYYY'),'"+itemId+"','"+segment+"','"+item.getCustomerReference()+"','"+item.getCustomerName()+"','"+item.getDropLineOD()+"','"+item.getActivity()+"','"+item.getMakerId()+"','"+item.getItemID()+"')");
	 System.out.println("SQLQUERY>>"+sqlInsertQuery);
	 diaryItemJdbc.insertODScheduleData(sqlInsertQuery);
	 System.out.println(""); 
	 }
	 sqlInsertQuery.delete(0, sqlInsertQuery.length());
	}
	}
	}
	catch (Exception e) {
		DefaultLogger.error(this, "", e);
		uploadFileError = "Y";
	}
	return uploadFileError;
	
}
 
private static void showExelData(List sheetData) {
//
// Iterates the data and print it out to the console.
//
for (int i = 0; i < sheetData.size(); i++) {
List list = (List) sheetData.get(i);
for (int j = 0; j < list.size(); j++) {
Cell cell = (Cell) list.get(j);
if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
System.out.print(cell.getNumericCellValue());
} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
System.out.print(cell.getRichStringCellValue());
} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
System.out.print(cell.getBooleanCellValue());
}
if (j < list.size() - 1) {
System.out.print(", ");
}
}
System.out.println("");
}
}
}