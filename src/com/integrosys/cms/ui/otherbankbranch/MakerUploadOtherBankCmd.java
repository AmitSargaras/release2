package com.integrosys.cms.ui.otherbankbranch;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.otherbank.bus.OBOtherBank;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue;
import com.integrosys.cms.app.otherbank.trx.OBOtherBankTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.valuationAgency.bus.ValuationAgencyException;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;

public class MakerUploadOtherBankCmd extends AbstractCommand implements ICommonEventConstant {
	public static final String OTHER_BANK_UPLOAD = "OtherBankUpload";
	
	public IOtherBankProxyManager getOtherBankProxyManager() {
		return otherBankProxyManager;
	}

	public void setOtherBankProxyManager(
			IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
	}
	private IOtherBankProxyManager otherBankProxyManager;
  
	private  static boolean isVACodeUnique = true;
	
	/**
	 * @return the isVACodeUnique
	 */
	public static boolean isVACodeUnique() {
		return isVACodeUnique;
	}

	/**
	 * @param isVACodeUnique the isVACodeUnique to set
	 */
	public static void setVACodeUnique(boolean isVACodeUnique) {
		MakerUploadOtherBankCmd.isVACodeUnique = isVACodeUnique;
	}

	

	
	/**
	 * Default Constructor
	 */
	
	
	public MakerUploadOtherBankCmd() {
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
	        		{"IOtherBankTrxValue", "com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue", SERVICE_SCOPE},
					{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
					{"remarks", "java.lang.String", REQUEST_SCOPE},
	                {"event", "java.lang.String", REQUEST_SCOPE},
	            	{"otherbankCode", "java.lang.String", REQUEST_SCOPE },
	        		{"OtherBankObj", "com.integrosys.cms.app.otherbank.bus.OBOtherBank", FORM_SCOPE }
	        }
	        );
	    }

	 public String[][] getResultDescriptor() {
			return (new String[][] {
					{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
					{"OtherBankObj", "com.integrosys.cms.app.otherbank.bus.OBOtherBank", FORM_SCOPE },
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
			 HashMap exceptionMap = new HashMap();
			

			String remarks = (String) map.get("remarks");
			
			IOtherBankTrxValue trxValueOut = new OBOtherBankTrxValue();
			try {
				OBOtherBank otherbank = (OBOtherBank) map.get("OtherBankObj");			
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				String strError = "";
				String fileCheckSum ="";
				String fileUploadPending ="";
				String transDenied="false";
				
				//check is file type is not then set error page.	
			    String fileType = "";
			    if(!otherbank.getFileUpload().getFileName().toLowerCase().endsWith(".csv")){
			    	fileType ="NOT_CSV";
			    	strError ="errorEveList";
			    }else {
			    	
			    	//Read Uploaded File 
					ProcessDataFile dataFile = new ProcessDataFile();
					ArrayList resultList = dataFile.processFile(otherbank.getFileUpload(),OTHER_BANK_UPLOAD);
				  //check if any pending files not approved
				
				if(getOtherBankProxyManager().isPrevFileUploadPending()){  
					fileUploadPending="PENDING";
					strError ="errorEveList";
				}else {
					
					
					
					//Check if check-sum FooterCount must meet row Count
					if(dataFile.isCheckSumFooter()){
						fileCheckSum="MISMATCH";
						strError ="errorEveList";
					}else { 
						  long fileId = (long)(Math.random()*1000000);
							OBFileMapperID obFileMapperID = new OBFileMapperID();
							obFileMapperID.setFileId(fileId);
							String userName = "";
						    if (ctx != null) {
								userName = ctx.getUser().getUserName();
						    }

							HashMap uniqueErrorMap = new HashMap();
							String[][] errorList;
							for (int index = 0; index < resultList.size(); index++) {
								int incr = 0;
								HashMap eachDataMap = (HashMap) resultList.get(index);
								errorList = new String[3][2];
								boolean validCode = false;
								boolean isValidBankName = false;
								String bankCode = (String) eachDataMap.get("BANK_CODE");
								
								String codeValue = "";
								String countryCode = (String) eachDataMap.get("COUNTRY");
								String regionCode = (String) eachDataMap.get("REGION");
								String stateCode = (String) eachDataMap.get("STATE");
								String cityCode = (String) eachDataMap.get("CITY");
								String bankName = (String) eachDataMap.get("BANK_NAME");
								
																
								if (bankCode != null && bankCode.trim() != "") {
									try {
										validCode = getOtherBankProxyManager().isUniqueCode(bankCode);  
									} catch (OtherBankException e) {
										throw new OtherBankException("BANK CODE null !");
									} catch (Exception e) {
										throw new OtherBankException("BANK CODE null!");
									}
								}
								if (validCode) {
									errorList[incr][0] = "BANK_CODE";
									errorList[incr][1] = "Already exist";
									uniqueErrorMap.put(new Integer(index),errorList);
									dataFile.setErrorList(uniqueErrorMap);
									dataFile.setValid(false);
									strError = "errorEveList";
									incr++;
								}
								
								// Added for Upload Validation								
								if ( countryCode != null && !countryCode.trim().equals("") ) {
									try {
										codeValue = getOtherBankProxyManager().isCodeExisting(countryCode,regionCode,stateCode,cityCode);
										if ( codeValue != null && ! codeValue.equals("") ){
											if( codeValue.equalsIgnoreCase("countryCode") )
												errorList[incr][0] = "COUNTRY_CODE";
											else if( codeValue.equalsIgnoreCase("regionCode") )
												errorList[incr][0] = "REGION_CODE";
											else if( codeValue.equalsIgnoreCase("stateCode") )
												errorList[incr][0] = "STATE_CODE";
											else if( codeValue.equalsIgnoreCase("cityCode") )
												errorList[incr][0] = "CITY_CODE";
											
											errorList[incr][1] = "Invalid Code";
											uniqueErrorMap.put(new Integer(index),errorList);
											dataFile.setErrorList(uniqueErrorMap);
											dataFile.setValid(false);
											strError = "errorEveList";
											incr++;
										}										
									} catch (OtherBankException e) {
										throw new OtherBankException("Code Value Invalid or Does Not Exist");
									} catch (Exception e) {
										throw new OtherBankException("Code Value Invalid or Does Not Exist ");
									}
								}
																
								if ( bankName != null && !bankName.trim().equals("") ) {
									try {										
										isValidBankName = ASSTValidator.isValidOtherBankName(bankName);										
									} catch (OtherBankException e) {
										throw new OtherBankException("Special Character(s) Not Allowed except {& - ' ( ), .} for Bank Name");
									} catch (Exception e) {
										throw new OtherBankException("Special Character(s) Not Allowed except {& - ' ( ), .} for Bank Name");
									}
								}
								else {
									errorList[incr][0] = "BANK_NAME";
									errorList[incr][1] = "This is Mandatory Field";
									uniqueErrorMap.put(new Integer(index),errorList);
									dataFile.setErrorList(uniqueErrorMap);
									dataFile.setValid(false);
									strError = "errorEveList";
									incr++;
								}
								if ( isValidBankName ) {
									errorList[incr][0] = "BANK_NAME";
									errorList[incr][1] = "Special Character(s) Not Allowed except {& - ' ( ), .}";
									uniqueErrorMap.put(new Integer(index),errorList);
									dataFile.setErrorList(uniqueErrorMap);
									dataFile.setValid(false);
									strError = "errorEveList";
									incr++;   
								}
							}
							if(dataFile.isValid() &&  fileCheckSum.equals("") && fileUploadPending.equals("") && fileType.equals("")){	 
								
								//create a record to get Transaction id
						    	trxValueOut = getOtherBankProxyManager().makerInsertMapperOtherBank(ctx, obFileMapperID);
						    	
						    	//Set Transaction ID into Mapper Table
							    OBFileMapperMaster obFileMapperMaster = new OBFileMapperMaster();
							    obFileMapperMaster.setFileId(fileId);
							    obFileMapperMaster.setTransId(trxValueOut.getTransactionID());
							    
							    //insert records in OtherBank staging table and store it's ID and transactionId in a master table 
								try {
									int noOfRowsInserted = getOtherBankProxyManager().insertOtherBank(obFileMapperMaster, userName, resultList);
									DefaultLogger.debug(this,"No of rows inserted == " + noOfRowsInserted);
									resultMap.put("request.ITrxValue", trxValueOut);
								} catch (Exception e) {
									transDenied="true";     
									strError = "errorEveList";   
									getOtherBankProxyManager().deleteTransaction(obFileMapperMaster);
								}
						}
						
						//If there is a Error
						if(dataFile.isValid()==false) { 
							resultMap.put("rowCount", new Integer(dataFile.getMaxCount()));
							resultMap.put("errorList",dataFile.getErrorList());			
							strError ="errorEveList";		
						}
					}
					
					}
					
						 // If there no error
					}
			    
			    resultMap.put("transDenied",transDenied);
				resultMap.put("fileUploadPending",fileUploadPending);
				resultMap.put("fileCheckSum",fileCheckSum);	
				resultMap.put("fileType", fileType);
		    	resultMap.put("errorEveList", strError);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				return returnMap;
			}catch (OtherBankException ex) {
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
