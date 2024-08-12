package com.integrosys.cms.ui.valuationAgency;

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
import com.integrosys.cms.app.valuationAgency.bus.OBValuationAgency;
import com.integrosys.cms.app.valuationAgency.bus.ValuationAgencyException;
import com.integrosys.cms.app.valuationAgency.proxy.IValuationAgencyProxyManager;
import com.integrosys.cms.app.valuationAgency.trx.IValuationAgencyTrxValue;
import com.integrosys.cms.app.valuationAgency.trx.OBValuationAgencyTrxValue;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;

public class MakerUploadValuationAgencyCmd extends AbstractCommand implements ICommonEventConstant {
	public static final String VAL_AGENCY_UPLOAD = "ValuationAgencyUpload";
	
	private IValuationAgencyProxyManager valuationAgencyProxy;
  
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
		MakerUploadValuationAgencyCmd.isVACodeUnique = isVACodeUnique;
	}

	public IValuationAgencyProxyManager getValuationAgencyProxy() {
		return valuationAgencyProxy;
	}

	public void setValuationAgencyProxy(
			IValuationAgencyProxyManager valuationAgencyProxy) {
		this.valuationAgencyProxy = valuationAgencyProxy;
	}

	
	/**
	 * Default Constructor
	 */
	
	
	public MakerUploadValuationAgencyCmd() {
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
	        		{"IValuationAgencyTrxValue", "com.integrosys.cms.app.valuationAgency.trx.IValuationAgencyTrxValue", SERVICE_SCOPE},
					{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
					{"remarks", "java.lang.String", REQUEST_SCOPE},
	                {"event", "java.lang.String", REQUEST_SCOPE},
	            	{ "valuationAgencyCode", "java.lang.String", REQUEST_SCOPE },
	        		{"valuationObj", "com.integrosys.cms.app.valuationAgency.bus.OBValuationAgency", FORM_SCOPE }
	        }
	        );
	    }

	 public String[][] getResultDescriptor() {
			return (new String[][] {
					{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
					{"valuationObj", "com.integrosys.cms.app.valuationAgency.bus.OBValuationAgency", FORM_SCOPE },
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
			
			IValuationAgencyTrxValue trxValueOut = new OBValuationAgencyTrxValue();
			try {
				OBValuationAgency valuationAgency = (OBValuationAgency) map.get("valuationObj");			
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				String strError = "";
				String fileCheckSum ="";
				String fileUploadPending ="";
				String transDenied="false";
				
				//check is file type is not then set error page.	
			    String fileType = "";
			    if(!valuationAgency.getFileUpload().getFileName().toLowerCase().endsWith(".csv")){
			    	fileType ="NOT_CSV";
			    	strError ="errorEveList";
			    }else {
			    	
			    	//Read Uploaded File 
					ProcessDataFile dataFile = new ProcessDataFile();
					ArrayList resultList = dataFile.processFile(valuationAgency.getFileUpload(),VAL_AGENCY_UPLOAD);
				  //check if any pending files not approved
				
				if(getValuationAgencyProxy().isPrevFileUploadPending()){
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
								boolean isValuationNameUnique = false;
								String codeValue = "";
								
								String valuationName = (String) eachDataMap.get("VALUATION_AGENCY_NAME");
								String countryCode = (String) eachDataMap.get("COUNTRY_CODE");
								String regionCode = (String) eachDataMap.get("REGION_CODE");
								String stateCode = (String) eachDataMap.get("STATE_CODE");
								String cityCode = (String) eachDataMap.get("CITY_CODE");
								
								if ( valuationName != null && !valuationName.trim().equals("") ) {
									try {
										isValuationNameUnique = getValuationAgencyProxy().isValuationNameUnique(valuationName.trim());
										if ( isValuationNameUnique ){
											errorList[incr][0] = "VALUATION_AGENCY_NAME";
											errorList[incr][1] = "Already exist";
											uniqueErrorMap.put(new Integer(index),errorList);
											dataFile.setErrorList(uniqueErrorMap);
											dataFile.setValid(false);
											strError = "errorEveList";
											incr++;
										}										
									} catch (ValuationAgencyException e) {
										throw new ValuationAgencyException("VALUATION AGENCY null !");
									} catch (Exception e) {
										throw new ValuationAgencyException("VALUATION AGENCY null !");
									}
								}
								
								// Added for Upload Validation								
								if ( countryCode != null && !countryCode.trim().equals("") ) {
									try {
										codeValue = getValuationAgencyProxy().isCodeExisting(countryCode,regionCode,stateCode,cityCode);
										if ( codeValue != null && ! codeValue.equals("") ){
											if( codeValue.equalsIgnoreCase("countryCode") )
												errorList[incr][0] = "COUNTRY_CODE";
											else if( codeValue.equalsIgnoreCase("regionCode") )
												errorList[incr][0] = "REGION_CODE";
											else if( codeValue.equalsIgnoreCase("stateCode") )
												errorList[incr][0] = "STATE_CODE";
											else if( codeValue.equalsIgnoreCase("cityCode") )
												errorList[incr][0] = "CITY_CODE";
											
											errorList[0][1] = "Invalid Code";
											uniqueErrorMap.put(new Integer(index),errorList);
											dataFile.setErrorList(uniqueErrorMap);
											dataFile.setValid(false);
											strError = "errorEveList";
											incr++;
										}
										
									} catch (ValuationAgencyException e) {
										throw new ValuationAgencyException("Code Value Invalid or Does Not Exist");
									} catch (Exception e) {
										throw new ValuationAgencyException("Code Value Invalid or Does Not Exist ");
									}
								}		
								if (ASSTValidator.isValidAndDotDashRoundBrackets(valuationName)) {
									errorList[incr][0] = "VALUATION_AGENCY_NAME";
									errorList[incr][1] = "Special Character(s) Not Allowed except {& . - ( )}";
									uniqueErrorMap.put(new Integer(index),errorList);
									dataFile.setErrorList(uniqueErrorMap);
									dataFile.setValid(false);
									strError = "errorEveList";
									incr++;
								}
							}//end loop
							if(dataFile.isValid() &&  fileCheckSum.equals("") && fileUploadPending.equals("") && fileType.equals("")){	 
								
								//create a record to get Transaction id
						    	trxValueOut = getValuationAgencyProxy().makerInsertMapperValuationAgency(ctx, obFileMapperID);
						    	
						    	//Set Transaction ID into Mapper Table
							    OBFileMapperMaster obFileMapperMaster = new OBFileMapperMaster();
							    obFileMapperMaster.setFileId(fileId);
							    obFileMapperMaster.setTransId(trxValueOut.getTransactionID());
							    
							    //insert records in ValuationAgency staging table and store it's ID and transactionId in a master table 
								try {
									int noOfRowsInserted = getValuationAgencyProxy().insertValuationAgency(obFileMapperMaster, userName, resultList);
//									System.out.println("No of rows inserted == " + noOfRowsInserted);
									resultMap.put("request.ITrxValue", trxValueOut);
								} catch (Exception e) {
									transDenied="true";     
									strError = "errorEveList";   
									getValuationAgencyProxy().deleteTransaction(obFileMapperMaster);
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
		}catch (ValuationAgencyException ex) {
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
