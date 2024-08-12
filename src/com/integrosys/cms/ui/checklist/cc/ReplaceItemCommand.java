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
 * @version $Revision: 1.4 $
 * @since $Date: 2005/04/07 10:57:33 $ Tag: $Name: $
 */
public class ReplaceItemCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ReplaceItemCommand() {
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
				{ "replaceList", "java.util.ArrayList", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap. Here, the list of replaceable documents is
	 * retrieved.
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

		ArrayList replaceList = new ArrayList();
		// iterates through entire checklist and extracts list of checklist
		// items for replacement
		for (int i = 0; i < list.length; i++) {
			long checkListItemRef = list[i].getCheckListItemRef();
			String checkListItemRefStr = String.valueOf(checkListItemRef);
			String itemDesc = list[i].getItemDesc();
			String itemCode = list[i].getItemCode();

			String[] data = { checkListItemRefStr, itemCode, itemDesc };
			if (!CheckListHelper.isNewCheckListItem(actualCheckList.getCheckListItemList(), checkListItemRef)) {
				// if the current checklist item iteration is not the
				// checklist item itself
				if (checkListItemRef != currCheckListItemRef) {
					replaceList.add(data);
				}
			}
		}

		resultMap.put("checkListItem", list[index]);
		resultMap.put("index", map.get("index"));
		resultMap.put("replaceList", replaceList);

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
