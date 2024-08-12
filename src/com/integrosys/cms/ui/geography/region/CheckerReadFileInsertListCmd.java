/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.geography.region;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.region.proxy.IRegionProxyManager;
import com.integrosys.cms.app.geography.region.trx.IRegionTrxValue;
import com.integrosys.cms.app.geography.region.trx.OBRegionTrxValue;
import com.integrosys.cms.app.holiday.bus.HolidayException;

/**
 *$Author: Abhijit R $
 *Command for checker to read System bank Trx value
 */
public class CheckerReadFileInsertListCmd extends AbstractCommand implements ICommonEventConstant {
	
	
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
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "loginId", "java.lang.String", REQUEST_SCOPE},
				{ "startIndex", "java.lang.String", REQUEST_SCOPE }
				
		});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ "regionObj", "com.integrosys.cms.app.geography.region.bus.OBRegion", FORM_SCOPE },
				{"IRegionTrxValue", "com.integrosys.cms.app.geography.region.trx.IRegionTrxValue", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{"regionList", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE},
				{"startIndex", "java.lang.String", REQUEST_SCOPE},
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,HolidayException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			IFileMapperId region;
			IRegionTrxValue trxValue=null;
			String transId=(String) (map.get("TrxId"));
			String event = (String) map.get("event");

			String startIndex =(String) map.get("startIndex");
			SearchResult regionList=null;
			
			String login = (String)map.get("loginId");
			
			if(startIndex == null){
				startIndex ="0"; 
			}
			
			if(login==null){
				login="";
			}
			List result = new ArrayList();
			
			// function to get system bank Trx value
			trxValue = (OBRegionTrxValue) getRegionProxy().getInsertFileByTrxID(transId);
			// systemBank = (OBSystemBank) trxValue.getSystemBank();

			result = (List)  getRegionProxy().getAllStage(transId,login);
			
			// function to get stging value of system bank trx value
			region = (OBFileMapperID) trxValue.getStagingFileMapperID();
			
			regionList = new SearchResult(Integer.parseInt(startIndex), 10, result.size(), result);
			
			resultMap.put("regionList",regionList);
			resultMap.put("IRegionTrxValue", trxValue);
			resultMap.put("regionObj", region);
			resultMap.put("event", event);
			resultMap.put("startIndex", startIndex);
			resultMap.put("TrxId", transId);
		} catch (NoSuchGeographyException e) {
		
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (TransactionException e) {
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
}
