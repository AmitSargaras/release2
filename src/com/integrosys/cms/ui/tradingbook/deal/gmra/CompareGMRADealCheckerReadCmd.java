/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.deal.gmra;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.tradingbook.bus.IGMRADealVal;
import com.integrosys.cms.app.tradingbook.trx.IGMRADealValTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Describe this class. Purpose: for checker to read the transaction
 * Description: command that let the checker to read the transaction that being
 * make by the maker
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class CompareGMRADealCheckerReadCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "GMRADealValTrxValue", "com.integrosys.cms.app.tradingbook.trx.OBGMRADealValTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce
																													// the
																													// length
																													// .
				{ "length", "java.lang.Integer", SERVICE_SCOPE }, // To populate
																	// the form.
		});
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
				{ "GMRADealValTrxValue", "com.integrosys.cms.app.tradingbook.trx.OBGMRADealValTrxValue", SERVICE_SCOPE },
				{ "compareResultsList", "java.util.List", SERVICE_SCOPE }, // Produce
																			// the
																			// trx
																			// value
																			// nevertheless
																			// .
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce
																													// the
																													// length
																													// .
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get data from database for Interest
	 * Rate is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();

		IGMRADealValTrxValue gMRADealValTrxVal = (IGMRADealValTrxValue) map.get("GMRADealValTrxValue");
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");

		int offset = ((Integer) map.get("offset")).intValue();
		int length = ((Integer) map.get("length")).intValue();

		IGMRADealVal[] actualGMRADealVal = gMRADealValTrxVal.getGMRADealValuation();
		IGMRADealVal[] stagingGMRADealVal = gMRADealValTrxVal.getStagingGMRADealValuation();

		try {

			List compareResultsList = CompareOBUtil.compOBArray(stagingGMRADealVal, actualGMRADealVal);

			offset = GMRADealValMapper.adjustOffset(offset, length, compareResultsList.size());

			resultMap.put("compareResultsList", compareResultsList);
			resultMap.put("GMRADealValTrxValue", gMRADealValTrxVal);
			resultMap.put("offset", new Integer(offset));

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

}
