/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.seccoltask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CollateralCheckListSummary;
import com.integrosys.cms.app.collaborationtask.bus.CollaborationTaskException;
import com.integrosys.cms.app.collaborationtask.bus.CollaborationTaskNotAllowedException;
import com.integrosys.cms.app.collaborationtask.proxy.CollaborationTaskProxyManagerFactory;
import com.integrosys.cms.app.collaborationtask.proxy.ICollaborationTaskProxyManager;
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
 * @version $Revision: 1.7 $
 * @since $Date: 2005/02/04 03:19:37 $ Tag: $Name: $
 */
public class ListSecurityColCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ListSecurityColCommand() {
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
		return (new String[][] { { "colTaskList", "java.util.List", SERVICE_SCOPE },
				{ "status", "java.util.List", REQUEST_SCOPE }, { "colTaskList1", "java.util.List", SERVICE_SCOPE },
				{ "status1", "java.util.List", REQUEST_SCOPE }, { "frame", "java.lang.String", SERVICE_SCOPE },
				{ "error", "java.lang.String", REQUEST_SCOPE },
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
			theOBTrxContext.setTrxCountryOrigin(limit.getOriginatingLocation().getCountryCode());
			// theOBTrxContext.setTrxOrganisationOrigin(limit.
			// getOriginatingLocation().getOrganisationCode());
			DefaultLogger.debug(this, "limitProfileID before backend call" + limitProfileID);
			ICollaborationTaskProxyManager proxy = CollaborationTaskProxyManagerFactory.getProxyManager();
			HashMap hm = proxy.getCollateralSummaryList(theOBTrxContext, limit);
			// Iterator iter = hm.keySet().iterator();
			ArrayList list1 = null;
			ArrayList list2 = null;
			ArrayList list3 = null;
			ArrayList list4 = null;
			CollateralCheckListSummary[] summaryList = (CollateralCheckListSummary[]) hm
					.get(ICMSConstant.SORTED_TASK_LIST);

			// while (iter.hasNext())
			for (int ii = 0; ii < summaryList.length; ii++) {
				if (list1 == null) {
					list1 = new ArrayList();
					list2 = new ArrayList();
					list3 = new ArrayList();
					list4 = new ArrayList();
				}
				// CollateralCheckListSummary summary =
				// (CollateralCheckListSummary)iter.next();
				CollateralCheckListSummary summary = summaryList[ii];
				String canCreateInd = (String) hm.get(summary);
				// For CR CMS-534 Starts
				// list1.add(summary);
				// list2.add(canCreateInd);
				String chkListStatus = summary.getCheckListStatus();
				if (ICMSConstant.STATE_DELETED.equals(chkListStatus)) {
					list3.add(summary);
					list4.add(canCreateInd);

				}
				else if (!ICMSConstant.STATE_OBSOLETE.equals(chkListStatus)) {
					list1.add(summary);
					list2.add(canCreateInd);
				}
				// For CR CMS-534 Ends
				DefaultLogger.debug(this, "Status ---->" + canCreateInd);
			}

			// DefaultLogger.debug(this,"result from proxy "+hm);

			// CR 13 link inner to outer limit bca
			ICustomerProxy customerproxy = CustomerProxyFactory.getProxy();
			ILimit[] limits = limit.getLimits();
			CustomerSearchCriteria searchCriteria = new CustomerSearchCriteria();
			searchCriteria.setCtx(theOBTrxContext);
			searchCriteria.setLimits(limits);
			SearchResult bcaResult = customerproxy.searchCustomer(searchCriteria);

			if (bcaResult != null) {
				Collection resultCollection = bcaResult.getResultList();
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
			// ------------------------------------

			resultMap.put("colTaskList", list1);
			resultMap.put("status", list2);
			resultMap.put("frame", "true");
			resultMap.put("limitProfile", String.valueOf(limitProfileID));
			// For Cr CMS-534
			resultMap.put("colTaskList1", list3);
			resultMap.put("status1", list4);
		}
		catch (CollaborationTaskNotAllowedException e) {
			resultMap.put("error", "error");
		}
		catch (CollaborationTaskException e) {
			String errorCode = e.getErrorCode();
			if (errorCode != null) {
				resultMap.put("error", e.getErrorCode());
			}
			else {
				throw (new CommandProcessingException(e.getMessage()));
			}
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
