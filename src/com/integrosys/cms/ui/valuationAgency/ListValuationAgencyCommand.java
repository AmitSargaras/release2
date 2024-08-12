package com.integrosys.cms.ui.valuationAgency;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.valuationAgency.bus.ValuationAgencyException;
import com.integrosys.cms.app.valuationAgency.proxy.IValuationAgencyProxyManager;
import com.integrosys.cms.asst.validator.ASSTValidator;

/**
 * @author rajib.aich For Valuation Agency Command for checker to approve edit .
 */
public class ListValuationAgencyCommand extends AbstractCommand implements
		ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	private IValuationAgencyProxyManager valuationAgencyProxy;

	public IValuationAgencyProxyManager getValuationAgencyProxy() {
		return valuationAgencyProxy;
	}

	public void setValuationAgencyProxy(
			IValuationAgencyProxyManager valuationAgencyProxy) {
		this.valuationAgencyProxy = valuationAgencyProxy;
	}

	public ListValuationAgencyCommand() {

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

		{ "valuationAgencyCodeSearch", "java.lang.String", REQUEST_SCOPE },
		{ "go", "java.lang.String", REQUEST_SCOPE },
		{ "valuationAgencyCodeSession", "java.lang.String", SERVICE_SCOPE },
		{ "valuationAgencyNameSearch", "java.lang.String", REQUEST_SCOPE },
		{ "valuationAgencyNameSession", "java.lang.String", SERVICE_SCOPE },
		{ "event", "java.lang.String", REQUEST_SCOPE },
		{ "startIndex", "java.lang.String", REQUEST_SCOPE }

		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "valuationAgencyCodeSearch", "java.lang.String", SERVICE_SCOPE },
				{ "valuationAgencyCodeSession", "java.lang.String", SERVICE_SCOPE },
				{ "valuationAgencyNameSearch", "java.lang.String", SERVICE_SCOPE },
				{ "valuationAgencyNameSession", "java.lang.String", SERVICE_SCOPE },
				{ "valuationAgencyList", "java.util.ArrayList", REQUEST_SCOPE },
				 { "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{
						com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,
						"java.util.Locale", GLOBAL_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {

		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String go="";
		String	valuationAgencyCodeSearch = "";
		String	valuationAgencyCodeSession="";
		String	valuationAgencyNameSearch="";
		String	valuationAgencyNameSession="";
		valuationAgencyCodeSearch=(String) map.get("valuationAgencyCodeSearch");
		valuationAgencyNameSearch=(String) map.get("valuationAgencyNameSearch");
		valuationAgencyCodeSession=(String) map.get("valuationAgencyCodeSession");
		valuationAgencyNameSession=(String) map.get("valuationAgencyNameSession");
		String event = (String) (map.get("event"));
		go=(String) map.get("go");
		// remove all values from session if valuation agency is freshly entered
				if((event.equals("checker_list_valuation")||event.equals("maker_list_valuation"))&&go==null)
				valuationAgencyCodeSession=valuationAgencyNameSession=null;
				//--> END removing values from session.
				
		// if go button is clicked then put values in session
		if(go!=null){
		if(go.equalsIgnoreCase("y")){
			valuationAgencyCodeSession=valuationAgencyCodeSearch;
			valuationAgencyNameSession=valuationAgencyNameSearch;
		}/*else{
			valuationAgencyCodeSession=valuationAgencyCode;
			valuationAgencyNameSession=valuationAgencyName;
		}*/
		}
		
		// get values from session.
		valuationAgencyCodeSearch=valuationAgencyCodeSession;
		valuationAgencyNameSearch=valuationAgencyNameSession;
		            
		try {
			
			if(null!=valuationAgencyCodeSearch)
				valuationAgencyCodeSearch=valuationAgencyCodeSearch.trim();
			if(null!=valuationAgencyNameSearch)
				valuationAgencyNameSearch=valuationAgencyNameSearch.trim();
		
			String startIdx = (String) map.get("startIndex");
			ArrayList valuationAgencyList = new ArrayList();
			if(ASSTValidator.isValidAndDotDashRoundBrackets(valuationAgencyCodeSearch)){
				valuationAgencyCodeSearch="";
			}
			if(ASSTValidator.isValidAndDotDashRoundBrackets(valuationAgencyNameSearch)){
				valuationAgencyNameSearch="";
			}
				valuationAgencyList = (ArrayList) getValuationAgencyProxy().getFilteredActual(valuationAgencyCodeSearch,valuationAgencyNameSearch);
			resultMap.put("valuationAgencyList", valuationAgencyList);
			resultMap.put("startIndex", startIdx);
			resultMap.put("event", event);
			resultMap.put("valuationAgencyCodeSearch", valuationAgencyCodeSearch);
			resultMap.put("valuationAgencyNameSearch", valuationAgencyNameSearch);
			resultMap.put("valuationAgencyCodeSession", valuationAgencyCodeSession);
			resultMap.put("valuationAgencyNameSession", valuationAgencyNameSession);
		} catch (ValuationAgencyException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(
					"Internal error while processing."));
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
