/*
 * Created on Nov 3, 2004 By Prashant Soneria
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

import java.io.File;
import java.text.DecimalFormat;



/**
 * <p><b>
 * This is the base class for all Writers. It declares some methods that have to be implemented by the extending
 * classes. It also defines the basic methods that will be used by all.
 * </b></p>
 * 
 * @version 1.0
 * 
 * @author Prashant Soneria
 * 
 */
public abstract class DataWriter
{
	//General properties to be used.
    private String basePath = null;
    protected String fileName = null;
	protected String absFileName = null;
	protected FileFormat fileFormat = null;
	protected int lineType = 0;
	protected Integer[] columnWidth;
	
	//#########################   Line Types   #################################//
	
	public static final int LINE_TYPE_GENERAL = 1;
	public static final int LINE_TYPE_RECORD = 2;
	public static final int LINE_TYPE_HEADER= 3;
	public static final int LINE_TYPE_TITLE= 0;
	
	//#########################   Line Types   #################################//
	
	/**
	 * <p><b>
	 * Constructor that takes a FileFormat and a String parameter. The String parameter specifies the base path 
	 * where the file will be written.
	 * </b></p>
	 * 
	 * @param		fileFormat		The format of the file to be written
	 * @param		basePath		The base path where the file will be written
	 * @param columnWidth TODO
	 * 
	 */
	public DataWriter(FileFormat fileFormat, String basePath, Integer[] columnWidth)
	{
		this.fileFormat = fileFormat;
		this.basePath = basePath;
		this.columnWidth = columnWidth;
	}
	
	/**
	 * 
	 * <p><b>
	 * Opens a file in write mode.
	 * </b></p> 
	 * 
	 * @param		fileName		The name of the file to be opened
	 * @param dataSize TODO
	 * 
	 * @return	"True" if the file was properly opened in write mode. "False" indicates there is some problem and the
	 * 			data will not be properly written to the file.
	 * 
	 * @throws DataWriterException
	 *
	 */
	public boolean open(String fileName, int dataSize) throws Exception
	{
	    this.fileName = fileName;
	    
	    File file = new File(basePath);
	    if(!file.exists()) {
	    	file.mkdirs();
		}
		absFileName = basePath + System.getProperty("file.separator") + fileName;
		
		/*
		 * Check whether the file format is properly set. Throws Exception in case the file format is not set
		 * properly as per the file type.
		 */
		if(dataSize > 0 )
			fileFormat.validateFileFormat();
		
	   //Check if the file type is properly set or not
		checkFileType();
		return intialiseFile();
	}
	
	/**
	 * 
	 * <p><b>
	 * This method intialises the opened file for writing.
	 * </b></p>
	 * 
	 * @return	"True" if the file was properly initialised. "False" indicates there is some problem and the
	 * 			data will not be properly written to the file.
	 * 
	 * @throws DataWriterException
	 *
	 */
	protected abstract boolean intialiseFile() throws Exception;
	
	/**
	 * 
	 * <p><b>
	 * Writes a line in the file.
	 * </b></p>
	 * 
	 * @param		line		String array that represents one record that will be written in the file.
	 * @param		type		Type of line (Header/ Footer/ Heading/ General/ Record)
	 * 
	 * @return	"True" if the record was properly written to the file, "False" otherwise.
	 * 
	 * @throws DataWriterException
	 * @throws Exception
	 *
	 */
	public abstract boolean writeLine(String[] line, int type) throws Exception, Exception;
	
	/**
	 * 
	 * <p><b>
	 * Closes the file.
	 * </b></p>
	 * 
	 * @return	"True" if the file was properly closed, "False" otherwise.
	 * 
	 * @throws DataWriterException
	 *
	 */
	public abstract boolean close() throws Exception;
	
	/**
	 * 
	 * <p><b>
	 * This method checks whether the file type is properly set or not.
	 * </b></p>
	 * 
	 * @throws Exception
	 *
	 */
	protected abstract void checkFileType() throws Exception;
	
	/**
	 * 
	 * <p><b>
	 * This method validates the field to check whether it is conforming to the specification.
	 * </b></p>
	 * 
	 * @param		fieldValue		The field that has to be validated
	 * @param		fieldIndex		The index of the current field
	 * @param		lineNo			The current line number
	 * 
	 * @throws DataWriterException
	 * @throws Exception
	 *
	 */
	protected void validateField(String fieldValue, int fieldIndex, int lineNo) throws Exception
	{
		if(fieldValue == null)
		    return;
		
	    String columnNames[] = fileFormat.getFileColumnNames();
		
		//Check if the maxlength is defined and if the length is not exceeded
		if(fileFormat.getColMaxLength() != null && fileFormat.getColMaxLength().length >= fieldIndex && fieldValue.length() > fileFormat.getColMaxLength()[fieldIndex])
			throw new Exception("The field \"" + columnNames[fieldIndex] + "\" in line no. " + lineNo + " has length more than the maximum length allowed.");
		
		//Check if the minlength is defined and if the length is not exceeded
		if(fileFormat.getColMinLength() != null && fileFormat.getColMinLength().length < fieldIndex && fieldValue.length() < fileFormat.getColMinLength()[fieldIndex])
			throw new Exception("The field \"" + columnNames[fieldIndex] + "\" in line no. " + lineNo + " has length less than the minimum length allowed.");
	}

}