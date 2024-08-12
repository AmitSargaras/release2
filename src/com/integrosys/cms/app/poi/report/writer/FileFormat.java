/*
 * Created on Nov 4, 2004 By Prashant Soneria
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


/**
 * <p><b>
 * This class stores the basic information about the format of the file that will be read or written.
 * </b></p>
 * 
 * @version 1.0
 * 
 * @author Prashant Soneria
 * 
 */
public class FileFormat
{
	/////////////////////////////////////  Type Of File  //////////////////////////////////////////
	
    private static final int FILE_TYPE_BASE = 10000;
    
	/**
	 * Delimited Text File.
	 */
	public static final int FILE_TYPE_TEXT_DELIMITED = FILE_TYPE_BASE + 1;
	
	/**
	 * Fixed Width Text File.
	 */
	public static final int FILE_TYPE_TEXT_FIXED = FILE_TYPE_BASE + 2;
	
	/**
	 * A DBF File.
	 */
	public static final int FILE_TYPE_DBF = FILE_TYPE_BASE + 4;
	
	/**
	 * Microsoft Excel File.
	 */
	public static final int FILE_TYPE_EXCEL = FILE_TYPE_BASE + 8;
	
	/**
	 * XML File.
	 */
	public static final int FILE_TYPE_XML = FILE_TYPE_BASE + 16;
	
	/**
	 * PDF File.
	 */
	public static final int FILE_TYPE_PDF = FILE_TYPE_BASE + 32;
	
	/**
	 * RTF File
	 */
	public static final int FILE_TYPE_RTF = FILE_TYPE_BASE + 64;
	
	/////////////////////////////////////  Type Of File  //////////////////////////////////////////
	
	
	///////////////////////////////////  Type Of Column  ////////////////////////////////////////
	
	private static final int COL_TYPE_BASE = 0;
	
	/**
	 * The column must be integer (number without decimal point).
	 */
	public static final int COL_TYPE_INTEGER = COL_TYPE_BASE + 1;
	
	/**
	 * The column must be a floating point number.
	 */
	public static final int COL_TYPE_FLOAT = COL_TYPE_BASE + 2;
	
	/**
	 * The column must be integer amount (number without decimal point).
	 */
	public static final int COL_TYPE_AMOUNT_INTEGER = COL_TYPE_BASE + 4;
	
	/**
	 * The column must be a floating point amount (number with maximum of two digits after decimal point).
	 */
	public static final int COL_TYPE_AMOUNT_FLOAT = COL_TYPE_BASE + 8;
	
	/**
	 * This field must be a date value in one of the supported date formats. Date 
	 * separators '-' and '/' are supported with all date formats.
	 */
	public static final int COL_TYPE_DATE = COL_TYPE_BASE + 16;
	
	/**
	 * The value of the field must be case insensitive 'Y' or 'N'
	 */	
	public static final int COL_TYPE_YN = COL_TYPE_BASE + 32;
	
	/**
	 * The value of the field must be case insensitive 'YES' or 'NO'
	 */	
	public static final int COL_TYPE_YESNO = COL_TYPE_BASE + 64;
	
	/**
	 * The value of the field must be case insensitive 'T' or 'F'
	 */	
	public static final int COL_TYPE_TF = COL_TYPE_BASE + 128;
	
	/**
	 * The value of the field must be case insensitive 'TRUE' or 'FALSE'
	 */	
	public static final int COL_TYPE_TRUEFALSE = COL_TYPE_BASE + 256;
	
	/**
	 * The value of the field is a string
	 */	
	public static final int COL_TYPE_STRING = COL_TYPE_BASE + 512;
	///////////////////////////////////  Type Of Column  ////////////////////////////////////////
	
	///////////////////////////////////  Date Formats  //////////////////////////////////////////
	
	private static final int DATE_FORMAT_BASE = 40000;
	
	/**
	 * Sortable date format, examples of this date format are: 2004-10-15, 2003/01/30
	 */
	public static final int DATE_FORMAT_YYYYMMDD = DATE_FORMAT_BASE + 1;
	
	/**
	 * Sortable date format, examples of this date format are: 04-10-15,  03/01/30
	 */
	public static final int DATE_FORMAT_YYMMDD = DATE_FORMAT_BASE + 2;
	
	/**
	 * Asia/Europe style date format, examples of this date format are: 15-10-04,  30/01/03
	 */
	public static final int DATE_FORMAT_DDMMYY = DATE_FORMAT_BASE + 4;
	
	/**
	 * Asia/Europe style date format, examples of this date format are: 15-Oct-04,  30/Jan/03
	 */
	public static final int DATE_FORMAT_DDMMMYY = DATE_FORMAT_BASE + 8;
	
	/**
	 * Asia/Europe style date format, examples of this date format are: 15-10-2004,  30/01/2003
	 */
	public static final int DATE_FORMAT_DDMMYYYY = DATE_FORMAT_BASE + 16;
	
	/**
	 * US style date format, examples of this date format are: 10-15-04,  01/30/03
	 */
	public static final int DATE_FORMAT_MMDDYY = DATE_FORMAT_BASE + 32;
	
	/**
	 * US style date format, examples of this date format are: Oct-15-04,  Jan/30/03
	 */
	public static final int DATE_FORMAT_MMMDDYY = DATE_FORMAT_BASE + 64;
	
	/**
	 * US style date format, examples of this date format are: 10-15-2004,  01/30/2003
	 */
	public static final int DATE_FORMAT_MMDDYYYY = DATE_FORMAT_BASE + 128;
	
	///////////////////////////////////  Date Formats  //////////////////////////////////////////
	
	
	//////////////////////////////  File Properties  ////////////////////////////////////////////
	
	private int fileType = -1;					//The type of the file
	private int fileColumnCount = -1;			//The number of columns in the file
	private int fileHeaderLines = 0;			//Number of header lines in the file
	private int fileFooterLines = 0;			//Number of footer lines in the file
	private boolean fileColCountVariable;		//true if the file can have variable number of columns with fileColumnCount as upper limit
	private int[] colType;						//The type of column for file being imported/exported
	private int[] colDateFormat;				//The date format for columns that are of type date
	private int[] colMinLength;					//The minimum length of values for this column
	private int[] colMaxLength;					//The maximum length of values for this column
	private String[] fileColumnNames;			//The columns names in the file
	
	
	/*##########   Properties for Reader Only   ##################*/
	
	private int fileMinRows = -1;		//The minimum number or rows the input file (in case of reader) must have
	private int fileMaxRows = -1;		//The maximum number or rows the input file (in case of reader) can have
	/*##########   Properties for Reader Only   ##################*/
	
	
	/*##########   Properties for Text File Only   ##################*/
	
	private String[] fileHeaderText;		//The header text to be written to file, text format only
	private String[] fileFooterText;		//The footer text to be written to file, text format only
	private String[] delimiters;			//Array of delimiters for the input file, use first one while exporting
	private int[] colWidths;				//Width of columns for fixed-width text files.
	private char[] colPaddingCharacter;		//The padding character for columns for fixed-width text files.
	private boolean[] isPrefix;				//Whether the padding is prefix or a suffix.
	private int[] colStartPosition;			//Start position of columns for fixed-width text files.
	/*##########   Properties for Text File Only   ##################*/
	
	
	/*##########   Properties for XML File Only   ##################*/
	
	private String fileRootName;			//The name of the root element of xml file
	private String fileRowElementName;		//The name of the root element of xml file
	private String[] amountFormat;
	/*##########   Properties for XML File Only   ##################*/
	
	//////////////////////////////  File Properties  ////////////////////////////////////////////
	
	public void setFileFormatWithSrNos(FileFormat fileFormat, String[] columns ) throws Exception
	{
		try
		{	
			if(columns != null) {
				fileFormat.setFileColCount(columns.length);
				fileFormat.setFileColumnNames(columns);
			}
			else {
				
				fileFormat.setFileColCount(1);
				fileFormat.setFileColumnNames(new String[]{""});
			}
		}
		catch(Exception sqle)
		{
			throw new Exception(sqle.getMessage());
		}
	}
	
	/**
	 * <p><b>
	 * Default constructor that intialises the file properties to default values.
	 * </b></p>
	 *
	 */
	public FileFormat()
	{
		/* The component does not support importing files with more than 256 columns */
	    colWidths = new int[256];
	    colStartPosition = new int[256];
		colType = new int[256];
		colDateFormat = new int[256];
		amountFormat = null;
		colType = new int[256];
		colMinLength = null;
		colMaxLength = null;
		fileHeaderText = null;
		fileFooterText = null;
		fileColumnNames = null;
		fileColumnCount = -1;
		delimiters = null;
		fileRootName = null;
		fileRowElementName = null;
	}
	
	/**
	 * <p><b>
	 * Constructor that intialises the file properties to default values and sets the file type.
	 * </b></p>
	 * 
	 * @param		fileType	Integer specifying the type of file
	 * 
	 */
	public FileFormat(int fileType)
	{
		this();
		this.fileType = fileType;
	}

	/**
	 * <p><b>
	 * Constructor to be used with Delimited-Text files. It intialises the file properties to default values, and
	 * also sets the file type and delimitors.
	 * </b></p>
	 * 
	 * @param		delimitors		String array specifying the delimitors used to separate fields (Text-Delimited) 
	 * 
	 */
	public FileFormat(String[] delimiters)
	{
		this();
		this.fileType = FILE_TYPE_TEXT_DELIMITED;
		this.delimiters = delimiters;
	}

	/**
	 * 
	 * <p><b>
	 * Returns the file type
	 * </b></p>
	 * 
	 * @return	Integer specifying the type of file.
	 *
	 */
	public int getFileType()
	{
		return fileType;
	}

	/**
	 * 
	 * <p><b>
	 * Sets the file type
	 * </b></p>
	 * 
	 * @param		fileType	Integer specifying the type of file.
	 *
	 */
	public void setFileType(int fileType)
	{
		this.fileType = fileType;
	}
	
	/**
	 * 
	 * <p><b>
	 * Returns the delimiters
	 * </b></p>
	 * 
	 * @return	String array specifying the delimitors.
	 * 
	 * @throws Exception
	 *
	 */
	public String[] getDelimiters() throws Exception
	{
	    if(fileType != FILE_TYPE_TEXT_DELIMITED)
	        throw new Exception("Delimitors can be defined for Text-Delimited files only.");
	    
		return delimiters;
	}

	/**
	 * 
	 * <p><b>
	 * Sets the delimiters
	 * </b></p>
	 * 
	 * @param		delimiters		String array specifying the delimitors.
	 * 
	 * @throws Exception
	 *
	 */
	public void setDelimiters(String[] delimiters) throws Exception
	{
	    if(fileType != FILE_TYPE_TEXT_DELIMITED)
			throw new Exception("Delimitors can be set for Text-Delimited files only.");
	    this.delimiters = delimiters;
	}

	/**
	 * 
	 * <p><b>
	 * Returns the number of file footer lines
	 * </b></p>
	 * 
	 * @return	The number of file footer lines
	 *
	 */
	public int getFileFooterLines()
	{
		return fileFooterLines;
	}

	/**
	 * 
	 * <p><b>
	 * Sets the number of file footer lines
	 * </b></p>
	 * 
	 * @param		fileFooterLines		The number of file footer lines
	 *
	 */
	public void setFileFooterLines(int fileFooterLines)
	{
		this.fileFooterLines = fileFooterLines;
	}

	/**
	 * 
	 * <p><b>
	 * Returns the number of file header lines
	 * </b></p>
	 * 
	 * @return	The number of file header lines
	 *
	 */
	public int getFileHeaderLines()
	{
		return fileHeaderLines;
	}

	/**
	 * 
	 * <p><b>
	 * Sets the number of file header lines
	 * </b></p>
	 * 
	 * @param		fileHeaderLines		The number of file header lines
	 *
	 */
	public void setFileHeaderLines(int fileHeaderLines)
	{
		this.fileHeaderLines = fileHeaderLines;
	}

	/**
	 * 
	 * <p><b>
	 * Returns the file header text
	 * </b></p>
	 * 
	 * @return	String array representing the header text.
	 * 
	 * @throws Exception
	 *
	 */
	public String[] getFileHeaderText() throws Exception
	{
	    if(fileType != FILE_TYPE_TEXT_DELIMITED && fileType != FILE_TYPE_TEXT_FIXED)
	        throw new Exception("Header text can be defined for Text files only.");
	    
		return fileHeaderText;
	}
	
	/**
	 * 
	 * <p><b>
	 * Sets the file header text
	 * </b></p>
	 * 
	 * @param		fileHeaderText		String array representing the header text.
	 * 
	 * @throws Exception
	 *
	 */
	public void setFileHeaderText(String[] fileHeaderText) throws Exception
	{
	    if(fileType != FILE_TYPE_TEXT_DELIMITED && fileType != FILE_TYPE_TEXT_FIXED)
	        throw new Exception("Header text can be set for Text files only.");
	    
		this.fileHeaderText = fileHeaderText;
	}

	/**
	 * 
	 * <p><b>
	 * Returns the file footer text
	 * </b></p>
	 * 
	 * @return	String array representing the footer text.
	 * 
	 * @throws Exception
	 *
	 */
	public String[] getFileFooterText() throws Exception
	{
	    if(fileType != FILE_TYPE_TEXT_DELIMITED && fileType != FILE_TYPE_TEXT_FIXED)
	        throw new Exception("Footer text can be defined for Text files only.");
	    
		return fileFooterText;
	}

	/**
	 * 
	 * <p><b>
	 * Sets the file footer text
	 * </b></p>
	 * 
	 * @param		fileFooterText		String array representing the footer text.
	 * 
	 * @throws Exception
	 *
	 */
	public void setFileFooterText(String[] fileFooterText) throws Exception
	{
	    if(fileType != FILE_TYPE_TEXT_DELIMITED && fileType != FILE_TYPE_TEXT_FIXED)
	        throw new Exception("Footer text can be set for Text files only.");
	    
		this.fileFooterText = fileFooterText;
	}

	/**
	 * 
	 * <p><b>
	 * Returns the number of columns in the file
	 * </b></p>
	 * 
	 * @return	The number of columns in the file
	 *
	 */
	public int getFileColCount()
	{
		return fileColumnCount;
	}
	
	/**
	 * 
	 * <p><b>
	 * Sets the number of columns in the file
	 * </b></p>
	 * 
	 * @param		fileColumnCount		The number of columns in the file
	 *
	 */
	public void setFileColCount(int fileColumnCount)
	{
		this.fileColumnCount = fileColumnCount;
	}

	/**
	 * 
	 * <p><b>
	 * Returns the maximum number of rows allowed in the file
	 * </b></p>
	 * 
	 * @return	The maximum number of rows allowed in the file
	 *
	 */
	public int getFileMaxRows()
	{
		return fileMaxRows;
	}
	
	/**
	 * 
	 * <p><b>
	 * Sets the maximum number of rows allowed in the file
	 * </b></p>
	 * 
	 * @param		fileMinRows		The maximum number of rows allowed in the file
	 *
	 */
	public void setFileMaxRows(int fileMinRows)
	{
		this.fileMaxRows = fileMinRows;
	}
	
	/**
	 * 
	 * <p><b>
	 * Returns the minimum number of rows that the file must have
	 * </b></p>
	 * 
	 * @return	The minimum number of rows that the file must have
	 *
	 */
	public int getFileMinRows()
	{
		return fileMinRows;
	}

	/**
	 * 
	 * <p><b>
	 * Sets the minimum number of rows that the file must have
	 * </b></p>
	 * 
	 * @param		fileMinRows		The minimum number of rows that the file must have
	 *
	 */
	public void setFileMinRows(int fileMinRows)
	{
		this.fileMinRows = fileMinRows;
	}

	/**
	 * 
	 * <p><b>
	 * Returns the date formats for the columns
	 * </b></p>
	 * 
	 * @return	The date formats for the columns
	 *
	 */
	public int[] getColDateFormat()
	{
		return colDateFormat;
	}
	
	/**
	 * 
	 * <p><b>
	 * Sets the date formats for the columns
	 * </b></p>
	 * 
	 * @param		colDateFormat		The date formats for the columns
	 *
	 */
	public void setColDateFormat(int[] colDateFormat)
	{
		this.colDateFormat = colDateFormat;
	}
	
	/**
	 * 
	 * <p><b>
	 * Returns the maximum number of columns allowed
	 * </b></p>
	 * 
	 * @return	The maximum number of columns allowed
	 *
	 */
	public int[] getColMaxLength()
	{
		return colMaxLength;
	}
	
	/**
	 * 
	 * <p><b>
	 * Sets the maximum number of columns allowed
	 * </b></p>
	 * 
	 * @param		colMaxLength		The maximum number of columns allowed
	 *
	 */
	public void setColMaxLength(int[] colMaxLength)
	{
		this.colMaxLength = colMaxLength;
	}

	/**
	 * 
	 * <p><b>
	 * Returns the minimum number of columns allowed
	 * </b></p>
	 * 
	 * @return	The minimum number of columns allowed
	 *
	 */
	public int[] getColMinLength()
	{
		return colMinLength;
	}
	
	/**
	 * 
	 * <p><b>
	 * Sets the minimum number of columns allowed
	 * </b></p>
	 * 
	 * @param		colMinLength		The minimum number of columns allowed
	 *
	 */
	public void setColMinLength(int[] colMinLength)
	{
		this.colMinLength = colMinLength;
	}

	/**
	 * 
	 * <p><b>
	 * Returns an integer array specifying the type of columns
	 * </b></p>
	 * 
	 * @return	An integer array specifying the type of columns
	 *
	 */
	public int[] getColType()
	{
		return colType;
	}
	
	/**
	 * 
	 * <p><b>
	 * Sets the type of columns
	 * </b></p>
	 * 
	 * @param		colType		An integer array specifying the type of columns
	 *
	 */
	public void setColType(int[] colType)
	{
		this.colType = colType;
	}

	/**
	 * 
	 * <p><b>
	 * Returns the widths of the columns
	 * </b></p>
	 * 
	 * @return	Integer array specifying the widths of the columns
	 * 
	 * @throws Exception
	 *
	 */
	public int[] getColWidths() throws Exception
	{
		if(fileType != FILE_TYPE_TEXT_FIXED)
		    throw new Exception("Column width can be defined for Text-Fixed Length files only.");
		
		return colWidths;
	}
	
	/**
	 * 
	 * <p><b>
	 * Sets the widths of the columns
	 * </b></p>
	 * 
	 * @param		colWidths		Integer array specifying the widths of the columns
	 * 
	 * @throws Exception
	 *
	 */
	public void setColWidths(int[] colWidths) throws Exception
	{
		if(fileType != FILE_TYPE_TEXT_FIXED)
		    throw new Exception("Column width can be set for Text-Fixed Length files only.");

		this.colWidths = colWidths;
	}
	
	/**
	 * 
	 * <p><b>
	 * Returns the padding characters for respective columns
	 * </b></p>
	 * 
	 * @return	Character array specifying the padding characters of the columns
	 * 
	 * @throws Exception
	 *
	 */
	public char[] getColPaddingCharacter() throws Exception
	{
		if(fileType != FILE_TYPE_TEXT_FIXED)
		    throw new Exception("Padding characters can be defined for Text-Fixed Length files only.");
		
		return colPaddingCharacter;
	}
	
	/**
	 * 
	 * <p><b>
	 * Sets the padding characters of the columns
	 * </b></p>
	 * 
	 * @param		colPaddingCharacter		Character array specifying the padding characters of the columns
	 * 
	 * @throws Exception
	 *
	 */
	public void setColPaddingCharacter(char[] colPaddingCharacter) throws Exception
	{
		if(fileType != FILE_TYPE_TEXT_FIXED)
		    throw new Exception("Padding characters can be set for Text-Fixed Length files only.");

		this.colPaddingCharacter = colPaddingCharacter;
	}
	
	/**
	 * 
	 * <p><b>
	 * Returns the array specifying whether padding is prefix or suffix for respective columns
	 * </b></p>
	 * 
	 * @return	Boolean array specifying whether padding is prefix or suffix for respective columns
	 * 
	 * @throws Exception
	 *
	 */
	public boolean[] getIsPrefix() throws Exception
	{
		if(fileType != FILE_TYPE_TEXT_FIXED)
		    throw new Exception("Padding characters Prefix/ Suffix can be defined for Text-Fixed Length files only.");
		
		return isPrefix;
	}
	
	/**
	 * 
	 * <p><b>
	 * Sets the array specifying whether padding is prefix or suffix for respective columns
	 * </b></p>
	 * 
	 * @param		isPrefix		Boolean array specifying whether padding is prefix or suffix for respective columns
	 * 
	 * @throws Exception
	 *
	 */
	public void setIsPrefix(boolean[] isPrefix) throws Exception
	{
		if(fileType != FILE_TYPE_TEXT_FIXED)
		    throw new Exception("Padding characters Prefix/ Suffix can be set for Text-Fixed Length files only.");

		this.isPrefix = isPrefix;
	}
	
	
	public String[] getAmountFormat() {
		return amountFormat;
	}

	public void setAmountFormat(String[] amountFormat) {
		this.amountFormat = amountFormat;
	}

	/**
	 * 
	 * <p><b>
	 * Returns "True" if the number of columns in the file can be variable. 
	 * </b></p>
	 * 
	 * @return	Specifies whether the number of columns in the file can be variable.
	 *
	 */
	public boolean isFileColCountVariable()
	{
		return fileColCountVariable;
	}

	/**
	 * 
	 * <p><b>
	 * Sets/ Resets the variable column count.
	 * </b></p>
	 * 
	 * @param		isFileColCountVariable		"True" means the number of columns in the file can be variable.
	 *
	 */
	public void setFileColCountVariable(boolean fileColCountVariable)
	{
		this.fileColCountVariable = fileColCountVariable;
	}

	/**
	 * 
	 * <p><b>
	 * Returns the column names.
	 * </b></p>
	 * 
	 * @return	String array representing the column names.
	 * 
	 * @throws Exception
	 *
	 */
	public String[] getFileColumnNames() throws Exception
	{
	    return fileColumnNames;
	}

	/**
	 * 
	 * <p><b>
	 * Sets the column names.
	 * </b></p>
	 * 
	 * @param		fileColumnNames		String array representing the column names.
	 * 
	 * @throws Exception
	 *
	 */
	public void setFileColumnNames(String[] fileColumnNames) throws Exception
	{
	    this.fileColumnNames = fileColumnNames;
	}

	/**
	 * 
	 * <p><b>
	 * Returns the file root name.
	 * </b></p>
	 * 
	 * @return	The file root name.
	 * 
	 * @throws Exception
	 *
	 */
	public String getFileRootName() throws Exception
	{
	    if(fileType != FILE_TYPE_XML)
		    throw new Exception("File root name can be defined for XML files only.");
	    
		return fileRootName;
	}
	
	/**
	 * 
	 * <p><b>
	 * Sets the file row element name.
	 * </b></p>
	 * 
	 * @param		fileRootName		The file root name.
	 * 
	 * @throws Exception
	 *
	 */
	public void setFileRootName(String fileRootName) throws Exception
	{
	    if(fileType != FILE_TYPE_XML)
		    throw new Exception("File root name can be set for XML files only.");
	    
		this.fileRootName = fileRootName;
	}
	
	/**
	 * 
	 * <p><b>
	 * Returns the file row element name.
	 * </b></p>
	 * 
	 * @return	The file row element name.
	 * 
	 * @throws Exception
	 *
	 */
	public String getFileRowElementName() throws Exception
	{
	    if(fileType != FILE_TYPE_XML)
		    throw new Exception("File row element name can be defined for XML files only.");
	    
		return fileRowElementName;
	}
	
	/**
	 * 
	 * <p><b>
	 * Sets the file row element name.
	 * </b></p>
	 * 
	 * @author
	 * 
	 * @param		fileRowElementName		The file row element name.
	 * 
	 * @throws Exception
	 *
	 */
	public void setFileRowElementName(String fileRowElementName) throws Exception
	{
	    if(fileType != FILE_TYPE_XML)
		    throw new Exception("File row element name can be set for XML files only.");
	    
		this.fileRowElementName = fileRowElementName;
	}
	
	/**
	 * 
	 * <p><b>
	 * Returns an integer array representing the start position of the columns (useful only in case 
	 * of Text-Fixed Length).
	 * </b></p>
	 * 
	 * @return	An integer array representing the start position of the columns
	 * 
	 * @throws Exception
	 *
	 */
	public int[] getColStartPosition() throws Exception
	{
	    if(fileType != FILE_TYPE_TEXT_FIXED)
		    throw new Exception("Column start position can be defined for Text-Fixed Length files only.");
	    
		return colStartPosition;
	}

	/**
	 * 
	 * <p><b>
	 * Sets the start position of the columns
	 * </b></p>
	 * 
	 * @param		colStartPosition		An integer array representing the start position of the columns
	 * 
	 * @throws Exception
	 *
	 */
	public void setColStartPosition(int[] colStartPosition) throws Exception
	{
	    if(fileType != FILE_TYPE_TEXT_FIXED)
		    throw new Exception("Column start position can be set for Text-Fixed Length files only.");
	    
		this.colStartPosition = colStartPosition;
	}
	
	/**
	 * 
	 * <p><b>
	 * This method validates whether the file format has been properly set according to the file type or not. If not
	 * then it throws Exception with appropriate message.
	 * </b></p>
	 * 
	 * @throws Exception
	 *
	 */
	public void validateFileFormat() throws Exception
	{
	    //Check if the column types are not null or empty
		if(fileType < FILE_TYPE_BASE || fileType > FILE_TYPE_PDF)
			throw new Exception("The file type need to be specified.");
		
		//Column count is compulsory for all files
		if(fileColumnCount < 1)
			throw new Exception("The file column count need to be specified.");
		
		//Column Names are compulsory fields for all files
		if(fileColumnNames == null || fileColumnNames.length < 1 || (!fileColCountVariable && fileColumnNames.length < fileColumnCount))
			throw new Exception("The file column names need to be specified.");
		
		//Check if the column names are not null or empty
		for(int i = 0; i < fileColumnNames.length; i++)
		{
			//if(fileColumnNames[i] == null || fileColumnNames[i].trim().equals(""))
			if(fileColumnNames[i] == null)
				throw new Exception("The file column names need to be specified.");
		}
		
		//Column types are compulsory fields for all files
		if(colType == null || colType.length < 1 || (!fileColCountVariable && colType.length < fileColumnCount))
			throw new Exception("The file column types need to be specified.");
		
		//Check if the column types are not null or empty
		for(int i = 0; i < colType.length; i++)
		{
			if(colType[i] < COL_TYPE_BASE || colType[i] > COL_TYPE_STRING)
				throw new Exception("The file column types need to be specified.");
		}
		
		if(fileType == FILE_TYPE_TEXT_DELIMITED)
	    {
			//Check for delimitors
			
	        if(delimiters == null || delimiters.length < 1)
				throw new Exception("In case of Text-Delimited files, the delimitors need to be specified.");
	        
	        //Check if the delimitors are not null or empty
			for(int i = 0; i < delimiters.length; i++)
			{
				if(delimiters[i] == null || delimiters[i].trim().equals(""))
					throw new Exception("In case of Text-Delimited files, the delimitors need to be specified.");
			}
	    }
	    else if(fileType == FILE_TYPE_TEXT_FIXED)
	    {
	        //Check for column widths
			
	    	if(colWidths == null || colWidths.length < 1 || (!fileColCountVariable && colWidths.length < fileColumnCount))
				throw new Exception("In case of Text-Fixed Length files, the column widths need to be specified.");
			
			//Check if the column widths are not null or empty
			for(int i = 0; i < colWidths.length; i++)
			//for(int i = 1; i <= colWidths.length; i++)
			{
				if(colWidths[i] < 1)
					throw new Exception("In case of Text-Fixed Length files, the column widths need to be specified.");
			}
			
			//Check for column start positions
			if(colStartPosition == null || colStartPosition.length < 1 || (!fileColCountVariable && colStartPosition.length < fileColumnCount))
				throw new Exception("In case of Text-Fixed Length files, the column start positions need to be specified.");
			
			//Check if the column widths are not null or empty
			//for(int i = 0; i < colStartPosition.length; i++)
			//if(colStartPosition[0] == 0) {
				int colLength = colStartPosition.length;
				for(int i = 0; i < colStartPosition.length; i++)
				{ 
					
					if(colStartPosition[i] < 0)
						throw new Exception("In case of Text-Fixed Length files, the column start positions need to be specified.");
					if(colStartPosition[i] == 0 ) {
						if(i > 0 && (colStartPosition[i] - colStartPosition[i-1]) != colWidths[i - 1])
							throw new Exception("The field \"" + fileColumnNames[i] + "\" has column start positions that does not conform with the column widths specified.");
					}
					else
					{
						if(colLength == i+1) {
							
							if(i > 0 && ( colStartPosition[i] - colStartPosition[i-1]) != colWidths[i-1])
									throw new Exception("The field \"" + fileColumnNames[i] + "\" has column start positions that does not conform with the column widths specified.");
						}
						else {
							if(i > 0 && ( colStartPosition[i+1] - colStartPosition[i]) != colWidths[i])
									throw new Exception("The field \"" + fileColumnNames[i] + "\" has column start positions that does not conform with the column widths specified.");
							}
					}
				}
			//}
			
			//Check for column padding characters
			if(colPaddingCharacter == null || colPaddingCharacter.length < 1 || (!fileColCountVariable && colPaddingCharacter.length < fileColumnCount))
				throw new Exception("In case of Text-Fixed Length files, the column padding characters need to be specified.");
			
			//Check for column prefix
			if(isPrefix == null || isPrefix.length < 1 || (!fileColCountVariable && isPrefix.length < fileColumnCount))
				throw new Exception("In case of Text-Fixed Length files, whether the padding characters are prefixes or suffixes needs to be specified.");
	    }
	    else if(fileType == FILE_TYPE_XML)
	    {
			//Check for file root name
			if(fileRootName == null || fileRootName.trim().equals(""))
				throw new Exception("In case of XML files, the file root name need to be specified.");
			
			//Check for file row element name
			if(fileRowElementName == null || fileRowElementName.trim().equals(""))
				throw new Exception("In case of XML files, the file row element name need to be specified.");
	    }
	}
}