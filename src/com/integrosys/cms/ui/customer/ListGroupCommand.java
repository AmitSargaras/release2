/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/ListGroupCommand.java,v 1.1 2003/09/04 09:18:52 lakshman Exp $
 */

package com.integrosys.cms.ui.customer;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.GroupSearchCriteria;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * This class is used to list the customer details based on some search
 * contsraints
 * @author $Author: lakshman $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/04 09:18:52 $ Tag: $Name: $
 */
public class ListGroupCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public ListGroupCommand() {

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
				{ "groupSearchCriteria", "com.integrosys.cms.app.customer.bus.GroupSearchCriteria", FORM_SCOPE },
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
		return (new String[][] { { "groupList", "com.integrosys.base.businfra.search.SearchResult", FORM_SCOPE },
		// {IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,
		// "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",
		// GLOBAL_SCOPE},
		});
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
		GroupSearchCriteria objSearch = new GroupSearchCriteria();
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		objSearch = (GroupSearchCriteria) map.get("groupSearchCriteria");
		objSearch.setCtx(theOBTrxContext);

		// DefaultLogger.debug(this,"Context is:"+theOBTrxContext);
		String event = (String) map.get("event");
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		// DefaultLogger.debug(this, "Inside doExecute()");
		if (event.equals("prepare")) {

		}
		else {
			try {
				// DefaultLogger.debug(this, "Before Doing Search");
				ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
				SearchResult sr = custproxy.searchGroup(objSearch);
				result.put("groupList", sr);
				// result.put(IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,
				// objSearch);
				if (sr == null) {
					// DefaultLogger.debug(this, "Result is null");
				}
				else {
					// DefaultLogger.debug(this,
					// "After Doing Search--> start index" +
					// sr.getStartIndex());
				}

			}
			catch (Exception e) {
				DefaultLogger.debug(this, "got exception in doExecute of List CustomerCommand" + e);
				throw (new CommandProcessingException(e.getMessage()));
			}
		}
		// DefaultLogger.debug(this, "Going out of doExecute()");
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;

	}

}
