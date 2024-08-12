/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.cccoltask;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CCCheckListSummary;
import com.integrosys.cms.app.collaborationtask.bus.CollaborationTaskException;
import com.integrosys.cms.app.collaborationtask.bus.CollaborationTaskNotAllowedException;
import com.integrosys.cms.app.collaborationtask.proxy.CollaborationTaskProxyManagerFactory;
import com.integrosys.cms.app.collaborationtask.proxy.ICollaborationTaskProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSErrorCodes;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: kchua $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2005/02/15 07:30:02 $ Tag: $Name: $
 */
public class ListCCColCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ListCCColCommand() {
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
				{ "error", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
			ICollaborationTaskProxyManager proxy = CollaborationTaskProxyManagerFactory.getProxyManager();
			ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			HashMap hm = null;
			if (customer.getNonBorrowerInd()) {
				DefaultLogger.debug(this, "NON BORROWER");
				// hm = proxy.getCCSummaryList(theOBTrxContext,customer);
				ILimitProfile limit1 = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
				// theOBTrxContext.setTrxCountryOrigin(limit1.
				// getOriginatingLocation().getCountryCode());
				if (limit1 != null) {
					hm = proxy.getCCSummaryList(theOBTrxContext, limit1);
				}
				else {
					hm = proxy.getCCSummaryList(theOBTrxContext, customer);
				}
			}
			else {
				ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
				long limitProfileID = limit.getLimitProfileID();
				theOBTrxContext.setTrxCountryOrigin(limit.getOriginatingLocation().getCountryCode());
				DefaultLogger.debug(this, "limitProfileID before backend call" + limitProfileID);
				hm = proxy.getCCSummaryList(theOBTrxContext, limit);
				resultMap.put("limitProfile", String.valueOf(limitProfileID));
			}
			ArrayList list1 = null;
			ArrayList list2 = null;
			ArrayList list3 = null;
			ArrayList list4 = null;
			CCCheckListSummary[] summaryList = (CCCheckListSummary[]) hm.get(ICMSConstant.SORTED_TASK_LIST);
			if (summaryList != null) {
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
					CCCheckListSummary summary = summaryList[ii];
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
			}

			// DefaultLogger.debug(this,"result from proxy "+hm);

			resultMap.put("colTaskList", list1);
			resultMap.put("status", list2);
			resultMap.put("frame", "true");
			// For Cr CMS-534
			resultMap.put("colTaskList1", list3);
			resultMap.put("status1", list4);

		}
		catch (CollaborationTaskNotAllowedException e) {
			AccessDeniedException ex = new AccessDeniedException("User is not allowed to access this url!");
			ex.setErrorCode(ICMSErrorCodes.DDAP_NO_ACCESS);
			throw ex;
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
