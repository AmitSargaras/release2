
package com.integrosys.cms.ui.feed.bond.item;

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
import com.integrosys.cms.app.feed.bus.bond.BondFeedGroupException;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry;
import com.integrosys.cms.app.feed.proxy.bond.IBondFeedProxy;
import com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.bond.OBBondFeedGroupTrxValue;
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
public class MakerUploadBondItemCommand extends AbstractCommand implements ICommonEventConstant ,ICMSConstant{
	

	private IBondFeedProxy bondFeedProxy;




	/**
	 * @return the bondFeedProxy
	 */
	public IBondFeedProxy getBondFeedProxy() {
		return bondFeedProxy;
	}

	/**
	 * @param bondFeedProxy the bondFeedProxy to set
	 */
	public void setBondFeedProxy(IBondFeedProxy bondFeedProxy) {
		this.bondFeedProxy = bondFeedProxy;
	}

	/**
	 * Default Constructor
	 */
	
	
	public MakerUploadBondItemCommand() {
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
	        		{ BondItemForm.MAPPER, "com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry", FORM_SCOPE },
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
					{"obBondFeedEntry", "com.integrosys.cms.app.feed.bus.bond.OBBondFeedEntry", FORM_SCOPE },
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
			String codeNotExist = "";
			String transDenied="false";

			String remarks = (String) map.get("remarks");
			Locale local = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			/*set local*/
			CSVReader.setLocale(local);
			IBondFeedGroupTrxValue trxValueOut = new OBBondFeedGroupTrxValue();
			try {
				IBondFeedEntry obBondFeedEntry = (IBondFeedEntry) map.get(BondItemForm.MAPPER);	
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");

				
				//check is file type is not then set error page.	
			    
			    if(!obBondFeedEntry.getFileUpload().getFileName().toLowerCase().endsWith(".csv")){
			    	fileType ="NOT_CSV";
			    	strError ="errorEveList";
			    } else {
			    
			    	  //check if any pending files not approved
					
					if(getBondFeedProxy().isPrevFileUploadPending()){
						fileUploadPending="PENDING";
						strError ="errorEveList";
					} else {
			    	
				//Read Uploaded File 
				ProcessDataFile dataFile = new ProcessDataFile();
				ArrayList resultList = dataFile.processFile(obBondFeedEntry.getFileUpload(),BOND_ITEM_UPLOAD);


				//Check if check-sum FooterCount must meet row Count
				
				if(dataFile.isCheckSumFooter()){
					fileCheckSum="MISMATCH";
					strError ="errorEveList";
				}else{		
					
					//Check is Records available in System for Updates.
					HashMap uniqueErrorMap = new HashMap();
					String [][]errorList = new String[1][2];
					boolean isRecordsmMtched = false;
					List bondCodeList = new ArrayList() ;
					for (int index = 0; index < resultList.size(); index++) {
						int incr = 0;
						HashMap eachDataMap = (HashMap) resultList.get(index);
						boolean validCode=false;
						String bondCode = (String) eachDataMap.get("BOND_CODE");
						String bondName = (String) eachDataMap.get("NAME");
						bondCodeList.add(bondCode);
							if (ASSTValidator.isValidPercentRoundBrackets(bondName)) {
								errorList[incr][0] = "NAME";
								errorList[incr][1] = "Special Character(s) Not Allowed except {% ( )}";
								uniqueErrorMap.put(new Integer(index),errorList);
								dataFile.setErrorList(uniqueErrorMap);
								dataFile.setValid(false);
								strError = "errorEveList";
								incr++;
						}
					}
					if(!bondCodeList.isEmpty())
					{
						boolean validCode = false;
						validCode = getBondFeedProxy().isBondCodeExist(bondCodeList);
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
			    
			 // If there no error
				if(dataFile.isValid() &&  fileCheckSum.equals("") && fileUploadPending.equals("") && fileType.equals("")){	 
					
						//create a record to get Transaction id
				    	trxValueOut = getBondFeedProxy().makerInsertMapperBondFeedEntry(ctx, obFileMapperID);
				    	
				    	//Set Transaction ID into Mapper Table
					    OBFileMapperMaster obFileMapperMaster = new OBFileMapperMaster();
					    obFileMapperMaster.setFileId(fileId);
					    obFileMapperMaster.setTransId(trxValueOut.getTransactionID());
					    
					    //insert records in BondFeedEntry staging table and store it's ID and transactionId in a master table 
						try {
						int noOfRowsInserted = getBondFeedProxy().insertBondFeedEntry(obFileMapperMaster, userName, resultList);
						DefaultLogger.debug(this,"No of rows inserted == " + noOfRowsInserted);
						resultMap.put("request.ITrxValue", trxValueOut);
						} catch (Exception e) {
							transDenied="true";     
							strError = "errorEveList";   
							getBondFeedProxy().deleteTransaction(obFileMapperMaster); 
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
			        resultMap.put("transDenied",transDenied);
			        resultMap.put("codeNotExist",codeNotExist);
					resultMap.put("fileUploadPending",fileUploadPending);
					resultMap.put("fileCheckSum",fileCheckSum);	
					resultMap.put("fileType", fileType);
			    	resultMap.put("errorEveList", strError);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					
					return returnMap;
			}catch (BondFeedGroupException ex) {
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
