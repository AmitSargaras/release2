/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.deal.isdacsa;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.tradingbook.bus.IISDACSADealVal;
import com.integrosys.cms.app.tradingbook.trx.IISDACSADealValTrxValue;
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

public class CompareISDACSADealCheckerReadCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "ISDACSADealTrxValue", "com.integrosys.cms.app.tradingbook.trx.OBISDACSADealValTrxValue",
						SERVICE_SCOPE },
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
				{ "ISDACSADealTrxValue", "com.integrosys.cms.app.tradingbook.trx.OBISDACSADealValTrxValue",
						SERVICE_SCOPE }, { "compareResultsList", "java.util.List", SERVICE_SCOPE }, // Produce
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

		IISDACSADealValTrxValue iSDADealValTrxVal = (IISDACSADealValTrxValue) map.get("ISDACSADealTrxValue");
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		DefaultLogger.debug(this, "Inside doExecute() iSDADealValTrxVal =" + iSDADealValTrxVal);

		int offset = ((Integer) map.get("offset")).intValue();
		int length = ((Integer) map.get("length")).intValue();

		// DefaultLogger.debug(this,
		// "Inside doExecute() iSDADealValTrxVal.getISDACSADealValuation() ="+
		// iSDADealValTrxVal.getISDACSADealValuation());
		IISDACSADealVal[] actualISDACSADealVal = iSDADealValTrxVal.getISDACSADealValuation();
		DefaultLogger.debug(this, "Inside doExecute() actualISDACSADealVal =" + actualISDACSADealVal);
		IISDACSADealVal[] stagingISDACSADealVal = iSDADealValTrxVal.getStagingISDACSADealValuation();

		try {

			List compareResultsList = CompareOBUtil.compOBArray(stagingISDACSADealVal, actualISDACSADealVal);

			offset = ISDACSADealMapper.adjustOffset(offset, length, compareResultsList.size());

			resultMap.put("compareResultsList", compareResultsList);
			resultMap.put("ISDACSADealValTrxValue", iSDADealValTrxVal);
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
