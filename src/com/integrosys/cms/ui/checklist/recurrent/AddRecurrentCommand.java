/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.recurrent;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem;
import com.integrosys.cms.app.recurrent.bus.OBRecurrentCheckList;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/09/21 12:30:04 $ Tag: $Name: $
 */
public class AddRecurrentCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public AddRecurrentCommand() {
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
				{ "recChkLst", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList", SERVICE_SCOPE },
				{ "recurrentItem", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem", FORM_SCOPE },
				{ "limitProfileId", "java.lang.String", REQUEST_SCOPE },
				{ "subProfileId", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "recChkLst", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList",
				SERVICE_SCOPE }, });
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
			String limitProfileId = (String) map.get("limitProfileId");
			String subProfileId = (String) map.get("subProfileId");
			IRecurrentCheckListItem recurrentItem = (IRecurrentCheckListItem) map.get("recurrentItem");
			IRecurrentCheckList recChkLst = (IRecurrentCheckList) map.get("recChkLst");

			if (recChkLst == null) {
				recChkLst = new OBRecurrentCheckList(Long.parseLong(limitProfileId), Long.parseLong(subProfileId));
			}
			recChkLst.addItem(recurrentItem);
			resultMap.put("recChkLst", recChkLst);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
