/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.secreceipt;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CollateralCheckListSummary;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.bus.ICustomerSearchResult;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: cwtan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/02/04 03:19:21 $ Tag: $Name: $
 */
public class ListFilteredSecurityReceiptCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ListFilteredSecurityReceiptCommand() {
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
		return (new String[][] { { "colChkLst", "java.util.List", REQUEST_SCOPE },
				{ "delColChkLst", "java.util.List", REQUEST_SCOPE },
				{ "limitProfileID", "java.lang.String", FORM_SCOPE },
				{ "innerOuterBcaObList", "java.util.HashMap", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			long limitProfileID = limit.getLimitProfileID();
			OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
			DefaultLogger.debug(this, "limitProfileID before backend call" + limitProfileID);
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			// CollateralCheckListSummary colChkLst[] =
			// proxy.getFilteredCollateralCheckListSummaryList(theOBTrxContext,
			// limitProfileID);\
			HashMap checkListMap = proxy.getAllFilteredCollateralCheckListSummaryList(theOBTrxContext, limitProfileID,
					true);
			if (checkListMap != null) {
				CollateralCheckListSummary[] colChkLst = (CollateralCheckListSummary[]) checkListMap
						.get(ICMSConstant.NORMAL_LIST);
				if (colChkLst != null) {
					List l = Arrays.asList(colChkLst);
					resultMap.put("colChkLst", l);
				}
				CollateralCheckListSummary[] delColChkLst = (CollateralCheckListSummary[]) checkListMap
						.get(ICMSConstant.DELETED_LIST);
				if (delColChkLst != null) {
					List res = Arrays.asList(delColChkLst);
					resultMap.put("delColChkLst", res);
				}
			}
			// CR 13
			ICustomerProxy customerProxy = CustomerProxyFactory.getProxy();
			HashMap bcaList = new HashMap();
			ILimit[] limitList = limit.getLimits();

			CustomerSearchCriteria searchCriteria = new CustomerSearchCriteria();
			searchCriteria.setCtx(theOBTrxContext);
			searchCriteria.setLimits(limitList);

			SearchResult searchResult = customerProxy.searchCustomer(searchCriteria);

			if (searchResult != null) {
				Collection resultCollection = searchResult.getResultList();
				if (resultCollection != null) {
					HashMap colBcaList = new HashMap();
					HashMap bcaInfo = null;
					Iterator itor = resultCollection.iterator();
					while (itor.hasNext()) {
						bcaInfo = new HashMap();
						ICustomerSearchResult customerSearchResult = (ICustomerSearchResult) itor.next();
						bcaInfo.put("bcaRef", customerSearchResult.getInstructionRefNo());
						bcaInfo.put("bkgLoc", customerSearchResult.getOrigLocCntry());
						bcaInfo.put("leId", customerSearchResult.getLegalReference());
						bcaInfo.put("custName", customerSearchResult.getCustomerName());
						colBcaList.put(String.valueOf(customerSearchResult.getInnerLimitID()), bcaInfo);
					}
					resultMap.put("innerOuterBcaObList", colBcaList);
				}
				else {
					resultMap.put("innerOuterBcaObList", new HashMap());
				}
			}
			else {
				resultMap.put("innerOuterBcaObList", new HashMap());
			}

			resultMap.put("limitProfileID", String.valueOf(limitProfileID));
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
