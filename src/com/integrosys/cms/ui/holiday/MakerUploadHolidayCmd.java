/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.holiday;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.holiday.bus.OBHoliday;
import com.integrosys.cms.app.holiday.proxy.IHolidayProxyManager;
import com.integrosys.cms.app.holiday.trx.IHolidayTrxValue;
import com.integrosys.cms.app.holiday.trx.OBHolidayTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;

/**
@author $Author: Abhijit R$
* Command for Create Holiday
 */
public class MakerUploadHolidayCmd extends AbstractCommand implements ICommonEventConstant {
	
	public static final String HOLIDAY_UPLOAD = "HolidayUpload";
	private IHolidayProxyManager holidayProxy;

	public IHolidayProxyManager getHolidayProxy() {
		return holidayProxy;
	}

	public void setHolidayProxy(IHolidayProxyManager holidayProxy) {
		this.holidayProxy = holidayProxy;
	}
	
	/**
	 * Default Constructor
	 */
	
	
	public MakerUploadHolidayCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	 public String[][] getParameterDescriptor() {
	        return (new String[][]{
	        		{"IHolidayTrxValue", "com.integrosys.cms.app.holiday.trx.IHolidayTrxValue", SERVICE_SCOPE},
					{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
					{"remarks", "java.lang.String", REQUEST_SCOPE},
	                {"event", "java.lang.String", REQUEST_SCOPE},
	        		{  "holidayObj", "com.integrosys.cms.app.holiday.bus.OBHoliday", FORM_SCOPE }
	        }
	        );
	    }

	 public String[][] getResultDescriptor() {
			return (new String[][] {
					{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
					{ "holidayObj", "com.integrosys.cms.app.holiday.bus.OBHoliday", FORM_SCOPE },
					{"errorList", "java.util.HashMap", REQUEST_SCOPE},
					{"rowCount", "java.lang.Integer", REQUEST_SCOPE},
					{"fileUploadPending", "java.lang.String", REQUEST_SCOPE},
					{"fileCheckSum", "java.lang.String", REQUEST_SCOPE},
					{"fileType", "java.lang.String", REQUEST_SCOPE},
					{"transDenied", "java.lang.String", REQUEST_SCOPE}
					});				  
		}
	    /**
	     * This method does the Business operations  with the HashMap and put the results back into
	     * the HashMap.
	     *
	     * @param map is of type HashMap
	     * @return HashMap with the Result
	     */
	    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
			HashMap returnMap = new HashMap();
			HashMap resultMap = new HashMap();
			String strError = "";
			String fileType = "";
			String fileUploadPending ="";
			String fileCheckSum ="";
			String transDenied="false";

			String remarks = (String) map.get("remarks");
			
			IHolidayTrxValue trxValueOut = new OBHolidayTrxValue();
			try {
				OBHoliday holiday = (OBHoliday) map.get("holidayObj");			
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
 
				
				//check is file type is not then set error page.	
			    if(!holiday.getFileUpload().getFileName().toLowerCase().endsWith(".csv")){
			    	fileType ="NOT_CSV";
			    	strError ="errorEveList";
			    }else{

				  //check if any pending files not approved
				if(getHolidayProxy().isPrevFileUploadPending()){
					fileUploadPending="PENDING";
					strError ="errorEveList";
				}else{
					//Read Uploaded File 
					ProcessDataFile dataFile = new ProcessDataFile();
					ArrayList resultList = dataFile.processFile(holiday.getFileUpload(),HOLIDAY_UPLOAD);
					
				//Check if check-sum FooterCount must meet row Count
				if(dataFile.isCheckSumFooter()){
					fileCheckSum="MISMATCH";
					strError ="errorEveList";
				}else {
				
				  //Insert a dummy fieldId (a random number) into a table in order to generate Transaction ID.
			    long fileId = (long)(Math.random()*1000000);
				OBFileMapperID obFileMapperID = new OBFileMapperID();
				obFileMapperID.setFileId(fileId);
				String userName = "";
			    if (ctx != null) {
					userName = ctx.getUser().getUserName();
			    }
			    
			    // Add validation for isRecurrent
				HashMap uniqueErrorMap = new HashMap();
				String[][] errorList;
				for (int index = 0; index < resultList.size(); index++) {
					HashMap eachDataMap = (HashMap) resultList.get(index);
					errorList = new String[1][2];
					String isRecurrent = (String) eachDataMap.get("IS_RECURRENT");
					
					if ( isRecurrent!=null && !(isRecurrent.equals("on") || isRecurrent.equals("")) ) {
						errorList[0][0] = "IS_RECURRENT";
						errorList[0][1] = "Invalid value";
						uniqueErrorMap.put(new Integer(index),errorList);
						dataFile.setErrorList(uniqueErrorMap);
						dataFile.setValid(false);
						strError = "errorEveList";
					}
				}
    
			 // If there no error
				if(dataFile.isValid() &&  fileCheckSum.equals("") && fileUploadPending.equals("") && fileType.equals("")){	 
					
						//create a record to get Transaction id
				    	trxValueOut = getHolidayProxy().makerInsertMapperHoliday(ctx, obFileMapperID);
				    	
				    	//Set Transaction ID into Mapper Table
					    OBFileMapperMaster obFileMapperMaster = new OBFileMapperMaster();
					    obFileMapperMaster.setFileId(fileId);
					    obFileMapperMaster.setTransId(trxValueOut.getTransactionID());
					    
					    //insert records in Holiday staging table and store it's ID and transactionId in a master table 
						try {
							int noOfRowsInserted = getHolidayProxy().insertHoliday(obFileMapperMaster, userName, resultList);
							DefaultLogger.debug(this,"No of rows inserted == " + noOfRowsInserted);
							resultMap.put("request.ITrxValue", trxValueOut);
						} catch (Exception e) {
							transDenied="true";     
							strError = "errorEveList";   
							getHolidayProxy().deleteTransaction(obFileMapperMaster);
						}
				}
				
				//If there is a Error
				if(dataFile.isValid()==false) { 
					resultMap.put("rowCount", new Integer(dataFile.getMaxCount()));
					resultMap.put("errorList",dataFile.getErrorList());
					strError ="errorEveList";
				}
				}//end else if for footer mismatch
				}//end else if for pending approval
			    }//end else if for CSV

			    resultMap.put("transDenied",transDenied);
				resultMap.put("fileUploadPending",fileUploadPending);
				resultMap.put("fileCheckSum",fileCheckSum);	
				resultMap.put("fileType", fileType);
		    	resultMap.put("errorEveList", strError);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				return returnMap;
			}catch (HolidayException ex) {
				DefaultLogger.debug(this, "got exception in doExecute" + ex);
				ex.printStackTrace();
				throw (new CommandProcessingException(ex.getMessage()));
			}
			catch (TransactionException e) {
				DefaultLogger.debug(this, "got exception in doExecute" + e);
				throw (new CommandProcessingException(e.getMessage()));
			}
			catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }
			
			
	    }


}
