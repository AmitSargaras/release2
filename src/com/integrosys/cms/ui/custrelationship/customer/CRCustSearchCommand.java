/**
 * 
 */
package com.integrosys.cms.ui.custrelationship.customer;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria;
import com.integrosys.cms.app.cci.proxy.CCICustomerProxyFactory;
import com.integrosys.cms.app.cci.proxy.ICCICustomerProxy;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author user
 *
 */
public class CRCustSearchCommand extends AbstractCommand {

	public final static String EXTERNAL_SEARCH_CRITERIA_OBJ = "counterpartySearchCriteria";
	
	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "legalID", "java.lang.String", REQUEST_SCOPE },
				{ "leIDType", "java.lang.String", REQUEST_SCOPE },
				{ "customerName", "java.lang.String", REQUEST_SCOPE },
				{ "idNO", "java.lang.String", REQUEST_SCOPE },
				{ "dbkey", "java.lang.String", REQUEST_SCOPE },
				{ "customerSeach", "java.lang.String", REQUEST_SCOPE },
				{ "all", "java.lang.String", REQUEST_SCOPE },
				{ "msgRefNo", "java.lang.String", SERVICE_SCOPE },				
				{CRCustSearchCommand.EXTERNAL_SEARCH_CRITERIA_OBJ,
						"com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria",
						GLOBAL_SCOPE },
        		{"CustRelationshipTrxValue",
        			"com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue", SERVICE_SCOPE}, 
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"offset", "java.lang.Integer", SERVICE_SCOPE},
                {"length", "java.lang.Integer", SERVICE_SCOPE},
                {"sub_profile_id", "java.lang.String", SERVICE_SCOPE},
                {"customerType", "java.lang.String", SERVICE_SCOPE},
				{"from_event", "java.lang.String", SERVICE_SCOPE},
				{"startIndex", "java.lang.String", REQUEST_SCOPE}
		});
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {

		return (new String[][] {
				{ "legalID", "java.lang.String", REQUEST_SCOPE },
				{ "leIDType", "java.lang.String", REQUEST_SCOPE },
				{ "customerName", "java.lang.String", REQUEST_SCOPE },
				{ "idNO", "java.lang.String", REQUEST_SCOPE },
				{ "dbkey", "java.lang.String", REQUEST_SCOPE },
				{ "customerSeach", "java.lang.String", REQUEST_SCOPE },
				{ "msgRefNo", "java.lang.String", SERVICE_SCOPE },
				//{ "found", "java.util.String", REQUEST_SCOPE },
				{ CRCustSearchCommand.EXTERNAL_SEARCH_CRITERIA_OBJ,
						"com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria", GLOBAL_SCOPE },
				{ "counterpartyList",
					"com.integrosys.base.businfra.search.SearchResult", FORM_SCOPE },
				{ "session.customerlist",
					"com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{"from_event", "java.lang.String", SERVICE_SCOPE}
		});
	}
	
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *             on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *             on errors
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		DefaultLogger.debug(this, "Inside doExecute()");
		
		DefaultLogger.debug(this, "Map in CRCustSearchCommand is " + map);

		// Search Parameters
		String legalId = StringUtils.defaultString((String) map.get("legalID"))
				.toUpperCase();

		String leIDType = (String) map.get("leIDType"); // leIDType = Source ID

		String customerName = StringUtils.defaultString(
				(String) map.get("customerName")).toUpperCase();
		String msgRefNo = (String) map.get("msgRefNo");

		String idNO = (String) map.get("idNO");

		String dbkey = (String) map.get("dbkey");
		String custSearch = (String) map.get("customerSeach");	
		
		String startIndexStr = (String) map.get("startIndex");
		
		int startIndex=0;
        if (startIndexStr != null) {
           startIndex= Integer.parseInt(startIndexStr) ;
        }
			
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		OBTrxContext theOBTrxContext = (OBTrxContext) map
											.get("theOBTrxContext");
		
		CounterpartySearchCriteria searchCriteria = null;

		try {
			// Constructs SearchCriteria from parameters

			searchCriteria = new CounterpartySearchCriteria();

			searchCriteria.setLeIDType(leIDType);

			searchCriteria.setLegalID(legalId);
			searchCriteria.setCustomerName(customerName);
			searchCriteria.setIdNO(idNO);
			searchCriteria.setCifSource(leIDType);
			
			searchCriteria.setDbKey(dbkey);
			searchCriteria.setMsgRefNo(msgRefNo);

			DefaultLogger.debug(this, AccessorUtil
					.printMethodValue(searchCriteria));			
			
			if (isSearchCriteriaEmpty (searchCriteria) && !((String) map.get("all")).equals("Y")) {
				searchCriteria = (CounterpartySearchCriteria) map.get("counterpartySearchCriteria"); 
			}
			
			searchCriteria.setStartIndex(startIndex);

			searchCriteria.setNItems(20);
			
			if (custSearch !=  null && custSearch.equals("true")){
               searchCriteria.setCustomerSeach(true) ;			   
            }
			
			if (theOBTrxContext != null)
				searchCriteria.setCtx(theOBTrxContext);
			
			ICCICustomerProxy custproxy = CCICustomerProxyFactory.getProxy();
			SearchResult result = custproxy.searchCCICustomer(searchCriteria);
			
			//result.
			//resultMap.put("found", ICMSConstant.TRUE_VALUE);
			resultMap.put("customerName", customerName);
			resultMap.put("counterpartyList", result);
			resultMap.put("session.customerlist", result);
			resultMap.put(EXTERNAL_SEARCH_CRITERIA_OBJ, searchCriteria);
			
		} 		
		catch (Exception e) {
			
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			//resultMap.put("found", "E");
		}

		DefaultLogger.debug(this, "Exiting doExecute()");

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return temp;
	}
	
	 private boolean isSearchCriteriaEmpty (CounterpartySearchCriteria searchCriteria) {
	    	
	    	if (searchCriteria.getLeIDType() == null && (searchCriteria.getLegalID() == null || searchCriteria.getLegalID().equals("")) &&
	    	   (searchCriteria.getCustomerName() == null || searchCriteria.getCustomerName().equals("")) && searchCriteria.getIdNO() == null && 
	    		searchCriteria.getCifSource() == null) {
	    		return true;
	    	}
	    	else {
	    		return false;
	    	}
	    	
	    }

	

}
