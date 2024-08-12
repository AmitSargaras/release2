package com.integrosys.cms.ui.valuationAgency;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.facilityNewMaster.proxy.IFacilityNewMasterProxyManager;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.valuationAgency.bus.ValuationAgencyException;
import com.integrosys.cms.app.valuationAgency.proxy.IValuationAgencyProxyManager;

public class PaginationCmd extends AbstractCommand implements ICommonEventConstant {
	private IValuationAgencyProxyManager valuationAgencyProxy;


	public IValuationAgencyProxyManager getValuationAgencyProxy() {
		return valuationAgencyProxy;
	}

	public void setValuationAgencyProxy(
			IValuationAgencyProxyManager valuationAgencyProxy) {
		this.valuationAgencyProxy = valuationAgencyProxy;
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
				{ "valuationAgencyCodeSearch", "java.lang.String", SERVICE_SCOPE },
				{ "valuationAgencyCodeSession", "java.lang.String", SERVICE_SCOPE },
				{ "valuationAgencyNameSearch", "java.lang.String", SERVICE_SCOPE },
				{ "valuationAgencyNameSession", "java.lang.String", SERVICE_SCOPE },
				{ "name", "java.lang.String", REQUEST_SCOPE },
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
				{ "valuationAgencyCodeSearch", "java.lang.String", SERVICE_SCOPE },
				{ "valuationAgencyCodeSession", "java.lang.String", SERVICE_SCOPE },
				{ "valuationAgencyNameSearch", "java.lang.String", SERVICE_SCOPE },
				{ "valuationAgencyNameSession", "java.lang.String", SERVICE_SCOPE },
				 {"valuationAgencyList", "java.util.ArrayList", REQUEST_SCOPE},
				 { "startIndex", "java.lang.String", REQUEST_SCOPE }
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
			ArrayList valuationAgencyList = new ArrayList();
			/*
			 * maintain valuationAgencyCode in session when clicking on next
			 */
			String valuationAgencyCodeSearch = "";
			String valuationAgencyCodeSession=(String) map.get("valuationAgencyCodeSession");
			if("".equals(valuationAgencyCodeSession))
				valuationAgencyCodeSession=null;
			if(valuationAgencyCodeSession!=null){
				valuationAgencyCodeSearch=valuationAgencyCodeSession;
			}else{
				valuationAgencyCodeSearch = (String) map.get("valuationAgencyCodeSearch");
				valuationAgencyCodeSession=valuationAgencyCodeSearch;
			}
			/*
			 * maintain valuationAgencyName in session when clicking on next
			 */
			String valuationAgencyNameSearch = "";
			String valuationAgencyNameSession=(String) map.get("valuationAgencyNameSession");
			if("".equals(valuationAgencyNameSession))
				valuationAgencyNameSession=null;
			if(valuationAgencyNameSession!=null){
				valuationAgencyNameSearch=valuationAgencyNameSession;
			}else{
				valuationAgencyNameSearch = (String) map.get("valuationAgencyNameSearch");
				valuationAgencyNameSession=valuationAgencyNameSearch;
			}

			DefaultLogger.debug(this, "Name: " + name);
			DefaultLogger.debug(this, "Login: " + login);
			DefaultLogger.debug(this, "StartIdx: " + startIdx);
		
			valuationAgencyList= (ArrayList)  getValuationAgencyProxy().getFilteredActual(valuationAgencyCodeSearch,valuationAgencyNameSearch);
			
			resultMap.put("valuationAgencyCodeSearch", valuationAgencyCodeSearch);
			resultMap.put("valuationAgencyNameSearch", valuationAgencyNameSearch);
			resultMap.put("valuationAgencyCodeSession", valuationAgencyCodeSession);
			resultMap.put("valuationAgencyNameSession", valuationAgencyNameSession);
			resultMap.put("valuationAgencyList", valuationAgencyList);
			resultMap.put("startIndex", startIdx);
			
			
		}catch (ValuationAgencyException ex) {
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
