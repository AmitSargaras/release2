/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.batch.common.filereader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.validator.routines.DateValidator;
import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.batch.common.datafileparser.Column;
import com.integrosys.cms.batch.common.datafileparser.DataFile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Reader for CSV file.
 * 
 * @author $Author: kltan, pctan $<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name: $
 */
public class CSVReader implements ICMSConstant{
	static Locale locale;
	boolean validationStatus = true;
	HashMap errorHm = new HashMap();
	private int maxRowCount = 0;
	String errorCode = "";
	private boolean isCheckSumFooter = false;
	/*Added by archana for USer upload - start*/
	private boolean userMasterValidationStatus = true ;
	private String secondaryDelimiter;
	/*Added by archana for USer upload - end*/
	public static void setLocale(Locale loca)
	{
		 locale = loca;	
	}
	
	public void readCSV(ProcessDataFile dd) {
		try {

			BufferedReader in = new BufferedReader(new FileReader(dd.dataFilePath));
			String str;
			Vector vectorHeader = new Vector();
//			Vector vectorDataType=new Vector();
			ArrayList maxHolder = new ArrayList();
			int maxCount = 0;

			int count = 0;
			
			while ((str = in.readLine()) != null) {
				if (count == dd.rowHeaderIndex) {
					vectorHeader = csvToVector(str, dd.delimiter);
					count++;
					continue;
				}/*else if(count == dd.dataTypeRowIndex){
					vectorDataType = csvToVector(str, dd.delimiter);
					count++;
					continue;
				}*/
				else if (count < dd.rowStartIndex) {
					count++;
					continue;
				}
				else if ((count > dd.rowEndIndex) && (dd.rowEndIndex != 0)) {
					break;
				}

				if (str.equals("")) {
					count++;
					continue;
				}

				HashMap hm = new HashMap();
				Vector vValus = csvToVector(str, dd.delimiter);
//				boolean validationStatus = true;
				if (dd.columnsIndex == null) {
					if (vValus.size() == vectorHeader.size()) {
						for (int i = dd.columnStartIndex; i < vectorHeader.size(); i++) {

							// System.out.println("header for "+i+", value = "+
							// vValus.elementAt(i));
							
							/*if(vectorDataType.elementAt(i).equals("ALPHABETIC")){
								boolean status = Validator.checkStringWithNoSpecialCharsAndSpace((String) vValus.elementAt(i), true, 1, 10).equals(Validator.ERROR_NONE);
								if (!status)
									validationStatus = false;
							}
							else if(vectorDataType.elementAt(i).equals("NUMERIC")){
								boolean status = Validator.checkNumericString((String) vValus.elementAt(i), true, 1, 10).equals(Validator.ERROR_NONE);
								if (!status)
									validationStatus = false;

							}*/
							hm.put(((String)vectorHeader.elementAt(i)).toUpperCase(), vValus.elementAt(i));
						}
					}
				}
				else {
					StringTokenizer st = new StringTokenizer(dd.columnsIndex, ",");
					while (st.hasMoreTokens()) {
						int i = Integer.parseInt(st.nextToken());

						// System.out.println("data for "+i+", value = "+vValus.
						// elementAt(i));
						/*if(vectorDataType.elementAt(i).equals("ALPHABETIC")){
							boolean status = Validator.checkStringWithNoSpecialCharsAndSpace((String) vValus.elementAt(i), true, 1, 10).equals(Validator.ERROR_NONE);
							if (!status)
								validationStatus = false;
						}
						else if(vectorDataType.elementAt(i).equals("NUMERIC")){
							boolean status = Validator.checkNumericString((String) vValus.elementAt(i), true, 1, 10).equals(Validator.ERROR_NONE);
							if (!status)
								validationStatus = false;

						}*/
						hm.put(((String)vectorHeader.elementAt(i)).toUpperCase(), vValus.elementAt(i));
					}
				}
//				if(validationStatus){
				if (dd.maxRecProcess == null) {
					dd.rowArray.add(hm);
				}
				else if (dd.maxRecProcess.intValue() >= maxCount) {
					dd.rowArray.add(maxHolder);
					maxCount = 0;
					maxHolder = new ArrayList();
				}
				if (dd.maxRecProcess != null) {
					maxHolder.add(hm);
					maxCount++;
				}
//				}
				count++;
			}// end while
			in.close();
			if ((dd.maxRecProcess != null) && !maxHolder.isEmpty()) {
				dd.rowArray.add(maxHolder);
			}

			// System.out.println("successful insert csv data into array...");
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			e.printStackTrace();
		}
	}

	public Vector csvToVector(String row, String delimStr) {
		int c;
		java.util.Vector v;
		boolean doubleQoutedFieldStarted;
		String fieldValue;
		v = new java.util.Vector();
		doubleQoutedFieldStarted = false;
		fieldValue = "";
		c = 0;

		char delim = delimStr.charAt(0);

		while (c < row.length()) {
			L424: {
				if (row.charAt(c) == 34) {
					if (((c + 1) < row.length()) && (row.charAt(c + 1) == 34)) {
						if (((c + 2) < row.length()) && (row.charAt(c + 2) == 34)) {
							if (doubleQoutedFieldStarted == false) {
								doubleQoutedFieldStarted = true;
							}
							else {
								doubleQoutedFieldStarted = false;
							}
							fieldValue = new StringBuffer().append(fieldValue).append(row.charAt(c + 2)).toString();
							c = c + 2;
						}
						else {
							if (((c + 2) == row.length()) || (((c + 2) < row.length()) && (row.charAt(c + 2) == delim))) {
								if (fieldValue.equals("")) {
									if (doubleQoutedFieldStarted == false) {
										doubleQoutedFieldStarted = true;
									}
									else {
										doubleQoutedFieldStarted = false;
									}
								}
								else {
									fieldValue = new StringBuffer().append(fieldValue).append(row.charAt(c)).toString();
								}
								break L424;
							}
							fieldValue = new StringBuffer().append(fieldValue).append(row.charAt(c + 1)).toString();
							c++;
						}
					}
					else {
						if (doubleQoutedFieldStarted) {
							if (((c + 1) == row.length()) || (((c + 1) < row.length()) && (row.charAt(c + 1) == delim))) {
								doubleQoutedFieldStarted = false;
							}
							else {
								fieldValue = new StringBuffer().append(fieldValue).append(row.charAt(c)).toString();
							}
							break L424;
						}
						doubleQoutedFieldStarted = true;
					}
					break L424;
				}
				if (row.charAt(c) == delim) {
					if (doubleQoutedFieldStarted) {
						fieldValue = new StringBuffer().append(fieldValue).append(row.charAt(c)).toString();
					}
					else {
						// System.out.println("fieldValue="+fieldValue);

						v.addElement(fieldValue);
						fieldValue = "";
					}
					break L424;
				}
				fieldValue = new StringBuffer().append(fieldValue).append(row.charAt(c)).toString();
			}
			c++;
		}// end while
		v.addElement(fieldValue);

		return v;
	}

	
	// Read File from UI
	public void readFileCSV(FormFile fileUpload, ProcessDataFile dd,DataFile dataFile,String master) {
		try {
			BufferedReader count_in=null;
			BufferedReader in=null;
			if(fileUpload==null)
			{
			count_in=new BufferedReader(new FileReader("D:\\testdata\\UBS.CSV"));
			}
			else
			{
			count_in = new BufferedReader(new InputStreamReader(fileUpload.getInputStream()));
			}
			String str;
			ArrayList maxHolder = new ArrayList();
			int maxCount = 0;
			int lastRowCount = 0;
			long rowCheckSumTotal = 0;
			//Get row count of Last record
			while ((str = count_in.readLine()) != null) {
				lastRowCount++;
			}
			if(fileUpload==null)
			{
			in=new BufferedReader(new FileReader("D:\\testdata\\UBS.CSV"));
			}
			else
			{
			in = new BufferedReader(new InputStreamReader(fileUpload.getInputStream()));
			}
			int count = 0;
			
			while ((str = in.readLine()) != null) {
				
				String [][]errorList = new String[dataFile.getHeader().getColumn().length][4];
				HashMap hm = new HashMap();
				Vector vValus = csvToVector(str, dataFile.getDelimiter());
				long rowCheckSum = 0;
							
				if(!master.equals(EXCHANGE_RATE_UPLOAD) && count == lastRowCount-1 && dataFile.getIsFooter()!=1){
					if(master.equals(USER_UPLOAD)){
						secondaryDelimiter = dataFile.getSecondaryDelimiter();
						/*Check if footer identifier and footer data count is correct */
						if(((String)vValus.elementAt(0)).equals(FOOTER_INDENTIFIER) && vValus.size()== 3){
							/*Check if total row count mentioned in the footer is correct and
							/*Check if total of row checksum matches with the checksum mentioned in the footer
							 * */
							if(Long.parseLong((String)vValus.elementAt(1)) == lastRowCount-2
								&& Long.parseLong((String)vValus.elementAt(2)) == rowCheckSumTotal){
								isCheckSumFooter = false;
								
							}else{
								String checked = (String)PropertyManager.getValue("masterupload.footer.required");
								if(checked.equalsIgnoreCase("No")){
									isCheckSumFooter = false;
								}else{
									isCheckSumFooter = true;
									//userMasterValidationStatus = false;
									validationStatus = false;
									errorList[1][0] = "";
									errorList[1][1] = "Footer checksum does not match";
								}
								
							}
							
						}else{
							//To Do - Handle this error condition - Reject this file
							
							userMasterValidationStatus = false;
							validationStatus = false;
							errorList[1][0] = "";
							errorList[1][1] = "Invalid File";
						}
					}else{
						//This footer check sum is for other masters
						isCheckSumFooter = checkSumRowCount(lastRowCount, str);
					}
					count++;
					continue;
				}else if (count < dd.rowStartIndex) {
					count++;
					continue;
				}
				else if ((count > dd.rowEndIndex) && (dd.rowEndIndex != 0)) {
					break;
				}

				if (str.equals("")) {
					validationStatus = false;
					errorList[1][0] = "";
					errorList[1][1] = "Remove all empty line from the file";
					errorHm.put(new Integer(count), errorList);
					count++;
					continue;
				}

				if (vValus.size() == dataFile.getHeader().getColumn().length) {
						Column[] column = dataFile.getHeader().getColumn();
						if(master.equals(USER_UPLOAD)) {
							if(null!=vValus.elementAt(column.length -1 ) && !"".equals(vValus.elementAt(column.length -1 )))
								rowCheckSum = Long.parseLong((String) vValus.elementAt(column.length -1 ));
							else
								rowCheckSum=0;
						}
						for (int i = dd.columnStartIndex; i < column.length; i++) {
								
							 if(column[i].getDataType().equals("ALPHABETIC")){ 
							 //This validation is added to avoid xml file relase. in case of xml, HDFC need to take approval from different levels. to avoid that code level change is performed
							 // this is not good practice. one should do requied change in xml only.
								 if(master.equalsIgnoreCase(UBS_UPLOAD) && column[i].getName().equalsIgnoreCase("LINE_NO")){

									 if (!(errorCode = Validator.checkString((String) vValus.elementAt(i), true, 1, column[i].getLength().intValue())).equals(Validator.ERROR_NONE)) {
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
											//	errorList[i][1] = errorCode;
											errorList[i][1] = "Invalid Entry";
											 errorList[i][2]=(String)vValus.elementAt(i);
											 errorList[i][3]=String.valueOf(count);
										}else{
											hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
										}
								 }
								 else if( column[i].getName().equalsIgnoreCase("NEW_BRANCH_CODE")||column[i].getName().equalsIgnoreCase("DEPARTMENT_CODE")){
									 if(!ASSTValidator.isValidAlphaNumStringWithSpace((String) vValus.elementAt(i))){
										 
										 	if("".equals((String)vValus.elementAt(i))){
										 		hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
										 	}
										 	else if (!(errorCode = Validator.checkString((String) vValus.elementAt(i), true, 0, column[i].getLength().intValue())).equals(Validator.ERROR_NONE)) {
												validationStatus = false;
												errorList[i][0] = ((String)column[i].getName()).toUpperCase();
												// errorList[i][1] = errorCode;
												errorList[i][1] = "Invalid Entry";
												 errorList[i][2]=(String)vValus.elementAt(i);
												 errorList[i][3]=String.valueOf(count);
											}else{
												hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											}
											}else{
												validationStatus = false;
												errorList[i][0] = ((String)column[i].getName()).toUpperCase();
												errorList[i][1] = "Invalid input data - Special Characters not allowed";
											}
								 }
									 
								 else if(!ASSTValidator.isValidAlphaNumStringWithSpace((String) vValus.elementAt(i))){
									 
								 if (!(errorCode = Validator.checkString((String) vValus.elementAt(i), true, 1, column[i].getLength().intValue())).equals(Validator.ERROR_NONE)) {
										validationStatus = false;
										errorList[i][0] = ((String)column[i].getName()).toUpperCase();
										// errorList[i][1] = errorCode;
										errorList[i][1] = "Invalid Entry";
										 errorList[i][2]=(String)vValus.elementAt(i);
										 errorList[i][3]=String.valueOf(count);
									}else{
										hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
									}
									}else{
										validationStatus = false;
										errorList[i][0] = ((String)column[i].getName()).toUpperCase();
										errorList[i][1] = "Invalid input data - Special Characters not allowed";
									}
								 }							
							 else if(column[i].getDataType().equals("ALPHABETIC_WITH_SPACIAL")){	
								  if (!(errorCode = Validator.checkString((String) vValus.elementAt(i), true, 1, column[i].getLength().intValue())).equals(Validator.ERROR_NONE)) {
										validationStatus = false;
										errorList[i][0] = ((String)column[i].getName()).toUpperCase();
//										 errorList[i][1] = errorCode;
										errorList[i][1] = "Invalid Entry";
										 errorList[i][2]=(String)vValus.elementAt(i);
										 errorList[i][3]=String.valueOf(count);
									}else{
										hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
									}
							     }else if(column[i].getDataType().equals("NUMERIC")){
							    	 
							    	 if(column[i].getName().equalsIgnoreCase("USER_ROLE")){
							    		 if("".equals((String)vValus.elementAt(i)) && "U".equalsIgnoreCase((String) vValus.get(1))){
										 		hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
										 }
							    		 else if (!(errorCode = Validator.checkNumber((String) vValus.elementAt(i), true, 0, 9999999999999999.D)).equals(Validator.ERROR_NONE)) {
												validationStatus = false;
												errorList[i][0] = ((String)column[i].getName()).toUpperCase();
	//											 errorList[i][1] = errorCode;
												errorList[i][1] = "Invalid Entry";
												 errorList[i][2]=(String)vValus.elementAt(i);
												 errorList[i][3]=String.valueOf(count);
								    	 }
							    		 else if (vValus.elementAt(i)!=null && "".equals(vValus.elementAt(i))) {
												validationStatus = false;
												errorList[i][0] = ((String)column[i].getName()).toUpperCase();
//												errorList[i][1] = errorCode;
												errorList[i][1] = "Invalid Entry";
												errorList[i][2]=(String)vValus.elementAt(i);
												errorList[i][3]=String.valueOf(count);
										} else{
											hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
										}
							    	 }
							    	 else if (!(errorCode = Validator.checkNumber((String) vValus.elementAt(i), true, 1, 9999999999999999.D)).equals(Validator.ERROR_NONE)) {
										validationStatus = false;
										errorList[i][0] = ((String)column[i].getName()).toUpperCase();
//										 errorList[i][1] = errorCode;
										errorList[i][1] = "Invalid Entry";
										 errorList[i][2]=(String)vValus.elementAt(i);
										 errorList[i][3]=String.valueOf(count);
									}else{
										hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
									}
								}
							 /*	NUMERIC_NON_MANDATORY Added by Sandeep Shinde*/
							     else if(column[i].getDataType().equals("NUMERIC_NON_MANDATORY")){
										if (!(errorCode = Validator.checkInteger((String) vValus.elementAt(i), false, 1, 1999999999)).equals(Validator.ERROR_NONE)) {
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
//											 errorList[i][1] = errorCode;
											errorList[i][1] = "Invalid Entry";
											 errorList[i][2]=(String)vValus.elementAt(i);
											 errorList[i][3]=String.valueOf(count);
										}else{
											hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
										}
									}
								else if(column[i].getDataType().equals("DECIMAL")){
									double maxValue = 0;
									if(column[i].getLength().intValue() == 9){
										maxValue = IGlobalConstant.MAXIMUM_ALLOWED_VALUE_7_2;
									}else if(column[i].getLength().intValue() == 7){
										maxValue = 99999.99;
									}else if(column[i].getLength().intValue() == 5){
										maxValue = IGlobalConstant.MAXIMUM_ALLOWED_VALUE_3_2;
									}else if(column[i].getLength().intValue() == 4){
										maxValue = 99.99;
									}else{
										maxValue = IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2;
									}
									String amount = (String) vValus.elementAt(i);
									if (!(errorCode = Validator.checkAmount(amount, false, 0,maxValue, IGlobalConstant.DEFAULT_CURRENCY, locale))
											.equals(Validator.ERROR_NONE)) {
										if(master.equals(EXCHANGE_RATE_UPLOAD) && errorCode.equals("decimalexceeded") && amount.substring(amount.indexOf(".")).length()-1 <= 4)
										{
											hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
										}else{
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
	//										errorList[i][1] = errorCode;
											errorList[i][1] = "Invalid Entry";
											errorList[i][2]=(String)vValus.elementAt(i);
											errorList[i][3]=String.valueOf(count);
										}
									}else{
										hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
									}
								}
								else if(column[i].getDataType().equals("CONTACT")){
									if(!Validator.ERROR_NONE.equals(errorCode = Validator.checkPhoneNumber(vValus.elementAt(i).toString().trim(),true,locale))) {
										validationStatus = false;
										errorList[i][0] = ((String)column[i].getName()).toUpperCase();
										 errorList[i][1] = "Invalid contact number";
										 errorList[i][2]=(String)vValus.elementAt(i);
										 errorList[i][3]=String.valueOf(count);
									}else{
										hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
									}
								}
							 /*	CONTACT_NON_MANDATORY Added by Sandeep Shinde*/
								else if(column[i].getDataType().equals("CONTACT_NON_MANDATORY")){
									if(!Validator.ERROR_NONE.equals(errorCode = Validator.checkPhoneNumber(vValus.elementAt(i).toString().trim(),false,locale))) {
										validationStatus = false;
										errorList[i][0] = ((String)column[i].getName()).toUpperCase();
										 errorList[i][1] = "Invalid Contact/Fax Number";
										 errorList[i][2]=(String)vValus.elementAt(i);
										 errorList[i][3]=String.valueOf(count);
									}else{
										hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
									}
								}
								else if(column[i].getDataType().equals("EMAIL")){
									if(!ASSTValidator.isValidEmail((String) vValus.elementAt(i))){
									if (!(errorCode = Validator.checkEmail((String) vValus.elementAt(i), true) ).equals(Validator.ERROR_NONE) && vValus.elementAt(i).toString().length()>0) {
										 validationStatus = false;
										 errorList[i][0] = ((String)column[i].getName()).toUpperCase();
										 errorList[i][1] = "Invalid Email";
										 errorList[i][2]=(String)vValus.elementAt(i);
										 errorList[i][3]=String.valueOf(count);
									}else{
										hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
									}
									}else{
										validationStatus = false;
										errorList[i][0] = ((String)column[i].getName()).toUpperCase();
										errorList[i][1] = "Invalid input data - Special Characters not allowed";
									}
								}else if(column[i].getDataType().equals("DATE_FORM")){
									//if(!ASSTValidator.isValidAlphaNumStringWithSpacewWithSlash((String) vValus.elementAt(i))){
									if(EXCHANGE_RATE_UPLOAD.equals(master)?!ASSTValidator.isValidAlphaNumStringWithSpacewWithSlash((String) vValus.elementAt(i)):
										!ASSTValidator.isValidDash((String) vValus.elementAt(i))){
									if(vValus.elementAt(i)==null || vValus.elementAt(i).toString().trim()=="" )	{
										 validationStatus = false;
										 errorList[i][0] = ((String)column[i].getName()).toUpperCase();
										 errorList[i][1] = "Date Mandatory";
										 errorList[i][2]=(String)vValus.elementAt(i);
										 errorList[i][3]=String.valueOf(count);
									} else if (!(errorCode = Validator.checkDate((String) vValus.elementAt(i),true, locale)).equals(Validator.ERROR_NONE)) {
										 validationStatus = false;
										 errorList[i][0] = ((String)column[i].getName()).toUpperCase();
										 errorList[i][1] = "Invalid Date";
										 errorList[i][2]=(String)vValus.elementAt(i);
										 errorList[i][3]=String.valueOf(count);
									}else{
										hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
									}
									}else{
										validationStatus = false;
										errorList[i][0] = ((String)column[i].getName()).toUpperCase();
										errorList[i][1] = "Invalid input data - Special Characters not allowed";
									}
							 }else if(column[i].getDataType().equals("DATE_FORM_NON_MANDATORY")){
									if(!ASSTValidator.isValidDash((String) vValus.elementAt(i))){
										if (!(errorCode = Validator.checkDate((String) vValus.elementAt(i),false, locale)).equals(Validator.ERROR_NONE)) {
										 validationStatus = false;
										 errorList[i][0] = ((String)column[i].getName()).toUpperCase();
										 errorList[i][1] = "Invalid Date";
										 errorList[i][2]=(String)vValus.elementAt(i);
										 errorList[i][3]=String.valueOf(count);
										}else{
										hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
										}
									}else{
										validationStatus = false;
										errorList[i][0] = ((String)column[i].getName()).toUpperCase();
										errorList[i][1] = "Invalid input data - Special Characters not allowed";
									}
							 }else if(column[i].getDataType().equals("ADDRESS")||column[i].getDataType().equals("ADDRESS_NON_MANDATORY")){ 
								 boolean mandatoryFlag = true;
								 	if(column[i].getDataType().equals("ADDRESS_NON_MANDATORY")){
								 		mandatoryFlag = false;
								 		}
										 if (!(errorCode = Validator.checkString((String) vValus.elementAt(i), mandatoryFlag, 1, column[i].getLength().intValue())).equals(Validator.ERROR_NONE)) {
												validationStatus = false;
												errorList[i][0] = ((String)column[i].getName()).toUpperCase();
												// errorList[i][1] = errorCode;
												errorList[i][1] = "Invalid Entry";
												 errorList[i][2]=(String)vValus.elementAt(i);
												 errorList[i][3]=String.valueOf(count);
											}else{
												hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											}
							}else if(column[i].getDataType().equals("ALPHABETIC_OR_EMPTY")){ 
								 if(!ASSTValidator.isValidAlphaNumStringWithSpace((String) vValus.elementAt(i))){	
									 if (!(errorCode = Validator.checkString((String) vValus.elementAt(i), false, 1, column[i].getLength().intValue())).equals(Validator.ERROR_NONE)) {
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
											//errorList[i][1] = errorCode;
											errorList[i][1] = "Invalid Entry";
											 errorList[i][2]=(String)vValus.elementAt(i);
											 errorList[i][3]=String.valueOf(count);
										}else{
											hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
										}
										}else{
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
											errorList[i][1] = "Invalid input data - Special Characters not allowed";
										}
							       }
						
						}
					}else{
						//To do - reject this record
						validationStatus = false;
						//userMasterValidationStatus = false;
						errorList[1][0] = "";
						errorList[1][1] = "Number of columns is not as per template";
						 errorList[1][2]=(String)vValus.elementAt(2);
					}

				if(!validationStatus && errorList!=null) {
					errorHm.put(new Integer(count), errorList);
				}//Make it else if
				//Govind S: Blow commented code replace with nested if condition.
//				if (validationStatus && dd.maxRecProcess == null) {
//					dd.rowArray.add(hm);
//				}
				if( dd.maxRecProcess == null)
				{
					if (validationStatus) {
						dd.rowArray.add(hm);
					}
					
					if(master.equals(USER_UPLOAD)||master.equals(UBS_UPLOAD)||master.equals(FINWARE_UPLOAD)||master.equals(FINWAREFD_UPLOAD)||master.equals(EXCHANGE_RATE_UPLOAD)||master.equals(STOCK_ITEM_UPLOAD)||master.equals(BOND_ITEM_UPLOAD)||master.equals(MUTUAL_FUNDS_ITEM_UPLOAD)){
						validationStatus=true;
						
					}
				}
				else if (dd.maxRecProcess.intValue() >= maxCount) {
					dd.rowArray.add(maxHolder);
					maxCount = 0;
					maxHolder = new ArrayList();
				}
				if (dd.maxRecProcess != null) {
					maxHolder.add(hm);
					maxCount++;
				}
				count++;
				rowCheckSumTotal = rowCheckSumTotal+ rowCheckSum;
			}// end while
			maxRowCount = count;
			
			in.close();
			if ((dd.maxRecProcess != null) && !maxHolder.isEmpty()) {
				if(validationStatus) {
					dd.rowArray.add(maxHolder);
				}else{
					dd.rowArray.add(maxHolder);
				}
			}

			// System.out.println("successful insert csv data into array...");
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			e.printStackTrace();
			throw new ProcessFileException(e.getMessage());
		}
	}
	
	public boolean checkSumRowCount(int lastRowCount,String row) {
		String checked = (String)PropertyManager.getValue("masterupload.footer.required");
		if(checked.equalsIgnoreCase("No")){
			return false;
		}
		String []footerKeyValue = row.split("|");
		try{
			int footerValue = Integer.parseInt(footerKeyValue[1].trim());
			if(!footerKeyValue[0].trim().equalsIgnoreCase("FOOTER") || lastRowCount-2 != Integer.parseInt(footerKeyValue[1].trim())){
				return true;
			}
		}catch(Exception e){
			return true;
		}
		return false;
	}

	public boolean isValidationStatus() {
		return validationStatus;
	}

	public void setValidationStatus(boolean validationStatus) {
		this.validationStatus = validationStatus;
	}

	public HashMap getErrorHm() {
		return errorHm;
	}

	public void setErrorHm(HashMap errorHm) {
		this.errorHm = errorHm;
	}

	public int getMaxRowCount() {
		return maxRowCount;
	}

	public void setMaxRowCount(int maxRowCount) {
		this.maxRowCount = maxRowCount;
	}

	public boolean isCheckSumFooter() {
		return isCheckSumFooter;
	}

	public void setCheckSumFooter(boolean isCheckSumFooter) {
		this.isCheckSumFooter = isCheckSumFooter;
	}
	/*Added by archana for USer upload - start*/
	/**
	 * @return the userMasterValidationStatus
	 */
	public boolean isUserMasterValidationStatus() {
		return userMasterValidationStatus;
	}

	/**
	 * @param userMasterValidationStatus the userMasterValidationStatus to set
	 */
	public void setUserMasterValidationStatus(boolean userMasterValidationStatus) {
		this.userMasterValidationStatus = userMasterValidationStatus;
	}

	/**
	 * @return the secondaryDelimiter
	 */
	public String getSecondaryDelimiter() {
		return secondaryDelimiter;
	}

	/**
	 * @param secondaryDelimiter the secondaryDelimiter to set
	 */
	public void setSecondaryDelimiter(String secondaryDelimiter) {
		this.secondaryDelimiter = secondaryDelimiter;
	}
	/*Added by archana for USer upload - End*/
	
	public void readFileCSV(File fileUpload, ProcessDataFile dd,DataFile dataFile,String master) {
		System.out.println("Inside ....readFileCSV method.. ");
		try {
			BufferedReader count_in=null;
			BufferedReader in=null;
			if(fileUpload==null)
			{
			count_in=new BufferedReader(new FileReader("D:\\testdata\\UBS.CSV"));
			}
			else
			{			
			count_in = new BufferedReader(new InputStreamReader(new FileInputStream(fileUpload)));
			
			}
			String str;
			ArrayList maxHolder = new ArrayList();
			int maxCount = 0;
			int lastRowCount = 0;
			long rowCheckSumTotal = 0;
			//Get row count of Last record
			while ((str = count_in.readLine()) != null) {
				lastRowCount++;
			}
			if(fileUpload==null)
			{
			in=new BufferedReader(new FileReader("D:\\testdata\\UBS.CSV"));
			}
			else
			{
			in = new BufferedReader(new InputStreamReader(new FileInputStream(fileUpload)));
			}
			int count = 0;
			
			while ((str = in.readLine()) != null) {
				
				String [][]errorList = new String[dataFile.getHeader().getColumn().length][4];
				HashMap hm = new HashMap();
				Vector vValus = csvToVector(str, dataFile.getDelimiter());
				long rowCheckSum = 0;
							
				if(count == lastRowCount-1 && dataFile.getIsFooter()!=1){
					if(master.equals(USER_UPLOAD)){
						secondaryDelimiter = dataFile.getSecondaryDelimiter();
						/*Check if footer identifier and footer data count is correct */
						if(((String)vValus.elementAt(0)).equals(FOOTER_INDENTIFIER) && vValus.size()== 3){
							/*Check if total row count mentioned in the footer is correct and
							/*Check if total of row checksum matches with the checksum mentioned in the footer
							 * */
							if(Long.parseLong((String)vValus.elementAt(1)) == lastRowCount-2
								&& Long.parseLong((String)vValus.elementAt(2)) == rowCheckSumTotal){
								isCheckSumFooter = false;
								
							}else{
								String checked = (String)PropertyManager.getValue("masterupload.footer.required");
								if(checked.equalsIgnoreCase("No")){
									isCheckSumFooter = false;
								}else{
									isCheckSumFooter = true;
									//userMasterValidationStatus = false;
									validationStatus = false;
									errorList[1][0] = "";
									errorList[1][1] = "Footer checksum does not match";
								}
								
							}
							
						}else{
							//To Do - Handle this error condition - Reject this file
							
							userMasterValidationStatus = false;
							validationStatus = false;
							errorList[1][0] = "";
							errorList[1][1] = "Invalid File";
						}
					}else{
						//This footer check sum is for other masters
						isCheckSumFooter = checkSumRowCount(lastRowCount, str);
					}
					count++;
					continue;
				}else if (count < dd.rowStartIndex) {
					count++;
					continue;
				}
				else if ((count > dd.rowEndIndex) && (dd.rowEndIndex != 0)) {
					break;
				}

				if (str.equals("")) {
					validationStatus = false;
					errorList[1][0] = "";
					errorList[1][1] = "Remove all empty line from the file";
					errorHm.put(new Integer(count), errorList);
					count++;
					continue;
				}

				
					if (vValus.size() == dataFile.getHeader().getColumn().length) {
						Column[] column = dataFile.getHeader().getColumn();
						if(master.equals(USER_UPLOAD))
							rowCheckSum = Long.parseLong((String) vValus.elementAt(column.length -1 ));
						for (int i = dd.columnStartIndex; i < column.length; i++) {
								
							 if(column[i].getDataType().equals("ALPHABETIC")){ 
							 //This validation is added to avoid xml file relase. in case of xml, HDFC need to take approval from different levels. to avoid that code level change is performed
							 // this is not good practice. one should do requied change in xml only.
								 if((column[i].getName().equalsIgnoreCase("LINE_NO") || column[i].getName().equalsIgnoreCase("CUSTOMER_ID") || column[i].getName().equalsIgnoreCase("CURRENCY_CODE"))){

									 if (!(errorCode = Validator.checkString((String) vValus.elementAt(i), true, 1, column[i].getLength().intValue())).equals(Validator.ERROR_NONE)) {
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
											//	errorList[i][1] = errorCode;
											errorList[i][1] = "Invalid Entry";
											 errorList[i][2]=(String)vValus.elementAt(i);
											 errorList[i][3]=String.valueOf(count);
										}else{
											hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
										}
								 }
								 
								 else{
									 if((errorCode = Validator.checkString((String) vValus.elementAt(i), true, 1, column[i].getLength().intValue())).equals(Validator.ERROR_NONE)){
										 if(ASSTValidator.isAlphaNumeric((String) vValus.elementAt(i))){
											 hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
									 }
									 //for RAM RATING CR	 
									 else if(column[i].getName().equalsIgnoreCase("LADCODE")) {
										  if(ASSTValidator.isAlphaNumericWithDash((String) vValus.elementAt(i))) {
											  hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
										  }
									 }else if(column[i].getName().equalsIgnoreCase("REMARKS")) {
										  //if(ASSTValidator.isAlphaNumericWithSpace((String) vValus.elementAt(i))) {
											  hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
										  //}
									  }
									 //end RAM RATING	 
									 else{
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
											//	errorList[i][1] = errorCode;
											errorList[i][1] = "Invalid Entry";
											 errorList[i][2]=(String)vValus.elementAt(i);
											 errorList[i][3]=String.valueOf(count); 
									  }
									 }
									 else{
										 if("Discrepancy".equals((String)vValus.elementAt(0))) {
											 if(("CHECKLISTID".equals((String)column[i].getName()) || 
													 "DOCID".equals((String)column[i].getName()) ||
													 "LADCODE".equals((String)column[i].getName()) ||
													 "REMARKS".equals((String)column[i].getName())) 
													 && "".equals(vValus.elementAt(i))){
												 hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											 }else {
												validationStatus = false;
												errorList[i][0] = ((String)column[i].getName()).toUpperCase();
												errorList[i][1] = "Invalid Entry";
												errorList[i][2]=(String)vValus.elementAt(i);
												errorList[i][3]=String.valueOf(count);
											 }
										 }else  if("OtherDocument".equals((String)vValus.elementAt(0))
												 || "CAM".equals((String)vValus.elementAt(0))
												 || "Security".equals((String)vValus.elementAt(0))
												 || "Facility".equals((String)vValus.elementAt(0))
												 || "RecurrentDoc".equals((String)vValus.elementAt(0))) {
											 if(("DISCREPANCYID".equals((String)column[i].getName()) || 
													 "LADCODE".equals((String)column[i].getName()) ||
													 "REMARKS".equals((String)column[i].getName())) 
													 && "".equals(vValus.elementAt(i))){
												 hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											 }else {
												validationStatus = false;
												errorList[i][0] = ((String)column[i].getName()).toUpperCase();
												errorList[i][1] = "Invalid Entry";
												errorList[i][2]=(String)vValus.elementAt(i);
												errorList[i][3]=String.valueOf(count);
											 }
										 }else  if("LAD".equals((String)vValus.elementAt(0))) {
											 if(("DISCREPANCYID".equals((String)column[i].getName()) || 
													 "DOCID".equals((String)column[i].getName()) ||
													 "REMARKS".equals((String)column[i].getName())) 
													 && "".equals(vValus.elementAt(i))){
												 hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											 }else {
												validationStatus = false;
												errorList[i][0] = ((String)column[i].getName()).toUpperCase();
												errorList[i][1] = "Invalid Entry";
												errorList[i][2]=(String)vValus.elementAt(i);
												errorList[i][3]=String.valueOf(count);
											 }
										 }else {
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
											errorList[i][1] = "Invalid Entry";
											errorList[i][2]=(String)vValus.elementAt(i);
											errorList[i][3]=String.valueOf(count);  
										 }
									 }
								 }
								/* else if( column[i].getName().equalsIgnoreCase("NEW_BRANCH_CODE")||column[i].getName().equalsIgnoreCase("DEPARTMENT_CODE")){
									 if(!ASSTValidator.isValidAlphaNumStringWithSpace((String) vValus.elementAt(i))){
										 
										 	if("".equals((String)vValus.elementAt(i))){
										 		hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
										 	}
										 	else if (!(errorCode = Validator.checkString((String) vValus.elementAt(i), true, 0, column[i].getLength().intValue())).equals(Validator.ERROR_NONE)) {
												validationStatus = false;
												errorList[i][0] = ((String)column[i].getName()).toUpperCase();
												// errorList[i][1] = errorCode;
												errorList[i][1] = "Invalid Entry";
												 errorList[i][2]=(String)vValus.elementAt(i);
												 errorList[i][3]=String.valueOf(count);
											}else{
												hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											}
											}else{
												validationStatus = false;
												errorList[i][0] = ((String)column[i].getName()).toUpperCase();
												errorList[i][1] = "Invalid input data - Special Characters not allowed";
											}
								 }*/
									 
								/* else if(!ASSTValidator.isValidAlphaNumStringWithSpace((String) vValus.elementAt(i))){
									 
								 if (!(errorCode = Validator.checkString((String) vValus.elementAt(i), true, 1, column[i].getLength().intValue())).equals(Validator.ERROR_NONE)) {
										validationStatus = false;
										errorList[i][0] = ((String)column[i].getName()).toUpperCase();
										// errorList[i][1] = errorCode;
										errorList[i][1] = "Invalid Entry";
										 errorList[i][2]=(String)vValus.elementAt(i);
										 errorList[i][3]=String.valueOf(count);
									}else{
										hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
									}
									}else{
										validationStatus = false;
										errorList[i][0] = ((String)column[i].getName()).toUpperCase();
										errorList[i][1] = "Invalid input data - Special Characters not allowed";
									}*/
								 }							
							/* else if(column[i].getDataType().equals("ALPHABETIC_WITH_SPACIAL")){	
								  if (!(errorCode = Validator.checkString((String) vValus.elementAt(i), true, 1, column[i].getLength().intValue())).equals(Validator.ERROR_NONE)) {
										validationStatus = false;
										errorList[i][0] = ((String)column[i].getName()).toUpperCase();
//										 errorList[i][1] = errorCode;
										errorList[i][1] = "Invalid Entry";
										 errorList[i][2]=(String)vValus.elementAt(i);
										 errorList[i][3]=String.valueOf(count);
									}else{
										hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
									}
							     }*/
							 else if(column[i].getDataType().equals("NUMERIC")){
							    	 
							    	 if(column[i].getName().equalsIgnoreCase("USER_ROLE") || column[i].getName().equalsIgnoreCase("LIMIT_AMOUNT") ||
							    			 column[i].getName().equalsIgnoreCase("UTILIZATION_AMOUNT")){
							    		 /*if("".equals((String)vValus.elementAt(i))){
										 		hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
										 }
							    		 else */
							    		if (!(errorCode = Validator.checkNumber((String) vValus.elementAt(i), true, 0, 9999999999999999.D)).equals(Validator.ERROR_NONE)) {
												validationStatus = false;
												errorList[i][0] = ((String)column[i].getName()).toUpperCase();
	//											 errorList[i][1] = errorCode;
												errorList[i][1] = "Invalid Entry";
												 errorList[i][2]=(String)vValus.elementAt(i);
												 errorList[i][3]=String.valueOf(count);
								    	 }
							    		 else if (vValus.elementAt(i)!=null && "".equals(vValus.elementAt(i))) {
												validationStatus = false;
												errorList[i][0] = ((String)column[i].getName()).toUpperCase();
//												errorList[i][1] = errorCode;
												errorList[i][1] = "Invalid Entry";
												errorList[i][2]=(String)vValus.elementAt(i);
												errorList[i][3]=String.valueOf(count);
									  }else{
											hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
										}
							    	 }
							    	 else if (!(errorCode = Validator.checkNumber((String) vValus.elementAt(i), true, 1, 9999999999999999.D)).equals(Validator.ERROR_NONE)) {
										validationStatus = false;
										errorList[i][0] = ((String)column[i].getName()).toUpperCase();
//										 errorList[i][1] = errorCode;
										errorList[i][1] = "Invalid Entry";
										 errorList[i][2]=(String)vValus.elementAt(i);
										 errorList[i][3]=String.valueOf(count);
									}else{
										hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
									}
								}
							 
							 //Start:Added by Uma Khot for Basel Upload on 28 OCt 2015
							 else if(column[i].getDataType().equals("NUMERIC_EMPTY_NEGATIVE_VALUE")){

					    		if (!(errorCode = Validator.checkNumber((String) vValus.elementAt(i), true, 0, 9999999999999999.D)).equals(Validator.ERROR_NONE)) {
					    			
					    			if(((String) vValus.elementAt(i)).trim().isEmpty()){
					    					hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
					    			}
					    			else if(((String) vValus.elementAt(i)).trim().startsWith("-")){
					    				hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
					    			}
					    			else{

					    				validationStatus = false;
										errorList[i][0] = ((String)column[i].getName()).toUpperCase();
//											 errorList[i][1] = errorCode;
										errorList[i][1] = "Invalid Entry";
										 errorList[i][2]=(String)vValus.elementAt(i);
										 errorList[i][3]=String.valueOf(count);
					    				
						    	 }
					    		}
					    		/* else if (vValus.elementAt(i)!=null && "".equals(vValus.elementAt(i))) {
										validationStatus = false;
										errorList[i][0] = ((String)column[i].getName()).toUpperCase();
//										errorList[i][1] = errorCode;
										errorList[i][1] = "Invalid Entry";
										errorList[i][2]=(String)vValus.elementAt(i);
										errorList[i][3]=String.valueOf(count);
							  }*/else{
									hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
								}
					    	 }
						 
							//End:Added by Uma Khot for Basel Upload on 28 OCt 2015
							 
							 
							 /*	NUMERIC_NON_MANDATORY Added by Sandeep Shinde*/
							    /* else if(column[i].getDataType().equals("NUMERIC_NON_MANDATORY")){
										if (!(errorCode = Validator.checkInteger((String) vValus.elementAt(i), false, 1, 1999999999)).equals(Validator.ERROR_NONE)) {
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
//											 errorList[i][1] = errorCode;
											errorList[i][1] = "Invalid Entry";
											 errorList[i][2]=(String)vValus.elementAt(i);
											 errorList[i][3]=String.valueOf(count);
										}else{
											hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
										}
									}*/
								else if(column[i].getDataType().equals("DECIMAL")){
									double maxValue = 0;
									if(column[i].getLength().intValue() == 9){
										maxValue = IGlobalConstant.MAXIMUM_ALLOWED_VALUE_7_2;
									}else if(column[i].getLength().intValue() == 7){
										maxValue = 99999.99;
									}else if(column[i].getLength().intValue() == 5){
										maxValue = IGlobalConstant.MAXIMUM_ALLOWED_VALUE_3_2;
									}else if(column[i].getLength().intValue() == 4){
										maxValue = 99.99;
									}else{
										maxValue = IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2;
									}
									
									if (!(errorCode = Validator.checkAmount((String) vValus.elementAt(i), false, 0,maxValue, IGlobalConstant.DEFAULT_CURRENCY,  new Locale("en")))
											.equals(Validator.ERROR_NONE)) {
										validationStatus = false;
										errorList[i][0] = ((String)column[i].getName()).toUpperCase();
//										errorList[i][1] = errorCode;
										errorList[i][1] = "Invalid Entry";
										errorList[i][2]=(String)vValus.elementAt(i);
										errorList[i][3]=String.valueOf(count);
									}
									else if (vValus.elementAt(i)!=null && (((String)vValus.elementAt(i)).isEmpty() || ((String)vValus.elementAt(i)).contains("\t")) ){
										validationStatus = false;
										errorList[i][0] = ((String)column[i].getName()).toUpperCase();
//										errorList[i][1] = errorCode;
										errorList[i][1] = "Invalid Entry";
										errorList[i][2]=(String)vValus.elementAt(i);
										errorList[i][3]=String.valueOf(count);
									}else{
										hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
									}
								}
								else if(column[i].getDataType().equals("CONTACT")){
									if(!Validator.ERROR_NONE.equals(errorCode = Validator.checkPhoneNumber(vValus.elementAt(i).toString().trim(),true,locale))) {
										validationStatus = false;
										errorList[i][0] = ((String)column[i].getName()).toUpperCase();
										 errorList[i][1] = "Invalid contact number";
										 errorList[i][2]=(String)vValus.elementAt(i);
										 errorList[i][3]=String.valueOf(count);
									}else{
										hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
									}
								}
							 /*	CONTACT_NON_MANDATORY Added by Sandeep Shinde*/
								else if(column[i].getDataType().equals("CONTACT_NON_MANDATORY")){
									if(!Validator.ERROR_NONE.equals(errorCode = Validator.checkPhoneNumber(vValus.elementAt(i).toString().trim(),false,locale))) {
										validationStatus = false;
										errorList[i][0] = ((String)column[i].getName()).toUpperCase();
										 errorList[i][1] = "Invalid Contact/Fax Number";
										 errorList[i][2]=(String)vValus.elementAt(i);
										 errorList[i][3]=String.valueOf(count);
									}else{
										hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
									}
								}
								else if(column[i].getDataType().equals("EMAIL")){
									if(!ASSTValidator.isValidEmail((String) vValus.elementAt(i))){
									if (!(errorCode = Validator.checkEmail((String) vValus.elementAt(i), true) ).equals(Validator.ERROR_NONE) && vValus.elementAt(i).toString().length()>0) {
										 validationStatus = false;
										 errorList[i][0] = ((String)column[i].getName()).toUpperCase();
										 errorList[i][1] = "Invalid Email";
										 errorList[i][2]=(String)vValus.elementAt(i);
										 errorList[i][3]=String.valueOf(count);
									}else{
										hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
									}
									}else{
										validationStatus = false;
										errorList[i][0] = ((String)column[i].getName()).toUpperCase();
										errorList[i][1] = "Invalid input data - Special Characters not allowed";
									}
								}else if(column[i].getDataType().equals("DATE_FORM")){
									//if(!ASSTValidator.isValidAlphaNumStringWithSpacewWithSlash((String) vValus.elementAt(i))){
									if(!ASSTValidator.isValidDash((String) vValus.elementAt(i))){
									if(vValus.elementAt(i)==null || vValus.elementAt(i).toString().trim()=="" )	{
										 validationStatus = false;
										 errorList[i][0] = ((String)column[i].getName()).toUpperCase();
										 errorList[i][1] = "Date Mandatory";
										 errorList[i][2]=(String)vValus.elementAt(i);
										 errorList[i][3]=String.valueOf(count);
									} else if (!(errorCode = Validator.checkDate((String) vValus.elementAt(i),true, locale)).equals(Validator.ERROR_NONE)) {
										 validationStatus = false;
										 errorList[i][0] = ((String)column[i].getName()).toUpperCase();
										 errorList[i][1] = "Invalid Date";
										 errorList[i][2]=(String)vValus.elementAt(i);
										 errorList[i][3]=String.valueOf(count);
									}else{
										hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
									}
									}else{
										validationStatus = false;
										errorList[i][0] = ((String)column[i].getName()).toUpperCase();
										errorList[i][1] = "Invalid input data - Special Characters not allowed";
									}
							 }else if(column[i].getDataType().equals("DATE_FORM_NON_MANDATORY")){
									if(!ASSTValidator.isValidDash((String) vValus.elementAt(i))){
										if (!(errorCode = Validator.checkDate((String) vValus.elementAt(i),false, locale)).equals(Validator.ERROR_NONE)) {
										 validationStatus = false;
										 errorList[i][0] = ((String)column[i].getName()).toUpperCase();
										 errorList[i][1] = "Invalid Date";
										 errorList[i][2]=(String)vValus.elementAt(i);
										 errorList[i][3]=String.valueOf(count);
										}else{
										hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
										}
									}else{
										validationStatus = false;
										errorList[i][0] = ((String)column[i].getName()).toUpperCase();
										errorList[i][1] = "Invalid input data - Special Characters not allowed";
									}
							 }else if(column[i].getDataType().equals("ADDRESS")||column[i].getDataType().equals("ADDRESS_NON_MANDATORY")){ 
								 boolean mandatoryFlag = true;
								 	if(column[i].getDataType().equals("ADDRESS_NON_MANDATORY")){
								 		mandatoryFlag = false;
								 		}
										 if (!(errorCode = Validator.checkString((String) vValus.elementAt(i), mandatoryFlag, 1, column[i].getLength().intValue())).equals(Validator.ERROR_NONE)) {
												validationStatus = false;
												errorList[i][0] = ((String)column[i].getName()).toUpperCase();
												// errorList[i][1] = errorCode;
												errorList[i][1] = "Invalid Entry";
												 errorList[i][2]=(String)vValus.elementAt(i);
												 errorList[i][3]=String.valueOf(count);
											}else{
												hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											}
							}else if(column[i].getDataType().equals("ALPHABETIC_OR_EMPTY")){ 
								 if(!ASSTValidator.isValidAlphaNumStringWithSpace((String) vValus.elementAt(i))){	
									 if (!(errorCode = Validator.checkString((String) vValus.elementAt(i), false, 1, column[i].getLength().intValue())).equals(Validator.ERROR_NONE)) {
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
											//errorList[i][1] = errorCode;
											errorList[i][1] = "Invalid Entry";
											 errorList[i][2]=(String)vValus.elementAt(i);
											 errorList[i][3]=String.valueOf(count);
										}else{
											hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
										}
										}else{
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
											errorList[i][1] = "Invalid input data - Special Characters not allowed";
										}
							 }
							 
							 //Added for FD upload
							else if("DATE".equals(column[i].getDataType())){
								//if(!ASSTValidator.isValidAlphaNumStringWithSpacewWithSlash((String) vValus.elementAt(i))){
								if(!ASSTValidator.isValidDash((String) vValus.elementAt(i))){
								if(vValus.elementAt(i)==null || vValus.elementAt(i).toString().trim().isEmpty() )	{
									if("DeferralExtensionUpload".equals(master)&&
											("Discrepancy".equals((String)vValus.elementAt(0))
											||"RecurrentDoc".equals((String)vValus.elementAt(0))
											||"LAD".equals((String)vValus.elementAt(0))
											||"CAM".equals((String)vValus.elementAt(0))
											||"Security".equals((String)vValus.elementAt(0))
											||"Facility".equals((String)vValus.elementAt(0))
											||"OtherDocument".equals((String)vValus.elementAt(0))
											)) {
										hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
									}else {
										 validationStatus = false;
										 errorList[i][0] = ((String)column[i].getName()).toUpperCase();
										 errorList[i][1] = "Date Mandatory";
										 errorList[i][2]=(String)vValus.elementAt(i);
										 errorList[i][3]=String.valueOf(count);
									}
								} else {
									DateValidator dateValidator=new DateValidator();
									String str1=(String)vValus.elementAt(i);
									boolean valid2 = dateValidator.isValid(str1, "dd-MMM-yyyy");
									if(valid2){
										 String[] split = str1.split("-");
										 if(split.length==3){
											if( split[2].length()==4){
										 hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											}
											 else{
												 validationStatus = false;
												 errorList[i][0] = ((String)column[i].getName()).toUpperCase();
												 errorList[i][1] = "Invalid Date, year is not in yyyy format";
												 errorList[i][2]=(String)vValus.elementAt(i);
												 errorList[i][3]=String.valueOf(count);
											 }
										 }
										 /*
										 else{
											 validationStatus = false;
											 errorList[i][0] = ((String)column[i].getName()).toUpperCase();
											 errorList[i][1] = "Invalid Date Format";
											 errorList[i][2]=(String)vValus.elementAt(i);
											 errorList[i][3]=String.valueOf(count);
										 } */
									}
									else{
									validationStatus = false;
									 errorList[i][0] = ((String)column[i].getName()).toUpperCase();
									 errorList[i][1] = "Invalid Date Format";
									 errorList[i][2]=(String)vValus.elementAt(i);
									 errorList[i][3]=String.valueOf(count);
									}
								}
								}else{
									validationStatus = false;
									errorList[i][0] = ((String)column[i].getName()).toUpperCase();
									errorList[i][1] = "Invalid input data - Special Characters not allowed";
								}
						  }
							else if("IGNORE_FIELD".equals(column[i].getDataType())){
								
							}
						}
					}else{
						//To do - reject this record
						validationStatus = false;
						//userMasterValidationStatus = false;
						errorList[1][0] = "";
						errorList[1][1] = "Number of columns is not as per template";
						String error = "";
						for(int i = 0; i<vValus.size();i++){
							error = error+vValus.elementAt(i)+",";
						}
						 errorList[1][2]=error;
					}

				if(!validationStatus && errorList!=null) {
					errorHm.put(new Integer(count), errorList);
				}//Make it else if
				//Govind S: Blow commented code replace with nested if condition.
//				if (validationStatus && dd.maxRecProcess == null) {
//					dd.rowArray.add(hm);
//				}
				if( dd.maxRecProcess == null)
				{
					if (validationStatus) {
						dd.rowArray.add(hm);
					}else if("DeferralExtensionUpload".equals(master)){
						dd.setErrorList(errorHm);
					}
					
					if(master.equals(USER_UPLOAD)
							||master.equals(UBS_UPLOAD)
							||master.equals(HONGKONG_UPLOAD)
						    ||master.equals(BAHRAIN_UPLOAD)
							||master.equals(FINWARE_UPLOAD)
							||master.equals(FINWAREFD_UPLOAD)
							||master.equals(EXCHANGE_RATE_UPLOAD)
							||master.equals(STOCK_ITEM_UPLOAD)
							||master.equals(BOND_ITEM_UPLOAD)
							||master.equals(MUTUAL_FUNDS_ITEM_UPLOAD)
							|| FD_UPLOAD.equals(master)){
						validationStatus=true;
					}
				}
				else if (dd.maxRecProcess.intValue() >= maxCount) {
					dd.rowArray.add(maxHolder);
					maxCount = 0;
					maxHolder = new ArrayList();
				}
				if (dd.maxRecProcess != null) {
					maxHolder.add(hm);
					maxCount++;
				}
				count++;
				rowCheckSumTotal = rowCheckSumTotal+ rowCheckSum;
			}// end while
			maxRowCount = count;
			
			in.close();
			if ((dd.maxRecProcess != null) && !maxHolder.isEmpty()) {
				if(validationStatus) {
					dd.rowArray.add(maxHolder);
				}else{
					dd.rowArray.add(maxHolder);
				}
			}

			// System.out.println("successful insert csv data into array...");
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in readFileCSV method. ", e);
			System.out.println("Exception caught in readFileCSV method.. e=>"+e);
			e.printStackTrace();
			throw new ProcessFileException(e.getMessage());
		}
	}
	
	// Read File from UI
		public void readCSVFile(File fileUpload, ProcessDataFile dd,DataFile dataFile,String master) {
			try {
				//BufferedReader count_in=null;
				BufferedReader in=null;
				/*if(fileUpload==null)
				{
				count_in=new BufferedReader(new FileReader("D:\\testdata\\UBS.CSV"));
				}
				else
				{
				count_in = new BufferedReader(new InputStreamReader(new FileInputStream(fileUpload)));
				}*/
				String str;
				ArrayList maxHolder = new ArrayList();
				int maxCount = 0;
				//int lastRowCount = 0;
				long rowCheckSumTotal = 0;
				//Get row count of Last record
				/*while ((str = count_in.readLine()) != null) {
					lastRowCount++;
				}*/
				if(fileUpload==null)
				{
				in=new BufferedReader(new FileReader("D:\\testdata\\UBS.CSV"));
				}
				else
				{
				in = new BufferedReader(new InputStreamReader(new FileInputStream(fileUpload)));
				}
				int count = 0;
				locale = locale != null ? locale : Locale.getDefault();
				while ((str = in.readLine()) != null) {
					
					String [][]errorList = new String[dataFile.getHeader().getColumn().length][4];
					HashMap hm = new HashMap();
					Vector vValus = csvToVector(str, dataFile.getDelimiter());
					long rowCheckSum = 0;
								
					/*if(count == lastRowCount-1 && dataFile.getIsFooter()!=1){
						if(master.equals(USER_UPLOAD)){
							secondaryDelimiter = dataFile.getSecondaryDelimiter();
							Check if footer identifier and footer data count is correct 
							if(((String)vValus.elementAt(0)).equals(FOOTER_INDENTIFIER) && vValus.size()== 3){
								Check if total row count mentioned in the footer is correct and
								/*Check if total of row checksum matches with the checksum mentioned in the footer
								 * 
								if(Long.parseLong((String)vValus.elementAt(1)) == lastRowCount-2
									&& Long.parseLong((String)vValus.elementAt(2)) == rowCheckSumTotal){
									isCheckSumFooter = false;
									
								}else{
									String checked = (String)PropertyManager.getValue("masterupload.footer.required");
									if(checked.equalsIgnoreCase("No")){
										isCheckSumFooter = false;
									}else{
										isCheckSumFooter = true;
										//userMasterValidationStatus = false;
										validationStatus = false;
										errorList[1][0] = "";
										errorList[1][1] = "Footer checksum does not match";
									}
									
								}
								
							}else{
								//To Do - Handle this error condition - Reject this file
								
								userMasterValidationStatus = false;
								validationStatus = false;
								errorList[1][0] = "";
								errorList[1][1] = "Invalid File";
							}
						}else{
							//This footer check sum is for other masters
							isCheckSumFooter = checkSumRowCount(lastRowCount, str);
						}
						count++;
						continue;
					}else*/
						if (count < dd.rowStartIndex) {
						count++;
						continue;
					}
					else if ((count > dd.rowEndIndex) && (dd.rowEndIndex != 0)) {
						break;
					}

					if (str.equals("")) {
						validationStatus = false;
						errorList[1][0] = "";
						errorList[1][1] = "Remove all empty line from the file";
						errorHm.put(new Integer(count), errorList);
						count++;
						continue;
					}

					if (vValus.size() == dataFile.getHeader().getColumn().length) {
							Column[] column = dataFile.getHeader().getColumn();
							if(master.equals(USER_UPLOAD)) {
								if(null!=vValus.elementAt(column.length -1 ) && !"".equals(vValus.elementAt(column.length -1 )))
									rowCheckSum = Long.parseLong((String) vValus.elementAt(column.length -1 ));
								else
									rowCheckSum=0;
							}
							for (int i = dd.columnStartIndex; i < column.length; i++) {
									
								 if(column[i].getDataType().equals("ALPHABETIC")){ 
								 //This validation is added to avoid xml file relase. in case of xml, HDFC need to take approval from different levels. to avoid that code level change is performed
								 // this is not good practice. one should do requied change in xml only.
									 if(master.equalsIgnoreCase(UBS_UPLOAD) && column[i].getName().equalsIgnoreCase("LINE_NO")){

										 if (!(errorCode = Validator.checkString((String) vValus.elementAt(i), true, 1, column[i].getLength().intValue())).equals(Validator.ERROR_NONE)) {
												validationStatus = false;
												errorList[i][0] = ((String)column[i].getName()).toUpperCase();
												//	errorList[i][1] = errorCode;
												errorList[i][1] = "Invalid Entry";
												 errorList[i][2]=(String)vValus.elementAt(i);
												 errorList[i][3]=String.valueOf(count);
											}else{
												hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											}
									 }
									 else if( column[i].getName().equalsIgnoreCase("NEW_BRANCH_CODE")||column[i].getName().equalsIgnoreCase("DEPARTMENT_CODE")){
										 if(!ASSTValidator.isValidAlphaNumStringWithSpace((String) vValus.elementAt(i))){
											 
											 	if("".equals((String)vValus.elementAt(i))){
											 		hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											 	}
											 	else if (!(errorCode = Validator.checkString((String) vValus.elementAt(i), true, 0, column[i].getLength().intValue())).equals(Validator.ERROR_NONE)) {
													validationStatus = false;
													errorList[i][0] = ((String)column[i].getName()).toUpperCase();
													// errorList[i][1] = errorCode;
													errorList[i][1] = "Invalid Entry";
													 errorList[i][2]=(String)vValus.elementAt(i);
													 errorList[i][3]=String.valueOf(count);
												}else{
													hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
												}
												}else{
													validationStatus = false;
													errorList[i][0] = ((String)column[i].getName()).toUpperCase();
													errorList[i][1] = "Invalid input data - Special Characters not allowed";
												}
									 }
										 
									 else if(!ASSTValidator.isValidAlphaNumStringWithSpace((String) vValus.elementAt(i))){
										 
									 if (!(errorCode = Validator.checkString((String) vValus.elementAt(i), true, 1, column[i].getLength().intValue())).equals(Validator.ERROR_NONE)) {
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
											// errorList[i][1] = errorCode;
											errorList[i][1] = "Invalid Entry";
											 errorList[i][2]=(String)vValus.elementAt(i);
											 errorList[i][3]=String.valueOf(count);
										}else{
											hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
										}
										}else{
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
											errorList[i][1] = "Invalid input data - Special Characters not allowed";
										}
									 }							
								 else if(column[i].getDataType().equals("ALPHABETIC_WITH_SPACIAL")){	
									  if (!(errorCode = Validator.checkString((String) vValus.elementAt(i), true, 1, column[i].getLength().intValue())).equals(Validator.ERROR_NONE)) {
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
//											 errorList[i][1] = errorCode;
											errorList[i][1] = "Invalid Entry";
											 errorList[i][2]=(String)vValus.elementAt(i);
											 errorList[i][3]=String.valueOf(count);
										}else{
											hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
										}
								     }else if(column[i].getDataType().equals("NUMERIC")){
								    	 
								    	 if(column[i].getName().equalsIgnoreCase("USER_ROLE")){
								    		 if("".equals((String)vValus.elementAt(i)) && "U".equalsIgnoreCase((String) vValus.get(1))){
											 		hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											 }
								    		 else if (!(errorCode = Validator.checkNumber((String) vValus.elementAt(i), true, 0, 9999999999999999.D)).equals(Validator.ERROR_NONE)) {
													validationStatus = false;
													errorList[i][0] = ((String)column[i].getName()).toUpperCase();
		//											 errorList[i][1] = errorCode;
													errorList[i][1] = "Invalid Entry";
													 errorList[i][2]=(String)vValus.elementAt(i);
													 errorList[i][3]=String.valueOf(count);
									    	 }
								    		 else if (vValus.elementAt(i)!=null && "".equals(vValus.elementAt(i))) {
													validationStatus = false;
													errorList[i][0] = ((String)column[i].getName()).toUpperCase();
//													errorList[i][1] = errorCode;
													errorList[i][1] = "Invalid Entry";
													errorList[i][2]=(String)vValus.elementAt(i);
													errorList[i][3]=String.valueOf(count);
											} else{
												hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											}
								    	 }
								    	 else if (!(errorCode = Validator.checkNumber((String) vValus.elementAt(i), true, 1, 9999999999999999.D)).equals(Validator.ERROR_NONE)) {
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
//											 errorList[i][1] = errorCode;
											errorList[i][1] = "Invalid Entry";
											 errorList[i][2]=(String)vValus.elementAt(i);
											 errorList[i][3]=String.valueOf(count);
										}else{
											hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
										}
									}
								 /*	NUMERIC_NON_MANDATORY Added by Sandeep Shinde*/
								     else if(column[i].getDataType().equals("NUMERIC_NON_MANDATORY")){
											if (!(errorCode = Validator.checkInteger((String) vValus.elementAt(i), false, 1, 1999999999)).equals(Validator.ERROR_NONE)) {
												validationStatus = false;
												errorList[i][0] = ((String)column[i].getName()).toUpperCase();
//												 errorList[i][1] = errorCode;
												errorList[i][1] = "Invalid Entry";
												 errorList[i][2]=(String)vValus.elementAt(i);
												 errorList[i][3]=String.valueOf(count);
											}else{
												hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											}
										}
									else if(column[i].getDataType().equals("DECIMAL")){
										double maxValue = 0;
										if(column[i].getLength().intValue() == 9){
											maxValue = IGlobalConstant.MAXIMUM_ALLOWED_VALUE_7_2;
										}else if(column[i].getLength().intValue() == 7){
											maxValue = 99999.99;
										}else if(column[i].getLength().intValue() == 5){
											maxValue = IGlobalConstant.MAXIMUM_ALLOWED_VALUE_3_2;
										}else if(column[i].getLength().intValue() == 4){
											maxValue = 99.99;
										}else{
											maxValue = IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2;
										}
										String amount = (String) vValus.elementAt(i);
										if (!(errorCode = Validator.checkAmount(amount, false, 0,maxValue, IGlobalConstant.DEFAULT_CURRENCY, locale))
												.equals(Validator.ERROR_NONE)) {
											if(errorCode.equals("decimalexceeded") && amount.substring(amount.indexOf(".")).length()-1 <= 4)
											{
												hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											}else{
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
//											errorList[i][1] = errorCode;
											errorList[i][1] = "Invalid Entry";
											errorList[i][2]=(String)vValus.elementAt(i);
											errorList[i][3]=String.valueOf(count);
											}
										}else{
											hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
										}
									}
									else if(column[i].getDataType().equals("CONTACT")){
										if(!Validator.ERROR_NONE.equals(errorCode = Validator.checkPhoneNumber(vValus.elementAt(i).toString().trim(),true,locale))) {
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
											 errorList[i][1] = "Invalid contact number";
											 errorList[i][2]=(String)vValus.elementAt(i);
											 errorList[i][3]=String.valueOf(count);
										}else{
											hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
										}
									}
								 /*	CONTACT_NON_MANDATORY Added by Sandeep Shinde*/
									else if(column[i].getDataType().equals("CONTACT_NON_MANDATORY")){
										if(!Validator.ERROR_NONE.equals(errorCode = Validator.checkPhoneNumber(vValus.elementAt(i).toString().trim(),false,locale))) {
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
											 errorList[i][1] = "Invalid Contact/Fax Number";
											 errorList[i][2]=(String)vValus.elementAt(i);
											 errorList[i][3]=String.valueOf(count);
										}else{
											hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
										}
									}
									else if(column[i].getDataType().equals("EMAIL")){
										if(!ASSTValidator.isValidEmail((String) vValus.elementAt(i))){
										if (!(errorCode = Validator.checkEmail((String) vValus.elementAt(i), true) ).equals(Validator.ERROR_NONE) && vValus.elementAt(i).toString().length()>0) {
											 validationStatus = false;
											 errorList[i][0] = ((String)column[i].getName()).toUpperCase();
											 errorList[i][1] = "Invalid Email";
											 errorList[i][2]=(String)vValus.elementAt(i);
											 errorList[i][3]=String.valueOf(count);
										}else{
											hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
										}
										}else{
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
											errorList[i][1] = "Invalid input data - Special Characters not allowed";
										}
									}else if(column[i].getDataType().equals("DATE_FORM")){
										//if(!ASSTValidator.isValidAlphaNumStringWithSpacewWithSlash((String) vValus.elementAt(i))){
										//if(!ASSTValidator.isValidDash((String) vValus.elementAt(i))){
										if(vValus.elementAt(i)==null || vValus.elementAt(i).toString().trim()=="" )	{
											 validationStatus = false;
											 errorList[i][0] = ((String)column[i].getName()).toUpperCase();
											 errorList[i][1] = "Date Mandatory";
											 errorList[i][2]=(String)vValus.elementAt(i);
											 errorList[i][3]=String.valueOf(count);
										} else if (!(errorCode = Validator.checkDate((String) vValus.elementAt(i),true, locale)).equals(Validator.ERROR_NONE)) {
											 validationStatus = false;
											 errorList[i][0] = ((String)column[i].getName()).toUpperCase();
											 errorList[i][1] = "Invalid Date";
											 errorList[i][2]=(String)vValus.elementAt(i);
											 errorList[i][3]=String.valueOf(count);
										}else{
											hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
										}
										/*}else{
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
											errorList[i][1] = "Invalid input data - Special Characters not allowed";
										}*/
								 }else if(column[i].getDataType().equals("DATE_FORM_NON_MANDATORY")){
										if(!ASSTValidator.isValidDash((String) vValus.elementAt(i))){
											if (!(errorCode = Validator.checkDate((String) vValus.elementAt(i),false, locale)).equals(Validator.ERROR_NONE)) {
											 validationStatus = false;
											 errorList[i][0] = ((String)column[i].getName()).toUpperCase();
											 errorList[i][1] = "Invalid Date";
											 errorList[i][2]=(String)vValus.elementAt(i);
											 errorList[i][3]=String.valueOf(count);
											}else{
											hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											}
										}else{
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
											errorList[i][1] = "Invalid input data - Special Characters not allowed";
										}
								 }else if(column[i].getDataType().equals("ADDRESS")||column[i].getDataType().equals("ADDRESS_NON_MANDATORY")){ 
									 boolean mandatoryFlag = true;
									 	if(column[i].getDataType().equals("ADDRESS_NON_MANDATORY")){
									 		mandatoryFlag = false;
									 		}
											 if (!(errorCode = Validator.checkString((String) vValus.elementAt(i), mandatoryFlag, 1, column[i].getLength().intValue())).equals(Validator.ERROR_NONE)) {
													validationStatus = false;
													errorList[i][0] = ((String)column[i].getName()).toUpperCase();
													// errorList[i][1] = errorCode;
													errorList[i][1] = "Invalid Entry";
													 errorList[i][2]=(String)vValus.elementAt(i);
													 errorList[i][3]=String.valueOf(count);
												}else{
													hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
												}
								}else if(column[i].getDataType().equals("ALPHABETIC_OR_EMPTY")){ 
									 if(!ASSTValidator.isValidAlphaNumStringWithSpace((String) vValus.elementAt(i))){	
										 if (!(errorCode = Validator.checkString((String) vValus.elementAt(i), false, 1, column[i].getLength().intValue())).equals(Validator.ERROR_NONE)) {
												validationStatus = false;
												errorList[i][0] = ((String)column[i].getName()).toUpperCase();
												//errorList[i][1] = errorCode;
												errorList[i][1] = "Invalid Entry";
												 errorList[i][2]=(String)vValus.elementAt(i);
												 errorList[i][3]=String.valueOf(count);
											}else{
												hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											}
											}else{
												validationStatus = false;
												errorList[i][0] = ((String)column[i].getName()).toUpperCase();
												errorList[i][1] = "Invalid input data - Special Characters not allowed";
											}
								       }
							
							}
						}else{
							//To do - reject this record
							validationStatus = false;
							//userMasterValidationStatus = false;
							errorList[1][0] = "";
							errorList[1][1] = "Number of columns is not as per template";
							 errorList[1][2]=(String)vValus.elementAt(2);
						}

					if(!validationStatus && errorList!=null) {
						errorHm.put(new Integer(count), errorList);
					}//Make it else if
					//Govind S: Blow commented code replace with nested if condition.
//					if (validationStatus && dd.maxRecProcess == null) {
//						dd.rowArray.add(hm);
//					}
					if( dd.maxRecProcess == null)
					{
						if (validationStatus) {
							dd.rowArray.add(hm);
						}
						
						if(master.equals(USER_UPLOAD)||master.equals(UBS_UPLOAD)||master.equals(FINWARE_UPLOAD)||master.equals(FINWAREFD_UPLOAD)||master.equals(EXCHANGE_RATE_UPLOAD)||master.equals(STOCK_ITEM_UPLOAD)||master.equals(BOND_ITEM_UPLOAD)||master.equals(MUTUAL_FUNDS_ITEM_UPLOAD)){
							validationStatus=true;
							
						}
					}
					else if (dd.maxRecProcess.intValue() >= maxCount) {
						dd.rowArray.add(maxHolder);
						maxCount = 0;
						maxHolder = new ArrayList();
					}
					if (dd.maxRecProcess != null) {
						maxHolder.add(hm);
						maxCount++;
					}
					count++;
					rowCheckSumTotal = rowCheckSumTotal+ rowCheckSum;
				}// end while
				maxRowCount = count;
				
				in.close();
				if ((dd.maxRecProcess != null) && !maxHolder.isEmpty()) {
					if(validationStatus) {
						dd.rowArray.add(maxHolder);
					}else{
						dd.rowArray.add(maxHolder);
					}
				}

				// System.out.println("successful insert csv data into array...");
				
				
			}
			catch (Exception e) {
				DefaultLogger.error(this, "", e);
				e.printStackTrace();
				throw new ProcessFileException(e.getMessage());
			}
		}
	
}