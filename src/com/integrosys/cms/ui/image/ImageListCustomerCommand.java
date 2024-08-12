package com.integrosys.cms.ui.image;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;

public class ImageListCustomerCommand extends AbstractCommand {

	/**
	 * default constructor
	 */

	public ImageListCustomerCommand() {

	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER_TEAM,"com.integrosys.component.bizstructure.app.bus.ITeam",GLOBAL_SCOPE },
				{ "customerSearchCriteria","com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",FORM_SCOPE },
				{ "customerSearchCriteria1","com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",SERVICE_SCOPE },
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "indicator", "java.lang.String", REQUEST_SCOPE }});
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
				{ "customerList","com.integrosys.base.businfra.search.SearchResult",FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",GLOBAL_SCOPE },
				{ "customerSearchCriteria1","com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",SERVICE_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *             on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *             on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,CommandValidationException {
		DefaultLogger.debug(this, "Enter in doExecute()");
		String indicator = (String) map.get("indicator");
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap resultSet = new HashMap();
		String startInd=(String) map.get("startIndex");
		DefaultLogger.debug(this, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+startInd);
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		CustomerSearchCriteria formCriteria = (CustomerSearchCriteria) map.get("customerSearchCriteria");
		CustomerSearchCriteria searchCriteria = null;
		
		//Start: Uma Khot: Phase 3 CR: View Scanned and linked documents for Inactive Parties
		formCriteria.setCustomerStatus(ICMSConstant.CUSTOMER_STATUS_ALL);
		
		//End: Uma Khot: Phase 3 CR: View Scanned and linked documents for Inactive Parties
		
		if (searchCriteria == null) {
			DefaultLogger.debug(this, "- Search Criteria from Form !");
			searchCriteria = formCriteria;
		}
		
		ITeam team = (ITeam) (map.get(IGlobalConstant.USER_TEAM));
		long teamTypeID = team.getTeamType().getTeamTypeID();

		if (teamTypeID == ICMSConstant.TEAM_TYPE_MR) {
			searchCriteria.setLmtProfileType(ICMSConstant.AA_TYPE_TRADE);
		}
		searchCriteria.setCtx(theOBTrxContext);
		
		try {
			ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
//			SearchResult sr = custproxy.searchCustomer(searchCriteria);
			SearchResult sr = custproxy.searchCustomerImageUpload(searchCriteria);
			result.put("customerList", sr);
			result.put(IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,searchCriteria);
			result.put("customerSearchCriteria1", searchCriteria);
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			CommandProcessingException cpe = new CommandProcessingException("failed to search customer using search criteria '"+ searchCriteria + "'");
			cpe.initCause(e);
			throw cpe;
		}
		result.put("selectedArrayMap", null);
		resultSet.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		resultSet.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		DefaultLogger.debug(this, "Going out of doExecute()");
		return resultSet;
	}
}
