/*
 * Created on Nov 6, 2004 By Prashant Soneria
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;


/**
 * <p><b>
 * This class is the implementation of DataWriter that deals with EXCEL files.
 * </b></p>
 * 
 * @version 1.0
 * 
 * @author Prashant Soneria
 * 
 */

//TODO: Not Using Anil
public class ExcelWriter extends DataWriter
{
	//for image path
	String logoPath;
	
	//Variables declaration
	private int currentLineNo = 0;
	private boolean emptyLineExists = false;
	private short emptyLineColCount = 0;
	private String shortFileName = null;

	//Excel sheet access components
	private SXSSFWorkbook workBook;
//	private HSSFSheet sheet;
	private Sheet sheet;
	private FileOutputStream fos;
	Row emptyRow = null;
	
	//added to set Header & Footer
	public Header header;
	public Footer footer;

	//Horizontal alignment variables
	public short ALIGN_LEFT = 1;
	public short ALIGN_CENTER = 2;
	public short ALIGN_RIGHT = 3;
	public short ALIGN_JUSTIFY = 4;

	//Vertical alignment variables
	public short VERTICAL_TOP = 1;
	public short VERTICAL_CENTER = 2;
	public short VERTICAL_BOTTOM = 3;
	public short VERTICAL_JUSTIFY = 4;

	//Formatting variables
	public boolean setBorder = false;
	public boolean setBold = false;
	public String fontName = "verdana";
	public short fontSize = 10;
	public short horAlignment = ALIGN_LEFT;
	public short verAlignment = VERTICAL_TOP;
	public short bgColour = HSSFColor.WHITE.index;
	public short fontColour = HSSFColor.BLACK.index;
	private int[] colTypes;   

	public CellStyle cellStyleGlob;//for data with border
	public CellStyle cellStyleH;//for header
	public CellStyle cellStylenoBorderGlob;//for data without border
	public CellStyle cellStyleNumeric;//for numeric data

	public int cnt = 0;

	//Colour constants
	public static final short COLOUR_BLACK = HSSFColor.BLACK.index;
	public static final short COLOUR_WHITE = HSSFColor.WHITE.index;
	public static final short COLOUR_RED = HSSFColor.RED.index;
	public static final short COLOUR_GREEN = HSSFColor.GREEN.index;
	public static final short COLOUR_BLUE = HSSFColor.BLUE.index;

	public static final short COLOUR_BLUE_GREY = HSSFColor.BLUE_GREY.index;
	public static final short COLOUR_BLUE_CORNFLOWER = HSSFColor.CORNFLOWER_BLUE.index;
	public static final short COLOUR_BLUE_DARK = HSSFColor.DARK_BLUE.index;
	public static final short COLOUR_BLUE_LIGHT_CORNFLOWER = HSSFColor.LIGHT_CORNFLOWER_BLUE.index;
	public static final short COLOUR_BLUE_PALE = HSSFColor.PALE_BLUE.index;
	public static final short COLOUR_BLUE_ROYAL = HSSFColor.ROYAL_BLUE.index;

	public static final short COLOUR_GREEN_DARK = HSSFColor.DARK_GREEN.index;
	public static final short COLOUR_GREEN_SEA = HSSFColor.SEA_GREEN.index;
	public static final short COLOUR_GREEN_LIGHT = HSSFColor.LIGHT_GREEN.index;

	public static final short COLOUR_YELLOW = HSSFColor.YELLOW.index;
	public static final short COLOUR_YELLOW_LEMON = HSSFColor.LEMON_CHIFFON.index;
	public static final short COLOUR_YELLOW_LIGHT = HSSFColor.LIGHT_YELLOW.index;

	public static final short COLOUR_GREY = HSSFColor.GREY_50_PERCENT.index;
	public static final short COLOUR_GREY_LIGHT = HSSFColor.GREY_25_PERCENT.index;
	public static final short COLOUR_GREY_DARK = HSSFColor.GREY_80_PERCENT.index;

	public static final short COLOUR_BROWN = HSSFColor.BROWN.index;
	public static final short COLOUR_CORAL = HSSFColor.CORAL.index;
	public static final short COLOUR_INDIGO = HSSFColor.INDIGO.index;
	public static final short COLOUR_LIGHT_ORANGE = HSSFColor.LIGHT_ORANGE.index;
	public static final short COLOUR_LIME = HSSFColor.LIME.index;
	public static final short COLOUR_MAROON = HSSFColor.MAROON.index;
	public static final short COLOUR_PLUM = HSSFColor.PLUM.index;
	public static final short COLOUR_ROSE = HSSFColor.ROSE.index;
	public static final short COLOUR_TAN = HSSFColor.TAN.index;
	public static final short COLOUR_TEAL = HSSFColor.TEAL.index;	

	public short headerFontColor = COLOUR_WHITE;
	public short headerColor = COLOUR_BLUE_GREY;

	/**
	 * 
	 * <p><b>
	 * Constructor that takes a FileFormat and a String parameter. The String parameter specifies the base path 
	 * where the file will be written.
	 * </b></p>
	 * 
	 * @param		fileFormat		The format of the file to be written
	 * @param		basePath		The base path where the file will be written
	 * @param columnWidth TODO
	 */
	public ExcelWriter(FileFormat fileFormat, String basePath, Integer[] columnWidth)
	{
		super(fileFormat, basePath, columnWidth);
	}
	

	/**
	 * getter for getting the column width
	 * @return
	 */
	public Integer[] getColumnWidth(){
		return this.columnWidth;
	}
	
	/**
	 * setter for setting the column width
	 * @return
	 */
	public void setColumnWidth(Integer[] columnWidth){
		this.columnWidth = columnWidth;
	}

	/**
	 * <p><b>
	 * This method intialises the file for writing. The file is placed in the base directory specified in the
	 * constructor.
	 * </b></p>
	 * 
	 * @throws Exception
	 * 
	 */
	protected boolean intialiseFile() throws Exception
	{
		try
		{
			/*
			 * Check if the file already exists. If the file exists, delete the original file.
			 */
			deleteFile();

			//Get the name of the file without the extension
			int dotIndex = this.fileName.lastIndexOf(".");
			if(dotIndex == -1)
				dotIndex = this.fileName.length();

			shortFileName = this.fileName.substring(0, dotIndex);

			//Get the work book and a file output stream pointing to it
			workBook = new SXSSFWorkbook(50);
			sheet = (Sheet)workBook.createSheet("sheet1");	
			sheet.setDisplayGridlines(false);
			//workBook.setRepeatingRowsAndColumns(0,-1,-1,0,1);
			workBook.setRepeatingRowsAndColumns(0,-1,-1,0,0);

			headerColor = 9;
			headerFontColor = 8;


			this.setBorder = true; 	
			cellStyleGlob = getCellStyle();
			this.setBorder = false; 
			cellStylenoBorderGlob = getCellStyle();
			this.setBorder = true;
			cellStyleH = getCellStyleHeader();

			//for numeric data
			cellStyleNumeric = getCellStyleNumeric();

			PrintSetup printSetup = sheet.getPrintSetup();
			printSetup.setHeaderMargin(0.25);
			printSetup.setFooterMargin(0);

			this.header = sheet.getHeader();
			this.footer = sheet.getFooter();		
			
			if(logoPath!=null && logoPath.length()!=0)
			{
				this.createEmptyLine();
				this.commitEmptyLine();
				this.addLogo(sheet, workBook);
				
				//two empty lines created for printing logo in the header part
				this.createEmptyLine();
				this.commitEmptyLine();
				this.createEmptyLine();
				this.commitEmptyLine();
			}
				
			fos = new FileOutputStream(this.absFileName);
//			sheet.set
			sheet.setDefaultColumnWidth((short)14);
			colTypes = fileFormat.getColType();
		}
		catch(IOException ioe)
		{
			deleteFile();
			throw new Exception("The File " + this.fileName + " Could Not Be Opened");
		}

		return true;
	}
	
	public void logoPath(String path)
	{
		this.logoPath = path;
	}

	/**
	 * 
	 * <p><b>
	 * This method checks whether the file type is either Text-Delimited or Text-Fixed Length. If not it will throw
	 * an exception.
	 * </b></p>
	 * 
	 * @throws Exception
	 *
	 */
	protected void checkFileType() throws Exception
	{
		if(fileFormat.getFileType() != FileFormat.FILE_TYPE_EXCEL)
			throw new Exception("The file format should be Excel format.");
	}

	/**
	 * <p><b>
	 * Writes a line in the file.
	 * </b></p>
	 * 
	 * @param		line		String array that represents one record that will be written in the file.
	 * @param		type		Type of line (Header/ Footer/ Heading/ General/ Record)
	 * 
	 * @return	"True" if the record was properly written to the file, "False" otherwise.
	 * 
	 * @throws Exception
	 * @throws Exception
	 * 
	 */
	public boolean writeLine(String line[], int type) throws Exception, Exception
	{
		try
		{
			if(emptyLineExists)
				throw new Exception("There is an empty line which has not been committed. Hence new line cannot be written.");



			//Check if the text is not null or empty
			if(line == null || line.length == 0)
			{
//				Increment the current line number
				currentLineNo++;

				//Create a row in the sheet
				Row row = sheet.createRow((short)(currentLineNo - 1));
				return false;
			}
			else if(line.length == 1)
				if(line[0].equalsIgnoreCase("PageBreak"))
				{
					sheet.setRowBreak(currentLineNo-1);
					return true;
				}

			//	Increment the current line number
			currentLineNo++;

			//Create a row in the sheet
			Row row = sheet.createRow((short)(currentLineNo - 1));
			int[] colType = fileFormat.getColType();
			for(int i = 0; i < line.length; i++)
			{             
				//Add a cell for the field
				Cell cell = row.createCell((short)i);

				if(type == DataWriter.LINE_TYPE_RECORD)
				{
					//Validate individual fields
					validateField(line[i], i, currentLineNo);
				}

				CellStyle  cellStyle = null; 
				if(line[i] != null)
					line[i] = line[i].trim();

				if(type == 9)//for header
				{
					cellStyle = getCellStyleHeader();
//					cellStyle = cellStyleH;
					cell.setCellStyle(cellStyle);
					cell.setCellValue(line[i]);
				}else
				{
						
					 int colTypeForLine = 0;
					 try{
						 colTypeForLine = colType[i];
					 }catch (Exception e) {
					}
					 
					if(colTypeForLine == FileFormat.COL_TYPE_AMOUNT_FLOAT) {
						
						cellStyle = cellStyleNumeric;
						cell.setCellStyle(cellStyle);
						try {
//							int columnW = columnWidth[i]; 
//							sheet.setColumnWidth((short)i,(short)columnW);
						}
						catch (Exception e) {
						}
						
						try{
							if(line[i] != null &&  !line[i].equals("") && line[i].length() > 0)
								//TODO: Need to move the method to util CommonBaseReport.getFormattedString(line[i])
								cell.setCellValue(line[i]);
							else
								cell.setCellValue("");	

						}catch(NumberFormatException e)
						{	
							cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
							cellStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
							cell.setCellValue(line[i]);
						}
					}
					else
					{	
						cellStyle = cellStyleGlob;
						cell.setCellValue(line[i]);
					}
				}	
				cellStyle.setWrapText( true );
				cell.setCellStyle(cellStyle);

				cnt++;
			}
		}
		catch(Exception dwe)
		{
			deleteFile();
			throw dwe;
		}

		return true;
	}
	public boolean mergeCells(String mergeData[]) throws Exception{
		try{
			if(emptyLineExists)
				throw new Exception("There is an empty line which has not been committed. Hence new line cannot be written.");
		
		for (int i = 0; i < mergeData.length; i++) {
			String[] cellsNos = ((String)mergeData[i]).split("-");
			if(cellsNos!=null && cellsNos.length>0)
				sheet.addMergedRegion(new CellRangeAddress(currentLineNo - 1, (currentLineNo - 1),0,(fileFormat.getFileColCount() - 1)));
//				sheet.addMergedRegion(new Region((currentLineNo-1), (short)(Integer.parseInt(cellsNos[0])-1),(currentLineNo-1), (short)(Integer.parseInt(cellsNos[1])-1)));
		}
			
		}catch(Exception dwe)
		{
			deleteFile();
			throw dwe;
		}
		
		
		return true;
		
	}
	/**
	 * for dealer Supplier Headings in FSCM
	 */
	public boolean writeLine(String line[], int type,String[] coloumns)
	throws Exception {
		       //Create a row in the sheet
			   	Row row = sheet.createRow((short)(currentLineNo - 1));
			   	Cell cell =null;
			   	int i=1;
			   	StringBuffer sb= new StringBuffer();
				for(int j = 0; j < line.length; j++){             
					//Add a cell for the field
				    cell = row.createCell((short)i);
					if(j==0){
						sb.append(line[j]+"");
						cell.setCellValue(sb.toString()+""+"");	
						for(int c=1;c<=coloumns.length/2;c++){
							i++;
							cell = row.createCell((short)i);
					    }
			        }
					else
						cell.setCellValue(line[j]+""+"");
						
				}
				 return false;
	}


	/**
	 * <p><b>
	 * Writes a line in the file with setting as user Done.
	 * </b></p>
	 * 
	 * @param		line		String array that represents one record that will be written in the file.
	 * @param		type		Type of line (Header/ Footer/ Heading/ General/ Record)
	 * 
	 * @return	"True" if the record was properly written to the file, "False" otherwise.
	 * 
	 * @throws Exception
	 * @throws Exception
	 * 
	 */
	public boolean userWriteLine(String line) throws Exception, Exception
	{
		try
		{
			if(emptyLineExists)
				throw new Exception("There is an empty line which has not been committed. Hence new line cannot be written.");

			//Increment the current line number
			currentLineNo++;

			//Create a row in the sheet
			Row row = sheet.createRow((short)(currentLineNo - 1));


			if(line == null)
				line = "";

			Cell cell = row.createCell((short)0);

			CellStyle cellStyle = getCellStyle();

			cellStyle.setWrapText( true );

			cell.setCellStyle(cellStyle);

			cell.setCellValue(line);

//			sheet.addMergedRegion(new Region((currentLineNo - 1), (short)0,(currentLineNo - 1), (short)(fileFormat.getFileColCount() - 1)));
			sheet.addMergedRegion(new CellRangeAddress(currentLineNo - 1, (currentLineNo - 1),0,(fileFormat.getFileColCount() - 1)));

		}
		catch(Exception pe)
		{
			deleteFile();
			throw pe;
		}

		return true;
	}


	/**
	 * <p><b>
	 * Writes a line in the file.
	 * </b></p>
	 * 
	 * @param		line		String array that represents one record that will be written in the file.
	 * @param		type		Type of line (Header/ Footer/ Heading/ General/ Record)
	 * 
	 * @return	"True" if the record was properly written to the file, "False" otherwise.
	 * 
	 * @throws Exception
	 * @throws Exception
	 * 
	 */
	public boolean writeLine(String line) throws Exception, Exception
	{
		try
		{
			if(emptyLineExists)
				throw new Exception("There is an empty line which has not been committed. Hence new line cannot be written.");

			//Increment the current line number
			currentLineNo++;

			//Create a row in the sheet
			Row row = sheet.createRow((short)(currentLineNo - 1));

			//Check if the text is not null or empty
			//if(line == null || line.length == 0)
			//return false;

			if(line == null)
				line = "";

			Cell cell = row.createCell((short)0);

			CellStyle cellStyle = getCellStyle();

			cellStyle.setWrapText( true );
			cell.setCellStyle(cellStyle);
			cell.setCellValue(line);

//			sheet.addMergedRegion(new Region((currentLineNo - 1), (short)0,(currentLineNo - 1), (short)(fileFormat.getFileColCount() - 1)));
			sheet.addMergedRegion(new CellRangeAddress(currentLineNo - 1,(currentLineNo - 1),0, (fileFormat.getFileColCount() - 1)));
		}
		catch(Exception pe)
		{
			deleteFile();
			throw pe;
		}

		return true;
	}

	/**
	 * 
	 * <p><b>
	 * Creates an empty line in the file. The individual fields will be added using writeField() method.
	 * </b></p>
	 * 
	 * @throws Exception
	 *
	 */
	public void createEmptyLine() throws Exception
	{
		if(emptyLineExists)
		{
			deleteFile();
			throw new Exception("There is an empty line which has not been committed");
		}

		//Increment the current line number
		currentLineNo++;

		//Create a row in the sheet
		emptyRow = sheet.createRow((short)(currentLineNo - 1));
		emptyLineExists = true;

		//Set the empty line field count to "0"
		emptyLineColCount = 0;
	}

	/**
	 * 
	 * <p><b>
	 * Writes a field to the empty line that was created.
	 * </b></p>
	 * 
	 * @param field
	 * 
	 * @return	"True" if the field was properly written to the file, "False" otherwise.
	 * 
	 * @throws Exception
	 * @throws Exception
	 *
	 */
	public boolean writeField(String field) throws Exception, Exception
	{
		try
		{
			if(!emptyLineExists)
				throw new Exception("There is no empty line to write the fields to.");

			//If the field is null make it empty String
			if(field == null)
				field = "";

			//Add a cell for the field
			Cell cell = emptyRow.createCell(emptyLineColCount);

			CellStyle cellStyle = cellStyleGlob;

			cell.setCellStyle(cellStyle);

			cell.setCellValue(field);

			emptyLineColCount++;
		}
		catch(Exception pe)
		{
			deleteFile();
			throw pe;
		}

		return true;
	}

	public boolean writeHeaderField(String field) throws Exception
	{
		try
		{
			if(!emptyLineExists)
				throw new Exception("There is no empty line to write the fields to.");

			//If the field is null make it empty String
			if(field == null)
				field = "";

			//Add a cell for the field
			Cell cell = emptyRow.createCell(emptyLineColCount);
			CellStyle cellStyle = cellStyleGlob;
			cellStyle.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
			cellStyle.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
			cellStyle.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
			cellStyle.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(field);

			emptyLineColCount++;
		}
		catch(Exception pe)
		{
			deleteFile();
			throw pe;
		}

		return true;
	}

	public boolean writeEmptyField() throws Exception
	{
		try
		{
			if(!emptyLineExists)
				throw new Exception("There is no empty line to write the fields to.");

			//If the field is null make it empty String

			//Add a cell for the field
			Cell cell = emptyRow.createCell(emptyLineColCount);

			CellStyle cellStyle = cellStyleGlob;
			cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
			cellStyle.setBorderRight(CellStyle.BORDER_THIN);
			cellStyle.setBorderTop(CellStyle.BORDER_THIN);

			//	public static final short COLOUR_GREY = HSSFColor.GREY_50_PERCENT.index;
			//	public static final short COLOUR_GREY_LIGHT = HSSFColor.GREY_25_PERCENT.index;
			//	public static final short COLOUR_GREY_DARK = HSSFColor.GREY_80_PERCENT.index;

			cellStyle.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
			cellStyle.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
			cellStyle.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
			cellStyle.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);

			cell.setCellStyle(cellStyle);
			cell.setCellValue("");

			emptyLineColCount++;
		}
		catch(Exception pe)
		{
			deleteFile();
			throw pe;
		}

		return true;
	}



	/**
	 * 
	 * <p><b>
	 * The empty line that was created using createFieldLine(), and later the fields were written using writeField() 
	 * method is commited to the file.
	 * </b></p>
	 * 
	 * @throws Exception
	 *
	 */
	public void commitEmptyLine() throws Exception
	{
		if(!emptyLineExists)
		{
			deleteFile();
			throw new Exception("There is no empty line to commit");
		}

		emptyRow = null;
		emptyLineExists = false;
		emptyLineColCount = 0;
	}

	/**
	 * <p><b>
	 * Closes the file.
	 * </b></p>
	 * 
	 * @return	"True" if the file was properly closed, "False" otherwise.
	 * 
	 * @throws Exception
	 *
	 */
	public boolean close() throws Exception
	{
		try
		{
			//Close the output streams
			if(workBook != null)
				workBook.write(fos);

			if(fos != null)
				fos.close();
		}
		catch(IOException ioe)
		{
			deleteFile();
			throw new Exception("The File " + this.fileName + " Could Not Be Closed");
		}

		return true;
	}

	/**
	 * 
	 * <p><b>
	 * This method set the cell style and returns the style object which can be attached to a cell.
	 * </b></p>
	 * 
	 * @return	The formatted style object that will set the style onj the cell to which it is attached
	 *
	 */
	private CellStyle getCellStyle()
	{
		CellStyle cellStyle1 = workBook.createCellStyle();

		//Set the border
		if(setBorder)
		{
			cellStyle1.setBorderBottom(CellStyle.BORDER_THIN);
			cellStyle1.setBorderLeft(CellStyle.BORDER_THIN);
			cellStyle1.setBorderRight(CellStyle.BORDER_THIN);
			cellStyle1.setBorderTop(CellStyle.BORDER_THIN);
		}

		//Set the font name, size and bold
		Font font = workBook.createFont();

		//ReportAppConfiguration.getDetail("fontname");

		font.setFontName(fontName);
		font.setFontHeightInPoints(fontSize);

		if(setBold)
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		font.setColor(fontColour);
		cellStyle1.setFont(font);

		//Set horizontal alignment
		if(horAlignment == ALIGN_CENTER)
			cellStyle1.setAlignment(CellStyle.ALIGN_CENTER);
		else if(horAlignment == ALIGN_RIGHT)
			cellStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
		else if(horAlignment == ALIGN_JUSTIFY)
			cellStyle1.setAlignment(CellStyle.ALIGN_JUSTIFY);
		else
			cellStyle1.setAlignment(CellStyle.ALIGN_LEFT);

		//Set vertical alignment
		if(verAlignment == VERTICAL_CENTER)
			cellStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		else if(verAlignment == VERTICAL_BOTTOM)
			cellStyle1.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		else if(verAlignment == VERTICAL_JUSTIFY)
			cellStyle1.setVerticalAlignment(CellStyle.VERTICAL_JUSTIFY);
		else
			cellStyle1.setVerticalAlignment(CellStyle.VERTICAL_TOP);


		//Set the background colour and the foreground colour

		cellStyle1.setFillForegroundColor(HSSFColor.WHITE.index);  


		cellStyle1.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle1.setDataFormat((short)0x31); // 0x31 Display Text Format Value

		return cellStyle1;
	}

	/**
	 * 
	 * <p><b>
	 * Deletes the file that was created.
	 * </b></p>
	 * 
	 * @throws Exception
	 *
	 */
	private void deleteFile() throws Exception
	{
		/*
		 * Check if the file already exists. If the file exists, delete the original file.
		 */
		File file = new File(this.absFileName);
		if(file.exists())
		{
			close();
			boolean delete = file.delete();
			if(delete==false) {
				System.out.println("file  deletion failed for file:"+file.getPath());	
			}
		}
	}

	public Header getHeader()
	{
		return this.header;
	}

	public Footer getFooter()
	{
		return this.footer;
	}




	/**
	 * 
	 * <p><b>
	 * This cell style is for Header Values in the Reports
	 * This method set the cell style and returns the style object which can be attached to a cell.
	 * </b></p>
	 * 
	 * @return	The formatted style object that will set the style onj the cell to which it is attached
	 *
	 */
	private CellStyle getCellStyleHeader()
	{
		CellStyle cellStyle1 = workBook.createCellStyle();

		//Set the border
		//if(setBorder)
		//{
		cellStyle1.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle1.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle1.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle1.setBorderTop(CellStyle.BORDER_THIN);
		//}

		//Set the font name, size and bold
		Font font = workBook.createFont();
		font.setFontName(fontName);
		font.setFontHeightInPoints(fontSize);

		if(setBold)
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		font.setColor(headerFontColor);

		cellStyle1.setFont(font);

		//Set horizontal alignment
		if(horAlignment == ALIGN_CENTER)
			cellStyle1.setAlignment(CellStyle.ALIGN_CENTER);
		else if(horAlignment == ALIGN_RIGHT)
			cellStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
		else if(horAlignment == ALIGN_JUSTIFY)
			cellStyle1.setAlignment(CellStyle.ALIGN_JUSTIFY);
		else
			cellStyle1.setAlignment(CellStyle.ALIGN_LEFT);

		//Set vertical alignment
		if(verAlignment == VERTICAL_CENTER)
			cellStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		else if(verAlignment == VERTICAL_BOTTOM)
			cellStyle1.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		else if(verAlignment == VERTICAL_JUSTIFY)
			cellStyle1.setVerticalAlignment(CellStyle.VERTICAL_JUSTIFY);
		else
			cellStyle1.setVerticalAlignment(CellStyle.VERTICAL_TOP);

		//Set the background colour and the foreground colour
		cellStyle1.setFillForegroundColor(headerColor);  
		//cellStyle1.setWrapText(arg0)
		cellStyle1.setFillPattern(CellStyle.SOLID_FOREGROUND);


		return cellStyle1;
	}
	
	public void setHeaderColor(short headerColor) {
		this.headerColor = headerColor;
	}

	/**
	 * 
	 * <p><b>
	 * This cell style is for Numeric Valus in the Reports
	 * This method set the cell style and returns the style object which can be attached to a cell.
	 * </b></p>
	 * 
	 * @return	The formatted style object that will set the style onj the cell to which it is attached
	 *
	 */
	private CellStyle getCellStyleNumeric()
	{
		CellStyle cellStyle = workBook.createCellStyle();

		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);

		//Set the font name, size and bold
		Font font = workBook.createFont();
		font.setFontName(fontName);
		font.setFontHeightInPoints(fontSize);

		font.setColor(fontColour);
		cellStyle.setFont(font);

		cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);

		cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);  
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle.setDataFormat((short)2); //1- "0" ,  2 - "0.00"

		return cellStyle;
	}
	
	private int loadPicture( String path, SXSSFWorkbook wb ) throws IOException
	{
		int pictureIndex;
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try
		{
			fis = new FileInputStream(path);
			bos = new ByteArrayOutputStream( );
			int c;
			while ( (c = fis.read()) != -1)
				bos.write( c );
			pictureIndex = wb.addPicture(bos.toByteArray(),SXSSFWorkbook.PICTURE_TYPE_JPEG); 
		}
		finally
		{
			if (fis != null)
				fis.close();
			if (bos != null)
				bos.close();
		}
		return pictureIndex;
	}

	@SuppressWarnings("unused")
	private void addLogo(Sheet sheet, SXSSFWorkbook wb ) throws	IOException
	{
		Drawing patriarch = (Drawing)sheet.createDrawingPatriarch();
		ClientAnchor anchor;
		anchor = new XSSFClientAnchor(1,1,50,50,(short)0,0,(short)1,2);
		((Drawing)patriarch).createPicture(anchor, loadPicture(logoPath, wb ));
	}
    
	public boolean writeLineForPartiallyBoldHeaderDetails(String ...line) throws Exception{
		
		if(emptyLineExists){
			throw new Exception("There is an empty line which has not been committed. Hence new line cannot be written.");
		}
		
		//Check if the text is not null or empty
		if(line == null || line.length == 0){
			//Increment the current line number
			currentLineNo++;

			//Create a row in the sheet
			Row row = sheet.createRow((short)(currentLineNo - 1));
			return false;
		}else if(line.length == 1)
			if(line[0].equalsIgnoreCase("PageBreak"))
			{
				sheet.setRowBreak(currentLineNo-1);
				return true;
			}

		//	Increment the current line number
		currentLineNo++;

		//Create a row in the sheet
		Row row = sheet.createRow((short)(currentLineNo - 1));
		
		int currentCellNo = 0;
		for (int counter = 0; counter < line.length; counter++) {
			
			Cell cell = row.createCell((short)currentCellNo);
			
			CellStyle  cellStyle = null; 
			if(line[counter] != null){
				line[counter] = line[counter].trim();
			}
			
			if(counter%2 == 0){
				this.setBold = true;
			}else{
				this.setBold = false;
			}
			
			cellStyle = getCellStyle();
			cellStyle.setWrapText(false);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(line[counter]);
			
//			sheet.addMergedRegion(new Region((currentLineNo - 1), (short)currentCellNo,(currentLineNo - 1), (short)(++currentCellNo)));
			sheet.addMergedRegion(new CellRangeAddress(currentLineNo - 1, (currentLineNo - 1),0,(fileFormat.getFileColCount() - 1)));
			currentCellNo++;
		}
				
		return true;
	}
	
	/**
	 * Added by roshan for creating multiple sheets in a file
	 * @throws Exception
	 * @throws IOException
	 */
	public void createSheet() throws Exception, IOException {
		Integer sheetNo = workBook.getNumberOfSheets()+1;
		sheet = workBook.createSheet("sheet"+sheetNo);	
		sheet.setDisplayGridlines(false);
		PrintSetup printSetup = sheet.getPrintSetup();
		printSetup.setHeaderMargin(0.25);
		printSetup.setFooterMargin(0);
		this.header = sheet.getHeader();
		this.footer = sheet.getFooter();
		
		if(logoPath!=null && logoPath.length()!=0)
		{
			this.createEmptyLine();
			this.commitEmptyLine();
			this.addLogo(sheet, workBook);
			
			//two empty lines created for printing logo in the header part
			this.createEmptyLine();
			this.commitEmptyLine();
			this.createEmptyLine();
			this.commitEmptyLine();
		}
		
		sheet.setDefaultColumnWidth((short)14);
	}
	public void setCurrentLineNo(int currentLineNo){
		this.currentLineNo = currentLineNo;
	}
	
}