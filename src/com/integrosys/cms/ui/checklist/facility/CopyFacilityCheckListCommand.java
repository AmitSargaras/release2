/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.facility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
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
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.checklist.CheckListHelper;

/**
 * @author $Author: lyng $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/11/14 08:45:51 $ Tag: $Name: $
 */
public class CopyFacilityCheckListCommand extends AbstractCommand implements ICommonEventConstant {

	private ICheckListProxyManager checklistProxyManager;

	public void setCheckListProxyManager(ICheckListProxyManager checklistProxyManager) {
		this.checklistProxyManager = checklistProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public CopyFacilityCheckListCommand() {
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
				{ "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem", SERVICE_SCOPE }, });
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

		String docDesc = (String) map.get("docDesc");
		ICheckListItem parentItem = (ICheckListItem) map.get("checkListItem");
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

		checklist = copyCheckListItem(checklist, parentItem, docDesc);

		resultMap.put("checkList", checklist);
		resultMap.put("checkListTrxVal", checkListTrxVal);

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	/**
	 * Helper method to copy checklist item. It will spawn a premium receipt for
	 * an insurance policy specified in matrix common code category.
	 * 
	 * @param checklist of type ICheckList
	 * @param parentItem of type ICheckListItem
	 * @param docDesc of type String
	 * @return checklist newly updated with new items
	 * @throws Exception on any errors
	 */
	private ICheckList copyCheckListItem(ICheckList checklist, ICheckListItem parentItem, String docDesc) {
		ArrayList list = new ArrayList();
		ICheckListItem[] existingItems = checklist.getCheckListItemList();
		if (existingItems != null) {
			list = new ArrayList(Arrays.asList(existingItems));
		}

		IItem item = null;
		try {
			item = (IItem) AccessorUtil.deepClone(parentItem.getItem());
		}
		catch (IOException ex) {
			throw new CommandProcessingException("failed to clone parent item object [" + parentItem.getItem() + "]",
					ex);
		}
		catch (ClassNotFoundException ex) {
			throw new CommandProcessingException("failed to clone parent item object [" + parentItem.getItem()
					+ "] due to class not found, possible ?", ex);
		}

		item.setItemDesc(docDesc);
		item.setItemID(ICMSConstant.LONG_INVALID_VALUE);
		OBCheckListItem parentCheckListItem = new OBCheckListItem();
		parentCheckListItem.setItem(item);
		list.add(parentCheckListItem);

		// we are only interested in insurance policy defined in matrix to spawn
		// a premium receipt
		if ((item.getMonitorType() != null) && item.getMonitorType().equals(ICMSConstant.INSURANCE_POLICY)) {
			HashMap receiptMap = CheckListHelper.getPremiumReceiptMap();
			String childCode = (String) receiptMap.get(parentItem.getItem().getItemCode());
			if (childCode != null) {
				checklist.setCheckListItemList(null);
				IItem[] items;
				try {
					items = this.checklistProxyManager.getItemList(checklist);
				}
				catch (CheckListTemplateException ex) {
					throw new CommandProcessingException("failed to find template for template id ["
							+ checklist.getTemplateID() + "]", ex);
				}
				catch (CheckListException ex) {
					throw new CommandProcessingException("failed to get item list for checklist [" + checklist + "]",
							ex);
				}

				ICheckListItem childCheckListItem = null;
				if (items != null) {
					ArrayList docList = new ArrayList(Arrays.asList(items));
					childCheckListItem = CheckListHelper.getPremiumReceiptItem(childCode, docList);
				}
				if (childCheckListItem != null) {
					try {
						childCheckListItem.setParentCheckListItemRef(this.checklistProxyManager
								.generateCheckListItemSeqNo());
					}
					catch (CheckListException ex) {
						throw new CommandProcessingException("failed to generate checklist item seq no", ex);
					}

					parentCheckListItem.setCheckListItemRef(childCheckListItem.getParentCheckListItemRef());
					if (!list.contains(childCheckListItem)) {
						list.add(childCheckListItem);
					}
				}
			}
		}
		checklist.setCheckListItemList((ICheckListItem[]) list.toArray(new OBCheckListItem[0]));
		return checklist;
	}
}
