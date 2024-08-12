/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.recurrentMaster;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.FrequencyList;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/14 06:12:31 $ Tag: $Name: $
 */
public class PrepareRecurrentCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public PrepareRecurrentCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "frequencyLabels", "java.util.Collection", REQUEST_SCOPE },
				{ "frequencyValues", "java.util.Collection", REQUEST_SCOPE },
				{ "covenantLabels", "java.util.Collection", REQUEST_SCOPE },
				{ "covenantValues", "java.util.Collection", REQUEST_SCOPE } });
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
			FrequencyList frequencyList = FrequencyList.getInstance();
			resultMap.put("frequencyLabels", frequencyList.getFrequencyLabel());
			resultMap.put("frequencyValues", frequencyList.getFrequencyProperty());

			CommonCodeList commonCode = CommonCodeList.getInstance("COVENANT_CONDITION");

			resultMap.put("covenantLabels", commonCode.getCommonCodeLabels());
			resultMap.put("covenantValues", commonCode.getCommonCodeValues());
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
