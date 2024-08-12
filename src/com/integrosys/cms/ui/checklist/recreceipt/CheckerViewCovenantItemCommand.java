/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.recreceipt;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.recurrent.bus.IConvenant;
import com.integrosys.cms.app.recurrent.bus.IConvenantSubItem;
import com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue;

/**
 * @author $Author: ckchua $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/01/24 02:44:41 $ Tag: $Name: $
 */
public class CheckerViewCovenantItemCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public CheckerViewCovenantItemCommand() {
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
				{ "itemRef", "java.lang.String", REQUEST_SCOPE }, { "subItemRef", "java.lang.String", REQUEST_SCOPE } });
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
				{ "stagingOb", "com.integrosys.cms.app.recurrent.bus.IConvenant", REQUEST_SCOPE },
				{ "actualOb", "com.integrosys.cms.app.recurrent.bus.IConvenant", REQUEST_SCOPE },
				// {"covenantItem",
				// "com.integrosys.cms.app.recurrent.bus.IConvenant"
				// ,FORM_SCOPE},
				{ "stagingSubItem", "com.integrosys.cms.app.recurrent.bus.IConvenantSubItem", REQUEST_SCOPE },
				{ "actualSubItem", "com.integrosys.cms.app.recurrent.bus.IConvenantSubItem", REQUEST_SCOPE },
				{ "covenantItem", "com.integrosys.cms.app.recurrent.bus.IConvenant", FORM_SCOPE },
				{ "covenantSubItem", "com.integrosys.cms.app.recurrent.bus.IConvenant", FORM_SCOPE }

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
		IRecurrentCheckListTrxValue checkListTrxVal = (IRecurrentCheckListTrxValue) map.get("checkListTrxVal");
		long itemRef = Long.parseLong((String) map.get("itemRef"));
		long subItemRef = Long.parseLong((String) map.get("subItemRef"));
		IConvenant actual = null;
		IConvenant staging = null;
		IConvenantSubItem actualSubItem = null;
		IConvenantSubItem stagingSubItem = null;

		if (checkListTrxVal.getCheckList() != null) {
			actual = getItem(checkListTrxVal.getCheckList().getConvenantList(), itemRef);
		}
		if (checkListTrxVal.getStagingCheckList() != null) {
			staging = getItem(checkListTrxVal.getStagingCheckList().getConvenantList(), itemRef);
		}
		// get actual recurrent sub item
		if (actual != null) {
			actualSubItem = getSubItem(actual.getConvenantSubItemList(), subItemRef);
		}
		// get staging recurrent sub item
		if (staging != null) {
			stagingSubItem = getSubItem(staging.getConvenantSubItemList(), subItemRef);
		}

		resultMap.put("actualOb", actual);
		resultMap.put("stagingOb", staging);
		if (staging != null) {
			resultMap.put("covenantItem", staging);
		}
		else {
			resultMap.put("covenantItem", actual);
		}

		resultMap.put("actualSubItem", actualSubItem);
		resultMap.put("stagingSubItem", stagingSubItem);

		if (stagingSubItem != null) {
			resultMap.put("covenantSubItem", stagingSubItem);
		}
		else {
			resultMap.put("covenantSubItem", actualSubItem);
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	private IConvenant getItem(IConvenant temp[], long itemRef) {
		IConvenant item = null;
		if (temp == null) {
			return item;
		}
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].getConvenantRef() == itemRef) {
				item = temp[i];
			}
			else {
				continue;
			}
		}
		return item;
	}

	private IConvenantSubItem getSubItem(IConvenantSubItem temp[], long subItemRef) {
		IConvenantSubItem subItem = null;
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
