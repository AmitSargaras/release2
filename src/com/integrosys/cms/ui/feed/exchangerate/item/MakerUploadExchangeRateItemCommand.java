
package com.integrosys.cms.ui.feed.exchangerate.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.forex.ForexFeedGroupException;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;
import com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.forex.OBForexFeedGroupTrxValue;
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
public class MakerUploadExchangeRateItemCommand extends AbstractCommand implements ICommonEventConstant,ICMSConstant {
	
	private IForexFeedProxy forexFeedProxy;



	/**
	 * @return the forexFeedProxy
	 */
	public IForexFeedProxy getForexFeedProxy() {
		return forexFeedProxy;
	}

	/**
	 * @param forexFeedProxy the forexFeedProxy to set
	 */
	public void setForexFeedProxy(IForexFeedProxy forexFeedProxy) {
		this.forexFeedProxy = forexFeedProxy;
	}

	/**
	 * Default Constructor
	 */
	
	
	public MakerUploadExchangeRateItemCommand() {
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
	        		{ ExchangeRateItemForm.MAPPER, "com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry", FORM_SCOPE },
	        		{"forexFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue",SERVICE_SCOPE },
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
					{"obForexFeedEntry", "com.integrosys.cms.app.feed.bus.forex.OBForexFeedEntry", FORM_SCOPE },
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
			Locale local = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			/*set local*/
			CSVReader.setLocale(local);
			IForexFeedGroupTrxValue trxValueOut = new OBForexFeedGroupTrxValue();
			try {
				IForexFeedEntry obForexFeedEntry = (IForexFeedEntry) map.get(ExchangeRateItemForm.MAPPER);	
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");

				
				//check is file type is not then set error page.	
			    
			    if(!obForexFeedEntry.getFileUpload().getFileName().toLowerCase().endsWith(".csv")){
			    	fileType ="NOT_CSV";
			    	strError ="errorEveList";
			    } else {
			    
			    	  //check if any pending files not approved
					
					if(getForexFeedProxy().isPrevFileUploadPending()){
						fileUploadPending="PENDING";
						strError ="errorEveList";
					} else {
			    	
				//Read Uploaded File 
				ProcessDataFile dataFile = new ProcessDataFile();
				ArrayList resultList = dataFile.processFile(obForexFeedEntry.getFileUpload(),EXCHANGE_RATE_UPLOAD);


				//Check if check-sum FooterCount must meet row Count
				
				if(dataFile.isCheckSumFooter()){
					fileCheckSum="MISMATCH";
					strError ="errorEveList";
				}else{		
					//Check is Records available in System for Updates.
					HashMap uniqueErrorMap = new HashMap();
					String [][]errorList = new String[1][2];
					List currCodeList = new ArrayList() ;
					for (int index = 0; index < resultList.size(); index++) {
						HashMap eachDataMap = (HashMap) resultList.get(index);
						boolean validCode=false;
						String currCode = (String) eachDataMap.get("BUY_CURRENCY");
						currCodeList.add(currCode);
						
					}
					if(!currCodeList.isEmpty())
					{
						boolean validCode = false;
						
						validCode = getForexFeedProxy().isCurrencyCodeExist(currCodeList);
						
						if(!validCode){
							codeNotExist="CODENOTFOUND";
							dataFile.setValid(false);
							strError ="errorEveList";
						}
					}
					
				  //Insert a dummy fieldId (a random number) into a table in order to generate Transaction ID.
			    long fileId = (long)(Math.random()*1000000);
				OBFileMapperID obFileMapperID = new OBFileMapperID();
				obFileMapperID.setFileId(fileId);
				String userName = "";
			    if (ctx != null) {
					userName = ctx.getUser().getUserName();
			    }
			    //Currency Description Validation
			    for (int index = 0; index < resultList.size(); index++) {
					HashMap eachDataMap = (HashMap) resultList.get(index);
					int incr = 0;
					errorList = new String[1][2];
				
					String currencyDescription = (String) eachDataMap.get("CURRENCY_DESCRIPTION");
											
				    if (ASSTValidator.isValidCurrencyName(currencyDescription)) {
						errorList[incr][0] = "CURRENCY_DESCRIPTION";
						errorList[incr][1] = "Special characters are not allowed except {-}";
						uniqueErrorMap.put(new Integer(index),errorList);
						dataFile.setErrorList(uniqueErrorMap);
						dataFile.setValid(false);
						strError = "errorEveList";
						incr++;
					}
			  }//end for loop
			    
			 // If there no error
				if(dataFile.isValid() &&  fileCheckSum.equals("") && fileUploadPending.equals("") && fileType.equals("")){	 
					
						//create a record to get Transaction id
				    	trxValueOut = getForexFeedProxy().makerInsertMapperForexFeedEntry(ctx, obFileMapperID);
				    	
				    	//Set Transaction ID into Mapper Table
					    OBFileMapperMaster obFileMapperMaster = new OBFileMapperMaster();
					    obFileMapperMaster.setFileId(fileId);
					    obFileMapperMaster.setTransId(trxValueOut.getTransactionID());
					    
					    //insert records in ForexFeedEntry staging table and store it's ID and transactionId in a master table 
						try {
							int noOfRowsInserted = getForexFeedProxy().insertForexFeedEntry(obFileMapperMaster, userName, resultList);
							DefaultLogger.debug(this,"No of rows inserted == " + noOfRowsInserted);
							resultMap.put("request.ITrxValue", trxValueOut);
						} catch (Exception e) {
							transDenied="true";     
							strError = "errorEveList";   
							getForexFeedProxy().deleteTransaction(obFileMapperMaster); 
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
			}catch (ForexFeedGroupException ex) {
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
