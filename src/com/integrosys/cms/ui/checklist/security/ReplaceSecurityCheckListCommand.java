/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.security;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.checklist.CheckListHelper;

/**
 * @author $Author: wltan $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/05/10 10:17:44 $ Tag: $Name: $
 */
public class ReplaceSecurityCheckListCommand extends AbstractCommand implements ICommonEventConstant {

	private ICheckListProxyManager checklistProxyManager;

	public void setCheckListProxyManager(ICheckListProxyManager checklistProxyManager) {
		this.checklistProxyManager = checklistProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public ReplaceSecurityCheckListCommand() {
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
				{ "replaceCheckListItemRef", "java.lang.String", REQUEST_SCOPE } });
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

		String replaceItemRefStr = (String) map.get("replaceCheckListItemRef");
		long replaceItemRef = Long.parseLong(replaceItemRefStr);
		ICheckListItem replaceCheckListItem;
		try {
			replaceCheckListItem = this.checklistProxyManager.getCheckListItem(replaceItemRef);
		}
		catch (CheckListException ex) {
			throw new CommandProcessingException("failed to retrieve replace checklist item by reference number ["
					+ replaceItemRef + "]", ex);
		}

		long replaceParentItemRef = replaceCheckListItem.getParentCheckListItemRef();

		long currItemRef = ICMSConstant.LONG_INVALID_VALUE;
		long currParentItemRef = ICMSConstant.LONG_INVALID_VALUE;

		ICheckListItem checkListItem = (ICheckListItem) map.get("checkListItem");
		if (checkListItem != null) {
			currItemRef = checkListItem.getCheckListItemRef();
			currParentItemRef = checkListItem.getParentCheckListItemRef();
		}

		ICheckListTrxValue checkListTrxVal = (ICheckListTrxValue) map.get("checkListTrxVal");

		// gets the current checklist
		ICheckList checkList = null;
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

			ArrayList currChildList = getChildren(checkListItems, currItemRef);
			ArrayList replaceChildList = getChildren(checkListItems, replaceItemRef);

			// iterates through the checklist and does the following things:
			// 1. swaps the parentItemRefs of the replacer and replacee
			// 2. links the replacee's children with the replacer as parent
			// 3. links the replacer's children with the replacee as parent
			for (int i = 0; i < checkListItems.length; i++) {
				long itemRef = checkListItems[i].getCheckListItemRef();

				// swaps the parentItemRefs of the replacer and replacee
				if ((itemRef != ICMSConstant.LONG_INVALID_VALUE) && (itemRef == currItemRef)) {
					if (currItemRef == replaceParentItemRef) {
						checkListItems[i].setParentCheckListItemRef(replaceItemRef);
					}
					else {
						checkListItems[i].setParentCheckListItemRef(replaceParentItemRef);
					}
				}
				else if ((itemRef != ICMSConstant.LONG_INVALID_VALUE) && (itemRef == replaceItemRef)) {
					if (replaceItemRef == currParentItemRef) {
						checkListItems[i].setParentCheckListItemRef(currItemRef);
					}
					else {
						checkListItems[i].setParentCheckListItemRef(currParentItemRef);
					}
				}
				// links children of replacee/replacer with the correct
				// parent
				else if (currChildList.contains((Long) new Long(checkListItems[i].getCheckListItemRef()))) {
					checkListItems[i].setParentCheckListItemRef(replaceItemRef);
				}
				else if (replaceChildList.contains((Long) new Long(checkListItems[i].getCheckListItemRef()))) {
					checkListItems[i].setParentCheckListItemRef(currItemRef);
				}

			}
		}

		ICheckListItem[] sortedItems = CheckListHelper.sortByParentPrefix(checkList.getCheckListItemList());
		checkList.setCheckListItemList(sortedItems);

		resultMap.put("checkList", checkList);
		resultMap.put("checkListTrxVal", checkListTrxVal);

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	/**
	 * Traverses the document tree to get the children of a particular document.
	 * @param checkListItems array of ICheckList objects representing tree
	 * @param itemRef document to query for it's children
	 * @return ArrayList - the list of children found; an empty ArrayList
	 *         otherwise
	 */
	public ArrayList getChildren(ICheckListItem[] checkListItems, long itemRef) {
		ArrayList childItemRefList = new ArrayList();
		if ((checkListItems != null) && (itemRef != 0) && (itemRef != ICMSConstant.LONG_INVALID_VALUE)) {
			for (int i = 0; i < checkListItems.length; i++) {
				if (checkListItems[i].getParentCheckListItemRef() == itemRef) {
					childItemRefList.add(new Long(checkListItems[i].getCheckListItemRef()));
				}
			}
		}
		return childItemRefList;
	}

}
