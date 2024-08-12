package com.integrosys.cms.ui.manualinput.customer;

import static com.integrosys.cms.ui.manualinput.IManualInputConstants.SESSION_CO_BORROWER_DETAILS_KEY;

import java.util.HashMap;
import java.util.List;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.manualinput.aa.AAUIHelper;

	/**
	 * Class created by
	 * @author sandiip.shinde
	 * @since 17-03-2011
	 *
	 */

public class ManualInputListCustomerCommand extends AbstractCommand{
	
	
	
	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "customerNameShort", "java.lang.String", REQUEST_SCOPE },
				{ "legalId", "java.lang.String", REQUEST_SCOPE },
				{ "customerSearchCriteria", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", FORM_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, 
				{ "systemName", "java.lang.String", REQUEST_SCOPE },
				{ "systemId", "java.lang.String", REQUEST_SCOPE },
				{ "status", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				
				{ "legalSource", "java.lang.String", REQUEST_SCOPE }
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
				{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", FORM_SCOPE }, 
				{ "customerId", "java.lang.String", REQUEST_SCOPE },
				{ "customerNameShort", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "customerList", "java.util.List", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "found", "java.lang.String", REQUEST_SCOPE },
				{ SESSION_CO_BORROWER_DETAILS_KEY, List.class.getName(), SERVICE_SCOPE },
			});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get data from database for Interest
	 * Rate is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap result = new HashMap();

		String event = (String) map.get("event");              //get
		String startIndex = (String) map.get("startIndex");
		DefaultLogger.debug(this, "Inside doExecute()  event= " + event
				+ ", ManualInputCustomerID= obCMSLegalEntity.getLEReference()");
        String partyId = (String)map.get("legalId");
		String systemName = (String) map.get("systemName");
		String sourceSystemId = (String) map.get("systemId");
		String partyName = (String) map.get("customerNameShort");
		DefaultLogger.debug(this, " >>>>>>>>>.... Legal Id " + map.get("systemName"));
		DefaultLogger.debug(this, " >>>>>>>>>.... Legal Source " + map.get("systemId"));
//		DefaultLogger.debug(this, " >>>>>>>>>.... Legal Source " + map.get("customerNameShort"));

		String customerFound = ICMSConstant.FALSE_VALUE;
		ICMSCustomer customerOB = null;
		try {
		/*	
			CustomerSearchCriteria formCriteria = (CustomerSearchCriteria) map.get("customerSearchCriteria");
			CustomerSearchCriteria sessionCriteria = null;
			OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
			
			if (sessionCriteria == null) {
				sessionCriteria = formCriteria;
			}
			
			sessionCriteria.setCtx(theOBTrxContext);

			AAUIHelper helper = new AAUIHelper();
			SearchResult sr = helper.getSBMIAAProxy().searchMICustomer(sessionCriteria);
			resultMap.put("customerList", sr);*/
			/*if ("first_search".equals(event)) {*/
				DefaultLogger.debug(this, " >>>>>>>>>.... calling getCustomerByCIFSource ");

				// First search will trigger request to the Customer source for
				// data, if not found locally
				/*customerOB = CustomerProxyFactory.getProxy().getCustomerByCIFSource(legalId, legalSource,partyName);*/
				List cust = CustomerProxyFactory.getProxy().getCustomerByCIFSource(systemName, sourceSystemId,partyName,partyId);
				if ((customerOB != null) && (ICMSConstant.LONG_INVALID_VALUE != customerOB.getLegalEntity().getLEID())) {
					// Customer is found
					customerFound = ICMSConstant.TRUE_VALUE;
				}
				resultMap.put("customerList", cust);
				resultMap.put("event", event);
				resultMap.put("customerNameShort",partyName );
				resultMap.put("startIndex", startIndex);
				DefaultLogger.debug(this, " >>>>>>>>>.... end calling getCustomerByCIFSource ");
			/*}
			else // subsequent searching will poll db
			{
				DefaultLogger.debug(this, " >>>>>>>>>.... calling getCustomerByCIFSourceFromDB ");

				customerOB = CustomerProxyFactory.getProxy().getCustomerByCIFSourceFromDB(legalId, legalSource);
				if ((customerOB != null) && (ICMSConstant.LONG_INVALID_VALUE != customerOB.getLegalEntity().getLEID())) {
					// Customer is found
					customerFound = ICMSConstant.TRUE_VALUE;
				}
				DefaultLogger.debug(this, " >>>>>>>>>.... end calling getCustomerByCIFSourceFromDB ");
			}*/
		}
		catch (Exception e) {
			// Because this is used for AJAX, silently return default error
			// customerFound = N
		}

		/*if (customerFound == ICMSConstant.TRUE_VALUE) {	
			
			resultMap.put("OBCMSCustomer", customerOB);	//	Sandeep Shinde Commented On 28-Feb-2011		
//			DefaultLogger.debug(this, "Found customer details: " + customerOB);
			long customerId =customerOB.getCustomerID();
			String id = customerId+"";
			resultMap.put("customerId", id);
			DefaultLogger.debug(this, "Found getCustomerID : " + customerOB.getCustomerID());
			DefaultLogger.debug(this, "Found getCustomerName : " + customerOB.getCustomerName());
			DefaultLogger.debug(this, "Found getLEReference : " + customerOB.getCMSLegalEntity().getLEReference());

		}*/
		resultMap.put("found", customerFound);                        //put
//		long customerId =customerOB.getCustomerID();
//		String id = customerId+"";
//		resultMap.put("customerId", id);
		resultMap.put(SESSION_CO_BORROWER_DETAILS_KEY, null);
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	private boolean isEmptySearchCriteria(CustomerSearchCriteria criteria) {
		if (isNotEmptyStr(criteria.getCustomerName())) {
			return false;
		}
		if (isNotEmptyStr(criteria.getLegalID())) {
			return false;
		}
		if (isNotEmptyStr(criteria.getCustomerName())) {
			return false;
		}
		if (isNotEmptyStr(criteria.getCustomerName())) {
			return false;
		}
		if (isNotEmptyStr(criteria.getCustomerName())) {
			return false;
		}
		return true;
	}
	
	private boolean isNotEmptyStr(String str) {
		if (str == null) {
			return false;
		}
		if (str.trim().equals("")) {
			return false;
		}
		return true;
	}
	

}
