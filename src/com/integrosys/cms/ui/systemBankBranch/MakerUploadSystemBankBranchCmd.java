/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.systemBankBranch;

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
import com.integrosys.cms.app.systemBankBranch.bus.OBSystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.bus.SystemBankBranchException;
import com.integrosys.cms.app.systemBankBranch.proxy.ISystemBankBranchProxyManager;
import com.integrosys.cms.app.systemBankBranch.trx.ISystemBankBranchTrxValue;
import com.integrosys.cms.app.systemBankBranch.trx.OBSystemBankBranchTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.valuationAgency.bus.ValuationAgencyException;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;

/**
@author $Author: Abhijit R$
* Command for Create System Bank Branch
 */
public class MakerUploadSystemBankBranchCmd extends AbstractCommand implements ICommonEventConstant {
	public static final String SYS_BANK_BRANCH_UPLOAD = "SystemBankBranchUpload";
	
	private ISystemBankBranchProxyManager systemBankBranchProxy;

	public ISystemBankBranchProxyManager getSystemBankBranchProxy() {
		return systemBankBranchProxy;
	}

	public void setSystemBankBranchProxy(ISystemBankBranchProxyManager systemBankBranchProxy) {
		this.systemBankBranchProxy = systemBankBranchProxy;
	}
	
	
	/**
	 * Default Constructor
	 */
	
	
	public MakerUploadSystemBankBranchCmd() {
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
	        		{"ISystemBankBranchTrxValue", "com.integrosys.cms.app.systemBankBranch.trx.ISystemBankBranchTrxValue", SERVICE_SCOPE},
	                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	                {"remarks", "java.lang.String", REQUEST_SCOPE},
	                {"event", "java.lang.String", REQUEST_SCOPE},
	        		{ "systemBankBranchObj", "com.integrosys.cms.app.systemBankBranch.bus.OBSystemBankBranch", FORM_SCOPE }
	        }
	        );
	    }

	 public String[][] getResultDescriptor() {
			return (new String[][] {
					{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
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
			
			ISystemBankBranchTrxValue trxValueOut = new OBSystemBankBranchTrxValue();
			try {
				OBSystemBankBranch systemBankBranch = (OBSystemBankBranch) map.get("systemBankBranchObj");
//				System.out.println("systemBankBranch.getfileUpload "+systemBankBranch.getFileUpload());
				String strError = "";
				String fileCheckSum ="";
				String fileUploadPending ="";
				String transDenied="false";
				
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
								
				//Update insert into Mapper Table
			    long fileId = (long)(Math.random()*1000000);
				OBFileMapperID obFileMapperID = new OBFileMapperID();
				obFileMapperID.setFileId(fileId);
				String userName = "";
			    if (ctx != null) {
					userName = ctx.getUser().getUserName();
			    }
			    				
			  //check is file type is not then set error page.
			    String fileType = "";
			    if(!systemBankBranch.getFileUpload().getFileName().toLowerCase().endsWith(".csv")){
			    	fileType ="NOT_CSV";			
			    	strError ="errorEveList";
			    }else{
			    
			    //check if any pending previous FileUpload
				
				if(getSystemBankBranchProxy().isPrevFileUploadPending()){
					fileUploadPending="PENDING";
					strError ="errorEveList";
				} else {
			    
				//Read File
				ProcessDataFile dataFile = new ProcessDataFile();
				ArrayList resultList = dataFile.processFile(systemBankBranch.getFileUpload(),SYS_BANK_BRANCH_UPLOAD);
				
				//Check if check-sum FooterCount must meet row Count
				if(dataFile.isCheckSumFooter()){
					fileCheckSum="MISMATCH";
					strError ="errorEveList";
				}else{		
				
				//Check if record already exist with key Value
				HashMap uniqueErrorMap = new HashMap();
				String [][]errorList;
				for (int index = 0; index < resultList.size(); index++) {
					int incr = 0;
					HashMap eachDataMap = (HashMap) resultList.get(index);
					OBSystemBankBranch obSystemBankBranch = new OBSystemBankBranch();
					errorList = new String[8][2];
					boolean validCode = false;
					boolean validHub = false;
//					boolean isAllValid = false;
					
					String systemBankBranchCode = (String) eachDataMap.get("SYSTEM_BANK_BRANCH_CODE");
					String countryCode = (String) eachDataMap.get("COUNTRY");
					String regionCode = (String) eachDataMap.get("REGION");
					String stateCode = (String) eachDataMap.get("STATE");
					String cityCode = (String) eachDataMap.get("CITY_TOWN");
					
					/*String systemBankCode = (String) eachDataMap.get("SYSTEM_BANK_CODE");
					String address = (String) eachDataMap.get("ADDRESS");
					String contactMail = (String) eachDataMap.get("CONTACT_MAIL");
					String contactNumber = (String) eachDataMap.get("CONTACT_NUMBER");
					String faxNo = (String) eachDataMap.get("FAX_NUMBER");
					String rbiCode = (String) eachDataMap.get("RBI_CODE");
					*/
					String systemBankBranchName = (String) eachDataMap.get("SYSTEM_BANK_BRANCH_NAME");
					String isValult = (String) eachDataMap.get("IS_VAULT");
					String custodian1 = (String) eachDataMap.get("CUSTODIAN1");
					String custodian2 = (String) eachDataMap.get("CUSTODIAN2");
					
					String linkedHub = (String) eachDataMap.get("LINKED_HUB");
					String hub = (String) eachDataMap.get("IS_HUB");
					//String status = (String) eachDataMap.get("STATUS");
					String deprecated = (String) eachDataMap.get("DEPRECATED");
					
//					if( isAllValid ){		//	if for Validation Start
						if(systemBankBranchCode!=null && ! systemBankBranchCode.trim().equals("") ){
							 try {
								 validCode = getSystemBankBranchProxy().isUniqueCode("SYSTEM_BANK_BRANCH_CODE",systemBankBranchCode);							 
							} catch (SystemBankBranchException e) {
								throw new SystemBankBranchException("Branch code null !");
							}
							catch(Exception e){
								throw new SystemBankBranchException("Branch code null !");
							}
						}
						
						if( linkedHub != null && ! linkedHub.trim().equals("")){
							 try {
								 validHub = getSystemBankBranchProxy().isHubValid(linkedHub);							 
							} catch (SystemBankBranchException e) {
								throw new SystemBankBranchException("Branch code null !");
							}
							catch(Exception e){
								throw new SystemBankBranchException("Branch code null !");
							}
						}
						
						if( validCode ){
							errorList[incr][0] = "SYSTEM_BANK_BRANCH_CODE";
							 errorList[incr][1] = "Already exist- ";
							 uniqueErrorMap.put(new Integer(index), errorList);
							 dataFile.setErrorList(uniqueErrorMap);
							dataFile.setValid(false);
							strError ="errorEveList";
							incr++;
						}
						
						else if( validHub ){
							errorList[incr][0] = "LINKED_HUB";
							 errorList[incr][1] = "Invalid Value";
							 uniqueErrorMap.put(new Integer(index), errorList);
							 dataFile.setErrorList(uniqueErrorMap);
							dataFile.setValid(false);
							strError ="errorEveList";
							incr++;
						}else if( hub != null && !hub.equals("") ){
							if(hub.equalsIgnoreCase("N") && linkedHub!=null && linkedHub.equals("") ){
								errorList[incr][0] = "LINKED_HUB";
								errorList[incr][1] = "Linked Hub mandatory";
								uniqueErrorMap.put(new Integer(index), errorList);
								dataFile.setErrorList(uniqueErrorMap);
								dataFile.setValid(false);
								strError ="errorEveList";
								incr++;
							}else if(hub.equalsIgnoreCase("Y") && linkedHub!=null && !linkedHub.equals("") ){
								errorList[incr][0] = "LINKED_HUB";
								errorList[incr][1] = "Linked Hub should be empty";
								uniqueErrorMap.put(new Integer(index), errorList);
								dataFile.setErrorList(uniqueErrorMap);
								dataFile.setValid(false);
								strError ="errorEveList";
								incr++;
							}
						}
						
						boolean isHub = false;
						//boolean isStatus = false;
						boolean isDeprecated = false;
						
						if( hub != null && ! hub.equals("") ){
							if( ! ( hub.equalsIgnoreCase("Y") || hub.equalsIgnoreCase("N") ) ){
								isHub = true;
							errorList[incr][0] = "IS_HUB";							
							errorList[incr][1] = "Invalid Value";
							uniqueErrorMap.put(new Integer(index),errorList);
							dataFile.setErrorList(uniqueErrorMap);
							dataFile.setValid(false);
							strError = "errorEveList";
							incr++;
							}
						}
						
						
						
						if(isValult!=null && isValult.trim().equalsIgnoreCase("on"))
						{
							if((custodian1!=null && custodian1.trim().equals("")) && (custodian2!=null && custodian2.trim().equals("")))
							{
						        errorList[incr][0] = "CUSTODIAN";
								errorList[incr][1] = "Atleast one Custodian is needed.";
								uniqueErrorMap.put(new Integer(index), errorList);
								dataFile.setErrorList(uniqueErrorMap);
								dataFile.setValid(false);
								strError ="errorEveList";
								incr++;
							}
						}else {
							if(isValult!=null && !isValult.trim().equals(""))
							{
							   errorList[incr][0] = "IS_VAULT";
							   errorList[incr][1] = "isValult should be empty.";
							   uniqueErrorMap.put(new Integer(index), errorList);
							   dataFile.setErrorList(uniqueErrorMap);
							   dataFile.setValid(false);
							   strError ="errorEveList";
							   incr++;
							}
							if((custodian1!=null && !custodian1.trim().equals("")) || (custodian2!=null && !custodian2.trim().equals("")))
							{
						        errorList[incr][0] = "CUSTODIAN";
						        errorList[incr][1] = "";
						        if(!custodian1.trim().equals(""))
								errorList[incr][1] = "Custodian1 should be empty. ";
						        if(!custodian2.trim().equals(""))
									errorList[incr][1] = errorList[0][1]+"Custodian2 should be empty.";
								uniqueErrorMap.put(new Integer(index), errorList);
								dataFile.setErrorList(uniqueErrorMap);
								dataFile.setValid(false);
								strError ="errorEveList";
								incr++;
							}
						}
						
						String codeValue = "";			
						
						// Added for Upload Validation								
						if ( countryCode != null && !countryCode.trim().equals("") ) {
							try {
								codeValue = getSystemBankBranchProxy().isCodeExisting(countryCode,regionCode,stateCode,cityCode);
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
								
							} catch (ValuationAgencyException e) {
								throw new SystemBankBranchException("Code Value Invalid or Does Not Exist");
							} catch (Exception e) {
								throw new SystemBankBranchException("Code Value Invalid or Does Not Exist ");
							}
						}	
						if ( ASSTValidator.isValidOtherBankBranchName(systemBankBranchName) ) {
							errorList[incr][0] = "SYSTEM_BANK_BRANCH_NAME";
							errorList[incr][1] = "Special Character(s) Not Allowed except {& - }";
							uniqueErrorMap.put(new Integer(index),errorList);
							dataFile.setErrorList(uniqueErrorMap);
							dataFile.setValid(false);
							strError = "errorEveList";
							incr++;
					   }
//					}  // End if for Validation
				} //end for loop
				
			} //end else for CheckSum
				
				if(dataFile.isValid() &&  fileCheckSum.equals("") && fileUploadPending.equals("") && fileType.equals("")){	 // If there no error			
					//create a record to get Transaction id
				    trxValueOut = getSystemBankBranchProxy().makerInsertMapperSystemBankBranch(ctx, obFileMapperID);
					    OBFileMapperMaster obFileMapperMaster = new OBFileMapperMaster();
					    obFileMapperMaster.setFileId(fileId);
					    obFileMapperMaster.setTransId(trxValueOut.getTransactionID());
					    
					    //create records in systembankbranch staging table and store it's ID and transactionId in a master table 
						try {
							int noOfRowsInserted = getSystemBankBranchProxy().insertSystemBankBranch(obFileMapperMaster, userName, resultList);
//							System.out.println("No of rows inserted == " + noOfRowsInserted);
							resultMap.put("request.ITrxValue", trxValueOut);
						} catch (Exception e) {
							transDenied="true";     
							strError = "errorEveList";   
							getSystemBankBranchProxy().deleteTransaction(obFileMapperMaster);
						}
						
				} if(dataFile.isValid()==false) { //If there is a Error
					resultMap.put("rowCount", new Integer(dataFile.getMaxCount()));
					resultMap.put("errorList",dataFile.getErrorList());					
					strError ="errorEveList";
					}
				} //end else previous FileUpload
			}	
			    resultMap.put("transDenied",transDenied);
				resultMap.put("fileUploadPending",fileUploadPending);
				resultMap.put("fileCheckSum",fileCheckSum);	
				resultMap.put("fileType", fileType);
		    	resultMap.put("errorEveList", strError);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				return returnMap;
			}catch (SystemBankBranchException ex) {
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
