package com.integrosys.cms.ui.collateral.commodity;

import java.util.Arrays;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealSearchCriteria;
import com.integrosys.cms.app.commodity.deal.proxy.CommodityDealProxyFactory;
import com.integrosys.cms.app.commodity.deal.proxy.ICommodityDealProxy;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitComparator;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Description
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2005/11/12 08:43:30 $ Tag: $Name: $
 */
public class ReadExposureSummaryCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "dealList", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE },
				{ "outerLimitBCAList", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE },
				{ "limitObList", "com.integrosys.cms.app.limit.bus.OBLimitProfile", REQUEST_SCOPE },

		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
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
		SearchResult sr = null;
		CommodityDealSearchCriteria objSearch = new CommodityDealSearchCriteria();
		DefaultLogger.debug(this, "Inside doExecute()");

		try {
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			ICommodityDealProxy dealproxy = CommodityDealProxyFactory.getProxy();
			ICMSCustomer customerOB = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			ILimitProfile limitProfileOB = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);

			OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
			objSearch.setCustomerID(customerOB.getCustomerID());
			objSearch.setLimitProfileID(limitProfileOB.getLimitProfileID());
			sr = dealproxy.searchDeal(theOBTrxContext, objSearch);
			resultMap.put("dealList", sr);

			if (sr == null) {
				DefaultLogger.debug(this, "Result is null");
			}
			else {
				DefaultLogger.debug(this, "size of sr is" + sr.getNItems());
				DefaultLogger.debug(this, "resultlist" + sr.getResultList().size());
			}

			if (null == limitProfileOB) {
				throw new CommandProcessingException("ILimitProfile is null in session!");
			}
			if (limitProfileOB.getLimits() != null) {
				ILimit[] newLimits = LimitProxyFactory.getProxy().getFilteredNilColCheckListLimits(theOBTrxContext,
						limitProfileOB);
				limitProfileOB.setLimits(newLimits);

				Arrays.sort(limitProfileOB.getLimits(), new LimitComparator());

				CustomerSearchCriteria customerSearch = new CustomerSearchCriteria();
				customerSearch.setLimits(limitProfileOB.getLimits());
				ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
				sr = custproxy.searchCustomer(customerSearch);
				resultMap.put("outerLimitBCAList", sr);
			}
			resultMap.put("limitObList", limitProfileOB);

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
}
