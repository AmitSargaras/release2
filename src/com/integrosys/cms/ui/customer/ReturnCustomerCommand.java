/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/ReturnCustomerCommand.java,v 1.4 2005/05/11 10:25:30 hshii Exp $
 */

package com.integrosys.cms.ui.customer;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * This class is used to list the customer details based on some search
 * contsraints
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/05/11 10:25:30 $ Tag: $Name: $
 */
public class ReturnCustomerCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public ReturnCustomerCommand() {

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
				{ IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,
						"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", GLOBAL_SCOPE },
				// {"customerSearchCriteria",
				// "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",
				// FORM_SCOPE},
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
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
				// {IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,
				// "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria"
				// ,GLOBAL_SCOPE},
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
		CustomerSearchCriteria objSearch = (CustomerSearchCriteria) map
				.get(IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ);
		String event = (String) map.get("event");
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		if (event.equals("prepare")) {

		}
		else {
			try {
				DefaultLogger.debug(this, "Before Doing Search");
				ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
				DefaultLogger.debug(this, "start index: " + objSearch.getStartIndex());
				SearchResult sr = custproxy.searchCustomer(objSearch);

				result.put("customerList", sr);
				if (sr == null) {
					DefaultLogger.debug(this, "Result is null");
				}
				else {
				}

			}
			catch (Exception e) {
				DefaultLogger.debug(this, "got exception in doExecute", e);
				throw (new CommandProcessingException(e.getMessage()));
			}
			result.put("customerSearchCriteria1", objSearch);
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;

	}

}
