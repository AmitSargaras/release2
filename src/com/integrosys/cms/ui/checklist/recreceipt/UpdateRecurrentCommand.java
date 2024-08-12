/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.recreceipt;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListSubItem;
import com.integrosys.cms.app.recurrent.bus.OBRecurrentCheckListSubItem;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2006/09/21 12:27:54 $ Tag: $Name: $
 */
public class UpdateRecurrentCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public UpdateRecurrentCommand() {
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
				{ "recChkLst", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList", SERVICE_SCOPE },
				// {"recurrentItem",
				// "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem"
				// , FORM_SCOPE},
				{ "recurrentItem", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem", SERVICE_SCOPE },
				{ "recurrentSubItem", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListSubItem", FORM_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE }, { "subItemIndex", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "recChkLst", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList",
				SERVICE_SCOPE },
		// {"conList", "java.util.List", SERVICE_SCOPE},
		});
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
		HashMap exceptionMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		IRecurrentCheckList recChkLst = (IRecurrentCheckList) map.get("recChkLst");
		IRecurrentCheckListItem recurrentItem = (IRecurrentCheckListItem) map.get("recurrentItem");
		IRecurrentCheckListSubItem recurrentSubItem = (IRecurrentCheckListSubItem) map.get("recurrentSubItem");

		String index = (String) map.get("index");
		String subItemIndex = (String) map.get("subItemIndex");
		if (recurrentItem != null) {
			IRecurrentCheckListSubItem tempSubItem = recurrentItem.getRecurrentCheckListSubItemList()[Integer
					.parseInt(subItemIndex)];

			if (((tempSubItem.getDeferredDate() == null) && (recurrentSubItem.getDeferredDate() != null))
					|| ((recurrentSubItem.getDeferredDate() == null) && (tempSubItem.getDeferredDate() != null))
					|| ((tempSubItem.getDeferredDate() != null) && (recurrentSubItem.getDeferredDate() != null) && (tempSubItem
							.getDeferredDate().compareTo(recurrentSubItem.getDeferredDate()) != 0))) {
				if ((tempSubItem.getReceivedDate() == null) && (recurrentSubItem.getReceivedDate() != null)) {
					exceptionMap.put("dateReceived", new ActionMessage("error.date.notAllowEditDate"));
				}
				else if ((tempSubItem.getReceivedDate() != null) && (recurrentSubItem.getReceivedDate() == null)) {
					exceptionMap.put("dateReceived", new ActionMessage("error.date.notAllowEditDate"));
				}
				else if ((tempSubItem.getReceivedDate() != null) && (recurrentSubItem.getReceivedDate() != null)
						&& (tempSubItem.getReceivedDate().compareTo(recurrentSubItem.getReceivedDate()) != 0)) {
					exceptionMap.put("dateReceived", new ActionMessage("error.date.notAllowEditDate"));
				}
			}

			if (exceptionMap.isEmpty()) {
				IRecurrentCheckListSubItem[] subItemList = recurrentItem
						.getSubItemsByCondition(ICMSConstant.RECCOV_SUB_ITEM_COND_PENDING);

				if ((subItemList.length > 0) && (recurrentSubItem.getSubItemID() == subItemList[0].getSubItemID())
						&& ((recurrentSubItem.getReceivedDate() != null) || (recurrentSubItem.getWaivedDate() != null))) {
					IRecurrentCheckListSubItem nextSubItem = new OBRecurrentCheckListSubItem();

					IRecurrentCheckListSubItem[] aPendingSubItemList = recurrentItem
							.getSubItemsByCondition(ICMSConstant.RECCOV_SUB_ITEM_COND_PENDING);
					IRecurrentCheckListSubItem[] aNonPendingSubItemList = recurrentItem
							.getSubItemsByCondition(ICMSConstant.RECCOV_SUB_ITEM_COND_NON_PENDING);

					if (aPendingSubItemList.length == 1) {
						if (aNonPendingSubItemList.length > 0) {
							if (recurrentSubItem.getSubItemID() > aNonPendingSubItemList[0].getSubItemID()) {
								nextSubItem = aNonPendingSubItemList[0];
							}
							else {
								nextSubItem = recurrentSubItem;
							}
						}
						else {
							nextSubItem = recurrentSubItem;
						}
					}
					else {
						nextSubItem = aPendingSubItemList[1];
					}

					recurrentItem.setInitialDocEndDate(nextSubItem.getDocEndDate());
					recurrentItem.setInitialDueDate(nextSubItem.getDueDate());
				}
				//Added for DP
				if("DP".equals(recurrentItem.getDocType())){
					recurrentItem.setFrequency(recurrentSubItem.getFrequency());
					recurrentItem.setFrequencyUnit(recurrentSubItem.getFrequencyUnit());
				}

				recurrentItem.updateSubItem(Integer.parseInt(subItemIndex), recurrentSubItem);
			}
		}
		if (exceptionMap.isEmpty()) {
			recChkLst.updateItem(Integer.parseInt(index), recurrentItem);
			/*
			 * IConvenant conList[] = recChkLst.getConvenantList(); List c = new
			 * ArrayList(); if(conList!=null){ //Added by Pratheepa for CR234
			 * Arrays.sort(conList, new ConvenantComparator()); c =
			 * Arrays.asList(conList); }
			 */
			resultMap.put("recChkLst", recChkLst);
			// resultMap.put("conList", c);
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		returnMap.put(COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}