/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.cc;

import java.util.ArrayList;
import java.util.HashMap;

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
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/04/07 10:56:46 $ Tag: $Name: $
 */
public class LinkItemCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public LinkItemCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "index", "java.lang.String", REQUEST_SCOPE },
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "mandatoryRows", "java.lang.String", REQUEST_SCOPE },
				{ "checkedInVault", "java.lang.String", REQUEST_SCOPE },
				{ "checkedExtCustodian", "java.lang.String", REQUEST_SCOPE },
				{ "checkedAudit", "java.lang.String", REQUEST_SCOPE },
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "index", "java.lang.String", REQUEST_SCOPE },
				{ "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem", SERVICE_SCOPE },
				{ "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem", FORM_SCOPE },
				{ "linkedList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "linkableList", "java.util.ArrayList", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap. Here, the list of checklist items is split
	 * into linkable and linked lists.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");

		int index = Integer.parseInt((String) map.get("index"));
		ICheckListTrxValue trxValue = (ICheckListTrxValue) map.get("checkListTrxVal");
		ICheckList actualCheckList = trxValue.getCheckList();

		ICheckList checkList = (ICheckList) map.get("checkList");

		ICheckListItem[] list = checkList.getCheckListItemList();
		long currCheckListItemRef = list[index].getCheckListItemRef();

		ArrayList linkableList = new ArrayList();
		ArrayList linkedList = new ArrayList();
		for (int i = 0; i < list.length; i++) {
			long checkListItemID = list[i].getCheckListItemID();
			String checkListItemIDStr = String.valueOf(checkListItemID);
			long checkListItemRef = list[i].getCheckListItemRef();
			long parentItemRef = list[i].getParentCheckListItemRef();

			String itemDesc = list[i].getItemDesc();
			String itemCode = list[i].getItemCode();
			String itemRef = String.valueOf(checkListItemRef);

			String[] data = { checkListItemIDStr, itemCode, itemDesc, itemRef };
			if (!CheckListHelper.isNewCheckListItem(actualCheckList.getCheckListItemList(), checkListItemRef)) {
				// if not linked yet and this is not an ancestor of the
				// currently selected item
				if (((parentItemRef == ICMSConstant.LONG_INVALID_VALUE) || (parentItemRef == 0))
						&& !isAncestor(list, currCheckListItemRef, checkListItemRef)) {
					linkableList.add(data);
				}
				else if (parentItemRef == currCheckListItemRef) {
					linkedList.add(data);
				}
			}
		}

		resultMap.put("checkListItem", list[index]);
		resultMap.put("index", map.get("index"));
		resultMap.put("linkedList", linkedList);
		resultMap.put("linkableList", linkableList);

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	/**
	 * Traverses the document tree to determine if ancesterItemRef is an
	 * ancestor of currItemRef.
	 * @param checkListItems array of ICheckList objects representing tree
	 * @param currItemRef currently selected checkListItemRef
	 * @param ancestorItemRef checkListItemRef to check for ancestry
	 * @return boolean - true if ancestorItemRef is an ancestor of currItemRef
	 */
	public boolean isAncestor(ICheckListItem[] checkListItems, long currItemRef, long ancestorItemRef) {
		if ((checkListItems != null) && (currItemRef != ancestorItemRef)) {
			ArrayList itemRefList = new ArrayList();
			ArrayList parentItemRefList = new ArrayList();
			for (int i = 0; i < checkListItems.length; i++) {
				itemRefList.add(new Long(checkListItems[i].getCheckListItemRef()));
				parentItemRefList.add(new Long(checkListItems[i].getParentCheckListItemRef()));
			}

			Long parentItemRefLong = null;
			Long currItemRefLong = new Long(currItemRef);
			if (itemRefList.contains(currItemRefLong)) {
				parentItemRefLong = (Long) parentItemRefList.get(itemRefList.indexOf(currItemRefLong));
			}

			while ((parentItemRefLong != null) && (parentItemRefLong.longValue() != 0)
					&& (parentItemRefLong.longValue() != ICMSConstant.LONG_INVALID_VALUE)) {
				currItemRefLong = parentItemRefLong;
				if (currItemRefLong.longValue() == ancestorItemRef) {
					return true;
				}
				else if (itemRefList.contains(currItemRefLong)) {
					parentItemRefLong = (Long) parentItemRefList.get(itemRefList.indexOf(currItemRefLong));
				}
				else {
					return false;
				}
			}
		}
		return false;
	}

}
