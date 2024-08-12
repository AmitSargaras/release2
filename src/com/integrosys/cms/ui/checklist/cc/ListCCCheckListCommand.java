/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.cc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CCCheckListSummary;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: jychong $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2006/09/25 07:07:47 $ Tag: $Name: $
 */
public class ListCCCheckListCommand extends AbstractCommand implements ICommonEventConstant {

	private ICheckListProxyManager checklistProxyManager;

	public void setCheckListProxyManager(ICheckListProxyManager checklistProxyManager) {
		this.checklistProxyManager = checklistProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public ListCCCheckListCommand() {
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
				{ "isViewFlag", "java.lang.String", REQUEST_SCOPE },
                { IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }
                });
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
				{ "limitProfileID", "java.lang.String", FORM_SCOPE },
                { "isViewFlag", "java.lang.Boolean", REQUEST_SCOPE }});
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

		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
        String isViewFlag = (String) map.get("isViewFlag");

		CCCheckListSummary[] colChkLst = null;
		if (customer.getNonBorrowerInd()) {
			long aLimitProfileID = ICMSConstant.LONG_MIN_VALUE;
			boolean isNBCheckListOnly = true;
			ILimitProfile aLimitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			if (aLimitProfile != null) {
				aLimitProfileID = aLimitProfile.getLimitProfileID();
				isNBCheckListOnly = false;
				resultMap.put("limitProfileID", String.valueOf(aLimitProfileID));
			}

			try {
				colChkLst = this.checklistProxyManager.getCCCheckListSummaryListForNonBorrower(theOBTrxContext,
						aLimitProfileID, customer.getCustomerID(), isNBCheckListOnly);
			}
			catch (CheckListTemplateException ex) {
				throw new CommandProcessingException("failed to retrieve cc checklist summary list for non borrower",
						ex);
			}
			catch (CheckListException ex) {
				throw new CommandProcessingException("failed to retrieve cc checklist summary list for non borrower",
						ex);
			}
		}
		else {
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			long limitProfileID = limit.getLimitProfileID();
			theOBTrxContext.setTrxCountryOrigin(limit.getOriginatingLocation().getCountryCode());

			try {
				colChkLst = this.checklistProxyManager.getCCCheckListSummaryList(theOBTrxContext, limitProfileID);
			}
			catch (CheckListTemplateException ex) {
				throw new CommandProcessingException("failed to retrieve cc checklist summary list for borrower", ex);
			}
			catch (CheckListException ex) {
				throw new CommandProcessingException("failed to retrieve cc checklist summary list for borrower", ex);
			}

			resultMap.put("limitProfileID", String.valueOf(limitProfileID));
		}

		List l = new ArrayList();
		if (colChkLst != null) {
			l = Arrays.asList(colChkLst);
		}

		resultMap.put("colChkLst", l);
		resultMap.put("frame", "true");
        resultMap.put("isViewFlag", new Boolean(isViewFlag));

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
