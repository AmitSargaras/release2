/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.relationshipmgr;

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
import com.integrosys.cms.app.holiday.bus.OBHoliday;
import com.integrosys.cms.app.insurancecoverage.bus.InsuranceCoverageException;
import com.integrosys.cms.app.relationshipmgr.bus.IHRMSData;
import com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue;
import com.integrosys.cms.app.relationshipmgr.trx.OBRelationshipMgrTrxValue;
import com.integrosys.cms.app.systemBankBranch.bus.OBSystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.bus.SystemBankBranchException;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;

/**
@author $Author: Abhijit R$
* Command for Create Holiday
 */
public class MakerUploadRelationshipManagerCmd extends AbstractCommand implements ICommonEventConstant {
	public static final String RM_UPLOAD = "RelationshipManagerUpload";
	
	private IRelationshipMgrProxyManager relationshipMgrProxyManager;

	/**
	 * @return the relationshipMgrProxyManager
	 */
	public IRelationshipMgrProxyManager getRelationshipMgrProxyManager() {
		return relationshipMgrProxyManager;
	}

	/**
	 * @param relationshipMgrProxyManager the relationshipMgrProxyManager to set
	 */
	public void setRelationshipMgrProxyManager(
			IRelationshipMgrProxyManager relationshipMgrProxyManager) {
		this.relationshipMgrProxyManager = relationshipMgrProxyManager;
	}

	/**
	 * Default Constructor
	 */
	
	
	public MakerUploadRelationshipManagerCmd() {
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
	        		{ "RelationshipMgrObj", "com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr", FORM_SCOPE },
					{"IRelationshipMgrTrxValue", "com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue", SERVICE_SCOPE},
		            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
					{"remarks", "java.lang.String", REQUEST_SCOPE},
	                {"event", "java.lang.String", REQUEST_SCOPE},
	        }
	        );
	    }

	 public String[][] getResultDescriptor() {
			return (new String[][] {
					{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
					{ "RelationshipMgrObj", "com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr", FORM_SCOPE },
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

			String remarks = (String) map.get("remarks");
			
			IRelationshipMgrTrxValue trxValueOut = new OBRelationshipMgrTrxValue();
			try {
				OBRelationshipMgr relationshipMgr = (OBRelationshipMgr) map.get("RelationshipMgrObj");			
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
 
				
				//check is file type is not then set error page.	
			    String fileType = "";
			    String fileUploadPending ="";
			    String fileCheckSum ="";
			    String transDenied="false";
			    
			if (!relationshipMgr.getFileUpload().getFileName().toLowerCase().endsWith(".csv")) {
				fileType = "NOT_CSV";
				strError = "errorEveList";
			} else {

				// Read Uploaded File
				ProcessDataFile dataFile = new ProcessDataFile();
				ArrayList resultList = dataFile.processFile(relationshipMgr
						.getFileUpload(),RM_UPLOAD);

				// check if any pending files not approved

				if (getRelationshipMgrProxyManager().isPrevFileUploadPending()) {
					fileUploadPending = "PENDING";
					strError = "errorEveList";
				} else {

					// Check if check-sum FooterCount must meet row Count

					if (dataFile.isCheckSumFooter()) {
						fileCheckSum = "MISMATCH";
						strError = "errorEveList";
					} else {

						// Insert a dummy fieldId (a random number) into a table
						// in order to generate Transaction ID.
						long fileId = (long) (Math.random() * 1000000);
						OBFileMapperID obFileMapperID = new OBFileMapperID();
						obFileMapperID.setFileId(fileId);
						String userName = "";
						if (ctx != null) {
							userName = ctx.getUser().getUserName();
						}

						// Check if record already exist with key Value
						HashMap uniqueErrorMap = new HashMap();
						String[][] errorList;
						for (int index = 0; index < resultList.size(); index++) {
							int incr = 0;
							int incrW = 0;
							int incrS = 0;
							HashMap eachDataMap = (HashMap) resultList.get(index);
							errorList = new String[1][2];
							boolean isValidRegionCode = false;
							boolean isValidWboRegionCode = false;
							boolean isValidSupervisorRegionCode = false;
							String regionCode = (String) eachDataMap.get("REGION_CODE");
							String WBO_RegionCode = (String) eachDataMap.get("WBO_REGION");
							String supervisor_RegionCode = (String) eachDataMap.get("SUPERVISOR_REGION");
							
							String rmEmployeeCode = (String) eachDataMap.get("RM_MGR_CODE");
							
							//check duplicate/existing
							
					boolean	RMCodeUniqueCheck = getRelationshipMgrProxyManager().isRMCodeUnique(rmEmployeeCode);
					if(RMCodeUniqueCheck==false) {
							IHRMSData ihrmsData = getRelationshipMgrProxyManager().getHRMSEmpDetails(rmEmployeeCode);
							if(null == ihrmsData) {
								errorList[incr][0] = "RM_MGR_CODE";
								errorList[incr][1] = "Invalid Code";
								uniqueErrorMap.put(new Integer(index),errorList);
								dataFile.setValid(false);
								dataFile.setErrorList(uniqueErrorMap);
								strError = "errorEveList";
								
							}else {
												eachDataMap.put("RM_MGR_NAME", ihrmsData.getName());
												eachDataMap.put("RM_MGR_MAIL", ihrmsData.getEmailId());
												eachDataMap.put("RM_MOBILE_NO", ihrmsData.getMobileNo());
												eachDataMap.put("RM_CITY", ihrmsData.getCity());
												eachDataMap.put("RM_STATE", ihrmsData.getState());
												eachDataMap.put("REPORTING_HEAD_EMPLOYEE_CODE", ihrmsData.getSupervisorEmployeeCode());
												

							if (null != ihrmsData.getSupervisorEmployeeCode()) {
								IHRMSData data = getRelationshipMgrProxyManager()
										.getHRMSEmpDetails(ihrmsData.getSupervisorEmployeeCode().toString());
								if (null != data) {
									eachDataMap.put("EMPLOYEE_ID", ihrmsData.getSupervisorEmployeeCode());
									eachDataMap.put("REPORTING_HEAD_EMPLOYEE_CODE", data.getEmployeeCode());
									eachDataMap.put("REPORTING_HEAD", data.getName());
									eachDataMap.put("REPORTING_HEAD_MAIL", data.getEmailId());
									eachDataMap.put("REPORTING_HEAD_MOBILE_NO", data.getMobileNo());
								}
							}
						}
				
							if ( regionCode != null && regionCode.trim() != "") {
								try {
									isValidRegionCode = getRelationshipMgrProxyManager().isValidRegionCode(regionCode);
								} catch (RelationshipMgrException e) {
									throw new RelationshipMgrException("IC code null !");
								} catch (Exception e) {
									throw new RelationshipMgrException("IC code null !");
								}
							}
							else{
								errorList[incr][0] = "REGION_CODE";
								errorList[incr][1] = "Mandatory Field";
								uniqueErrorMap.put(new Integer(index),errorList);
								dataFile.setErrorList(uniqueErrorMap);
								dataFile.setValid(false);
								strError = "errorEveList";
							    incr++;
							}
							
							if ( isValidRegionCode ) {
								errorList[incr][0] = "REGION_CODE";
								errorList[incr][1] = "Invalid Code";
								uniqueErrorMap.put(new Integer(index),errorList);
								dataFile.setErrorList(uniqueErrorMap);
								dataFile.setValid(false);
								strError = "errorEveList";
								incr++;
							}
				//CheckingWboRegion			
							if ( WBO_RegionCode != null && WBO_RegionCode.trim() != "") {
								try {
									isValidWboRegionCode = getRelationshipMgrProxyManager().isValidRegionCode(WBO_RegionCode);
								} catch (RelationshipMgrException e) {
									throw new RelationshipMgrException("IC code null !");
								} catch (Exception e) {
									throw new RelationshipMgrException("IC code null !");
								}
							}
							else{
								errorList[incrW][0] = "WBO_Region";
								errorList[incrW][1] = "Mandatory Field";
								uniqueErrorMap.put(new Integer(index),errorList);
								dataFile.setErrorList(uniqueErrorMap);
								dataFile.setValid(false);
								strError = "errorEveList";
							    incrW++;
							}
							
							if ( isValidWboRegionCode ) {
								errorList[incrW][0] = "WBO_REGION";
								errorList[incrW][1] = "Invalid Code";
								uniqueErrorMap.put(new Integer(index),errorList);
								dataFile.setErrorList(uniqueErrorMap);
								dataFile.setValid(false);
								strError = "errorEveList";
								incrW++;
							}
					//checkingSupervisorRegion		
							if ( supervisor_RegionCode != null && supervisor_RegionCode.trim() != "") {
								try {
									isValidSupervisorRegionCode = getRelationshipMgrProxyManager().isValidRegionCode(supervisor_RegionCode);
								} catch (RelationshipMgrException e) {
									throw new RelationshipMgrException("IC code null !");
								} catch (Exception e) {
									throw new RelationshipMgrException("IC code null !");
								}
							}
							else{
								errorList[incrS][0] = "supervisor_Region";
								errorList[incrS][1] = "Mandatory Field";
								uniqueErrorMap.put(new Integer(index),errorList);
								dataFile.setErrorList(uniqueErrorMap);
								dataFile.setValid(false);
								strError = "errorEveList";
								incrS++;
							}
							
							if ( isValidSupervisorRegionCode ) {
								errorList[incrS][0] = "supervisor_Region";
								errorList[incrS][1] = "Invalid Code";
								uniqueErrorMap.put(new Integer(index),errorList);
								dataFile.setErrorList(uniqueErrorMap);
								dataFile.setValid(false);
								strError = "errorEveList";
								incrS++;
							}
					}
					else {
						errorList[incr][0] = "RM_MGR_CODE";
						errorList[incr][1] = "Duplicate";
						uniqueErrorMap.put(new Integer(index),errorList);
						dataFile.setErrorList(uniqueErrorMap);
						dataFile.setValid(false);
					}
						} // end for loop						
						// If there no error
						if (dataFile.isValid() && fileCheckSum.equals("")
								&& fileUploadPending.equals("")
								&& fileType.equals("")) {

							// create a record to get Transaction id
							trxValueOut = getRelationshipMgrProxyManager().makerInsertMapperRelationshipMgr(ctx,obFileMapperID);

							// Set Transaction ID into Mapper Table
							OBFileMapperMaster obFileMapperMaster = new OBFileMapperMaster();
							obFileMapperMaster.setFileId(fileId);
							obFileMapperMaster.setTransId(trxValueOut.getTransactionID());

							// insert records in RelationshipMgr staging table
							// & store it's ID and transactionId in a master
							// table
							try {
								int noOfRowsInserted = getRelationshipMgrProxyManager().insertRelationshipMgr(obFileMapperMaster,userName, resultList);
								DefaultLogger.debug(this,"No of rows inserted == "+ noOfRowsInserted);
								resultMap.put("request.ITrxValue", trxValueOut);
							} catch (Exception e) {
								transDenied="true";     
								strError = "errorEveList";   
								getRelationshipMgrProxyManager().deleteTransaction(obFileMapperMaster);
							}
						}

						// If there is a Error
						if (dataFile.isValid() == false) {
							strError = "errorEveList";
							resultMap.put("rowCount", new Integer(dataFile
									.getMaxCount()));
							resultMap.put("errorList", dataFile.getErrorList());
						}
					}// end of Footer Checksum
				}// end of PENDING Approval
			}// end of CSV file 
				
				resultMap.put("transDenied",transDenied);
				resultMap.put("fileUploadPending",fileUploadPending);
				resultMap.put("fileCheckSum",fileCheckSum);	
				resultMap.put("fileType", fileType);
		    	resultMap.put("errorEveList", strError);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				return returnMap;
			}catch (RelationshipMgrException ex) {
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
