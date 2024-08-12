/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.geography.city;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.bus.OBCity;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.city.trx.ICityTrxValue;
import com.integrosys.cms.app.geography.city.trx.OBCityTrxValue;
import com.integrosys.cms.app.geography.state.proxy.IStateProxyManager;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;

/**
@author $Author: Abhijit R$
* Command for Create Holiday
 */
public class MakerUploadCityCmd extends AbstractCommand implements ICommonEventConstant {
	public static final String CITY_UPLOAD = "CityUpload";
	
	private ICityProxyManager cityProxy;
	
	/**
	 * @return the cityProxy
	 */
	public ICityProxyManager getCityProxy() {
		return cityProxy;
	}

	/**
	 * @param cityProxy the cityProxy to set
	 */
	public void setCityProxy(ICityProxyManager cityProxy) {
		this.cityProxy = cityProxy;
	}

	/**
	 * Default Constructor
	 */
	
	
	public MakerUploadCityCmd() {
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
	        		{ "cityObj", "com.integrosys.cms.app.geography.city.bus.OBCity", FORM_SCOPE },
					{"ICityTrxValue", "com.integrosys.cms.app.geography.city.trx.ICityTrxValue", SERVICE_SCOPE},
		            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
					{"remarks", "java.lang.String", REQUEST_SCOPE},
	                {"event", "java.lang.String", REQUEST_SCOPE},
	        }
	        );
	    }

	 public String[][] getResultDescriptor() {
			return (new String[][] {
					{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
					{"cityObj", "com.integrosys.cms.app.geography.city.bus.OBCity", FORM_SCOPE },
					{"errorList", "java.util.HashMap", REQUEST_SCOPE},
					{"rowCount", "java.lang.Integer", REQUEST_SCOPE},
					{"fileUploadPending", "java.lang.String", REQUEST_SCOPE},
					{"fileCheckSum", "java.lang.String", REQUEST_SCOPE},
					{"fileType", "java.lang.String", REQUEST_SCOPE},
					{"transDenied", "java.lang.String", REQUEST_SCOPE},
					{"noOfRecords", "java.lang.Integer", REQUEST_SCOPE}
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
			
			ICityTrxValue trxValueOut = new OBCityTrxValue();
			try {
				OBCity city = (OBCity) map.get("cityObj");			
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				
				//check is file type is not then set error page.	
			    String fileType = "";
			    String fileUploadPending ="";
			    String fileCheckSum ="";
			    String transDenied="false";
			if (!city.getFileUpload().getFileName().toLowerCase().endsWith(".csv")) {  //M shiv 160811  
				fileType = "NOT_CSV";
				strError = "errorEveList";
			} else {

				// Read Uploaded File
				ProcessDataFile dataFile = new ProcessDataFile();
				ArrayList resultList = dataFile.processFile(city
						.getFileUpload(),CITY_UPLOAD);

				// check if any pending files not approved

				if (getCityProxy().isPrevFileUploadPending()) {
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
							errorList = new String[3][2];
							boolean isCityCodeValid = false;
							boolean isStateCodeValid = false;
							String cityCode = (String) eachDataMap.get("CITY_CODE");
							String stateCode = (String) eachDataMap.get("STATE_CODE");
							String cityName = (String) eachDataMap.get("CITY_NAME");								
							if (resultList.size()>=1000 ) {
								dataFile.setValid(false);
								strError = "errorEveList";
							}							
							if (cityCode != null && cityCode.trim() != "") {
								try {
									isCityCodeValid = getCityProxy().isCityCodeUnique(cityCode);
								} catch (NoSuchGeographyException e) {
									throw new NoSuchGeographyException("City code null !");
								} catch (Exception e) {
									throw new NoSuchGeographyException("City code null !");
								}
							}
							
							if ( stateCode != null && ! stateCode.equals("") ) {
								try {
									IStateProxyManager state = (IStateProxyManager)BeanHouse.get("stateProxy");
									isStateCodeValid = state.isStateCodeUnique(stateCode);
									if( isStateCodeValid )
										isStateCodeValid = false;									
									else
										isStateCodeValid = true;									
								} catch (NoSuchGeographyException e) {
									throw new NoSuchGeographyException("State code null !");
								} catch (Exception e) {
									throw new NoSuchGeographyException("State code null !");
								}
							}
							else
								isStateCodeValid = true;
							
							if ( isCityCodeValid ) {
								errorList[incr][0] = "CITY_CODE";
								errorList[incr][1] = "Already exist";
								uniqueErrorMap.put(new Integer(index),errorList);
								dataFile.setErrorList(uniqueErrorMap);
								dataFile.setValid(false);
								strError = "errorEveList";
								incr++;
							}
							
							else if ( isStateCodeValid ) {
								errorList[incr][0] = "STATE_CODE";
								errorList[incr][1] = "Invalid Code";
								uniqueErrorMap.put(new Integer(index),errorList);
								dataFile.setErrorList(uniqueErrorMap);
								dataFile.setValid(false);
								strError = "errorEveList";
								incr++;
							}
							if ( ASSTValidator.isValidANDName(cityName) ) {
									errorList[incr][0] = "CITY_NAME";
									errorList[incr][1] = "Special Character(s) Not Allowed except {&}";
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
							trxValueOut = getCityProxy().makerInsertMapperCity(ctx,obFileMapperID);
							// Set Transaction ID into Mapper Table
							OBFileMapperMaster obFileMapperMaster = new OBFileMapperMaster();
							obFileMapperMaster.setFileId(fileId);
							obFileMapperMaster.setTransId(trxValueOut.getTransactionID());

							// insert records in City staging table
							// and store it's ID and transactionId in a master
							// table
							try {
								int noOfRowsInserted = getCityProxy().insertCity(obFileMapperMaster,userName, resultList);
								DefaultLogger.debug(this,"No of rows inserted == "+ noOfRowsInserted);
							} catch (Exception e) {
								transDenied="true";     //A shiv 170811
								strError = "errorEveList";   //A shiv 170811
								getCityProxy().deleteTransaction (obFileMapperMaster);  //A shiv 170811
							}
							resultMap.put("request.ITrxValue", trxValueOut);
						}

						// If there is a Error
						if (dataFile.isValid() == false) {
							strError = "errorEveList";
							resultMap.put("rowCount", new Integer(dataFile.getMaxCount()));
							resultMap.put("errorList", dataFile.getErrorList());
						}
					}// end of Footer Checksum
				}// end of PENDING Approval
				resultMap.put("noOfRecords",resultList.size() );
			}// end of CSV file 
					
				resultMap.put("transDenied",transDenied);  //A shiv 170811
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
