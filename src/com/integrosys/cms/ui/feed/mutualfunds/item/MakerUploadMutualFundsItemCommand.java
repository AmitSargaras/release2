
package com.integrosys.cms.ui.feed.mutualfunds.item;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedEntry;
import com.integrosys.cms.app.feed.bus.mutualfunds.MutualFundsFeedGroupException;
import com.integrosys.cms.app.feed.proxy.mutualfunds.IMutualFundsFeedProxy;
import com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.mutualfunds.OBMutualFundsFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.batch.common.filereader.CSVReader;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;

/**
@author $Govind: Sahu$
* Command for Credit Approval
 */
public class MakerUploadMutualFundsItemCommand extends AbstractCommand implements ICommonEventConstant,ICMSConstant {
	

	private IMutualFundsFeedProxy mutualFundsFeedProxy;





	/**
	 * @return the mutualFundsFeedProxy
	 */
	public IMutualFundsFeedProxy getMutualFundsFeedProxy() {
		return mutualFundsFeedProxy;
	}

	/**
	 * @param mutualFundsFeedProxy the mutualFundsFeedProxy to set
	 */
	public void setMutualFundsFeedProxy(IMutualFundsFeedProxy mutualFundsFeedProxy) {
		this.mutualFundsFeedProxy = mutualFundsFeedProxy;
	}

	/**
	 * Default Constructor
	 */
	
	
	public MakerUploadMutualFundsItemCommand() {
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
	        		{ MutualFundsItemForm.MAPPER, "com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedEntry", FORM_SCOPE },
					{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
					{"remarks", "java.lang.String", REQUEST_SCOPE},
	                {"event", "java.lang.String", REQUEST_SCOPE},
	                { com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale",
	    				GLOBAL_SCOPE }
	          	   
	        }
	        );
	    }

	 public String[][] getResultDescriptor() {
			return (new String[][] {
					{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
					{"obMutualFundsFeedEntry", "com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedEntry", FORM_SCOPE },
					{"errorList", "java.util.HashMap", REQUEST_SCOPE},
					{"rowCount", "java.lang.Integer", REQUEST_SCOPE},
					{"fileUploadPending", "java.lang.String", REQUEST_SCOPE},
					{"fileCheckSum", "java.lang.String", REQUEST_SCOPE},
					{"fileType", "java.lang.String", REQUEST_SCOPE},
					{"transDenied", "java.lang.String", REQUEST_SCOPE},
					{"codeNotExist", "java.lang.String", REQUEST_SCOPE},
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
			String fileCheckSum ="";
			String fileUploadPending ="";
			String transDenied="false";
			String codeNotExist = "";
			

			String remarks = (String) map.get("remarks");
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			/*set local*/
			CSVReader.setLocale(locale);
			IMutualFundsFeedGroupTrxValue trxValueOut = new OBMutualFundsFeedGroupTrxValue();
			try {
				IMutualFundsFeedEntry obMutualFundsFeedEntry = (IMutualFundsFeedEntry) map.get(MutualFundsItemForm.MAPPER);	
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");

				
				//check is file type is not then set error page.	
			    
			    if(!obMutualFundsFeedEntry.getFileUpload().getFileName().toLowerCase().endsWith(".csv")){
			    	fileType ="NOT_CSV";
			    	strError ="errorEveList";
			    } else {
			    
			    	  //check if any pending files not approved
					
					if(getMutualFundsFeedProxy().isPrevFileUploadPending()){
						fileUploadPending="PENDING";
						strError ="errorEveList";
					} else {
			    	
				//Read Uploaded File 
				ProcessDataFile dataFile = new ProcessDataFile();
				ArrayList resultList = dataFile.processFile(obMutualFundsFeedEntry.getFileUpload(),MUTUAL_FUNDS_ITEM_UPLOAD);


				//Check if check-sum FooterCount must meet row Count
				
				if(dataFile.isCheckSumFooter()){
					fileCheckSum="MISMATCH";
					strError ="errorEveList";
				}else{		
					
					//Check is Records available in System for Updates.
					HashMap uniqueErrorMap = new HashMap();
					String [][]errorList = new String[2][2];
					List schemeCodeList = new ArrayList() ;
					for (int index = 0; index < resultList.size(); index++) {
						int incr = 0;
						HashMap eachDataMap = (HashMap) resultList.get(index);
						boolean validCode=false;
						String bondCode = (String) eachDataMap.get("SCHEME_CODE");
						String schemeName = (String) eachDataMap.get("SCHEME_NAME");
						schemeCodeList.add(bondCode);
						if (ASSTValidator.isValidMutualFundName(schemeName)) {
							errorList[incr][0] = "NAME";
							errorList[incr][1] = "Special Character(s) Not Allowed except {& ( )-}";
							uniqueErrorMap.put(new Integer(index),errorList);
							dataFile.setErrorList(uniqueErrorMap);
							dataFile.setValid(false);
							strError = "errorEveList";
							incr++;
					}
						
					}
					if(!schemeCodeList.isEmpty())
					{
						boolean validCode = false;
						
						validCode = getMutualFundsFeedProxy().isMutualFundsCodeExist(schemeCodeList);
						
						if(!validCode){
							codeNotExist="CODENOTFOUND";
							dataFile.setValid(false);
							strError ="errorEveList";
						}
					} //end for loop
					
					
			    for (int index = 0; index < resultList.size(); index++) {
			    		int incr = 0;
						HashMap eachDataMap = (HashMap) resultList.get(index);
						String strStartDate = (String) eachDataMap.get("START_DATE");
						String strExpiryDate = (String) eachDataMap.get("EXPIRY_DATE");
					
					if(strExpiryDate != null && (strExpiryDate.trim()).length()>0){
						Date dtExpiryDate = DateUtil.convertDate(locale, strExpiryDate);
						if (dtExpiryDate==null) {
							 errorList[incr][0] = "EXPIRY_DATE";
							 errorList[incr][1] = "Error in date format";
							 uniqueErrorMap.put(new Integer(index), errorList);
							 dataFile.setErrorList(uniqueErrorMap);
							 dataFile.setValid(false);
							 strError ="errorEveList";
							 incr++;
						}
						else if (dtExpiryDate.before(new Date())) {
							 errorList[incr][0] = "EXPIRY_DATE";
							 errorList[incr][1] = "Cannot be a past date.";
							 uniqueErrorMap.put(new Integer(index), errorList);
							 dataFile.setErrorList(uniqueErrorMap);
							 dataFile.setValid(false);
							 strError ="errorEveList";
							 incr++;
						}
					}	
					
					if(strStartDate != null && strStartDate.trim().length()>0){
						Date dtStartDate = DateUtil.convertDate(locale, strStartDate);
						if (dtStartDate==null) {
							 errorList[incr][0] = "START_DATE";
							 errorList[incr][1] = "Error in date format";
							 uniqueErrorMap.put(new Integer(index), errorList);
							 dataFile.setErrorList(uniqueErrorMap);
							 dataFile.setValid(false);
							 strError ="errorEveList";
							 incr++;
						}
						else if (dtStartDate.after(new Date())) {
							 errorList[incr][0] = "START_DATE";
							 errorList[incr][1] = "Cannot be a future date.";
							 uniqueErrorMap.put(new Integer(index), errorList);
							 dataFile.setErrorList(uniqueErrorMap);
							 dataFile.setValid(false);
							 strError ="errorEveList";
							 incr++;
						}
					 }
					}//end for loop
					
					
				  //Insert a dummy fieldId (a random number) into a table in order to generate Transaction ID.
			    long fileId = (long)(Math.random()*1000000);
				OBFileMapperID obFileMapperID = new OBFileMapperID();
				obFileMapperID.setFileId(fileId);
				String userName = "";
			    if (ctx != null) {
					userName = ctx.getUser().getUserName();
			    }
			    
			 // If there no error
				if(dataFile.isValid() &&  fileCheckSum.equals("") && fileUploadPending.equals("") && fileType.equals("")){	 
					
						//create a record to get Transaction id
				    	trxValueOut = getMutualFundsFeedProxy().makerInsertMapperMutualFundsFeedEntry(ctx, obFileMapperID);
				    	
				    	//Set Transaction ID into Mapper Table
					    OBFileMapperMaster obFileMapperMaster = new OBFileMapperMaster();
					    obFileMapperMaster.setFileId(fileId);
					    obFileMapperMaster.setTransId(trxValueOut.getTransactionID());
					    
					    //insert records in MutualFundsFeedEntry staging table and store it's ID and transactionId in a master table 
			    try{
						int noOfRowsInserted = getMutualFundsFeedProxy().insertMutualFundsFeedEntry(obFileMapperMaster, userName, resultList);
						DefaultLogger.debug(this,"No of rows inserted == " + noOfRowsInserted);
						resultMap.put("request.ITrxValue", trxValueOut);
				} catch (RuntimeException e) {
					transDenied="true";     //A shiv 170811
					strError = "errorEveList";   //A shiv 170811
					getMutualFundsFeedProxy().deleteTransaction (obFileMapperMaster);
				}
				}
				
				//If there is a Error
				if(dataFile.isValid()==false) { 
					resultMap.put("rowCount", new Integer(dataFile.getMaxCount()));
					resultMap.put("errorList",dataFile.getErrorList());	
					strError ="errorEveList";
				}
				} //end else for CheckSum
					}// end pending app
			    }// end csv
			    	resultMap.put("codeNotExist",codeNotExist);
			    	resultMap.put("transDenied",transDenied); 
					resultMap.put("fileUploadPending",fileUploadPending);
					resultMap.put("fileCheckSum",fileCheckSum);	
					resultMap.put("fileType", fileType);
			    	resultMap.put("errorEveList", strError);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					
					return returnMap;
			}catch (MutualFundsFeedGroupException ex) {
				DefaultLogger.debug(this, "got exception in doExecute" + ex);
				ex.printStackTrace();
				throw (new CommandProcessingException(ex.getMessage()));
			}
//			catch (TransactionException e) {
//				DefaultLogger.debug(this, "got exception in doExecute" + e);
//				throw (new CommandProcessingException(e.getMessage()));
//			}
			catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }
			
			
	    }


}
