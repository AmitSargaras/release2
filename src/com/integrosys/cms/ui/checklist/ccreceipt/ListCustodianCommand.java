/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.ccreceipt;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CCCheckListSummary;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: jychong $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2006/07/27 06:45:18 $ Tag: $Name: $
 */
public class ListCustodianCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ListCustodianCommand() {
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
		return (new String[][] { { "colChkLst", "java.util.List", REQUEST_SCOPE },
				{ "delColChkLst", "java.util.List", REQUEST_SCOPE },
				{ "limitProfileID", "java.lang.String", FORM_SCOPE }, });
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

			OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			CCCheckListSummary[] colChkLst = null;
			// DefaultLogger.debug(this, "OBTrxContext: " + theOBTrxContext);
			DefaultLogger.debug(this, "Customer: " + customer.getNonBorrowerInd());
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			if (customer.getNonBorrowerInd()) {
				if (limit != null) {
					HashMap rmap = proxy.getAllCCCheckListSummaryListForNonBorrower(theOBTrxContext, limit
							.getLimitProfileID(), customer.getCustomerID());
					colChkLst = (CCCheckListSummary[]) rmap.get(ICMSConstant.NORMAL_LIST);
					if (colChkLst != null) {
						List l = Arrays.asList(colChkLst);
						resultMap.put("colChkLst", l);
					}
					CCCheckListSummary[] delColChkLst = (CCCheckListSummary[]) rmap.get(ICMSConstant.DELETED_LIST);
					if (delColChkLst != null) {
						List deletedList = Arrays.asList(delColChkLst);
						resultMap.put("delColChkLst", deletedList);
					}
					resultMap.put("limitProfileID", String.valueOf(limit.getLimitProfileID()));
				}
				else {
					HashMap stateCheckListsMap = proxy.getAllCCCheckListSummaryListForNonBorrower(theOBTrxContext,
							ICMSConstant.LONG_MIN_VALUE, customer.getCustomerID());

					colChkLst = (CCCheckListSummary[]) stateCheckListsMap.get(ICMSConstant.NORMAL_LIST);
					if (colChkLst == null) {

						CCCheckListSummary[] delColChkLst = (CCCheckListSummary[]) stateCheckListsMap
								.get(ICMSConstant.DELETED_LIST);
						if (delColChkLst != null) {
							List deletedList = Arrays.asList(delColChkLst);
							resultMap.put("delColChkLst", deletedList);
						}
					}
					else {
						List l = Arrays.asList(colChkLst);
						resultMap.put("colChkLst", l);
					}
				}
			}
			else {
				long limitProfileID = limit.getLimitProfileID();
				DefaultLogger.debug(this, "limitProfileID before backend call" + limitProfileID);
				// colChkLst =
				// proxy.getCCCheckListSummaryListForCustodian(theOBTrxContext
				// ,limitProfileID);
				HashMap rmap = proxy.getAllCCCheckListSummaryListForCustodian(theOBTrxContext, limitProfileID);
				colChkLst = (CCCheckListSummary[]) rmap.get(ICMSConstant.NORMAL_LIST);
				if (colChkLst != null) {
					List l = Arrays.asList(colChkLst);
					resultMap.put("colChkLst", l);
				}
				CCCheckListSummary[] delColChkLst = (CCCheckListSummary[]) rmap.get(ICMSConstant.DELETED_LIST);
				if (delColChkLst != null) {
					List deletedList = Arrays.asList(delColChkLst);
					resultMap.put("delColChkLst", deletedList);
				}
				resultMap.put("limitProfileID", String.valueOf(limitProfileID));
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
