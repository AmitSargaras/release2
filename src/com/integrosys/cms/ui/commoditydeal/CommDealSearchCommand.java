/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/CommDealSearchCommand.java,v 1.3 2004/06/07 03:34:31 pooja Exp $
 */
package com.integrosys.cms.ui.commoditydeal;

import java.util.HashMap;
import java.util.Vector;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealSearchCriteria;
import com.integrosys.cms.app.commodity.deal.bus.OBCommodityDealSearchResult;
import com.integrosys.cms.app.commodity.deal.proxy.CommodityDealProxyFactory;
import com.integrosys.cms.app.commodity.deal.proxy.ICommodityDealProxy;
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
 * @author $Author: pooja $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/06/07 03:34:31 $ Tag: $Name: $
 */
public class CommDealSearchCommand extends AbstractCommand {
	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "commodityDealSearchCriteria",
						"com.integrosys.cms.app.commodity.deal.bus.CommodityDealSearchCriteria", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "dealNo", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },

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
				{ "dealList", "com.integrosys.base.businfra.search.SearchResult", FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },

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
		CommodityDealSearchCriteria objSearch = new CommodityDealSearchCriteria();
		objSearch = (CommodityDealSearchCriteria) map.get("commodityDealSearchCriteria");
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		SearchResult sr = null;
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			ICommodityDealProxy dealproxy = CommodityDealProxyFactory.getProxy();
			String dealNo = (String) map.get("dealNo");
			String event = (String) map.get("event");

			if (!event.equals(EVENT_SEARCH)) {
				if (dealNo != null) {
					objSearch.setDealNo(dealNo);
					sr = dealproxy.searchDeal(theOBTrxContext, objSearch);
					if (sr != null) {
						DefaultLogger.debug(this, "sr is  not null");
						DefaultLogger.debug(this, "sr is  not null" + sr.getResultList().size());
						if (sr.getResultList().size() > 0) {
							Vector v = new Vector();
							v = (Vector) sr.getResultList();
							OBCommodityDealSearchResult deal = (OBCommodityDealSearchResult) v.elementAt(0);
							if (deal != null) {
								if ((deal.getLimitProfileID()) > 0) {
									resultMap = setSessionLimitProfile(deal.getLimitProfileID(), resultMap);
								}
								else {
									DefaultLogger.debug(this, "LimitProfileID not in Deal.");
								}

								if (deal.getCustomerID() > 0) {
									resultMap = setSessionCustomer(deal.getCustomerID(), resultMap);
								}
								else {
									DefaultLogger.debug(this, "CustomerID not in Deal.");
								}
								DefaultLogger.debug(this, "Going out of doExecute()");

							}

						}
					}
				}
			}
			resultMap.put("dealList", sr);
			if (sr == null) {
				DefaultLogger.debug(this, "Result is null");
			}
			else {
				DefaultLogger.debug(this, "size of sr is" + sr.getNItems());
				DefaultLogger.debug(this, "resultlist" + sr.getResultList().size());
			}

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
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
