/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.cccoltask;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collaborationtask.proxy.CollaborationTaskProxyManagerFactory;
import com.integrosys.cms.app.collaborationtask.proxy.ICollaborationTaskProxyManager;
import com.integrosys.cms.app.collaborationtask.trx.ICCTaskTrxValue;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/10/14 08:19:00 $ Tag: $Name: $
 */
public class ReadStagingCCColTaskCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ReadStagingCCColTaskCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
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
				{ "colTrxVal", "com.integrosys.cms.app.collaborationtask.trx.ICCTaskTrxValue", SERVICE_SCOPE },
				{ "colTask", "com.integrosys.cms.app.collaborationtask.bus.ICCTask", SERVICE_SCOPE },
				{ "colTask", "com.integrosys.cms.app.collaborationtask.bus.ICCTask", FORM_SCOPE },
				{ "closeFlag", "java.lang.String", REQUEST_SCOPE }, { "frame", "java.lang.String", SERVICE_SCOPE },
				{ "originEvent", "java.lang.String", SERVICE_SCOPE } });
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
			String trxID = (String) map.get("trxID");
			DefaultLogger.debug(this, "TrxiD before backend call" + trxID);
			ICollaborationTaskProxyManager proxy = CollaborationTaskProxyManagerFactory.getProxyManager();
			ICCTaskTrxValue colTrxVal = proxy.getCCTaskByTrxID(trxID);
			resultMap.put("originEvent", map.get("event"));
			resultMap.put("colTask", colTrxVal.getStagingCCTask());
			resultMap.put("colTrxVal", colTrxVal);
			resultMap.put("closeFlag", "true");
			resultMap.put("frame", "false");// used to hide frames when user
											// comes from to do list
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
