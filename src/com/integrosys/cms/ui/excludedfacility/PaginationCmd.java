package com.integrosys.cms.ui.excludedfacility;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.excludedfacility.proxy.IExcludedFacilityProxyManager;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;

public class PaginationCmd extends AbstractCommand implements ICommonEventConstant{

	private IExcludedFacilityProxyManager excludedFacilityProxy;

	public IExcludedFacilityProxyManager getExcludedFacilityProxy() {
		return excludedFacilityProxy;
	}

	public void setExcludedFacilityProxy(IExcludedFacilityProxyManager excludedFacilityProxy) {
		this.excludedFacilityProxy = excludedFacilityProxy;
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
				
				{ "name", "java.lang.String", REQUEST_SCOPE },
				{ "loginId", "java.lang.String", REQUEST_SCOPE},
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ "length", "java.lang.Integer", SERVICE_SCOPE },
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
				
				 {"excludedFacilityList", "java.util.ArrayList", REQUEST_SCOPE},
				 { "startIndex", "java.lang.String", REQUEST_SCOPE },
				 { "offset", "java.lang.Integer", SERVICE_SCOPE },
					{ "length", "java.lang.Integer", SERVICE_SCOPE },
				 });
	}
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			
			String name = (String) map.get("name");
			String login = (String)map.get("loginId");
			String startIdx = (String) map.get("startIndex");
			SearchResult excludedFacilityList = new SearchResult();
			int offset = 0,length=0;
			if(null!=map.get("offset"))
			offset = ((Integer) map.get("offset")).intValue();
			if(null!=map.get("length"))
			length = ((Integer) map.get("length")).intValue();
			DefaultLogger.debug(this, "Name: " + name);
			DefaultLogger.debug(this, "Login: " + login);
			DefaultLogger.debug(this, "StartIdx: " + startIdx);
			DefaultLogger.debug(this, "Length: " + length);
			DefaultLogger.debug(this, "Offset: " + offset);
	
			excludedFacilityList= getExcludedFacilityProxy().getAllActualExcludedFacility();
		//	SearchResult facilityNewMasterSearchResult = new SearchResult(0, 10, 100, facilityNewMasterList);
			String targetOffset=startIdx;			
			resultMap.put("offset", new Integer(targetOffset));
			resultMap.put("startIndex", startIdx);
		//	resultMap.put("facilityNewMasterList", facilityNewMasterSearchResult);
			
			resultMap.put("excludedFacilityList", excludedFacilityList);
			
			
		}catch (SystemBankException ex) {
       	 DefaultLogger.debug(this, "got exception in doExecute" + ex);
         ex.printStackTrace();
         throw (new CommandProcessingException(ex.getMessage()));
	}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage(), e));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
