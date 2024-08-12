/*
 * Created on Nov 20, 2004 By Prashant Soneria
 *
 * Copyright (c) 2004 aurionPro Solutions Pvt, Ltd.
 * B4, Udyog Sadan III, MIDC Andheri-East, Mumbai-93.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of 
 * aurionPro Solutions Pvt, Ltd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with aurionPro Solutions Pvt, Ltd.
 * 
 */
package com.integrosys.cms.app.poi.report.writer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;


/**
 * <p><b>
 * Gegeral report writer that is used to write the report details into Text/ Excel/ PDF files. These files can be
 * downloaded later.
 * </b></p>
 * 
 * @version 1.0
 * 
 * @author Prashant Soneria
 * 
 */
public class CopyOfExcelReportWriter extends BaseReportWriter
{
	String fileHeader = null;
	String fileFooter = null;

	FileFormat ff = null;
	List<String[]> recordsArray = null;
	String fileName = null;
	String basePath = null;

	String columnNames[] = null;
	String columnsSubNames[] = null;
	String columnsNameMerge[] = null;
	String columnNameData[] = null;
	String columnNameValues[] = null;
	String delimiters[] = {"|"};
	int columnCount = -1;
	String columnsHeading[] = null;
	float columnWidths[] = null;
	int totalWidth = 0;
	Map<String, Object> map = null;
	Integer columnWidth[] = null;
	String[] summList = null;

	String imagePath = null;
	String addressDetails = null;
	private List<int[]> singleRecordColoumnType;
	private List<Integer[]> singleRecordColoumnWidth;
	private List<List<String[]>> dataList;
	private List<String[]> singleRecordColumnHeadings;
	private List<String[]> listOfColoumnNames = null;
	private List<String[]> listOfColoumnNameValues = null;
	private String bankName;
	public static final short COLOUR_BLACK = 0;
	public static final short COLOUR_WHITE = 1;

	
	public CopyOfExcelReportWriter()
	{

	}

	// (Ticket)parameters.get("ticket"), parameters, fileName, dataList, fileType
	public CopyOfExcelReportWriter( String basePath, Map<String, Object> map, String fileName, List<String[]> recordList) throws Exception
	{
		setParameters(basePath, map, fileName, recordList);
	}

	public void setParameters(String basePath, Map<String, Object> map, String fileName, List<String[]> recordList) {

		try
		{
			ff = new FileFormat();
			String [] columnsMap = (String[]) map.get("columnsMap");
			ff.setFileFormatWithSrNos(ff, columnsMap);
			
			this.recordsArray = recordList;
			this.map = map;
			this.fileName = fileName;
			this.basePath = basePath;
			this.columnNames = columnsMap;
			// this was added by poonam.kadam for sub headers and merging the first table heads
			this.columnsSubNames = (String[]) map.get("columnsSubMap");
			this.columnsNameMerge = (String[]) map.get("columnsMapMerge");
			// end poonam.kadam
			this.columnNameData = (String[]) map.get("columnNameData");
			this.columnsHeading = (String[]) map.get("columnsHeading");
			this.columnNameValues = (String[]) map.get("columnNameValues");
			this.columnWidth = (Integer[]) map.get("columnWidths");
			this.summList = (String[]) map.get("summaryList");
			this.imagePath = (String) map.get("imagePath");
			this.addressDetails = (String) map.get("addressDetails");
			
			this.singleRecordColoumnType = (List<int[]>)map.get("singlerecordcoloumntype");
			this.singleRecordColoumnWidth = (List<Integer[]>)map.get("singlerecordcoloumnwidth");
			this.dataList = (List<List<String[]>>)map.get("recordsList");
			this.singleRecordColumnHeadings = (List<String[]>)map.get("columnheadingsrowwise");
			this.listOfColoumnNames  = (List<String[]>)map.get("listofcoloumnnames");
			this.listOfColoumnNameValues   = (List<String[]>)map.get("listofcoloumnnamevalues");
			
			if(map.get("columnType") != null) {
				int[] colType = (int[]) map.get("columnType");
				ff.setColType(colType);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();

		}
	}

	public void generate()
	{

		try
		{
			ff.setFileType(FileFormat.FILE_TYPE_EXCEL);
			ExcelWriter ex = new ExcelWriter(ff, basePath, columnWidth);
			
			int recordSize = 0;
			if(recordsArray != null && !recordsArray.isEmpty()){
				recordSize = recordsArray.size();
			}else if(dataList != null && !dataList.isEmpty()){
				recordSize = dataList.size();
				ff.setFileFormatWithSrNos(ff, singleRecordColumnHeadings.get(0));
			}
				
			if(imagePath != null && !imagePath.equals(""))
			{
				ex.logoPath(imagePath);
			}

			ex.open(fileName, recordSize);

			if(recordSize > 0 ) {

				ex.bgColour = ExcelWriter.COLOUR_BLUE_GREY;
				ex.fontColour = ExcelWriter.COLOUR_WHITE;
				ex.horAlignment = ex.ALIGN_CENTER;
				ex.setBold = true;

				Header header = ex.getHeader();

				//  setting the header
				String strtmpHeaderAlign="", strtmpHeaderText="", strCombinedHeaderText="";
				int headerAlign = 0;

				if (fileHeader != null && !fileHeader.equals(""))
				{
					StringTokenizer strHeader = new StringTokenizer(fileHeader,"@");
					while (strHeader.hasMoreTokens())
					{
						String strHeaderValue = strHeader.nextToken();
						StringTokenizer strTmpToken = new StringTokenizer(strHeaderValue,":");

						if (strHeaderValue != null && !strHeaderValue.equals(""))
						{
							strtmpHeaderAlign = strTmpToken.nextToken();
							strtmpHeaderText = strTmpToken.nextToken();
							strCombinedHeaderText = strCombinedHeaderText + strtmpHeaderText + " \n";
						}
					}
					////////////////////
					//Image image = Image.getInstance(PDFWriter.getUWBTitleImagePath());
					header.setLeft(strCombinedHeaderText);
					//setting the header alignment
					if (strtmpHeaderAlign.equals("left"))
					{
						headerAlign = 0;
					}
					else if (strtmpHeaderAlign.equals("center"))
					{
						headerAlign = 1;
					}
					else
					{	
						headerAlign = 2;
					}
				}

				//////////////////////////////Added By Karuna 

				///Displying Report Name
				ex.setBorder = false;

				ex.fontColour = ExcelWriter.COLOUR_BLACK;
				ex.bgColour = ExcelWriter.COLOUR_WHITE;

				ex.verAlignment = ex.ALIGN_CENTER;
				ex.horAlignment = ex.ALIGN_CENTER;
//				ex.createEmptyLine();
				ex.setBold = true;
				
				////// Added by bijal
				if(bankName != null && !"".equalsIgnoreCase(bankName)){
					ex.fontSize = 12;
					ex.writeLine(bankName);
					//ex.writeLine("");
					ex.fontSize = 10;
				}
				/*if(addressDetails != null && !"".equalsIgnoreCase(addressDetails)){
					ex.fontSize = 8;
					ex.setBold = false;
					ex.writeLine(addressDetails);
					ex.writeLine("");
					ex.fontSize = 10;
					ex.setBold = true;
				}*/
				
				Object object = map.get("reportname");
				String reportName = null;
				if(object != null && !"".equals(object)){ 

					String [] reportname = ((String)object).split("\n\n");

					if(reportname[0] != null && !"".equalsIgnoreCase(reportname[0])) {
						ex.writeLine(reportname[0]);
						reportName = reportname[0];
						ex.writeLine("");
						if(reportname.length > 1)
							ex.writeLine(reportname[1]);
					}
				}

				ex.setBold = false;

				//if((!columnNameData.equals("") || !columnNameData.equals(null)))
				if(columnNameData != null)
				{	
					ex.writeLine(null);
					for(int i = 0; i < columnNameData.length ; i++)
					{
						//ex.writeLine(null);
						ex.setBorder = false;
						ex.setBold = false;
						ex.fontColour = ExcelWriter.COLOUR_BLACK;
						ex.bgColour = ExcelWriter.COLOUR_WHITE;
						ex.horAlignment = ex.ALIGN_LEFT;
						ex.writeLine(columnNameData[i] +" :"+ columnNameValues[i]);
					}	
				}else if(listOfColoumnNames != null && !listOfColoumnNames.isEmpty())
				{	
					for(int counter = 0;counter<listOfColoumnNames.size();counter++){
						String[] coloumnNames = listOfColoumnNames.get(counter);
						String[] colNameValues = listOfColoumnNameValues.get(counter);
						
						ex.setBorder = false;
						ex.setBold = false;
						ex.fontColour = ExcelWriter.COLOUR_BLACK;
						ex.bgColour = ExcelWriter.COLOUR_WHITE;
						ex.horAlignment = ex.ALIGN_LEFT;
						
						String[] line = new String[coloumnNames.length*2];
						int index = 0;
						for(int count = 0; count < coloumnNames.length ; count++)
						{
							line[index] = coloumnNames[count] + " : ";
							index++;
							line[index] = colNameValues[count];
							index++;
						}
						ex.writeLineForPartiallyBoldHeaderDetails(line);
					}
					
				}else
				{
					if(reportName != null && !"".equals(reportName)) {
						ex.createEmptyLine();
						ex.commitEmptyLine();
					}
				}
				////////////////////////////////Ended .... KAruna

				Footer footer = ex.getFooter();
				String leftFooter = "";
				String centerFooter = "";
				String rightFooter = "";

				if (fileFooter != null && !fileFooter.equals(""))
				{
					StringTokenizer strFooter = new StringTokenizer(fileFooter, "-");
					while (strFooter.hasMoreTokens())
					{
						String tmpFooter = (String)strFooter.nextToken();
						StringTokenizer tmpFooterInternal = new StringTokenizer(tmpFooter, ":");
						String alignment = (String)tmpFooterInternal.nextToken();
						String tmpfooter1 = (String)tmpFooterInternal.nextToken();

						if (alignment.equals("left"))
						{
							leftFooter = leftFooter + tmpfooter1 + "\n";
						}
						else if (alignment.equals("center"))
						{
							centerFooter = centerFooter + " " + tmpfooter1 + "\n";
						}
						else
						{
							rightFooter = rightFooter + "\n" + tmpfooter1 + HSSFFooter.page() + " of " + HSSFFooter.numPages();
						}
					}//end of while
					footer.setLeft( centerFooter + leftFooter );
					footer.setRight(rightFooter);
				}//end of if



				ex.horAlignment = ex.ALIGN_RIGHT;
				ex.verAlignment = ex.ALIGN_RIGHT;
				String currDate = (String)map.get("currentdate");
				if(currDate != null && !"".equalsIgnoreCase(currDate))
					ex.writeLine("Report Date : "+currDate);

				ex.setBorder = true;
				ex.bgColour = ExcelWriter.COLOUR_BLUE_ROYAL;
				ex.fontColour = ExcelWriter.COLOUR_WHITE;
				ex.horAlignment = ex.ALIGN_LEFT;

				if(columnsHeading !=null && columnsHeading.length == 2)
				{ 
					ex.createEmptyLine();
					ex.commitEmptyLine();
					ex.setBorder = false;
					ex.fontColour = CopyOfExcelReportWriter.COLOUR_BLACK;
					ex.bgColour = CopyOfExcelReportWriter.COLOUR_WHITE;
					ex.horAlignment = ex.ALIGN_CENTER;
					ex.verAlignment = ex.ALIGN_CENTER;
					ex.writeLine(columnsHeading, DataWriter.LINE_TYPE_HEADER,columnNames);
				}

				ex.verAlignment = ex.VERTICAL_TOP;
				ex.setBold = true;
				if(columnNames != null){
					ex.writeLine(columnNames, 9);//for 9 ExcelWriter sets CellStyle for header
				}else if(this.singleRecordColumnHeadings != null && !this.singleRecordColumnHeadings.isEmpty()){
					
					ex.setHeaderColor(ExcelWriter.COLOUR_GREY_LIGHT);
					for (int counter = 0; counter < singleRecordColumnHeadings.size(); counter++) {
						ff.setFileFormatWithSrNos(ff, singleRecordColumnHeadings.get(counter));
						ex.setColumnWidth(singleRecordColoumnWidth.get(counter));
						ff.setColType(singleRecordColoumnType.get(counter));
						ex.writeLine(singleRecordColumnHeadings.get(counter), 9);//for 9 ExcelWriter sets CellStyle for header
						
					}
					ex.setHeaderColor(ExcelWriter.COLOUR_WHITE);
				}
				// this was added by poonam.kadam for sub headers and merging the first table heads 
				if(columnsNameMerge != null)
					ex.mergeCells(columnsNameMerge);
				if(columnsSubNames != null)
					ex.writeLine(columnsSubNames, 9);
				// end poonam.kadam
				ex.setBold = false;
				ex.horAlignment = ex.ALIGN_LEFT;
				ex.fontColour = ExcelWriter.COLOUR_BLACK;
				ex.bgColour = ExcelWriter.COLOUR_WHITE;
				
				//final Integer excelRecordSize =500 ;               
				Integer recordLength = 0;
				
				if(recordsArray != null && !recordsArray.isEmpty()){
					for(int i = 0; i < recordsArray.size(); i++)
					{
						
						recordLength = recordLength+1;
						if(recordLength == 50000){
							ex.createSheet();
							ex.setCurrentLineNo(0);
							setExcelDataWriter(ex);
							recordLength = 0;
							
						}
						/*String[] strings = recordsArray.get(i);
						if(strings[0] != null && "BOLD".contains(strings[0])) {
							ex.setBold = true;
						}
						else {
							ex.setBold = false;
						}*/
						
						String[] str = (String[])recordsArray.get(i);
						int ii = 0;
						for (String string : str) {
							if(string != null)
								str[ii] = string.replace('–','-');
							ii++;
						}
	
						ex.writeLine((String[])recordsArray.get(i), DataWriter.LINE_TYPE_GENERAL);
					}
				}else if(dataList != null && !dataList.isEmpty()){
					for (List<String[]> oneRecord : dataList) {
						for (int counter = 0; counter < oneRecord.size(); counter++) {
							ff.setFileFormatWithSrNos(ff, singleRecordColumnHeadings.get(counter));
							ex.setColumnWidth(singleRecordColoumnWidth.get(counter));
							ff.setColType(singleRecordColoumnType.get(counter));
							ex.writeLine(oneRecord.get(counter), DataWriter.LINE_TYPE_GENERAL);
							
						}
					}
				}

				ex.setBold = true;


				if(summList != null)
				{
					ex.writeLine(null, DataWriter.LINE_TYPE_GENERAL);
					for(int i = 0; i < summList.length; i++)
					{
						ex.setBold = false;
						ex.setBorder = false;
						ex.writeLine(summList[i]);
						ex.setBorder = false;
						ex.horAlignment = ex.ALIGN_LEFT;
						ex.setBold = false;
					}
					ex.writeLine(null, DataWriter.LINE_TYPE_GENERAL);
				}
				additionalWriter(ex, map);

				ex.createEmptyLine();
				ex.commitEmptyLine();
				ex.close();
			}
			else {

				ex.horAlignment = ex.ALIGN_CENTER;
				ex.verAlignment = ex.ALIGN_CENTER;
				ex.createEmptyLine();
				ex.writeField("NO MATCHING RECORDS FOUND.");
				ex.commitEmptyLine();
				ex.close();

			}
		
			map.put("basePath", basePath);
			// call to generate Zip file
			//convertFileToZipAndWrite(fileName,map);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void additionalWriter(ExcelWriter ex, Map<String, Object> map) {

		try {

			List <List<String[]>> additionalDetails = (List<List<String[]>>) map.get("additionalDetails");
			List <String []> addiColumnsMapList = (ArrayList<String []>) map.get("addiColumnsMapList");
			List <int[]> addiColumnTypeList = (ArrayList<int[]>)map.get("addiColumnTypeList");
			String tableHeading[] = (String[])map.get("tableHeading");
			List<String[]> summList = (ArrayList<String[]>) map.get("addiSummList");

			if(additionalDetails != null && additionalDetails.size() > 0){
				for(int j =0; j<additionalDetails.size();j++){
					try{
						ff.setColType(addiColumnTypeList.get(j));
					}catch (Exception e) {
					}
					List<String[]> addiRecordsArray = additionalDetails.get(j);
					if(addiRecordsArray != null && addiRecordsArray.size() > 0) { 
						String tableHeadingName = tableHeading[j];
						if(tableHeadingName!=null  || tableHeadingName.equals("")){
							ex.setBorder = false;
							ex.setBold = false;
							ex.fontColour = ExcelWriter.COLOUR_BLACK;
							ex.bgColour = ExcelWriter.COLOUR_WHITE;
							ex.horAlignment = ex.ALIGN_LEFT;
							ex.writeLine(tableHeadingName);
						}
						String [] columnsMap = addiColumnsMapList.get(j);
						FileFormat ff = new FileFormat();
						ff.setFileFormatWithSrNos(ff, columnsMap);
						ex.setBold = true;
						ex.writeLine(columnsMap, 9); 

						ex.setBold = false;
						ex.horAlignment = ex.ALIGN_LEFT;
						ex.fontColour = ExcelWriter.COLOUR_BLACK;
						ex.bgColour = ExcelWriter.COLOUR_WHITE;


						for(int i = 0; i < addiRecordsArray.size(); i++)
						{
							ex.writeLine((String[])addiRecordsArray.get(i), DataWriter.LINE_TYPE_GENERAL);
						}

						String [] summListArray = summList.get(j);
						if(summListArray != null)
						{
							ex.writeLine(null, DataWriter.LINE_TYPE_GENERAL);
							for(int i = 0; i < summListArray.length; i++)
							{
								ex.setBold = false;
								ex.setBorder = false;
								ex.writeLine(summListArray[i]);
								ex.setBorder = false;
								ex.horAlignment = ex.ALIGN_LEFT;
								ex.setBold = false;
							}
						}

						ex.writeLine(null,DataWriter.LINE_TYPE_GENERAL);
						ex.setBold = true;

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Added for creating multiple sheets in a file
	 * @param ex
	 * @throws Exception
	 */
	private void setExcelDataWriter(ExcelWriter ex) throws Exception {
		ex.bgColour = ExcelWriter.COLOUR_BLUE_GREY;
		ex.fontColour = ExcelWriter.COLOUR_WHITE;
		ex.horAlignment = ex.ALIGN_CENTER;
		ex.setBold = true;

		Header header = ex.getHeader();

		//  setting the header
		String strtmpHeaderAlign="", strtmpHeaderText="", strCombinedHeaderText="";
		int headerAlign = 0;

		if (fileHeader != null && !fileHeader.equals(""))
		{
			StringTokenizer strHeader = new StringTokenizer(fileHeader,"@");
			while (strHeader.hasMoreTokens())
			{
				String strHeaderValue = strHeader.nextToken();
				StringTokenizer strTmpToken = new StringTokenizer(strHeaderValue,":");

				if (strHeaderValue != null && !strHeaderValue.equals(""))
				{
					strtmpHeaderAlign = strTmpToken.nextToken();
					strtmpHeaderText = strTmpToken.nextToken();
					strCombinedHeaderText = strCombinedHeaderText + strtmpHeaderText + " \n";
				}
			}
			////////////////////
			//Image image = Image.getInstance(PDFWriter.getUWBTitleImagePath());
			header.setLeft(strCombinedHeaderText);
			//setting the header alignment
			if (strtmpHeaderAlign.equals("left"))
			{
				headerAlign = 0;
			}
			else if (strtmpHeaderAlign.equals("center"))
			{
				headerAlign = 1;
			}
			else
			{	
				headerAlign = 2;
			}
		}
	}
}