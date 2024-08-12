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
import com.integrosys.cms.app.recurrent.bus.IConvenant;
import com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/10/07 03:40:24 $ Tag: $Name: $
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
		return (new String[][] { { "stagingOb", "com.integrosys.cms.app.recurrent.bus.IConvenant", REQUEST_SCOPE },
				{ "actualOb", "com.integrosys.cms.app.recurrent.bus.IConvenant", REQUEST_SCOPE },
				{ "covenantItem", "com.integrosys.cms.app.recurrent.bus.IConvenant", FORM_SCOPE }, });
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
			IRecurrentCheckListTrxValue checkListTrxVal = (IRecurrentCheckListTrxValue) map.get("checkListTrxVal");
			long itemRef = Long.parseLong((String) map.get("itemRef"));
			IConvenant actual = null;
			IConvenant staging = null;
			if (checkListTrxVal.getCheckList() != null) {
				actual = getItem(checkListTrxVal.getCheckList().getConvenantList(), itemRef);
			}
			if (checkListTrxVal.getStagingCheckList() != null) {
				staging = getItem(checkListTrxVal.getStagingCheckList().getConvenantList(), itemRef);
			}
			resultMap.put("actualOb", actual);
			resultMap.put("stagingOb", staging);
			if (staging != null) {
				resultMap.put("covenantItem", staging);
			}
			else {
				resultMap.put("covenantItem", actual);
			}

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
}
