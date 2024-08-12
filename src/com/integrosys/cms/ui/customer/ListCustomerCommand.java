/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/ListCustomerCommand.java,v 1.17 2005/05/12 12:58:51 jtan Exp $
 */

package com.integrosys.cms.ui.customer;

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

/**
 * This class is used to list the customer details based on some search
 * contsraints
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.17 $
 * @since $Date: 2005/05/12 12:58:51 $ Tag: $Name: $
 */
public class ListCustomerCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public ListCustomerCommand() {

	}

	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "customerSearchCriteria", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", FORM_SCOPE },
				{ "customerSearchCriteria1", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "indicator", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "customerList", "com.integrosys.base.businfra.search.SearchResult", FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,
						"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", GLOBAL_SCOPE },
				{ "customerSearchCriteria1", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",
						SERVICE_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		String indicator = (String) map.get("indicator");
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		CustomerSearchCriteria formCriteria = (CustomerSearchCriteria) map.get("customerSearchCriteria");
		CustomerSearchCriteria searchCriteria = null;
		
		//Start: Uma Khot: Phase 3 CR: View Scanned and linked documents for Inactive Parties
		formCriteria.setCustomerStatus(ICMSConstant.CUSTOMER_STATUS_ALL);
		
		//End: Uma Khot: Phase 3 CR: View Scanned and linked documents for Inactive Parties
		if (isEmptySearchCriteria(formCriteria) && !"*".equals(indicator)) {
			DefaultLogger.warn(this, "Search Criteria from form scope is empty using Search Criteria from Session.");
			searchCriteria = (CustomerSearchCriteria) map.get("customerSearchCriteria1");
			if (searchCriteria != null) {
				searchCriteria.setStartIndex(formCriteria.getStartIndex());
				searchCriteria.setNItems(formCriteria.getNItems());
			}
		}
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
			SearchResult sr = custproxy.searchCustomer(searchCriteria);

			result.put("customerList", sr);
			result.put(IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ, searchCriteria);
			result.put("customerSearchCriteria1", searchCriteria);
		}
		catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException(
					"failed to search customer using search criteria '" + searchCriteria + "'");
			cpe.initCause(e);
			throw cpe;
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private boolean isEmptySearchCriteria(CustomerSearchCriteria criteria) {
		if (isNotEmptyStr(criteria.getCustomerName())) {
			return false;
		}
		if (isNotEmptyStr(criteria.getLegalID())) {
			return false;
		}
		// if (isNotEmptyStr(criteria.getLeIDType())) {
		// return false;
		// }
		if (isNotEmptyStr(criteria.getIdNO())) {
			return false;
		}
		if (isNotEmptyStr(criteria.getAaNumber())) {
			return false;
		}
		if (isNotEmptyStr(criteria.getDocBarCode())) {
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
