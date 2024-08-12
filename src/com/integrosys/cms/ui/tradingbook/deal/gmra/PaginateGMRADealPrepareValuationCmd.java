/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.deal.gmra;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.tradingbook.bus.IGMRADealVal;
import com.integrosys.cms.app.tradingbook.trx.IGMRADealValTrxValue;

/**
 * Describe this class. Purpose: to prepare the GMRA Deal value to be view or
 * edit Description: command that get the value from database to let the user to
 * view or edit the value
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class PaginateGMRADealPrepareValuationCmd extends AbstractCommand {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "InitialGMRADealVal", "java.util.List", FORM_SCOPE },
				{ "GMRADealValTrxValue", "com.integrosys.cms.app.tradingbook.trx.OBGMRADealValTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce the
																	// length.
				{ "length", "java.lang.Integer", SERVICE_SCOPE }, // To populate
																	// the form.
				{ "targetOffset", "java.lang.String", REQUEST_SCOPE },
				{ "preEvent", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce the
																	// length.
				{ "length", "java.lang.Integer", SERVICE_SCOPE }, // To populate
																	// the form.
				{ "request.ITrxValue", "com.integrosys.cms.app.tradingbook.trx.OBGMRADealValTrxValue", REQUEST_SCOPE },
				{ "InitialGMRADealVal", "java.util.Object", FORM_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			List inputList = (List) map.get("InitialGMRADealVal");
			int offset = ((Integer) map.get("offset")).intValue();
			int length = ((Integer) map.get("length")).intValue();
			String preEvent = (String) map.get("preEvent");
			System.out.println("offset : " + offset);

			int targetOffset = Integer.parseInt((String) inputList.get(0));
			IGMRADealVal[] inputValuation = (IGMRADealVal[]) inputList.get(1);

			IGMRADealValTrxValue gMRADealValTrxVal = (IGMRADealValTrxValue) map.get("GMRADealValTrxValue");
			IGMRADealVal[] valuationArr = null;
			if (preEvent.equals("maker_edit_valuation_reject") || preEvent.equals("maker_edit_reject_confirm")) {
				valuationArr = gMRADealValTrxVal.getStagingGMRADealValuation();
			}
			else {
				valuationArr = gMRADealValTrxVal.getGMRADealValuation();
			}

			for (int i = 0; i < inputValuation.length; i++) {
				if ((inputValuation[i].getMarketValue() != null) && !inputValuation[i].getMarketValue().equals("")) {
					valuationArr[offset + i].setMarketValue(inputValuation[i].getMarketValue());
				}
				else {
					valuationArr[offset + i].setMarketValue(null);
				}
				System.out.println("valuationArr[i] : " + valuationArr[i]);
			}
			System.out.println("valuationArr[valuationArr.length-1] : " + valuationArr[valuationArr.length - 1]);

			gMRADealValTrxVal.setStagingGMRADealValuation(valuationArr);

			targetOffset = GMRADealValMapper.adjustOffset(targetOffset, length, valuationArr.length);

			resultMap.put("request.ITrxValue", gMRADealValTrxVal);
			resultMap.put("GMRADealValTrxValue", gMRADealValTrxVal);
			resultMap.put("offset", new Integer(targetOffset));
			resultMap.put("InitialGMRADealVal", gMRADealValTrxVal.getStagingGMRADealValuation());

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
