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
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;

/**
 * @author $Author: wltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2006/07/20 01:44:56 $ Tag: $Name: $
 */
public class RedirectCheckListCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public RedirectCheckListCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "custTypeTrxID", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "trxSubType", "java.lang.String", REQUEST_SCOPE },
				{ "checkListCategory", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap. Gets the transaction sub-type for the
	 * required transaction to find out which action to forward to.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 * @throws CommandProcessingException on errors
	 * @throws CommandValidationException on errors
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");

		String trxIDStr = (String) map.get("custTypeTrxID");
		String trxSubType = null;
		String chkListCategory = null;
		try {
			long trxID = Long.parseLong(trxIDStr);
			String[] result = CheckListProxyManagerFactory.getCheckListProxyManager().getTrxSubTypeByTrxID(trxID);
			if ((result != null) && (result.length == 2)) {
				trxSubType = result[0];
				chkListCategory = result[1];
			}
		}
		catch (CheckListException e) {
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		resultMap.put("trxSubType", trxSubType);
		resultMap.put("checkListCategory", chkListCategory);

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
