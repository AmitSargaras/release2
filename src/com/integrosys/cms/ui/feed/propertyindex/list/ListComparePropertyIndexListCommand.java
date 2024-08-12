/*
 * Copyright Integro Technologies Pte Ltd
 * $header$
 */
package com.integrosys.cms.ui.feed.propertyindex.list;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue;

/**
 * @author $Author: btchng $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/01 09:28:23 $ Tag: $Name: $
 */
public class ListComparePropertyIndexListCommand extends AbstractCommand {

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		DefaultLogger.debug(this, "entering doExecute(...)");

		int targetOffset = ((Integer) map.get(PropertyIndexListForm.MAPPER)).intValue();
		int length = ((Integer) map.get("length")).intValue();
		// Pass through to the mapper to prepare for display.

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		IPropertyIndexFeedGroupTrxValue trxValue = (IPropertyIndexFeedGroupTrxValue) map
				.get("propertyIndexFeedGroupTrxValue");
		List compareResultsList = (List) map.get("compareResultsList");

		targetOffset = PropertyIndexListMapper.adjustOffset(targetOffset, length, compareResultsList.size());
		resultMap.put("compareResultsList", compareResultsList);
		resultMap.put("propertyIndexFeedGroupTrxValue", trxValue);
		resultMap.put(PropertyIndexListForm.MAPPER, compareResultsList);
		resultMap.put("offset", new Integer(targetOffset));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return new String[][] {// Consume the results list.
				{ "compareResultsList", "java.util.List", SERVICE_SCOPE }, // Consume
																			// the
																			// trx
																			// value
																			// .
				{ "propertyIndexFeedGroupTrxValue",
						"com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue", SERVICE_SCOPE },
				{ PropertyIndexListForm.MAPPER, "java.lang.Integer", FORM_SCOPE },
				{ "length", "java.lang.Integer", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return new String[][] {// Produce the comparision results list.
				{ "compareResultsList", "java.util.List", SERVICE_SCOPE }, // Produce
																			// the
																			// trx
																			// value
																			// nevertheless
																			// .
				{ "propertyIndexFeedGroupTrxValue",
						"com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE } };
	}

}
