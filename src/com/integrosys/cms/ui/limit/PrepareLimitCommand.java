/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/ViewLimitsCommand.java,v 1.21 2006/09/27 06:09:07 hshii Exp $
 */
package com.integrosys.cms.ui.limit;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;

/**
 * This class is used to list the company borrowers based on some search
 * contsraints
 * @author $Author: hshii $<br>
 * @version $Revision: 1.21 $
 * @since $Date: 2006/09/27 06:09:07 $ Tag: $Name: $
 */
public class PrepareLimitCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public PrepareLimitCommand() {

	}

	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "service.limitTrxValue", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "form.limit", "java.lang.Object", FORM_SCOPE },
				{ "limitOb", "com.integrosys.cms.app.limit.bus.ILimit", REQUEST_SCOPE },
				{ "fromPage", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		try {
			String fromPage = (String) map.get("fromPage");

			String event = (String) map.get("event");

			ILimitTrxValue limitTrxValue = (ILimitTrxValue) map.get("service.limitTrxValue");

			if (LimitsAction.EVENT_PREPARE.equals(event)) {

			}
			Object[] returnObj = { limitTrxValue.getStagingLimit(), "hasError" };
			result.put("form.limit", returnObj);
			result.put("limitOb", limitTrxValue.getStagingLimit());
			result.put("fromPage", fromPage);
		}
		catch (Exception e) {
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}
