/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.geography.region;

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
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.app.geography.region.proxy.IRegionProxyManager;
import com.integrosys.cms.app.geography.region.trx.IRegionTrxValue;
import com.integrosys.cms.app.geography.region.trx.OBRegionTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;

/**
@author $Author: Abhijit R$
* Command for Create Holiday
 */
public class MakerUploadRegionCmd extends AbstractCommand implements ICommonEventConstant {
	public static final String REGION_UPLOAD = "RegionUpload";
	
	private IRegionProxyManager regionProxy;
	
	/**
	 * @return the regionProxy
	 */
	public IRegionProxyManager getRegionProxy() {
		return regionProxy;
	}

	/**
	 * @param regionProxy the regionProxy to set
	 */
	public void setRegionProxy(IRegionProxyManager regionProxy) {
		this.regionProxy = regionProxy;
	}

	/**
	 * Default Constructor
	 */
	
	
	public MakerUploadRegionCmd() {
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
	        		{ "regionObj", "com.integrosys.cms.app.geography.region.bus.OBRegion", FORM_SCOPE },
					{"IRegionTrxValue", "com.integrosys.cms.app.geography.region.trx.IRegionTrxValue", SERVICE_SCOPE},
		            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
					{"remarks", "java.lang.String", REQUEST_SCOPE},
	                {"event", "java.lang.String", REQUEST_SCOPE},
	        }
	        );
	    }

	 public String[][] getResultDescriptor() {
			return (new String[][] {
					{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
					{"regionObj", "com.integrosys.cms.app.geography.region.bus.OBRegion", FORM_SCOPE },
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
			
			IRegionTrxValue trxValueOut = new OBRegionTrxValue();
			try {
				OBRegion region = (OBRegion) map.get("regionObj");			
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				
				//check is file type is not then set error page.	
			    String fileType = "";
			    String fileUploadPending ="";
			    String fileCheckSum ="";
			    String transDenied="false";
			if (!region.getFileUpload().getFileName().toLowerCase().endsWith(".csv")) {
				fileType = "NOT_CSV";
				strError = "errorEveList";
			} else {

				// Read Uploaded File
				ProcessDataFile dataFile = new ProcessDataFile();
				ArrayList resultList = dataFile.processFile(region
						.getFileUpload(),REGION_UPLOAD);

				// check if any pending files not approved

				if (getRegionProxy().isPrevFileUploadPending()) {
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
							HashMap eachDataMap = (HashMap) resultList.get(index);
							errorList = new String[1][2];
							
							boolean validCode = false;
							boolean validName = false;
							boolean inValidCountryName = false;
							
							String regionCode = (String) eachDataMap.get("REGION_CODE");
							String regionName = (String) eachDataMap.get("REGION_NAME");
							String countryCode = (String) eachDataMap.get("COUNTRY_CODE");
							
							if ( regionCode != null && regionCode.trim() != "" ) {
								try {
									validCode = getRegionProxy().isRegionCodeUnique(regionCode);									
								} catch (NoSuchGeographyException e) {
									throw new NoSuchGeographyException("Region code null !");
								} catch (Exception e) {
									throw new NoSuchGeographyException("Region code null !");
								}
							}
							
							if ( regionName != null && regionName.trim() != "" ) {
								try {
									ICountry country = getRegionProxy().getCountryByCountryCode(countryCode);
									if( country != null )
										validName = getRegionProxy().isRegionNameUnique(regionName,country.getIdCountry());
									else
										inValidCountryName = true;
								} catch (NoSuchGeographyException e) {
									throw new NoSuchGeographyException("Region code null !");
								} catch (Exception e) {
									throw new NoSuchGeographyException("Region code null !");
								}
							}
							
							if ( validCode ){
								errorList[0][0] = "REGION_CODE";
								errorList[0][1] = "Already exist";
							}
							
							else if ( validName ){
								errorList[0][0] = "REGION_NAME";
								errorList[0][1] = "Already exist";
							}
							
							else if ( inValidCountryName ){
								errorList[0][0] = "COUNTRY_CODE";
								errorList[0][1] = "Invalid Code";
							}
							if ( validCode || validName || inValidCountryName ) {								
								
								uniqueErrorMap.put(new Integer(index),errorList);
								dataFile.setErrorList(uniqueErrorMap);
								dataFile.setValid(false);
								strError = "errorEveList";
							}
						} // end for loop

						// If there no error
						if (dataFile.isValid() && fileCheckSum.equals("") && fileUploadPending.equals("") && fileType.equals("")) {

							// create a record to get Transaction id
							trxValueOut = getRegionProxy().makerInsertMapperRegion(ctx,obFileMapperID);

							// Set Transaction ID into Mapper Table
							OBFileMapperMaster obFileMapperMaster = new OBFileMapperMaster();
							obFileMapperMaster.setFileId(fileId);
							obFileMapperMaster.setTransId(trxValueOut
									.getTransactionID());

							// insert records in Region staging table
							// and store it's ID and transactionId in a master
							// table
							try {
								int noOfRowsInserted = getRegionProxy().insertRegion(obFileMapperMaster,userName, resultList);
								DefaultLogger.debug(this,"No of rows inserted == "+ noOfRowsInserted);
								resultMap.put("request.ITrxValue", trxValueOut);
							} catch (Exception e) {
								transDenied="true";     
								strError = "errorEveList";   
								getRegionProxy().deleteTransaction(obFileMapperMaster);
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
