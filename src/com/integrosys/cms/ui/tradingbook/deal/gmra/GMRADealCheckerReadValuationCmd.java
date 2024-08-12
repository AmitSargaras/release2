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
import com.integrosys.cms.app.tradingbook.bus.TradingBookException;
import com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy;
import com.integrosys.cms.app.tradingbook.proxy.TradingBookProxyFactory;
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

public class GMRADealCheckerReadValuationCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce
																													// the
																													// length
																													// .
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
				{ "GMRADealValTrxValue", "com.integrosys.cms.app.tradingbook.trx.OBGMRADealValTrxValue", SERVICE_SCOPE },
				{ "timefrequencies.map", "java.util.HashMap", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce
																													// the
																													// length
																													// .
				{ "length", "java.lang.Integer", SERVICE_SCOPE }, // To populate
																	// the form.
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

		int length = 10;
		String trxId = (String) map.get("TrxId");
		trxId = trxId.trim();
		DefaultLogger.debug(this, "Inside doExecute()  = " + trxId);

		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");

		try {
			ITradingBookProxy proxy = TradingBookProxyFactory.getProxy();

			IGMRADealValTrxValue gMRADealValTrxVal = proxy.getGMRADealValuationTrxValueByTrxID(trxContext, trxId);
			IGMRADealVal[] obGMRADealVal = gMRADealValTrxVal.getStagingGMRADealValuation();
			resultMap.put("GMRADealValTrxValue", gMRADealValTrxVal);

			String target = (String) map.get("targetOffset");
			if (target != null) {
				int targetOffset = Integer.parseInt(target);
				targetOffset = GMRADealValMapper.adjustOffset(targetOffset, length, obGMRADealVal.length);
				resultMap.put("offset", new Integer(targetOffset));
			}
			else {
				resultMap.put("offset", new Integer(0));
			}

			resultMap.put("length", new Integer(10));
			// set FrequencyUnit List
			resultMap.put("timefrequencies.map", GMRADealValHelper.buildTimeFrequencyMap());

		}
		catch (TradingBookException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			DefaultLogger.error(this, e);
			throw (new CommandProcessingException(e.getMessage()));
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
