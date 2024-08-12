
package com.integrosys.cms.ui.creditApproval;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalException;
import com.integrosys.cms.app.creditApproval.bus.OBCreditApproval;
import com.integrosys.cms.app.creditApproval.proxy.ICreditApprovalProxy;
import com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue;
import com.integrosys.cms.app.creditApproval.trx.OBCreditApprovalTrxValue;
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
public class MakerUploadCreditApprovalCommand extends AbstractCommand implements ICommonEventConstant {
	public static final String CREDIT_APP_UPLOAD = "CreditApprovalUpload";
	private ICreditApprovalProxy creditApprovalProxy;


	
	/**
	 * @return the creditApprovalProxy
	 */
	public ICreditApprovalProxy getCreditApprovalProxy() {
		return creditApprovalProxy;
	}

	/**
	 * @param creditApprovalProxy the creditApprovalProxy to set
	 */
	public void setCreditApprovalProxy(ICreditApprovalProxy creditApprovalProxy) {
		this.creditApprovalProxy = creditApprovalProxy;
	}

	/**
	 * Default Constructor
	 */
	
	
	public MakerUploadCreditApprovalCommand() {
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
	        		{"creditApprovalTrxValue", "com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue", SERVICE_SCOPE },
					{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
					{"remarks", "java.lang.String", REQUEST_SCOPE},
	                {"event", "java.lang.String", REQUEST_SCOPE},
	          	    {"oBCreditApproval", "com.integrosys.cms.app.creditApproval.bus.OBCreditApproval", FORM_SCOPE },
	          	    { com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale",
	    				GLOBAL_SCOPE }
	        }
	        );
	    }

	 public String[][] getResultDescriptor() {
			return (new String[][] {
					{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
					{"oBCreditApproval", "com.integrosys.cms.app.creditApproval.bus.OBCreditApproval", FORM_SCOPE },
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
			String fileCheckSum ="";
			String fileUploadPending ="";
			String transDenied="false";

			String remarks = (String) map.get("remarks");
			Locale local = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			/*set local*/
			CSVReader.setLocale(local);
			
			ICreditApprovalTrxValue trxValueOut = new OBCreditApprovalTrxValue();
			try {
				OBCreditApproval creditApproval = (OBCreditApproval) map.get("oBCreditApproval");			
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
 
				
				//check is file type is not then set error page.	
			    
			    if(!creditApproval.getFileUpload().getFileName().toLowerCase().endsWith(".csv")){
			    	fileType ="NOT_CSV";
			    	strError ="errorEveList";
			    } else {
			    
			    	  //check if any pending files not approved
					
					if(getCreditApprovalProxy().isPrevFileUploadPending()){
						fileUploadPending="PENDING";
						strError ="errorEveList";
					} else {
			    	
				//Read Uploaded File 
				ProcessDataFile dataFile = new ProcessDataFile();
				ArrayList resultList = dataFile.processFile(creditApproval.getFileUpload(),CREDIT_APP_UPLOAD);


				//Check if check-sum FooterCount must meet row Count
				
				if(dataFile.isCheckSumFooter()){
					fileCheckSum="MISMATCH";
					strError ="errorEveList";
				}else{		
					
					//Check if record already exist with key Value
					HashMap uniqueErrorMap = new HashMap();
					String [][]errorList ;
					
				  //Insert a dummy fieldId (a random number) into a table in order to generate Transaction ID.
				    long fileId = (long)(Math.random()*1000000);
					OBFileMapperID obFileMapperID = new OBFileMapperID();
					obFileMapperID.setFileId(fileId);
					String userName = "";
				    if (ctx != null) {
						userName = ctx.getUser().getUserName();
				    }
				    
				    long countryId =  getCreditApprovalProxy().getCountryIdForCountry("INDIA");
				    
				   
				    for (int index = 0; index < resultList.size(); index++) {
						HashMap eachDataMap = (HashMap) resultList.get(index);
						int incr = 0;
						errorList = new String[7][2];
					
						String creditApprovalName = (String) eachDataMap.get("APPROVAL_NAME");
						String regionCode = (String) eachDataMap.get("REGION_CODE");
						String senior = (String) eachDataMap.get("SENIOR");
						String risk = (String) eachDataMap.get("RISK");
						String deferralPowers = (String) eachDataMap.get("DEFERRAL_POWERS");
						String waivingPowers = (String) eachDataMap.get("WAIVING_POWERS");
						String minimumLimit = (String) eachDataMap.get("MINIMUM_LIMIT");
						String maximumLimit = (String) eachDataMap.get("MAXIMUM_LIMIT");
					
						boolean isCreditApprovalNameUnique = false;
						boolean isRegionCodeVaild = false;
						boolean isSenior = false;
						boolean isRisk = false;
						boolean isDeferralPower = false;
						boolean isWaivingPower = false;
						
						if ( creditApprovalName != null && creditApprovalName.trim() != "" ) {
							try {
								isCreditApprovalNameUnique = getCreditApprovalProxy().isCreditApprovalNameUnique(creditApprovalName);
							} catch (CreditApprovalException e) {
								throw new CreditApprovalException("Credit Approval Name null !");
							} catch (Exception e) {
								throw new CreditApprovalException("Credit Approval Name null !");
							}
						}
						
						if ( regionCode != null && regionCode.trim() != "" ) {
							try {
								isRegionCodeVaild = getCreditApprovalProxy().isRegionCodeVaild(regionCode,countryId);
							} catch (CreditApprovalException e) {
								throw new CreditApprovalException("Credit Approval Region Code or Country Id null !");
							} catch (Exception e) {
								throw new CreditApprovalException("Credit Approval Region Code or Country Id null !");
							}
						}
						
						if( senior != null && ! senior.equals("") ){
							if( ! ( senior.equalsIgnoreCase("Y") || senior.equalsIgnoreCase("N") ) ){
								isSenior = true;
								errorList[incr][0] = "SENIOR";
								errorList[incr][1] = "Invalid Value";
								incr++;
							}
						}
						
						if( risk != null && ! risk.equals("") ){
							if( ! ( risk.equalsIgnoreCase("Y") || risk.equalsIgnoreCase("N") ) ){
								isRisk = true;
								errorList[incr][0] = "RISK";
								errorList[incr][1] = "Invalid Value";
								incr++;
							}
						}
						
						if( deferralPowers != null && ! deferralPowers.equals("") ){
							if( ! ( deferralPowers.equalsIgnoreCase("Y") || deferralPowers.equalsIgnoreCase("N") ) ){
								isDeferralPower = true;
								errorList[incr][0] = "DEFERRAL_POWERS";
								errorList[incr][1] = "Invalid Value";
								incr++;
							}
						}
						
						if( waivingPowers != null && ! waivingPowers.equals("") ){
							if( ! ( waivingPowers.equalsIgnoreCase("Y") || waivingPowers.equalsIgnoreCase("N") ) ){
								isWaivingPower = true;
								errorList[incr][0] = "WAIVING_POWERS";
								errorList[incr][1] = "Invalid Value";
								incr++;
							}
						}
						
						if ( isSenior || isRisk || isDeferralPower || isWaivingPower ) {
							uniqueErrorMap.put(new Integer(index),errorList);
							dataFile.setErrorList(uniqueErrorMap);
							dataFile.setValid(false);
							strError = "errorEveList";
						}
						if ( isCreditApprovalNameUnique ) {
							errorList[incr][0] = "APPROVAL_NAME";
							errorList[incr][1] = "Already Exist";
							uniqueErrorMap.put(new Integer(index),errorList);
							dataFile.setErrorList(uniqueErrorMap);
							dataFile.setValid(false);
							strError = "errorEveList";
							incr++;
						}
						if ( isRegionCodeVaild ) {
							errorList[incr][0] = "REGION_CODE";
							errorList[incr][1] = "Invalid Value";
							uniqueErrorMap.put(new Integer(index),errorList);
							dataFile.setErrorList(uniqueErrorMap);
							dataFile.setValid(false);
							strError = "errorEveList";
							incr++;
						}
					    if (ASSTValidator.isValidCreditApproverName(creditApprovalName)) {
							errorList[incr][0] = "APPROVAL_NAME";
							errorList[incr][1] = "Special Character(s) Not Allowed except {. '}";
							uniqueErrorMap.put(new Integer(index),errorList);
							dataFile.setErrorList(uniqueErrorMap);
							dataFile.setValid(false);
							strError = "errorEveList";
							incr++;
						}
					    if (!StringUtils.isBlank(minimumLimit)&& !StringUtils.isBlank(maximumLimit)) {
							BigDecimal bigMaximumLimit = new BigDecimal(maximumLimit);
							BigDecimal bigMinimumLimit = new BigDecimal(minimumLimit);
							int intComp = bigMinimumLimit.compareTo(bigMaximumLimit);
							if (intComp>0) {
								errorList[incr][0] = "MINIMUM_LIMIT";
								errorList[incr][1] = "Minimum value should be less then Maximum value";
								uniqueErrorMap.put(new Integer(index),errorList);
								dataFile.setErrorList(uniqueErrorMap);
								dataFile.setValid(false);
								strError = "errorEveList";
								incr++;
							}
					    }
				    }
			    
			 // If there no error
				if(dataFile.isValid() &&  fileCheckSum.equals("") && fileUploadPending.equals("") && fileType.equals("")){	 
					
					//create a record to get Transaction id
			    	trxValueOut = getCreditApprovalProxy().makerInsertMapperCreditApproval(ctx, obFileMapperID);
			    	
			    	//Set Transaction ID into Mapper Table
				    OBFileMapperMaster obFileMapperMaster = new OBFileMapperMaster();
				    obFileMapperMaster.setFileId(fileId);
				    obFileMapperMaster.setTransId(trxValueOut.getTransactionID());
				    
				    //insert records in Holiday staging table and store it's ID and transactionId in a master table 
					try {
						int noOfRowsInserted = getCreditApprovalProxy().insertCreditApproval(obFileMapperMaster, userName, resultList, countryId);
						DefaultLogger.debug(this,"No of rows inserted == " + noOfRowsInserted);
						resultMap.put("request.ITrxValue", trxValueOut);
					} catch (Exception e) {
						transDenied="true";     
						strError = "errorEveList";   
						getCreditApprovalProxy().deleteTransaction(obFileMapperMaster);
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
				resultMap.put("fileUploadPending",fileUploadPending);
				resultMap.put("fileCheckSum",fileCheckSum);	
				resultMap.put("fileType", fileType);
		    	resultMap.put("errorEveList", strError);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				return returnMap;
			}catch (CreditApprovalException ex) {
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
