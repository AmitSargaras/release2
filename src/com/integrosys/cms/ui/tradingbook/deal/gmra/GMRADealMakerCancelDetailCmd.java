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
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tradingbook.bus.TradingBookException;
import com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy;
import com.integrosys.cms.app.tradingbook.proxy.TradingBookProxyFactory;
import com.integrosys.cms.app.tradingbook.trx.IGMRADealTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Describe this class. Purpose: for Maker to close the transaction Description:
 * command that let the maker to close the transaction that being make
 * previously
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name$
 */

public class GMRADealMakerCancelDetailCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "GMRADealTrxValue", "com.integrosys.cms.app.tradingbook.trx.OBGMRADealTrxValue", SERVICE_SCOPE },
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
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.tradingbook.trx.OBGMRADealTrxValue",
				REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here close transaction for Interest Rate is
	 * done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		IGMRADealTrxValue gMRADealTrxVal = (IGMRADealTrxValue) map.get("GMRADealTrxValue");
		DefaultLogger.debug(this, "Inside doExecute()  = " + trxContext);

		try {

			ITradingBookProxy proxy = TradingBookProxyFactory.getProxy();

			if (gMRADealTrxVal.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE)) {
				gMRADealTrxVal = proxy.makerCloseCreateGMRADeal(trxContext, gMRADealTrxVal);

			}
			else {
				gMRADealTrxVal = proxy.makerCloseUpdateGMRADeal(trxContext, gMRADealTrxVal);
			}

			resultMap.put("request.ITrxValue", gMRADealTrxVal);

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
