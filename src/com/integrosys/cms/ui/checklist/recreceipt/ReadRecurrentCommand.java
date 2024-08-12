/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.recreceipt;

import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListSubItem;
import com.integrosys.cms.ui.common.FrequencyList;

/**
 * @author $Author: btan $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2004/10/28 08:41:41 $ Tag: $Name: $
 */
public class ReadRecurrentCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public ReadRecurrentCommand() {
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
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "subItemIndex", "java.lang.String", REQUEST_SCOPE },
				{ "isDPDueDateEditable", "java.lang.String", REQUEST_SCOPE },
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
		return (new String[][] {
				{ "isDPDueDateEditable", "java.lang.String", REQUEST_SCOPE },
				{ "frequencyLabels", "java.util.Collection", REQUEST_SCOPE },
				{ "frequencyValues", "java.util.Collection", REQUEST_SCOPE },
				{ "recurrentItem", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem", FORM_SCOPE },
				{ "recurrentSubItem", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListSubItem", FORM_SCOPE },
				{ "recurrentItem", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem", SERVICE_SCOPE },
				{ "recurrentSubItem", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListSubItem", SERVICE_SCOPE }
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
		int index = Integer.parseInt((String) map.get("index"));
		int subItemIndex = Integer.parseInt((String) map.get("subItemIndex"));

		IRecurrentCheckList recChkLst = (IRecurrentCheckList) map.get("recChkLst");
		IRecurrentCheckListItem[] list = recChkLst.getCheckListItemList();
		IRecurrentCheckListItem item = null;
		IRecurrentCheckListSubItem subItem = null;
		if ((list != null) && (index >= 0)) {
			item = list[index];
			if (item != null) {
				IRecurrentCheckListSubItem[] subItemsList = item.getRecurrentCheckListSubItemList();
				if ((subItemsList != null) && (subItemIndex >= 0)) {
					subItem = subItemsList[subItemIndex];
				}
			}
		}
		//Edited for DP by Anil
		String event=(String)map.get("event");
		if("edit_recurrent_dp_recipt".equals(event)){	
			FrequencyList frequencyList = FrequencyList.getInstance();
			Collection frequencyLabel = frequencyList.getFrequencyLabel();
			frequencyLabel.remove("Year(s)");
			Collection frequencyProperty = frequencyList.getFrequencyProperty();
			frequencyProperty.remove("Y");
			resultMap.put("frequencyLabels", frequencyLabel);
			resultMap.put("frequencyValues", frequencyProperty);
		}
		
		resultMap.put("recurrentItem", item);
		resultMap.put("recurrentSubItem", subItem);
		resultMap.put("isDPDueDateEditable", map.get("isDPDueDateEditable"));

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
