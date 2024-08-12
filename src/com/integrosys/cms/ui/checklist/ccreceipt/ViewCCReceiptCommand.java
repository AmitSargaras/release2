/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/checklist/ccreceipt/ViewCCReceiptCommand.java,v 1.5 2006/06/21 10:53:43 czhou Exp $
 */
package com.integrosys.cms.ui.checklist.ccreceipt;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;

/**
 * Description
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/06/21 10:53:43 $ Tag: $Name: $
 */
public class ViewCCReceiptCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				// {"checkListTrxVal",
				// "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue",
				// SERVICE_SCOPE},
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "itemRef", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "itemOb", "com.integrosys.cms.app.checklist.bus.ICheckListItem", REQUEST_SCOPE },
				{ "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem", FORM_SCOPE },
		// {"actualShareCheckList", "java.util.List", REQUEST_SCOPE}
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
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			long itemRef = Long.parseLong((String) map.get("itemRef"));
			ICheckList chkList = (ICheckList) map.get("checkList");
			ICheckListItem item = getItem(chkList.getCheckListItemList(), itemRef);

			resultMap.put("checkListItem", item);
			resultMap.put("itemOb", item);
			// resultMap.put("actualShareCheckList", getShareCheckList(item));
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	private ICheckListItem getItem(ICheckListItem temp[], long itemRef) {
		ICheckListItem item = null;
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

	/*
	 * private List getShareCheckList(ICheckListItem aCheckListItem) { List
	 * shareCheckList = new ArrayList(); if (aCheckListItem.getShareCheckList()
	 * != null) { IShareDoc[] temp = aCheckListItem.getShareCheckList(); if
	 * (temp.length > 0) { for (int i = 0; i < temp.length; i++) {
	 * ((OBShareDoc)temp[i]).setLeNameFromDB(); shareCheckList.add(temp[i]); } }
	 * } return shareCheckList; }
	 */
}
