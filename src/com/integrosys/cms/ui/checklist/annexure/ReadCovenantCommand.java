/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.annexure;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.recurrent.bus.IConvenant;
import com.integrosys.cms.app.recurrent.bus.IConvenantSubItem;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/09/21 12:27:54 $ Tag: $Name: $
 */
public class ReadCovenantCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public ReadCovenantCommand() {
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
				{ "isComplied", "java.lang.String", REQUEST_SCOPE },
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
		return (new String[][] { { "covenantItem", "com.integrosys.cms.app.recurrent.bus.IConvenant", SERVICE_SCOPE },
				{ "covenantItem", "com.integrosys.cms.app.recurrent.bus.IConvenant", FORM_SCOPE },
				{ "covenantSubItem", "com.integrosys.cms.app.recurrent.bus.IConvenantSubItem", FORM_SCOPE },
				{ "covenantSubItem", "com.integrosys.cms.app.recurrent.bus.IConvenantSubItem", SERVICE_SCOPE } });
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
		IConvenant[] conList = recChkLst.getConvenantList();

		int index = Integer.parseInt((String) map.get("index"));
		int subItemIndex = Integer.parseInt((String) map.get("subItemIndex"));
		IConvenant covenant = conList[index];

		IConvenantSubItem covenantSubItem = null;
		if (covenant != null) {
			IConvenantSubItem subItems[] = covenant.getConvenantSubItemList();
			if ((subItems != null) && (subItemIndex >= 0)) {
				covenantSubItem = subItems[subItemIndex];
			}
		}

		String isComplied = (String) map.get("isComplied");
		DefaultLogger.debug(this, "indicator:" + isComplied);
		if ((isComplied != null) && (isComplied.trim().length() > 0)) {
			Boolean value = new Boolean(isComplied);
			covenantSubItem.setIsVerifiedInd(value.booleanValue());
		}

		resultMap.put("covenantItem", covenant);
		resultMap.put("covenantSubItem", covenantSubItem);

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
