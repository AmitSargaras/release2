/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.deal.isdacsa;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.tradingbook.bus.IISDACSADealVal;
import com.integrosys.cms.app.tradingbook.trx.IISDACSADealValTrxValue;

/**
 * Describe this class. Purpose: to prepare the ISDA CSA Deal value to be view
 * or edit Description: command that get the value from database to let the user
 * to view or edit the value
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class PaginateISDACSADealPrepareCmd extends AbstractCommand {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "InitialISDACSADeal", "java.util.List", FORM_SCOPE }, // Collection
																		// of
																		// com.
																		// integrosys
																		// .cms.
																		// app.
																		// interestrate
																		// .bus.
																		// OBInterestRate
				{ "ISDACSADealTrxValue", "com.integrosys.cms.app.tradingbook.trx.OBISDACSADealValTrxValue",
						SERVICE_SCOPE },
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
				{ "ISDACSADealTrxValue", "com.integrosys.cms.app.tradingbook.trx.OBISDACSADealValTrxValue",
						SERVICE_SCOPE }, { "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce
																							// the
																							// length
																							// .
				{ "length", "java.lang.Integer", SERVICE_SCOPE }, // To populate
																	// the form.
				{ "InitialISDACSADeal", "java.util.Object", FORM_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			List inputList = (List) map.get("InitialISDACSADeal");
			int offset = ((Integer) map.get("offset")).intValue();
			int length = ((Integer) map.get("length")).intValue();
			String preEvent = (String) map.get("preEvent");
			System.out.println("offset : " + offset);

			int targetOffset = Integer.parseInt((String) inputList.get(0));
			IISDACSADealVal[] inputValuation = (IISDACSADealVal[]) inputList.get(1);

			IISDACSADealValTrxValue isdaCsaDealValTrxVal = (IISDACSADealValTrxValue) map.get("ISDACSADealTrxValue");
			IISDACSADealVal[] valuationArr = null;
			if (preEvent.equals("maker_edit_valuation_reject") || preEvent.equals("maker_edit_reject_confirm")) {
				valuationArr = isdaCsaDealValTrxVal.getStagingISDACSADealValuation();
			}
			else {
				valuationArr = isdaCsaDealValTrxVal.getISDACSADealValuation();
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

			isdaCsaDealValTrxVal.setStagingISDACSADealValuation(valuationArr);

			targetOffset = ISDACSADealMapper.adjustOffset(targetOffset, length, valuationArr.length);

			resultMap.put("ISDACSADealTrxValue", isdaCsaDealValTrxVal);
			resultMap.put("offset", new Integer(targetOffset));
			resultMap.put("InitialISDACSADeal", isdaCsaDealValTrxVal.getStagingISDACSADealValuation());

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
