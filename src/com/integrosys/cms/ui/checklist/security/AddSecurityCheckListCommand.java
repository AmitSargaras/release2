/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.chktemplate.bus.OBItem;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.checklist.CheckListHelper;

/**
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/03/23 09:54:53 $ Tag: $Name: $
 */
public class AddSecurityCheckListCommand extends AbstractCommand implements ICommonEventConstant {

	private ICheckListProxyManager checklistProxyManager;

	public void setCheckListProxyManager(ICheckListProxyManager checklistProxyManager) {
		this.checklistProxyManager = checklistProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public AddSecurityCheckListCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "hiddenItemID", "java.lang.String", REQUEST_SCOPE },
				{ "ccAddCheckList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE }, });
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
		String hiddenItemID = (String) map.get("hiddenItemID");
		StringTokenizer st = new StringTokenizer(hiddenItemID, ",");
		HashMap hm = new HashMap();
		while (st.hasMoreTokens()) {
			String key = st.nextToken();
			hm.put(key, key);
		}
		ArrayList list = (ArrayList) map.get("ccAddCheckList");
		Iterator itr = list.iterator();

		ArrayList itemList = new ArrayList();
		while (itr.hasNext()) {
			IItem item = (OBItem) itr.next();
			if (hm.containsKey(String.valueOf(item.getItemCode()))) {
				itemList.add(item);
			}
		}
		IItem[] tempAry = (IItem[]) itemList.toArray(new IItem[itemList.size()]);

		ICheckListTrxValue checkListTrxVal = (ICheckListTrxValue) map.get("checkListTrxVal");
		ICheckList checklist = null;

		if (checkListTrxVal != null) {
			if (ICMSConstant.STATE_REJECTED.equals(checkListTrxVal.getStatus())) {
				checklist = checkListTrxVal.getStagingCheckList();
			}
			else {
				checklist = checkListTrxVal.getCheckList();
			}
		}
		else {
			checklist = (ICheckList) map.get("checkList");
		}

		if (checklist.getCheckListItemList() != null) {
			Arrays.sort(checklist.getCheckListItemList());
		}
		checklist = linkInsuranceReceipt(checklist, tempAry);
		resultMap.put("checkList", checklist);
		resultMap.put("checkListTrxVal", checkListTrxVal);

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	/**
	 * Add newItems to the checklist. This will also spawn a specific insurance
	 * policy a predefined premium receipt. As for now, the matrix is stored in
	 * common code catetory entry.
	 * 
	 * @param checkList of type ICheckList
	 * @param newItems of type IItem[]
	 * @return ICheckList checklist updated with new checklist items
	 * @throws Exception on any errors encountered
	 */
	private ICheckList linkInsuranceReceipt(ICheckList checkList, IItem[] newItems) {
		ArrayList totalNewItems = new ArrayList();

		ICheckListItem[] existingItems = checkList.getCheckListItemList();
		if (existingItems != null) {
			totalNewItems = new ArrayList(Arrays.asList(existingItems));
		}

		HashMap tempMap = new HashMap(); // helper var to keep track
		// user-selected premium receipts.
		HashMap receiptMap = CheckListHelper.getPremiumReceiptMap();

		int count = newItems == null ? 0 : newItems.length;

		checkList.setCheckListItemList(null);
		IItem[] items;
		try {
			items = this.checklistProxyManager.getItemList(checkList);
		}
		catch (CheckListTemplateException ex) {
			throw new CommandProcessingException("failed to find template for template id ["
					+ checkList.getTemplateID() + "]", ex);
		}
		catch (CheckListException ex) {
			throw new CommandProcessingException("failed to get item list for checklist [" + checkList + "]", ex);
		}

		ArrayList docList = new ArrayList();
		if (items != null) {
			docList = new ArrayList(Arrays.asList(items));
		}

		for (int i = 0; i < count; i++) {
			IItem newItem = newItems[i];

			ICheckListItem parentItem = new OBCheckListItem();
			parentItem.setItem(newItem);
			totalNewItems.add(parentItem);

			if (newItem.getMonitorType() != null) {
				// to keep track of user selected premium receipts
				if (newItem.getMonitorType().equals(ICMSConstant.PREMIUM_RECEIPT)) {
					if (!tempMap.containsKey(newItem.getItemCode())) {
						tempMap.put(newItem.getItemCode(), parentItem);
					}
					else {
						totalNewItems.remove(parentItem);
					}
				}
				// specific insurance policy must spawn a predefined premium
				// receipt.
				else if (newItem.getMonitorType().equals(ICMSConstant.INSURANCE_POLICY)) {
					String childCode = (String) receiptMap.get(newItem.getItemCode());
					if (childCode == null) {
						continue; // no receipt tied to the policy, so no need
						// to spawn.
					}
					ICheckListItem childItem = (ICheckListItem) tempMap.get(childCode);
					if (childItem == null) {
						childItem = CheckListHelper.getPremiumReceiptItem(childCode, docList);
					}

					if (childItem != null) {
						long ref = 0;
						try {
							ref = this.checklistProxyManager.generateCheckListItemSeqNo();
						}
						catch (CheckListException ex) {
							throw new CommandProcessingException("failed to generate checklist item seq no", ex);
						}

						parentItem.setCheckListItemRef(ref);
						childItem.setParentCheckListItemRef(ref);
						if (!totalNewItems.contains(childItem)) {
							totalNewItems.add(childItem);
						}
						tempMap.put(childItem.getItemCode(), childItem);
					}
				}
			}
		}
		checkList.setCheckListItemList((ICheckListItem[]) totalNewItems.toArray(new OBCheckListItem[0]));
		return checkList;
	}
}
