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

import com.integrosys.base.techinfra.logger.DefaultLogger;


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
public class ExcelReportWriter extends BaseReportWriter
{
	String fileHeader = null;
	String fileFooter = null;

	FileFormat ff = null;
	FileFormat ff1 = null;
	List<String[]> recordsArray = null;
	String fileName = null;
	String fileName2=null;
	String fileName3=null;
	String basePath = null;

	String columnNames[] = null;
	String columnNames1[] = null;
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
	
	public ExcelReportWriter()
	{

	}

	// (Ticket)parameters.get("ticket"), parameters, fileName, dataList, fileType
	public ExcelReportWriter( String basePath, Map<String, Object> map, String fileName, List<String[]> recordList) throws Exception
	{
		setParameters(basePath, map, fileName, recordList);
	}
	
	//Added For Cersai Charge Release Report for more than 1000 records start
	public ExcelReportWriter( String basePath, Map<String, Object> map, String fileName,String fileName2,String fileName3, List<String[]> recordList) throws Exception
	{
		setParameters(basePath, map, fileName,fileName2,fileName3, recordList);
	}
	//end 
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
	
	//Added For Cersai Charge Release Report for more than 1000 records start
	public void setParameters(String basePath, Map<String, Object> map, String fileName,String fileName2,String fileName3, List<String[]> recordList) {

		try
		{
			ff = new FileFormat();
			ff1 = new FileFormat();
			String [] columnsMap = (String[]) map.get("columnsMap");
			String [] columnsMap1 = new String[columnsMap.length];
			for(int i=0;i<columnsMap.length;i++) {
				columnsMap1[i]=columnsMap[i];
			}
			String n1 = columnsMap[2];
			int n2 = Integer.parseInt(n1);
			int n3 = 0;
			String n5="";
			
			if(n2 > 1000) {
				n3 = n2 - 1000;
				n2 = 1000;
				n1 = Integer.toString(n2);
				columnsMap[2] = n1;
			}
			ff.setFileFormatWithSrNos(ff, columnsMap);
			this.columnNames = columnsMap;
			
			if(n3 > 1000) {
				n3 = 1000;
				n5 = Integer.toString(n3);
				columnsMap1[2] = n5;
			}else {
				n5 = Integer.toString(n3);
				columnsMap1[2] = n5;
			}
			ff1.setFileFormatWithSrNos(ff1, columnsMap1);
			this.columnNames1 = columnsMap1;
			this.recordsArray = recordList;
			this.map = map;
			this.fileName = fileName;
			this.fileName2 = fileName2;
			this.fileName3 = fileName3;
			this.basePath = basePath;
//			this.columnNames = columnsMap;
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
				ff1.setColType(colType);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();

		}
	}
// end 

	public void generate()
	{

		try
		{
			ff.setFileType(FileFormat.FILE_TYPE_EXCEL);
			if(ff1 != null) {
				ff1.setFileType(FileFormat.FILE_TYPE_EXCEL);
			}
			ExcelWriter ex = new ExcelWriter(ff, basePath, columnWidth);
			ExcelWriter ex2 = null;
			DefaultLogger.debug(this,"inside generate() call from BaseReport");
			
			int recordSize = 0;
			int recordSize2 = 0;
			String nameOfReport = (String) map.get("reportname");
			if(recordsArray != null && !recordsArray.isEmpty()){
				recordSize = recordsArray.size();
				if("CERSAI Charge Release report".equals(nameOfReport)) {
					if(recordSize > 1000 ) {
						ex2=new ExcelWriter(ff1, basePath, columnWidth);
					}
				}
				
			}else if(dataList != null && !dataList.isEmpty()){
				recordSize = dataList.size();
				if("CERSAI Charge Release report".equals(nameOfReport)) {
					if(recordSize > 1000 ) {
						ex2=new ExcelWriter(ff1, basePath, columnWidth);
						ff1.setFileFormatWithSrNos(ff1, singleRecordColumnHeadings.get(0));
					}
				}
				ff.setFileFormatWithSrNos(ff, singleRecordColumnHeadings.get(0));
				
			}
			
			if(imagePath != null && !imagePath.equals(""))
			{
				ex.logoPath(imagePath);
				if("CERSAI Charge Release report".equals(nameOfReport)) {
					if(recordSize > 1000 ) {
						ex2.logoPath(imagePath);
					}
				}
			}
			if("CERSAI Charge Release report".equals(nameOfReport)) {
				if(recordSize > 1000 ) {
					recordSize2 = recordSize - 1000;
					recordSize = recordSize - recordSize2;
				}
			}
			ex.open(fileName, recordSize);
			
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
//				bankName = FieldNameUtil.getOtherBankName(Constants.systemBankId);
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
					ex.fontColour = ExcelReportWriter.COLOUR_BLACK;
					ex.bgColour = ExcelReportWriter.COLOUR_WHITE;
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
				
				//===================================Anil
				if(recordSize > 0 ) {
				ex.setBold = false;
				ex.horAlignment = ex.ALIGN_LEFT;
				ex.fontColour = ExcelWriter.COLOUR_BLACK;
				ex.bgColour = ExcelWriter.COLOUR_WHITE;
				
				//final Integer excelRecordSize =500 ;               
				Integer recordLength = 0;
				
				DefaultLogger.debug(this,"recordsArray.size() =========================== "+recordsArray.size());
				
				if(recordsArray != null && !recordsArray.isEmpty()){
					for(int i = 0; i < (recordsArray.size()) - recordSize2 ; i++)
					{
						if(i == 1000 && reportName != null && !"".equals(reportName) && "CERSAI Charge Release report".equals(reportName)) {
							break;
						}
						
						recordLength = recordLength+1;
						if(recordLength > 30000){
							ex.createSheet();
							ex.setCurrentLineNo(0);
							setExcelDataWriter(ex);
							recordLength = 0;
							DefaultLogger.debug(this,"Creating new sheet after 30000 records");
						}

						String[] str = (String[])recordsArray.get(i);
						int ii = 0;
						for (String string : str) {
							if(string != null)
								str[ii] = string.replace('–','-');
							ii++;
						}
						if(i == 0 && reportName != null && !("".equals(reportName))) {
							if(reportName.equals("CERSAI Charge Release report")) {
								ex.createEmptyLine();
								ex.commitEmptyLine();
							}
						}
						ex.writeLine((String[])recordsArray.get(i), DataWriter.LINE_TYPE_GENERAL);
					}
				}else if(dataList != null && !dataList.isEmpty()){
					for (List<String[]> oneRecord : dataList) {
						for (int counter = 0; counter < (oneRecord.size()) - recordSize2 ; counter++) {
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
				if(recordsArray.size() == 0){
					//String[] str = {"NO MATCHING RECORDS FOUND."};
					//ex.writeLine(str,DataWriter.LINE_TYPE_GENERAL);
					ex.setBold = false;
					ex.setBorder = false;
					ex.horAlignment = ex.ALIGN_CENTER;
					ex.verAlignment = ex.ALIGN_CENTER;
					ex.fontColour = ExcelWriter.COLOUR_BLACK;
					ex.bgColour = ExcelWriter.COLOUR_WHITE;
					
					//ex.createEmptyLine();
					ex.writeLine("NO MATCHING RECORDS FOUND.");
					//ex.commitEmptyLine();
					
				}
				ex.close();

			}
				
				// Added for Cersai Charge Release report if records more than 1000
			if(recordSize2 != 0 ) {
				ex2.open(fileName2, recordSize2);
					
				ex2.bgColour = ExcelWriter.COLOUR_BLUE_GREY;
				ex2.fontColour = ExcelWriter.COLOUR_WHITE;
				ex2.horAlignment = ex2.ALIGN_CENTER;
				ex2.setBold = true;
				
				Header header2 = ex2.getHeader();

				//  setting the header
				String strtmpHeaderAlign2="", strtmpHeaderText2="", strCombinedHeaderText2="";
				int headerAlign2 = 0;

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
					header2.setLeft(strCombinedHeaderText);
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
				ex2.setBorder = false;

				ex2.fontColour = ExcelWriter.COLOUR_BLACK;
				ex2.bgColour = ExcelWriter.COLOUR_WHITE;

				ex2.verAlignment = ex2.ALIGN_CENTER;
				ex2.horAlignment = ex2.ALIGN_CENTER;
//				ex2.createEmptyLine();
				ex2.setBold = true;
				
				if(bankName != null && !"".equalsIgnoreCase(bankName)){
					ex2.fontSize = 12;
					ex2.writeLine(bankName);
					//ex2.writeLine("");
					ex2.fontSize = 10;
				}
				/*if(addressDetails != null && !"".equalsIgnoreCase(addressDetails)){
					ex.fontSize = 8;
					ex.setBold = false;
					ex.writeLine(addressDetails);
					ex.writeLine("");
					ex.fontSize = 10;
					ex.setBold = true;
				}*/
				
				Object object2 = map.get("reportname");
				String reportName2 = null;
				if(object2 != null && !"".equals(object2)){ 

					String [] reportname2 = ((String)object2).split("\n\n");

					if(reportname2[0] != null && !"".equalsIgnoreCase(reportname2[0])) {
						ex2.writeLine(reportname2[0]);
						reportName2 = reportname2[0];
						ex2.writeLine("");
						if(reportname2.length > 1)
							ex2.writeLine(reportname2[1]);
					}
				}

				ex2.setBold = false;

				//if((!columnNameData.equals("") || !columnNameData.equals(null)))
				if(columnNameData != null)
				{	
					ex2.writeLine(null);
					for(int i = 0; i < columnNameData.length ; i++)
					{
						//ex.writeLine(null);
						ex2.setBorder = false;
						ex2.setBold = false;
						ex2.fontColour = ExcelWriter.COLOUR_BLACK;
						ex2.bgColour = ExcelWriter.COLOUR_WHITE;
						ex2.horAlignment = ex2.ALIGN_LEFT;
						ex2.writeLine(columnNameData[i] +" :"+ columnNameValues[i]);
					}	
				}else if(listOfColoumnNames != null && !listOfColoumnNames.isEmpty())
				{	
					for(int counter = 0;counter<listOfColoumnNames.size();counter++){
						String[] coloumnNames = listOfColoumnNames.get(counter);
						String[] colNameValues = listOfColoumnNameValues.get(counter);
						
						ex2.setBorder = false;
						ex2.setBold = false;
						ex2.fontColour = ExcelWriter.COLOUR_BLACK;
						ex2.bgColour = ExcelWriter.COLOUR_WHITE;
						ex2.horAlignment = ex2.ALIGN_LEFT;
						
						String[] line = new String[coloumnNames.length*2];
						int index = 0;
						for(int count = 0; count < coloumnNames.length ; count++)
						{
							line[index] = coloumnNames[count] + " : ";
							index++;
							line[index] = colNameValues[count];
							index++;
						}
						ex2.writeLineForPartiallyBoldHeaderDetails(line);
					}
					
				}else
				{
					if(reportName2 != null && !"".equals(reportName2)) {
						ex2.createEmptyLine();
						ex2.commitEmptyLine();
					}
				}
				////////////////////////////////Ended .... KAruna

				Footer footer2 = ex2.getFooter();
				String leftFooter2 = "";
				String centerFooter2 = "";
				String rightFooter2 = "";

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
							leftFooter2 = leftFooter2 + tmpfooter1 + "\n";
						}
						else if (alignment.equals("center"))
						{
							centerFooter2 = centerFooter2 + " " + tmpfooter1 + "\n";
						}
						else
						{
							rightFooter2 = rightFooter2 + "\n" + tmpfooter1 + HSSFFooter.page() + " of " + HSSFFooter.numPages();
						}
					}//end of while
					footer2.setLeft( centerFooter2 + leftFooter2 );
					footer2.setRight(rightFooter2);
				}//end of if



				ex2.horAlignment = ex2.ALIGN_RIGHT;
				ex2.verAlignment = ex2.ALIGN_RIGHT;
				String currDate2 = (String)map.get("currentdate");
				if(currDate2 != null && !"".equalsIgnoreCase(currDate2))
					ex2.writeLine("Report Date : "+currDate2);

				ex2.setBorder = true;
				ex2.bgColour = ExcelWriter.COLOUR_BLUE_ROYAL;
				ex2.fontColour = ExcelWriter.COLOUR_WHITE;
				ex2.horAlignment = ex2.ALIGN_LEFT;

				if(columnsHeading !=null && columnsHeading.length == 2)
				{ 
					ex2.createEmptyLine();
					ex2.commitEmptyLine();
					ex2.setBorder = false;
					ex2.fontColour = ExcelReportWriter.COLOUR_BLACK;
					ex2.bgColour = ExcelReportWriter.COLOUR_WHITE;
					ex2.horAlignment = ex2.ALIGN_CENTER;
					ex2.verAlignment = ex2.ALIGN_CENTER;
					ex2.writeLine(columnsHeading, DataWriter.LINE_TYPE_HEADER,columnNames1);
				}

				ex2.verAlignment = ex2.VERTICAL_TOP;
				ex2.setBold = true;
				if(columnNames1 != null){
					ex2.writeLine(columnNames1, 9);//for 9 ExcelWriter sets CellStyle for header
				}else if(this.singleRecordColumnHeadings != null && !this.singleRecordColumnHeadings.isEmpty()){
					
					ex2.setHeaderColor(ExcelWriter.COLOUR_GREY_LIGHT);
					for (int counter = 0; counter < singleRecordColumnHeadings.size(); counter++) {
						ff1.setFileFormatWithSrNos(ff1, singleRecordColumnHeadings.get(counter));
						ex2.setColumnWidth(singleRecordColoumnWidth.get(counter));
						ff1.setColType(singleRecordColoumnType.get(counter));
						ex2.writeLine(singleRecordColumnHeadings.get(counter), 9);//for 9 ExcelWriter sets CellStyle for header
						
					}
					ex2.setHeaderColor(ExcelWriter.COLOUR_WHITE);
				}
				// this was added by poonam.kadam for sub headers and merging the first table heads 
				if(columnsNameMerge != null)
					ex2.mergeCells(columnsNameMerge);
				if(columnsSubNames != null)
					ex2.writeLine(columnsSubNames, 9);
				// end poonam.kadam
				
				//===================================Anil
				if(recordSize2 > 0 ) {
				ex2.setBold = false;
				ex2.horAlignment = ex2.ALIGN_LEFT;
				ex2.fontColour = ExcelWriter.COLOUR_BLACK;
				ex2.bgColour = ExcelWriter.COLOUR_WHITE;
				
				//final Integer excelRecordSize =500 ;               
				Integer recordLength = 0;
				
				DefaultLogger.debug(this,"recordsArray.size() =========================== "+recordsArray.size());
				
				if(recordsArray != null && !recordsArray.isEmpty()){
//					for(int i = (recordsArray.size()) - recordSize2; i < recordsArray.size(); i++)
					for(int i = 1000; i < recordsArray.size(); i++)
					{

						if(i == 2000) {
							break;
						}
						recordLength = recordLength+1;
						if(recordLength > 30000){
							ex2.createSheet();
							ex2.setCurrentLineNo(0);
							setExcelDataWriter(ex2);
							recordLength = 0;
							DefaultLogger.debug(this,"Creating new sheet after 30000 records");
						}

						String[] str = (String[])recordsArray.get(i);
						int ii = 0;
						for (String string : str) {
							if(string != null)
								str[ii] = string.replace('–','-');
							ii++;
						}
						if(i == 1000) {
							ex2.createEmptyLine();
							ex2.commitEmptyLine();
						}
						ex2.writeLine((String[])recordsArray.get(i), DataWriter.LINE_TYPE_GENERAL);
					}
				}else if(dataList != null && !dataList.isEmpty()){
					for (List<String[]> oneRecord : dataList) {
						for (int counter = 1000; counter < oneRecord.size(); counter++) {
							if(counter == 2000) {
								break;
							}
							ff1.setFileFormatWithSrNos(ff1, singleRecordColumnHeadings.get(counter));
							ex2.setColumnWidth(singleRecordColoumnWidth.get(counter));
							ff1.setColType(singleRecordColoumnType.get(counter));
							ex2.writeLine(oneRecord.get(counter), DataWriter.LINE_TYPE_GENERAL);
							
						}
					}
				}

				ex2.setBold = true;


				if(summList != null)
				{
					ex2.writeLine(null, DataWriter.LINE_TYPE_GENERAL);
					for(int i = 0; i < summList.length; i++)
					{
						ex2.setBold = false;
						ex2.setBorder = false;
						ex2.writeLine(summList[i]);
						ex2.setBorder = false;
						ex2.horAlignment = ex2.ALIGN_LEFT;
						ex2.setBold = false;
					}
					ex2.writeLine(null, DataWriter.LINE_TYPE_GENERAL);
				}
				additionalWriter(ex2, map);

				ex2.createEmptyLine();
				ex2.commitEmptyLine();
				ex2.close();
			}
			else {
				if(recordsArray.size() == 0){
					//String[] str = {"NO MATCHING RECORDS FOUND."};
					//ex.writeLine(str,DataWriter.LINE_TYPE_GENERAL);
					ex2.setBold = false;
					ex2.setBorder = false;
					ex2.horAlignment = ex2.ALIGN_CENTER;
					ex2.verAlignment = ex2.ALIGN_CENTER;
					ex2.fontColour = ExcelWriter.COLOUR_BLACK;
					ex2.bgColour = ExcelWriter.COLOUR_WHITE;
					
					//ex.createEmptyLine();
					ex2.writeLine("NO MATCHING RECORDS FOUND.");
					//ex.commitEmptyLine();
					
				}
				ex2.close();

			}
				
				// End -- Cersai Charge Release report if records more than 1000.
			}
				
		
			map.put("basePath", basePath);
			// call to generate Zip file
//			convertFileToZipAndWrite(fileName,map);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		DefaultLogger.debug(this,"Out generate() call from BaseReport");
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