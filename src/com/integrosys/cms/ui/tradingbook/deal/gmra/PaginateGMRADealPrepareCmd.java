/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.deal.gmra;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.tradingbook.bus.IGMRADealVal;
import com.integrosys.cms.app.tradingbook.bus.OBGMRADealSummary;

/**
 * Describe this class. Purpose: to prepare the GMRA Deal value to be view or
 * edit Description: command that get the value from database to let the user to
 * view or edit the value
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class PaginateGMRADealPrepareCmd extends AbstractCommand {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "GMRADealSummaryTrxValue", "com.integrosys.cms.app.tradingbook.bus.OBGMRADealSummary", SERVICE_SCOPE },
				{ "length", "java.lang.Integer", SERVICE_SCOPE }, // To populate
																	// the form.
				{ "targetOffset", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "GMRADealSummaryTrxValue", "com.integrosys.cms.app.tradingbook.bus.OBGMRADealSummary", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce the
																	// length.
				{ "length", "java.lang.Integer", SERVICE_SCOPE }, // To populate
																	// the form.
		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {
			int length = ((Integer) map.get("length")).intValue();

			String tOffset = (String) map.get("targetOffset");
			int targetOffset = Integer.parseInt(tOffset);
			System.out.println("PaginateGMRADealPrepareCmd targetOffset : " + targetOffset);

			OBGMRADealSummary obGMRADealSummary = (OBGMRADealSummary) map.get("GMRADealSummaryTrxValue");
			IGMRADealVal[] obGMRADealVal = obGMRADealSummary.getGMRADealSummary();

			targetOffset = GMRADealMapper.adjustOffset(targetOffset, length, obGMRADealVal.length);
			System.out.println("targetOffset : " + targetOffset);

			resultMap.put("offset", new Integer(targetOffset));

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
