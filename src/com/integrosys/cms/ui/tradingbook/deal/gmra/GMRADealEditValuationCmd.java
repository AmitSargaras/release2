/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.deal.gmra;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.tradingbook.bus.IGMRADealVal;
import com.integrosys.cms.app.tradingbook.bus.OBGMRADealVal;
import com.integrosys.cms.app.tradingbook.bus.TradingBookException;
import com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy;
import com.integrosys.cms.app.tradingbook.proxy.TradingBookProxyFactory;
import com.integrosys.cms.app.tradingbook.trx.IGMRADealValTrxValue;
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

public class GMRADealEditValuationCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "InitialGMRADealVal", "java.util.List", FORM_SCOPE }, // Collection
																		// of
																		// com.
																		// integrosys
																		// .cms.
																		// app.
																		// interestrate
																		// .bus.
																		// OBInterestRate
				{ "GMRADealValTrxValue", "com.integrosys.cms.app.tradingbook.trx.OBGMRADealValTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce the
																	// length.
				{ "length", "java.lang.Integer", SERVICE_SCOPE }, // Produce the
																	// length.
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
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
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE },
				{ "isEmpty", "java.lang.String", REQUEST_SCOPE },
				{ "InitialGMRADealVal", "java.util.Object", FORM_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		List inputList = (List) map.get("InitialGMRADealVal");
		int offset = ((Integer) map.get("offset")).intValue();
		int length = ((Integer) map.get("length")).intValue();

		int targetOffset = Integer.parseInt((String) inputList.get(0));
		OBGMRADealVal[] inputValuation = (OBGMRADealVal[]) inputList.get(1);

		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");

		IGMRADealValTrxValue gMRADealValTrxVal = (IGMRADealValTrxValue) map.get("GMRADealValTrxValue");
		IGMRADealVal[] obGMRADealVal = gMRADealValTrxVal.getStagingGMRADealValuation();
		DefaultLogger.debug(this, "Inside doExecute()  before PROXY CALL the busValue 'inputValuation' = "
				+ inputValuation);
		DefaultLogger.debug(this, "Inside doExecute() trxContext  = " + trxContext);
		boolean isEmpty = false;

		try {
			ITradingBookProxy proxy = TradingBookProxyFactory.getProxy();

			DefaultLogger.debug(this, "Inside doExecute() offset  = " + offset + "; length = " + length
					+ "; targetOffset = " + targetOffset + "; inputValuation.length = " + inputValuation.length);
			for (int i = 0; i < inputValuation.length; i++) {
				DefaultLogger.debug(this, "Inside doExecute() offset+1  = " + offset);
				obGMRADealVal[offset + i].setMarketValue(inputValuation[i].getMarketValue());
			}

			for (int j = 0; j < obGMRADealVal.length; j++) {
				Amount marketValue = obGMRADealVal[j].getMarketValue();
				DefaultLogger.debug(this, "Inside doExecute() marketValue.getAmount  = " + marketValue.getAmount()
						+ "; marketValue.getCurrencyCode =" + marketValue.getCurrencyCode());
				if ((marketValue.getAmount() < 0) || marketValue.getCurrencyCode().equals(null)
						|| marketValue.getCurrencyCode().equals("")) {
					isEmpty = true;
				}
			}

			DefaultLogger.debug(this, "Inside doExecute() isEmpty  = " + isEmpty);
			if (isEmpty) {
				resultMap.put("isEmpty", "isEmpty");
				resultMap.put("InitialGMRADealVal", obGMRADealVal);
			}
			else {
				IGMRADealValTrxValue trxValue = proxy.makerUpdateGMRADealValuation(trxContext, gMRADealValTrxVal,
						obGMRADealVal);
				resultMap.put("request.ITrxValue", trxValue);
				resultMap.put("GMRADealValTrxValue", trxValue);
			}

		}
		catch (TradingBookException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		DefaultLogger.debug(this, "Skipping ...");
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
