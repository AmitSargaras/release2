/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.recurrentDoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.checklist.CheckListHelper;

/**
 * @author $Author: wltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/05/10 10:22:09 $ Tag: $Name: $
 */
public class LinkRecurrentDocCheckListCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public LinkRecurrentDocCheckListCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "docDesc", "java.lang.String", REQUEST_SCOPE },
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem", SERVICE_SCOPE },
				{ "linkableItemIDs", "java.lang.String", REQUEST_SCOPE },
				{ "linkedItemIDs", "java.lang.String", REQUEST_SCOPE } });
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
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE }, });
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
		String linkableListStr = (String) map.get("linkableItemIDs");
		String linkedListStr = (String) map.get("linkedItemIDs");
		StringTokenizer linkableTokenizer = new StringTokenizer(linkableListStr, ",");
		StringTokenizer linkedTokenizer = new StringTokenizer(linkedListStr, ",");
		ArrayList linkableList = new ArrayList();
		while (linkableTokenizer.hasMoreTokens()) {
			linkableList.add(linkableTokenizer.nextToken());
		}
		ArrayList linkedList = new ArrayList();
		while (linkedTokenizer.hasMoreTokens()) {
			linkedList.add(linkedTokenizer.nextToken());
		}

		ICheckListItem checkListItem = (ICheckListItem) map.get("checkListItem");
		ICheckListTrxValue checkListTrxVal = (ICheckListTrxValue) map.get("checkListTrxVal");

		ICheckList checkList = null;
		// gets the current checklist
		if (checkListTrxVal != null) {
			if (ICMSConstant.STATE_REJECTED.equals(checkListTrxVal.getStatus())) {
				checkList = checkListTrxVal.getStagingCheckList();
			}
			else {
				checkList = checkListTrxVal.getCheckList();
			}
		}
		else {
			checkList = (ICheckList) map.get("checkList");
		}

		if (checkList != null) {
			ICheckListItem[] checkListItems = checkList.getCheckListItemList();
			// iterates through the current checklist and sets the
			// parent_item_ref of those checkListItems to be linked
			for (int i = 0; i < checkListItems.length; i++) {
				if (checkListItems[i].getCheckListItemID() != ICMSConstant.LONG_INVALID_VALUE) {
					// if current checkListItem is to be linked, set
					// parentItemRef and parentItemID
					if (linkedList.contains(String.valueOf(checkListItems[i].getCheckListItemID()))) {
						checkListItems[i].setParentCheckListItemRef(checkListItem.getCheckListItemRef());
					}
					// if current checkListItem's parentItemRef is referring
					// to this master doc, unset it
					else if (checkListItem.getCheckListItemRef() == checkListItems[i].getParentCheckListItemRef()) {
						checkListItems[i].setParentCheckListItemRef(ICMSConstant.LONG_INVALID_VALUE);
					}
				}
			}
		}

		// Sorts checklist before putting into resultMap
		ICheckListItem[] sortedItems = CheckListHelper.sortByParentPrefix(checkList.getCheckListItemList());
		checkList.setCheckListItemList(sortedItems);

		resultMap.put("checkList", checkList);
		resultMap.put("checkListTrxVal", checkListTrxVal);

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
