/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.deal.isdacsa;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.tradingbook.bus.IISDACSADealVal;
import com.integrosys.cms.app.tradingbook.bus.OBISDACSADealVal;
import com.integrosys.cms.app.tradingbook.bus.TradingBookException;
import com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy;
import com.integrosys.cms.app.tradingbook.proxy.TradingBookProxyFactory;
import com.integrosys.cms.app.tradingbook.trx.IISDACSADealValTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Describe this class. Purpose: for Maker to edit the value Description:
 * command that let the maker to save the value that being edited to the
 * database
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date:$ Tag: $Name$
 */

public class ISDACSADealEditValuationCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "ISDACSADeal", "java.util.List", FORM_SCOPE }, // Collection
																	// of com.
																	// integrosys
																	// .cms.app.
																	// interestrate
																	// .bus.
																	// OBInterestRate
				{ "ISDACSADealTrxValue", "com.integrosys.cms.app.tradingbook.trx.OBISDACSADealValTrxValue",
						SERVICE_SCOPE }, { "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce
																							// the
																							// length
																							// .
				{ "length", "java.lang.Integer", SERVICE_SCOPE }, // Produce the
																	// length.
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }, });
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
				{ "ISDACSADealTrxValue", "com.integrosys.cms.app.tradingbook.trx.OBGMRADealValTrxValue", SERVICE_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE },
				{ "isEmpty", "java.lang.String", REQUEST_SCOPE },
				{ "InitialISDACSADeal", "java.util.Object", FORM_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		List inputList = (List) map.get("ISDACSADeal");
		int offset = ((Integer) map.get("offset")).intValue();
		int length = ((Integer) map.get("length")).intValue();

		int targetOffset = Integer.parseInt((String) inputList.get(0));
		OBISDACSADealVal[] inputValuation = (OBISDACSADealVal[]) inputList.get(1);

		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");

		IISDACSADealValTrxValue iSDADealValTrxVal = (IISDACSADealValTrxValue) map.get("ISDACSADealTrxValue");
		IISDACSADealVal[] obISDACSADealVal = iSDADealValTrxVal.getStagingISDACSADealValuation();
		if (obISDACSADealVal.length == 0) {
			obISDACSADealVal = iSDADealValTrxVal.getISDACSADealValuation();
		}
		DefaultLogger.debug(this, "Inside doExecute()  before PROXY CALL the busValue 'obISDACSADealVal' = "
				+ obISDACSADealVal.length);
		DefaultLogger.debug(this, "Inside doExecute() trxContext  = " + trxContext);
		boolean isEmpty = false;

		try {
			ITradingBookProxy proxy = TradingBookProxyFactory.getProxy();

			for (int i = 0; i < inputValuation.length; i++) {
				if (offset > 0) {
					int arrayInt = offset + i;
					obISDACSADealVal[arrayInt].setMarketValue(inputValuation[i].getMarketValue());
				}
				else {
					obISDACSADealVal[i].setMarketValue(inputValuation[i].getMarketValue());
				}
			}

			for (int j = 0; j < obISDACSADealVal.length; j++) {
				Amount marketValue = obISDACSADealVal[j].getMarketValue();
				if (marketValue == null) {
					isEmpty = true;
				}
			}

			DefaultLogger.debug(this, "Inside doExecute() isEmpty  = " + isEmpty);
			if (isEmpty) {
				resultMap.put("isEmpty", "isEmpty");
				resultMap.put("InitialISDACSADeal", obISDACSADealVal);
			}
			else {
				IISDACSADealValTrxValue trxValue = proxy.makerUpdateISDACSADealValuation(trxContext, iSDADealValTrxVal,
						obISDACSADealVal);
				resultMap.put("request.ITrxValue", trxValue);
				resultMap.put("ISDACSADealTrxValue", trxValue);
			}

		}
		catch (TradingBookException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
