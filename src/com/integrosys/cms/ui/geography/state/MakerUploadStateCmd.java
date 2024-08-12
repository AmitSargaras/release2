/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.geography.state;

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
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.OBState;
import com.integrosys.cms.app.geography.state.proxy.IStateProxyManager;
import com.integrosys.cms.app.geography.state.trx.IStateTrxValue;
import com.integrosys.cms.app.geography.state.trx.OBStateTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;

/**
@author $Author: Abhijit R$
* Command for Create Holiday
 */
public class MakerUploadStateCmd extends AbstractCommand implements ICommonEventConstant {
	
	public static final String STATE_UPLOAD = "StateUpload";
	
	private IStateProxyManager stateProxy;
	
	/**
	 * @return the stateProxy
	 */
	public IStateProxyManager getStateProxy() {
		return stateProxy;
	}

	/**
	 * @param stateProxy the stateProxy to set
	 */
	public void setStateProxy(IStateProxyManager stateProxy) {
		this.stateProxy = stateProxy;
	}

	/**
	 * Default Constructor
	 */
	
	
	public MakerUploadStateCmd() {
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
	        		{ "stateObj", "com.integrosys.cms.app.geography.state.bus.OBState", FORM_SCOPE },
					{"IStateTrxValue", "com.integrosys.cms.app.geography.state.trx.IStateTrxValue", SERVICE_SCOPE},
		            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
					{"remarks", "java.lang.String", REQUEST_SCOPE},
	                {"event", "java.lang.String", REQUEST_SCOPE},
	        }
	        );
	    }

	 public String[][] getResultDescriptor() {
			return (new String[][] {
					{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
					{"stateObj", "com.integrosys.cms.app.geography.state.bus.OBState", FORM_SCOPE },
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
			
			IStateTrxValue trxValueOut = new OBStateTrxValue();
			try {
				OBState state = (OBState) map.get("stateObj");			
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				
				//check is file type is not then set error page.	
			    String fileType = "";
			    String fileUploadPending ="";
			    String fileCheckSum ="";
			    String transDenied="false";
			if (!state.getFileUpload().getFileName().toLowerCase().endsWith(".csv")) {
				fileType = "NOT_CSV";
				strError = "errorEveList";
			} else {

				// Read Uploaded File
				ProcessDataFile dataFile = new ProcessDataFile();
				ArrayList resultList = dataFile.processFile(state
						.getFileUpload(),STATE_UPLOAD);

				// check if any pending files not approved

				if (getStateProxy().isPrevFileUploadPending()) {
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
							HashMap eachDataMap = (HashMap) resultList.get(index);
							errorList = new String[2][2];
							
							boolean validCode = false;
							boolean validName = false;
							boolean isValidRegionCode = false;
							
							String stateCode = (String) eachDataMap.get("STATE_CODE");
							String stateName = (String) eachDataMap.get("STATE_NAME");
							String regionCode = (String) eachDataMap.get("REGION_CODE");
							
							if ( stateCode != null && stateCode.trim() != "" ) {
								try {
									validCode = getStateProxy().isStateCodeUnique(stateCode);
								} catch (NoSuchGeographyException e) {
									throw new NoSuchGeographyException("State code null !");
								} catch (Exception e) {
									throw new NoSuchGeographyException("State code null !");
								}
							}
							
							if ( stateName != null && stateName.trim() != "" ) {
								try {
									IRegion region = getStateProxy().getRegionByRegionCode(regionCode);
									if( region != null )
										validName = getStateProxy().isStateNameUnique(stateName,region.getCountryId().getIdCountry());
									else
										isValidRegionCode = true;
								} catch (NoSuchGeographyException e) {
									throw new NoSuchGeographyException("State code null !");
								} catch (Exception e) {
									throw new NoSuchGeographyException("State code null !");
								}
							}
							
							if ( validCode ){
								errorList[incr][0] = "STATE_CODE";
								errorList[incr][1] = "Already exist";
							}
							
							else if ( validName ){
								errorList[incr][0] = "STATE_NAME";
								errorList[incr][1] = "Already exist";
							}
							
							else if ( isValidRegionCode ){
								errorList[incr][0] = "REGION_CODE";
								errorList[incr][1] = "Invalid Code";
							}
							
							if ( validCode || validName || isValidRegionCode ) {								
								uniqueErrorMap.put(new Integer(index),errorList);
								dataFile.setErrorList(uniqueErrorMap);
								dataFile.setValid(false);
								strError = "errorEveList";
								incr++;
							}
							 if ( ASSTValidator.isValidANDName(stateName) ) {
									errorList[incr][0] = "STATE_NAME";
									errorList[incr][1] = "Special Character(s) Not Allowed except &";
									uniqueErrorMap.put(new Integer(index),errorList);
									dataFile.setErrorList(uniqueErrorMap);
									dataFile.setValid(false);
									strError = "errorEveList";
									incr++;
								}
						} // end for loop

						// If there no error
						if (dataFile.isValid() && fileCheckSum.equals("") && fileUploadPending.equals("") && fileType.equals("")) {

							// create a record to get Transaction id
							trxValueOut = getStateProxy().makerInsertMapperState(ctx,obFileMapperID);

							// Set Transaction ID into Mapper Table
							OBFileMapperMaster obFileMapperMaster = new OBFileMapperMaster();
							obFileMapperMaster.setFileId(fileId);
							obFileMapperMaster.setTransId(trxValueOut.getTransactionID());

							// insert records in State staging table
							// and store it's ID and transactionId in a master
							// table
							try {
								int noOfRowsInserted = getStateProxy().insertState(obFileMapperMaster,userName, resultList);
								DefaultLogger.debug(this,"No of rows inserted == "+ noOfRowsInserted);
								resultMap.put("request.ITrxValue", trxValueOut);
							} catch (Exception e) {
								transDenied="true";     
								strError = "errorEveList";   
								getStateProxy().deleteTransaction(obFileMapperMaster);  
							}
						}

						// If there is a Error
						if (dataFile.isValid() == false) {
							strError = "errorEveList";
							resultMap.put("rowCount", new Integer(dataFile.getMaxCount()));
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
			}catch (NoSuchGeographyException ex) {
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
