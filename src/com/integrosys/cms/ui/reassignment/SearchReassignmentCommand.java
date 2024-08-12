/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/reassignment/SearchReassignmentCommand.java,v 1.2 2004/10/08 10:22:24 hshii Exp $
 */
package com.integrosys.cms.ui.reassignment;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListSearchCriteria;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealSearchCriteria;
import com.integrosys.cms.app.commodity.deal.proxy.CommodityDealProxyFactory;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/10/08 10:22:24 $ Tag: $Name: $
 */
public class SearchReassignmentCommand extends AbstractCommand {
	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "taskSearchCriteria", "java.lang.Object", FORM_SCOPE },
				{ "reassignmentType", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }, });
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
				{ "reassignmentSearchSummary", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE }, });
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
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		SearchResult search = null;
		String reassignmentType = (String) map.get("reassignmentType");
		resultMap.put("reassignmentType", reassignmentType);

		Object criteria = map.get("taskSearchCriteria");
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		if (criteria instanceof CommodityDealSearchCriteria) {
			try {
				search = CommodityDealProxyFactory.getProxy().searchDeal(ctx, (CommodityDealSearchCriteria) criteria);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}
		else {
			try {
				search = CheckListProxyManagerFactory.getCheckListProxyManager().searchCheckList(ctx,
						(CheckListSearchCriteria) criteria);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}

		resultMap.put("reassignmentSearchSummary", search);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

	private HashMap setSessionCustomer(long customerID, HashMap map) throws Exception {
		ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
		ICMSCustomer cust = custProxy.getCustomer(customerID);
		map.put(IGlobalConstant.GLOBAL_CUSTOMER_OBJ, cust);
		DefaultLogger.debug(this, "Putting Customer in Session with CustomerID: " + customerID);

		return map;
	}

	private HashMap setSessionLimitProfile(long profileID, HashMap map) throws Exception {
		ILimitProxy limitProxy = LimitProxyFactory.getProxy();
		ILimitProfile profile = limitProxy.getLimitProfile(profileID);
		map.put(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, profile);
		DefaultLogger.debug(this, "Putting LimitProfile in Session with LimitProfileID: " + profileID);

		return map;
	}
}
