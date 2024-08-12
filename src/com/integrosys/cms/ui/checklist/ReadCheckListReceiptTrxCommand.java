/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.recurrent.proxy.IRecurrentProxyManager;
import com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/14 11:56:12 $ Tag: $Name: $
 */
public class ReadCheckListReceiptTrxCommand extends AbstractCommand {

	private IRecurrentProxyManager recurrentProxyManager;

	public void setRecurrentProxyManager(IRecurrentProxyManager recurrentProxyManager) {
		this.recurrentProxyManager = recurrentProxyManager;
	}

	public IRecurrentProxyManager getRecurrentProxyManager() {
		return recurrentProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public ReadCheckListReceiptTrxCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "custTypeTrxID", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "checkListTrxVal",
				"com.integrosys.cms.app.checklist.trx.IRecurrentCheckListTrxValue", REQUEST_SCOPE }, });
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
			String custTypeTrxID = (String) map.get("custTypeTrxID");
			DefaultLogger.debug(this, "TrxiD before backend call" + custTypeTrxID);
			IRecurrentCheckListTrxValue checkListTrxVal = getRecurrentProxyManager().getRecurrentCheckListByTrxID(
					custTypeTrxID);
			resultMap.put("checkListTrxVal", checkListTrxVal);
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
}
