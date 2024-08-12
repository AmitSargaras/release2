package com.integrosys.cms.batch.common.filereader;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.IMapper;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.eod.bus.IEODSyncOutMasterDao;
import com.integrosys.cms.app.eod.bus.OBCpsToClimsMaster;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.batch.common.syncmaster.datafileparser.Column;
import com.integrosys.cms.batch.common.syncmaster.datafileparser.SyncMasterTemplateIn;
import com.integrosys.cms.batch.common.syncmaster.datafileparser.opencsv.bean.ColumnPositionMappingStrategy;
import com.integrosys.cms.batch.eod.IEndOfDaySyncMastersService;
import com.integrosys.cms.batch.eod.IEodSyncConstants;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Reader for DAT File
 * @author anil.pandey
 *
 */
public class DATReader implements ICMSConstant{
	
	
	static Locale locale;
	boolean validationStatus = true;
	HashMap errorHm = new HashMap();
	private int maxRowCount = 0;
	String errorCode = "";
	
	private boolean userMasterValidationStatus = true ;
	private String secondaryDelimiter;
	private static final String STATUS_SUCCESS="SUCCESS";
	private static final String STATUS_FAILED="FAIL";
	private static final String ALLOWD_CHARACTERS=PropertyManager.getValue("eod.sync.allowed.character","");
	private String ackFileData;
	IEndOfDaySyncMastersService endOfDaySyncMastersServiceImpl = (IEndOfDaySyncMastersService) BeanHouse.get("EndOfDaySyncMastersServiceImpl");
	IEODSyncOutMasterDao eodSyncOutMaster = (IEODSyncOutMasterDao) BeanHouse.get("eodSyncOutMasterDao");
	public static void setLocale(Locale loca)
	{
		 locale = loca;	
	}
	
	
	
	public HashMap getErrorHm() {
		return errorHm;
	}



	public void setErrorHm(HashMap errorHm) {
		this.errorHm = errorHm;
	}



	public boolean isValidationStatus() {
		return validationStatus;
	}



	public void setValidationStatus(boolean validationStatus) {
		this.validationStatus = validationStatus;
	}



	public int getMaxRowCount() {
		return maxRowCount;
	}



	public void setMaxRowCount(int maxRowCount) {
		this.maxRowCount = maxRowCount;
	}



	public boolean isUserMasterValidationStatus() {
		return userMasterValidationStatus;
	}



	public void setUserMasterValidationStatus(boolean userMasterValidationStatus) {
		this.userMasterValidationStatus = userMasterValidationStatus;
	}



	public String getSecondaryDelimiter() {
		return secondaryDelimiter;
	}



	public void setSecondaryDelimiter(String secondaryDelimiter) {
		this.secondaryDelimiter = secondaryDelimiter;
	}



	/**
	 * @return the ackFileData
	 */
	public String getAckFileData() {
		return ackFileData;
	}



	/**
	 * @param ackFileData the ackFileData to set
	 */
	public void setAckFileData(String ackFileData) {
		this.ackFileData = ackFileData;
	}



	// Read File 
	public void readFileDAT(File fileUpload, ProcessDataFileEOD dd,SyncMasterTemplateIn syncMasterTemplate,String master) {
		BufferedReader count_in=null;
		BufferedReader in=null;
		try {
			int count = 0;
			Long successfullRecordCount=0l;
			Long faliureRecordCount=0l;
			boolean isValidTemplateRow=true;
			StringBuffer ackStringBuffer= new StringBuffer();
			
			DefaultLogger.info(this, "fileUpload: "+fileUpload+", master"+master);
			if (fileUpload == null) {
				dd.setSuccessfullRecordCount(0l);
				dd.setFailureRecordCount(dd.fileRecordCount);
				ackStringBuffer.append("Master file not found with name : "+dd.getAckFileName().replace("_Ack", ""));
				if(!CPS_CLIMS_CONTROL_FILE.equals(master)){
					setAckFileData(ackStringBuffer.toString());
					generateFile(IEodSyncConstants.SYNC_DIRECTION_CPSTOCLIMS, dd.getAckFileName(),getAckFileData());
				}
				return;
			} else {
				count_in = new BufferedReader(new FileReader(fileUpload));
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
			dd.setTotalRecordCount(lastRowCount-dd.rowStartIndex);
			
			if (fileUpload != null) {
				in = new BufferedReader(new FileReader(fileUpload));
			}
			DefaultLogger.info(this, "total record Count: "+dd.getTotalRecordCount()+", file record count: "+dd.getFileRecordCount());
			//validating control file count with total record count
			if(dd.getTotalRecordCount()==dd.getFileRecordCount() || CPS_CLIMS_CONTROL_FILE.equalsIgnoreCase(master)){
				int mappedColumnLength = syncMasterTemplate.getMappedColumns().getColumn()!=null?syncMasterTemplate.getMappedColumns().getColumn().length:0;
				int defaultColumnLength = syncMasterTemplate.getDefaultColumns().getColumn()!=null?syncMasterTemplate.getDefaultColumns().getColumn().length:0;
				int totalcolumns=mappedColumnLength+defaultColumnLength;
				
				String[] mappedFields= new String[mappedColumnLength + defaultColumnLength];
				
				while ((str = in.readLine()) != null) {//reading row
					validationStatus=true;//reset status for every row.
					
					String [][]errorList = new String[mappedColumnLength][4];
					//validating end of line
					if(null!=syncMasterTemplate.getSecondaryDelimiter() && ! "".equals(syncMasterTemplate.getSecondaryDelimiter())){
						if(!str.trim().endsWith(syncMasterTemplate.getSecondaryDelimiter())){
							validationStatus = false;
							errorList[1][0] = "";
							errorList[1][1] = "Row is not ended as per configured delimeter";
							errorHm.put(new Integer(count), errorList);
							count++;
							continue;
							
						}
					}
					                            
	//				HashMap hm = new HashMap();
					HashMap<String,Object> valueMap = new HashMap<String,Object>();
					//Parsing row into column
					Vector vValus = datToVector(str.trim(), syncMasterTemplate.getDelimiter(),syncMasterTemplate.getSecondaryDelimiter());
					long rowCheckSum = 0;
					
					if (count < dd.rowStartIndex) {
						ackStringBuffer.append(str.replace(syncMasterTemplate.getSecondaryDelimiter(), ""));
						ackStringBuffer.append(syncMasterTemplate.getDelimiter());
						ackStringBuffer.append("OP_STATUS");
						ackStringBuffer.append(syncMasterTemplate.getDelimiter());
						ackStringBuffer.append("REASON");
						ackStringBuffer.append(syncMasterTemplate.getSecondaryDelimiter());
						ackStringBuffer.append("\n");
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
					
					if (vValus.size() == mappedColumnLength) {// Check if mapped column and fields in file are same
							Column[] column = syncMasterTemplate.getMappedColumns().getColumn();
							//looping column details
							for (int i = dd.columnStartIndex; i < column.length; i++) {
								
								if(count==1){ //get mapped field only once for first row.
									 if(column[i].getMappedFieldName()!=null && !"".equals(column[i].getMappedFieldName())){
										 mappedFields[i]=column[i].getMappedFieldName(); 
									 }else{
										 //MAPPING NOT FOUND ERROR.
									 }
								}
								if(column[i].getDataType().contains(("ALPHABETIC"))&& vValus.elementAt(i)!=null 
										&& !"cpsId".equalsIgnoreCase(column[i].getMappedFieldName()) ){
									
									vValus.set(i,vValus.elementAt(i).toString().replaceAll(ALLOWD_CHARACTERS, ""));
									
								}
								 if(column[i].getDataType().equals("ALPHABETIC")){ 
									 if(!ASSTValidator.isValidAlphaNumStringWithSpace((String) vValus.elementAt(i))){
										 if (!(errorCode = Validator.checkString((String) vValus.elementAt(i), true, 1, column[i].getLength().intValue())).equals(Validator.ERROR_NONE)) {
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
											// errorList[i][1] = errorCode;
											errorList[i][1] = "Invalid Entry";
											 errorList[i][2]=(String)vValus.elementAt(i);
											 errorList[i][3]=String.valueOf(count);
										}else{
											//hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											valueMap.put(((String)column[i].getMappedFieldName()).toUpperCase(), vValus.elementAt(i));
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
											//hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											valueMap.put(((String)column[i].getMappedFieldName()).toUpperCase(), vValus.elementAt(i));
										}
								 }else if(column[i].getDataType().equals("NUMERIC")){
								    	if (!(errorCode = Validator.checkNumber((String) vValus.elementAt(i), true, 1, 9999999999999999.D)).equals(Validator.ERROR_NONE)) {
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
											errorList[i][1] = "Invalid Entry";
											errorList[i][2]=(String)vValus.elementAt(i);
											errorList[i][3]=String.valueOf(count);
										}else{
											//hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											valueMap.put(((String)column[i].getMappedFieldName()).toUpperCase(), vValus.elementAt(i));
										}
								}else if(column[i].getDataType().equals("NUMERIC_WITH_SPECIFIC_VALUE")){
								    	if (!(errorCode = Validator.checkNumber((String) vValus.elementAt(i), true, 0, 1)).equals(Validator.ERROR_NONE)) {
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
											errorList[i][1] = "Invalid Entry";
											errorList[i][2]=(String)vValus.elementAt(i);
											errorList[i][3]=String.valueOf(count);
										}else{
											//hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											valueMap.put(((String)column[i].getMappedFieldName()).toUpperCase(), vValus.elementAt(i));
										}
								}
								 /*	NUMERIC_NON_MANDATORY Added by Sandeep Shinde*/
								else if(column[i].getDataType().equals("NUMERIC_NON_MANDATORY")){
										if (!(errorCode = Validator.checkInteger((String) vValus.elementAt(i), false, 1, 1999999999)).equals(Validator.ERROR_NONE)) {
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
											errorList[i][1] = "Invalid Entry";
											errorList[i][2]=(String)vValus.elementAt(i);
											errorList[i][3]=String.valueOf(count);
										}else{
											//hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											valueMap.put(((String)column[i].getMappedFieldName()).toUpperCase(), vValus.elementAt(i));
										}
								}
								else if (column[i].getDataType().equals("DECIMAL")) {
										double maxValue = 0;
										if(column[i].getLength().intValue() == 9){
											maxValue = IGlobalConstant.MAXIMUM_ALLOWED_VALUE_7_2;
										}else if(column[i].getLength().intValue() == 16){
											maxValue = IGlobalConstant.MAXIMUM_ALLOWED_VALUE_14_2;
										}else if(column[i].getLength().intValue() == 7){
											maxValue = 99999.99;
										}else if(column[i].getLength().intValue() == 5){
											maxValue = IGlobalConstant.MAXIMUM_ALLOWED_VALUE_3_2;
										}else if(column[i].getLength().intValue() == 4){
											maxValue = 99.99;
										}else{
											maxValue = IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2;
										}
										
										if (!(errorCode = Validator.checkAmount((String) vValus.elementAt(i), false, 0,maxValue, IGlobalConstant.DEFAULT_CURRENCY, new Locale("en")))
												.equals(Validator.ERROR_NONE)) {
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
											errorList[i][1] = "Invalid Entry";
											errorList[i][2]=(String)vValus.elementAt(i);
											errorList[i][3]=String.valueOf(count);
										}else{
											//hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											valueMap.put(((String)column[i].getMappedFieldName()).toUpperCase(), vValus.elementAt(i));
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
											//hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											valueMap.put(((String)column[i].getMappedFieldName()).toUpperCase(), vValus.elementAt(i));
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
										//	hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											valueMap.put(((String)column[i].getMappedFieldName()).toUpperCase(), vValus.elementAt(i));
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
										//	hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											valueMap.put(((String)column[i].getMappedFieldName()).toUpperCase(), vValus.elementAt(i));
										}
										}else{
											validationStatus = false;
											errorList[i][0] = ((String)column[i].getName()).toUpperCase();
											errorList[i][1] = "Invalid input data - Special Characters not allowed";
										}
									}else if(column[i].getDataType().equals("DATE_FORM")){
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
										//	hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											valueMap.put(((String)column[i].getMappedFieldName()).toUpperCase(), vValus.elementAt(i));
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
										//	hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
											valueMap.put(((String)column[i].getMappedFieldName()).toUpperCase(), vValus.elementAt(i));
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
													errorList[i][1] = "Invalid Entry";
													errorList[i][2]=(String)vValus.elementAt(i);
													errorList[i][3]=String.valueOf(count);
												}else{
												//	hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
													valueMap.put(((String)column[i].getMappedFieldName()).toUpperCase(), vValus.elementAt(i));
												}
								}else if(column[i].getDataType().equals("ALPHABETIC_OR_EMPTY")){ 
									 if(!ASSTValidator.isValidAlphaNumStringWithSpace((String) vValus.elementAt(i))){	
										 if (!(errorCode = Validator.checkString((String) vValus.elementAt(i), false, 1, column[i].getLength().intValue())).equals(Validator.ERROR_NONE)) {
												validationStatus = false;
												errorList[i][0] = ((String)column[i].getName()).toUpperCase();
												errorList[i][1] = "Invalid Entry";
												errorList[i][2]=(String)vValus.elementAt(i);
												errorList[i][3]=String.valueOf(count);
											}else{
												//hm.put(((String)column[i].getName()).toUpperCase(), vValus.elementAt(i));
												valueMap.put(((String)column[i].getMappedFieldName()).toUpperCase(), vValus.elementAt(i));
											}
											}else{
												validationStatus = false;
												errorList[i][0] = ((String)column[i].getName()).toUpperCase();
												errorList[i][1] = "Invalid input data - Special Characters not allowed";
											}
								       }else if(column[i].getDataType().equals("DATFILEXTENTION")){
											  if (!(errorCode = Validator.checkString((String) vValus.elementAt(i), true, 1, column[i].getLength().intValue())).equals(Validator.ERROR_NONE)) {
													validationStatus = false;
													errorList[i][0] = ((String)column[i].getName()).toUpperCase();
													errorList[i][1] = "Invalid Entry";
													errorList[i][2]=(String)vValus.elementAt(i);
													errorList[i][3]=String.valueOf(count);
												}else{
													if((String) vValus.elementAt(i)!=null && ((String)vValus.elementAt(i)).toLowerCase().endsWith(".dat")){
														valueMap.put(((String)column[i].getMappedFieldName()).toUpperCase(), vValus.elementAt(i));
													}else{
														validationStatus = false;
														errorList[i][0] = ((String)column[i].getName()).toUpperCase();
														errorList[i][1] = "Field has invalid file extention.";
														errorList[i][2]=(String)vValus.elementAt(i);
														errorList[i][3]=String.valueOf(count);
														valueMap.put(((String)column[i].getMappedFieldName()).toUpperCase(), vValus.elementAt(i));
													}
												}
										 
								       }
							
							}
							
						}else{
							isValidTemplateRow=false;
							//To do - reject this record
							validationStatus = false;
							//userMasterValidationStatus = false;
							errorList[1][0] = "";
							errorList[1][1] = "Number of columns is not as per template";
							//errorList[1][2]=(String)vValus.elementAt(2);
						}
					
					Column[] column = syncMasterTemplate.getDefaultColumns().getColumn();
					int defaultFieldCounter=0;
					//looping column details
					for (int i = mappedColumnLength; i < totalcolumns; i++) {
						if(count==1){ //get mapped field only once for first row.
							 if(column[defaultFieldCounter].getMappedFieldName()!=null && !"".equals(column[defaultFieldCounter].getMappedFieldName())){
								 mappedFields[i]=column[defaultFieldCounter].getMappedFieldName(); 
							 }else{
								 //MAPPING NOT FOUND ERROR.
							 }
						}
						if("ROWINDEX".equalsIgnoreCase((column[defaultFieldCounter].getMappedFieldName()))){
							valueMap.put(((String)column[defaultFieldCounter].getMappedFieldName()).toUpperCase(), count);
						}else{
							//hm.put(((String)column[defaultFieldCounter].getName()).toUpperCase(), (String)column[defaultFieldCounter].getDefaultValue());
							valueMap.put(((String)column[defaultFieldCounter].getMappedFieldName()).toUpperCase(), (String)column[defaultFieldCounter].getDefaultValue());
						}
						defaultFieldCounter++;
					}
					
					
					
					Object obToStore=null;
					//=====================Anil==============
					
					if(CPS_CLIMS_CONTROL_FILE.equals(master)){
						obToStore = populateForm(syncMasterTemplate, mappedFields,valueMap);
						String errorStr = getErrorString(errorList,new ActionErrors());
						OBCpsToClimsMaster obCpsToClimsMaster= (OBCpsToClimsMaster) obToStore;
						if(errorStr!=null  && !"".equals(errorStr.trim())){
							obCpsToClimsMaster.setValidRecord(false);
							obCpsToClimsMaster.setErrorMessage(errorStr);
							validationStatus = true;//bypass validation it is handled separately in ack file generation
						}else{
							obCpsToClimsMaster.setValidRecord(true);
						}
						obToStore=obCpsToClimsMaster;
					}else{
						String errorStr ="";
						if(!isValidTemplateRow){
							errorStr = getErrorString(errorList,null);
						     if(errorStr!=null  && !"".equals(errorStr.trim())){
						    	 validationStatus=false;//Validation Failed
						     }
						}else{
							 Object formObj = populateForm(syncMasterTemplate, mappedFields,valueMap); 
						     System.out.println(formObj.getClass());
						     
						     
						     //validation
						     Object errors = validateForm(syncMasterTemplate, formObj);
						     ActionErrors actionErrors= (ActionErrors) errors;
						     errorStr = getErrorString(errorList,actionErrors);
						     if(errorStr!=null  && !"".equals(errorStr.trim())){
						    	 validationStatus=false;//Validation Failed
						     }else{
						    	 //No error Found map form to OB
						    	 obToStore = populateOB(syncMasterTemplate, formObj);
						     }
						     if(validationStatus){
							     //Storing Object
							     try {
							    	 endOfDaySyncMastersServiceImpl.processRecord(master,obToStore);
								 } catch (Exception e) {
									 errorStr=e.getMessage();
									 validationStatus=false;//Failed storing Object hence record is rejected.
								 }
						     }
						}
					     String ackLine= getRowWithStatus(str, errorStr,syncMasterTemplate);
					     if(!"".equals(ackLine)){
					    	 ackStringBuffer.append(ackLine);
					    	 if((count+1)!=lastRowCount)
					    		 ackStringBuffer.append("\n");
					     }
					}
				     
					//========================Anil==================
					if(!validationStatus && errorList!=null) {
						errorHm.put(new Integer(count), errorList);
					}
					
					if( dd.maxRecProcess == null)
					{
						if (validationStatus && obToStore!=null) {
	//						dd.rowArray.add(hm);
							dd.rowArray.add(obToStore);
							validationStatus=true;//reset Flag for next row;
							successfullRecordCount++;
						}else{
							faliureRecordCount++;
						}
						
					}
					else if (dd.maxRecProcess.intValue() >= maxCount) {
						dd.rowArray.add(maxHolder);
						maxCount = 0;
						maxHolder = new ArrayList();
					}
					if (dd.maxRecProcess != null) {
	//					maxHolder.add(hm);
						dd.rowArray.add(obToStore);
						validationStatus=true;//reset Flag for next row;
						maxCount++;
					}
					count++;
					rowCheckSumTotal = rowCheckSumTotal+ rowCheckSum;
				}// end while
				maxRowCount = count;
			}else{
				ackStringBuffer.append("File is rejected. Total record count in file doesn’t match with control file.");
				successfullRecordCount=0l;
				faliureRecordCount=dd.getFileRecordCount();
			}
			DefaultLogger.info(this, "Acknowledgement message : "+ackStringBuffer.toString());
			if(!CPS_CLIMS_CONTROL_FILE.equals(master)){
				setAckFileData(ackStringBuffer.toString());
				generateFile(IEodSyncConstants.SYNC_DIRECTION_CPSTOCLIMS, dd.getAckFileName(),getAckFileData());
			}
			dd.setSuccessfullRecordCount(successfullRecordCount);
			dd.setFailureRecordCount(faliureRecordCount);
			
			
			if ((dd.maxRecProcess != null) && !maxHolder.isEmpty()) {
				if(validationStatus) {
					dd.rowArray.add(maxHolder);
				}else{
					dd.rowArray.add(maxHolder);
				}
			}

		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			e.printStackTrace();
			throw new ProcessFileException(e.getMessage());
		}finally{
			try {
				if (count_in != null) {
					count_in.close();
				}
			}catch (Exception e) {}
			try {
				if (in != null) {
					in.close();
				}
			}catch (Exception e) {}
		}
	}



	private Object populateOB(SyncMasterTemplateIn syncMasterTemplate,
			Object formObj) {
		Object obj=null;
		try {
			 Class mapperClass= Class.forName(syncMasterTemplate.getMapperClassName());
			 //HashMap inputs = new HashMap();
			 //Method mapFormToOBMethod = getMethod(mapperClass, "mapFormToOB");
			 //Object obj =  mapFormToOBMethod.invoke(mapFormToOBMethod, new Object[]{new CommonForm(),inputs});
			 System.out.println("mapFormToOBMethod............."+mapperClass.getName());
			 
			 IMapper imapper = (IMapper) mapperClass.newInstance();
			 obj = imapper.mapFormToOB((CommonForm)formObj, new HashMap());
		} catch (Exception e) {
			DefaultLogger.error(this, "Error while OB conversion file for mapper"+syncMasterTemplate.getMapperClassName(), e);
		}
		return obj;
	}



	private Object validateForm(SyncMasterTemplateIn syncMasterTemplate,
			Object formObj) {
		Object errors=null;
		try {
			 Class validatorClass= Class.forName(syncMasterTemplate.getValidatorClassName());
			 Method validateInputMethod = getMethod(validatorClass, "validateInput");
			  errors = validateInputMethod.invoke(validateInputMethod, new Object[] { formObj, new Locale("en")});
		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Error while validating file for validator"+syncMasterTemplate.getValidatorClassName(), e);
		}
		return errors;
	}



	private Object populateForm(SyncMasterTemplateIn syncMasterTemplate,
			String[] mappedFields, HashMap<String, Object> valueMap)
			 {
			Object formObj=null;
			try {
				Class formClass = Class.forName(syncMasterTemplate.getRequestClassName());
				ColumnPositionMappingStrategy<Object> mapper = new ColumnPositionMappingStrategy<Object>();
				mapper.setType(formClass);
				String[] columns = mappedFields; // the fields to bind do in your JavaBean
				mapper.setColumnMapping(columns);
				formObj = mapper.createBean();
					for (int i = 0; i < columns.length; i++) {
						PropertyDescriptor prop = mapper.findDescriptor(i);
						try {
							if (null != prop) {
								Object object = valueMap.get((prop.getName().toUpperCase()));
								String value =null;
								if(object!=null){
									value = checkForTrim(String.valueOf(object), prop);
								}
								System.out.println("columns : '"+columns[i]+"' value : '"+ value+"'");
								Object obj = convertValue(value, prop);
								prop.getWriteMethod().invoke(formObj, obj);
							}
						}
						catch (Exception e) {
							// catched exception to continue with next field
							e.printStackTrace();
							DefaultLogger.debug(this, "Error while populating Form" + e.getMessage());
						}
					}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Error while populating Form"+e.getMessage());
			} catch (InstantiationException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Error while populating Form"+e.getMessage());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Error while populating Form"+e.getMessage());
			} catch (IntrospectionException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Error while populating Form"+e.getMessage());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Error while populating Form"+e.getMessage());
			}
			
		return formObj;
	}
	public static void generateFile(String syncDirection,String filename, String ackFileData) {
		String basePath="";
		if(IEodSyncConstants.SYNC_DIRECTION_CLIMSTOCPS.equals(syncDirection)){
			basePath=PropertyManager.getValue(IEodSyncConstants.FTP_MASTER_UPLOAD_LOCAL_DIR);
		}
		if(IEodSyncConstants.SYNC_DIRECTION_CPSTOCLIMS.equals(syncDirection)){
			basePath = PropertyManager.getValue(IEodSyncConstants.FTP_MASTER_DOWNLOAD_LOCAL_ACK_DIR);//getBaseAckPath();
		}
		File baseDir= new File(basePath);
		if(!baseDir.exists()) {
			baseDir.mkdirs();//Initialize base dir
		}
		
		File file= new File(basePath+File.separator+filename);
		try {
			if(file.exists()){
				boolean delete = file.delete();//Delete Old File
				if(delete==false) {
					System.out.println("file  deletion failed for file:"+file.getPath());	
				}
				boolean createNewFile = file.createNewFile();//Create New File
				if(createNewFile==false) {
					System.out.println("Error while creating new file:"+file.getPath());	
				      }
			}else{
				boolean createNewFile = file.createNewFile();//Create New File
				if(createNewFile==false) {
					System.out.println("Error while creating new file:"+file.getPath());	
				      }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileWriter fstream = null;
		BufferedWriter out = null;
		if (file != null && file.exists()) {
			try {
				fstream = new FileWriter(file, true);
				out = new BufferedWriter(fstream);
				out.write(ackFileData);
				out.flush();
			} catch (Exception e) {// Catch exception if any
				DefaultLogger.error(DATReader.class.getName(), "Error while writing in file "+ file.getName(), e);
			} finally {
				if (null != out) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	
		
	}

	private static String getRowWithStatus(String line, String errorStr, SyncMasterTemplateIn syncMasterTemplate) {
		String lineWithStatus="";
		
		line=line.replace(syncMasterTemplate.getSecondaryDelimiter(), "");
		if("".equals(errorStr)){
			lineWithStatus=line+syncMasterTemplate.getDelimiter()+DATReader.STATUS_SUCCESS+syncMasterTemplate.getDelimiter()+"NA"+syncMasterTemplate.getSecondaryDelimiter();
		}else{
			lineWithStatus=line+syncMasterTemplate.getDelimiter()+DATReader.STATUS_FAILED+syncMasterTemplate.getDelimiter()+errorStr+syncMasterTemplate.getSecondaryDelimiter();
		}
		return lineWithStatus;
	}



	private static String getErrorString(String[][] errorList, ActionErrors actionErrors) {
		String errorStr="";
		//ProcessErrorMap
		if(errorList!=null){
			for (String[] error : errorList) {
				if(error[0]!=null || error[1]!=null ){
					errorStr += error[0]+":"+error[1]+",";
				}
			}
		}
		//ProcessAction Errors
		if(actionErrors!=null){
			Iterator errorMessages = actionErrors.get();
			while (errorMessages.hasNext()) {
				ActionMessage errorMsg = (ActionMessage) errorMessages.next();
				errorStr += errorMsg.getKey()+",";
				
			}
		}
		
		if(errorStr.endsWith(",")){
			errorStr=errorStr.substring(0,errorStr.length()-1);
		}
		return errorStr;
	}


	private String checkForTrim(String s, PropertyDescriptor prop) {
	    return trimmableProperty(prop) ? s.trim() : s;
	}
	
	private boolean trimmableProperty(PropertyDescriptor prop) {
	    return !prop.getPropertyType().getName().contains("String");
	}
	protected Object convertValue(String value, PropertyDescriptor prop) throws InstantiationException, IllegalAccessException {
	    PropertyEditor editor = getPropertyEditor(prop);
	    Object obj = value;
	    if (null != editor) {
	        editor.setAsText(value);
	        obj = editor.getValue();
	    }
	    return obj;
	}
	/*
     * Attempt to find custom property editor on descriptor first, else try the propery editor manager.
     */
	 private Map<Class<?>, PropertyEditor> editorMap = null;
    protected PropertyEditor getPropertyEditor(PropertyDescriptor desc) throws InstantiationException, IllegalAccessException {
        Class<?> cls = desc.getPropertyEditorClass();
        if (null != cls) return (PropertyEditor) cls.newInstance();
        return getPropertyEditorValue(desc.getPropertyType());
    }
    private PropertyEditor getPropertyEditorValue(Class<?> cls) {
        if (editorMap == null) {
            editorMap = new HashMap<Class<?>, PropertyEditor>();
        }

        PropertyEditor editor = editorMap.get(cls);

        if (editor == null) {
            editor = PropertyEditorManager.findEditor(cls);
            addEditorToMap(cls, editor);
        }

        return editor;
    }

    private void addEditorToMap(Class<?> cls, PropertyEditor editor) {
        if (editor != null) {
            editorMap.put(cls, editor);
        }
    }
	public Vector datToVector(String row, String delimStr,String secDelimStr) {
		java.util.Vector v = new java.util.Vector();
		if(delimStr.contains("|")){
			delimStr = delimStr.replace("|", "\\|");
		}
		if(secDelimStr.contains("|")){
			secDelimStr = secDelimStr.replace("|", "\\|");
		}
		if(row.endsWith(secDelimStr)){
			row= row.replace(secDelimStr, "");//removing end of line
			String result[]=row.split(delimStr);
			for (int i = 0; i < result.length; i++)
			{
				v.addElement(result[i]!=null?result[i].trim():result[i]);
			}
		}else{
			System.out.println("Invalid Row.Row dosnt ends with secondary delimeter.");
		}

		return v;
	}
	
	public static Method getMethod(Class clazz, String methodName) {
		Method methods[] = clazz.getDeclaredMethods(); 
		Method tempMethod = null;
		for(Method method: methods){
			if(method.getName().equalsIgnoreCase(methodName)) {
				tempMethod = method;
				Class params[] = method.getParameterTypes();
				if(params.length == 4)
					return method;
			}
		}
		return tempMethod;
	}
	
	public void readAckFile(File fileUpload, ProcessDataFileEOD dd,SyncMasterTemplateIn syncMasterTemplate,String master) {

		BufferedReader count_in=null;
		BufferedReader in=null;
		try {
			int count = 0;
			
			if (fileUpload == null) {
				return;
			} else {
				count_in = new BufferedReader(new FileReader(fileUpload));
			}
			
			String row;
			ArrayList maxHolder = new ArrayList();
			int maxCount = 0;
			int lastRowCount = 0;
			long rowCheckSumTotal = 0;
			//Get row count of Last record
			while ((row = count_in.readLine()) != null) {
				lastRowCount++;
			}
			dd.setTotalRecordCount(lastRowCount-dd.rowStartIndex);
			
			if (fileUpload != null) {
				in = new BufferedReader(new FileReader(fileUpload));
			}
			
			//validating control file count with total record count
			//if(dd.getTotalRecordCount()==dd.getFileRecordCount() ){
				int mappedColumnLength = syncMasterTemplate.getMappedColumns().getColumn()!=null?syncMasterTemplate.getMappedColumns().getColumn().length:0;
				int defaultColumnLength = syncMasterTemplate.getDefaultColumns().getColumn()!=null?syncMasterTemplate.getDefaultColumns().getColumn().length:0;
				int totalcolumns=mappedColumnLength+defaultColumnLength;
				
				String[] mappedFields= new String[mappedColumnLength + defaultColumnLength];
				
				while ((row = in.readLine()) != null) {//reading row
					validationStatus=true;//reset status for every row.
					
					String [][]errorList = new String[mappedColumnLength][4];
					//validating end of line
					if(null!=syncMasterTemplate.getSecondaryDelimiter() && ! "".equals(syncMasterTemplate.getSecondaryDelimiter())){
						if(!row.trim().endsWith(syncMasterTemplate.getSecondaryDelimiter())){
							validationStatus = false;
							errorList[1][0] = "";
							errorList[1][1] = "Row is not ended as per configured delimeter";
							errorHm.put(new Integer(count), errorList);
							count++;
							continue;
							
						}
					}
					                            
	//				HashMap hm = new HashMap();
					HashMap<String,Object> valueMap = new HashMap<String,Object>();
					//Parsing row into column
					Vector vValus = datToVector(row.trim(), syncMasterTemplate.getDelimiter(),syncMasterTemplate.getSecondaryDelimiter());
					long rowCheckSum = 0;
					
					if (count < dd.rowStartIndex) {
						count++;
						continue;
					}
					else if ((count > dd.rowEndIndex) && (dd.rowEndIndex != 0)) {
						break;
					}
	
					if (row.equals("")) {
						validationStatus = false;
						errorList[1][0] = "";
						errorList[1][1] = "Remove all empty line from the file";
						errorHm.put(new Integer(count), errorList);
						count++;
						continue;
					}
					
					if (vValus.size() == mappedColumnLength) {// Check if mapped column and fields in file are same
							Column[] column = syncMasterTemplate.getMappedColumns().getColumn();
							//looping column details
							for (int i = dd.columnStartIndex; i < column.length; i++) {
								
								if(count==1){ //get mapped field only once for first row.
									 if(column[i].getMappedFieldName()!=null && !"".equals(column[i].getMappedFieldName())){
										 mappedFields[i]=column[i].getMappedFieldName(); 
									 }else{
										 //MAPPING NOT FOUND ERROR.
									 }
								}
								valueMap.put(((String)column[i].getMappedFieldName()).toUpperCase(), vValus.elementAt(i));
							}
							
						}else{
							//To do - reject this record
							validationStatus = false;
							//userMasterValidationStatus = false;
							errorList[1][0] = "";
							errorList[1][1] = "Number of columns is not as per template";
							//errorList[1][2]=(String)vValus.elementAt(2);
						}
					
					/*Column[] column = syncMasterTemplate.getDefaultColumns().getColumn();
					int defaultFieldCounter=0;
					//looping column details
					for (int i = mappedColumnLength; i < totalcolumns; i++) {
						if(count==1){ //get mapped field only once for first row.
							 if(column[defaultFieldCounter].getMappedFieldName()!=null && !"".equals(column[defaultFieldCounter].getMappedFieldName())){
								 mappedFields[i]=column[defaultFieldCounter].getMappedFieldName(); 
							 }else{
								 //MAPPING NOT FOUND ERROR.
							 }
						}
						if("ROWINDEX".equalsIgnoreCase((column[defaultFieldCounter].getMappedFieldName()))){
							valueMap.put(((String)column[defaultFieldCounter].getMappedFieldName()).toUpperCase(), count);
						}else{
							//hm.put(((String)column[defaultFieldCounter].getName()).toUpperCase(), (String)column[defaultFieldCounter].getDefaultValue());
							valueMap.put(((String)column[defaultFieldCounter].getMappedFieldName()).toUpperCase(), (String)column[defaultFieldCounter].getDefaultValue());
						}
						defaultFieldCounter++;
					}*/
					
					
					
					Object obToStore=null;
					//=====================Anil==============
					DefaultLogger.debug(this, "Master: "+master+", mappedFields: "+Arrays.toString(mappedFields));
					DefaultLogger.debug(this, "values: "+valueMap);
						obToStore = populateForm(syncMasterTemplate, mappedFields,valueMap);
					if(!"CLIMS_CPS_CONTROL_FILE".equals(master)){
							
						try{
							eodSyncOutMaster.updateRecordsEodSyncStatus(master,obToStore);
						}catch (Exception e) {
							DefaultLogger.debug(this, "Error while updating ackStatus record for "+master);
							e.printStackTrace();
							
						}
					}
					//========================Anil==================
					if(!validationStatus && errorList!=null) {
						errorHm.put(new Integer(count), errorList);
					}
					
					if( dd.maxRecProcess == null)
					{
						if (validationStatus && obToStore!=null) {
	//						dd.rowArray.add(hm);
							dd.rowArray.add(obToStore);
							validationStatus=true;//reset Flag for next row;
						}else{
						}
						
					}
					else if (dd.maxRecProcess.intValue() >= maxCount) {
						dd.rowArray.add(maxHolder);
						maxCount = 0;
						maxHolder = new ArrayList();
					}
					if (dd.maxRecProcess != null) {
	//					maxHolder.add(hm);
						dd.rowArray.add(obToStore);
						validationStatus=true;//reset Flag for next row;
						maxCount++;
					}
					count++;
					rowCheckSumTotal = rowCheckSumTotal+ rowCheckSum;
				}// end while
				maxRowCount = count;
			//}
			
			if ((dd.maxRecProcess != null) && !maxHolder.isEmpty()) {
				if(validationStatus) {
					dd.rowArray.add(maxHolder);
				}else{
					dd.rowArray.add(maxHolder);
				}
			}

		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			e.printStackTrace();
			throw new ProcessFileException(e.getMessage());
		}finally{
			try {
				if (count_in != null) {
					count_in.close();
				}
			}catch (Exception e) {}
			try {
				if (in != null) {
					in.close();
				}
			}catch (Exception e) {}
		}
	
	}
}
