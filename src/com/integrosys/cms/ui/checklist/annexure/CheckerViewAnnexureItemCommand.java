/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.annexure;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListSubItem;
import com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue;

/**
 * @author $Author: mcchua $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/07/09 15:43:59 $ Tag: $Name: $
 */
public class CheckerViewAnnexureItemCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public CheckerViewAnnexureItemCommand() {
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
				{ "checkListTrxVal", "com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue", SERVICE_SCOPE },
				{ "itemRef", "java.lang.String", REQUEST_SCOPE }, { "subItemRef", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "stagingOb", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem", REQUEST_SCOPE },
				{ "actualOb", "com.integrosys.cms.app.recurrent.bus.IReccurentCheckListItem", REQUEST_SCOPE },
				{ "stagingSubItem", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListSubItem", REQUEST_SCOPE },
				{ "actualSubItem", "com.integrosys.cms.app.recurrent.bus.IReccurentCheckListSubItem", REQUEST_SCOPE },
				{ "recurrentItem", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem", FORM_SCOPE },
				{ "recurrentSubItem", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem", FORM_SCOPE }, });
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

		IRecurrentCheckListTrxValue checkListTrxVal = (IRecurrentCheckListTrxValue) map.get("checkListTrxVal");
		long itemRef = Long.parseLong((String) map.get("itemRef"));
		long subItemRef = Long.parseLong((String) map.get("subItemRef"));
		IRecurrentCheckListItem actual = null;
		IRecurrentCheckListItem staging = null;
		IRecurrentCheckListSubItem actualSubItem = null;
		IRecurrentCheckListSubItem stagingSubItem = null;
		// get actual recurrent item
		if (checkListTrxVal.getCheckList() != null) {
			actual = getItem(checkListTrxVal.getCheckList().getCheckListItemList(), itemRef);
		}
		// get actual recurrent sub item
		if (actual != null) {
			actualSubItem = getSubItem(actual.getRecurrentCheckListSubItemList(), subItemRef);
		}
		// get staging recurrent item
		if (checkListTrxVal.getStagingCheckList() != null) {
			staging = getItem(checkListTrxVal.getStagingCheckList().getCheckListItemList(), itemRef);
		}
		// get staging recurrent sub item
		if (staging != null) {
			stagingSubItem = getSubItem(staging.getRecurrentCheckListSubItemList(), subItemRef);
		}
		resultMap.put("actualOb", actual);
		resultMap.put("stagingOb", staging);
		resultMap.put("actualSubItem", actualSubItem);
		resultMap.put("stagingSubItem", stagingSubItem);
		if (staging != null) {
			resultMap.put("recurrentItem", staging);
		}
		else {
			resultMap.put("recurrentItem", actual);
		}
		if (stagingSubItem != null) {
			resultMap.put("recurrentSubItem", stagingSubItem);
		}
		else {
			resultMap.put("recurrentSubItem", actualSubItem);
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	private IRecurrentCheckListItem getItem(IRecurrentCheckListItem temp[], long itemRef) {
		IRecurrentCheckListItem item = null;
		if (temp == null) {
			return item;
		}
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].getCheckListItemRef() == itemRef) {
				item = temp[i];
			}
			else {
				continue;
			}
		}
		return item;
	}

	private IRecurrentCheckListSubItem getSubItem(IRecurrentCheckListSubItem temp[], long subItemRef) {
		IRecurrentCheckListSubItem subItem = null;
		if (temp == null) {
			return subItem;
		}
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].getSubItemRef() == subItemRef) {
				subItem = temp[i];
				break;
			}
			else {
				continue;
			}
		}
		return subItem;
	}

}
