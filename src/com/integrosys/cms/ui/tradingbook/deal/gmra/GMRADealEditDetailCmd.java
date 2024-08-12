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
import com.integrosys.cms.app.tradingbook.bus.OBGMRADeal;
import com.integrosys.cms.app.tradingbook.bus.TradingBookException;
import com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy;
import com.integrosys.cms.app.tradingbook.proxy.TradingBookProxyFactory;
import com.integrosys.cms.app.tradingbook.trx.IGMRADealTrxValue;
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

public class GMRADealEditDetailCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "GMRADeal", "com.integrosys.cms.app.tradingbook.bus.OBGMRADeal", FORM_SCOPE }, // Collection
																									// of
																									// com
																									// .
																									// integrosys
																									// .
																									// cms
																									// .
																									// app
																									// .
																									// interestrate
																									// .
																									// bus
																									// .
																									// OBGMRADeal
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

		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		OBGMRADeal obGMRADeal = (OBGMRADeal) map.get("GMRADeal");
		// OBGMRADeal obGMRADeal = new OBGMRADeal();
		// OBGMRADeal obGMRADeal = (OBGMRADeal)paramsDeal;

		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");

		IGMRADealTrxValue gMRADealTrxVal = (IGMRADealTrxValue) map.get("GMRADealTrxValue");
		DefaultLogger.debug(this, "Inside doExecute()  before PROXY CALL the busValue 'obGMRADeal' = " + obGMRADeal);
		DefaultLogger.debug(this, "Inside doExecute() trxContext  = " + trxContext);

		try {
			ITradingBookProxy proxy = TradingBookProxyFactory.getProxy();

			if (gMRADealTrxVal.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE)) {
				gMRADealTrxVal = proxy.makerCreateGMRADeal(trxContext, gMRADealTrxVal, gMRADealTrxVal.getAgreementID(),
						obGMRADeal);
			}
			else {
				gMRADealTrxVal = proxy.makerUpdateGMRADeal(trxContext, gMRADealTrxVal, obGMRADeal);
			}
			// IGMRADealTrxValue trxValue =
			// proxy.makerUpdateGMRADeal(trxContext, gMRADealTrxVal, obGMRADeal
			// );
			resultMap.put("request.ITrxValue", gMRADealTrxVal);
			resultMap.put("GMRADealTrxValue", gMRADealTrxVal);

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
