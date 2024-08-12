/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.recreceipt;

import java.util.Arrays;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.recurrent.bus.ConvenantComparator;
import com.integrosys.cms.app.recurrent.bus.IConvenant;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.RecurrentException;
import com.integrosys.cms.app.recurrent.proxy.IRecurrentProxyManager;
import com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2006/09/21 12:27:54 $ Tag: $Name: $
 */
public class ListRecurrentReceiptCommand extends AbstractCommand {

	private IRecurrentProxyManager recurrentProxyManager;

	public void setRecurrentProxyManager(IRecurrentProxyManager recurrentProxyManager) {
		this.recurrentProxyManager = recurrentProxyManager;
	}

	public IRecurrentProxyManager getRecurrentProxyManager() {
		return recurrentProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public ListRecurrentReceiptCommand() {
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
						GLOBAL_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "recChkLst", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList", SERVICE_SCOPE },
				// {"conList", "java.util.List", SERVICE_SCOPE},
				{ "checkListTrxVal", "com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue", SERVICE_SCOPE },
				{ "limitProfile", "com.integrosys.cms.app.limit.bus.ILimitProfile", FORM_SCOPE },
				{ "wip", "java.lang.String", REQUEST_SCOPE }, });
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
		String event = (String) map.get("event");
		ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		long limitProfileId = limit.getLimitProfileID();
		long subProfileId = limit.getCustomerID();
		boolean wip;
		try {
			wip = getRecurrentProxyManager().allowRecurrentCheckListTrx(limitProfileId, subProfileId);
		}
		catch (RecurrentException ex) {
			throw new CommandProcessingException(
					"failed to checklist whether recurrent checklist trx is allowed for limit profile id ["
							+ limitProfileId + "], sub profile id [" + subProfileId + "]", ex);
		}

		if (!EVENT_READ.equals(event) && !wip) {
			resultMap.put("wip", "wip");
		}
		else {
			IRecurrentCheckListTrxValue checkListTrxVal;
			try {
				checkListTrxVal = getRecurrentProxyManager().getRecurrentCheckListTrx(limitProfileId, subProfileId);
			}
			catch (RecurrentException ex) {
				throw new CommandProcessingException("failed to retrieve recurrent workflow for  limit profile id ["
						+ limitProfileId + "], sub profile id [" + subProfileId + "]", ex);
			}

			if (checkListTrxVal != null) {
				IRecurrentCheckList recChkLst = checkListTrxVal.getCheckList();
				IConvenant conList[] = recChkLst.getConvenantList();

				if ((conList != null) && (conList.length > 0)) {
					Arrays.sort(conList, new ConvenantComparator());
				}
				recChkLst.setConvenantList(conList);
				checkListTrxVal.setCheckList(recChkLst);

				resultMap.put("recChkLst", recChkLst);
			}
			resultMap.put("limitProfile", limit);
			resultMap.put("checkListTrxVal", checkListTrxVal);
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
