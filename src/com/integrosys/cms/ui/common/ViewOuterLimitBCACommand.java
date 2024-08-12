/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/01/28 05:25:22 $ Tag: $Name: $
 */
public class ViewOuterLimitBCACommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	public ViewOuterLimitBCACommand() {
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
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "limitID", "java.lang.String", REQUEST_SCOPE },
				{ "outerLimitID", "java.lang.String", REQUEST_SCOPE },
				{ "outerLimitProfileID", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "outerLimitBCA", "java.lang.Object", REQUEST_SCOPE },
				{ "fam", "java.lang.String", REQUEST_SCOPE }, { "famcode", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");

			long outerLimitID = Long.parseLong((String) map.get("outerLimitID"));
			long limitID = Long.parseLong((String) map.get("limitID"));
			long outerLimitProfileID = Long.parseLong((String) map.get("outerLimitProfileID"));

			ILimitProfile limitProfileOB = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			ICMSCustomer custOB = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);

			CustomerSearchCriteria objSearch = new CustomerSearchCriteria();
			objSearch.setCtx(ctx);
			objSearch.setCheckDAP(true);

			ILimit limit = new OBLimit();
			limit.setLimitID(limitID);
			limit.setOuterLimitID(outerLimitID);
			if (limitProfileOB != null) {
				limit.setLimitProfileID(limitProfileOB.getLimitProfileID());
			}
			limit.setOuterLimitProfileID(outerLimitProfileID);
			ILimit[] limitArr = new ILimit[1];
			limitArr[0] = limit;

			objSearch.setLimits(limitArr);

			ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
			SearchResult sr = custproxy.searchCustomer(objSearch);
			Collection resultList = sr.getResultList();
			if (!resultList.isEmpty()) {
				Iterator itr = resultList.iterator();
				resultMap.put("outerLimitBCA", itr.next());
			}

			String fam = null;
			String famcode = null;
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			if (custOB.getNonBorrowerInd()) {
				if (Long.toString(custOB.getCustomerID()) != null) {

					fam = (String) limitProxy.getFAMNameByCustomer(custOB.getCustomerID()).get(ICMSConstant.FAM_NAME);
					famcode = (String) limitProxy.getFAMNameByCustomer(custOB.getCustomerID()).get(
							ICMSConstant.FAM_CODE);
				}
			}
			else {
				if (Long.toString(limitProfileOB.getLimitProfileID()) != null) {
					fam = (String) limitProxy.getFAMName(limitProfileOB.getLimitProfileID()).get(ICMSConstant.FAM_NAME);
					famcode = (String) limitProxy.getFAMName(limitProfileOB.getLimitProfileID()).get(
							ICMSConstant.FAM_CODE);
				}
			}

			resultMap.put("fam", fam);
			resultMap.put("famcode", famcode);

			DefaultLogger.debug(this, "Going out of doExecute()");
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);

			return returnMap;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in ViewOuterLimitBCACommand!", e);
			throw (new CommandProcessingException(e.toString()));
		}
	}
}