/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.recreceipt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListSubItem;

/**
 * @author $Author: btan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/10/28 08:41:41 $ Tag: $Name: $
 */
public class ReadRecurrentHistoryCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public ReadRecurrentHistoryCommand() {
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
				{ "subItemIndex", "java.lang.String", REQUEST_SCOPE },
				{ "recChkLst", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList", SERVICE_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "itemhistory", "java.util.List", REQUEST_SCOPE },
				{ "showSubItemDetail", "java.lang.Boolean", REQUEST_SCOPE } });
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
		IRecurrentCheckList recChkLst = (IRecurrentCheckList) map.get("recChkLst");
		IRecurrentCheckListItem[] list = recChkLst.getCheckListItemList();
		IRecurrentCheckListSubItem[] historyList = null;
		int index = Integer.parseInt((String) map.get("index"));
		IRecurrentCheckListItem recurrentItem = list[index];
		if (recurrentItem != null) {
			historyList = recurrentItem.getRecurrentCheckListSubItemList();
		}

		List history = new ArrayList();
		if (historyList != null) {
			history = Arrays.asList(historyList);
		}
		if (history.size() > 0) {
			resultMap.put("itemhistory", history);
		}
		else {
			resultMap.put("itemhistory", null);
		}

		int subItemIndex = Integer.parseInt((String) map.get("subItemIndex"));
		Boolean showSubItemDetail = subItemIndex == -1 ? Boolean.FALSE : Boolean.TRUE;
		resultMap.put("showSubItemDetail", showSubItemDetail);

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
