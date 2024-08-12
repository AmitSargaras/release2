/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.cashmargin;

import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.tradingbook.bus.OBCashMargin;
import com.integrosys.cms.app.tradingbook.bus.TradingBookException;
import com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy;
import com.integrosys.cms.app.tradingbook.proxy.TradingBookProxyFactory;
import com.integrosys.cms.app.tradingbook.trx.ICashMarginTrxValue;
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

public class CashMarginEditCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "CashMargin", "java.util.Collection", FORM_SCOPE }, // Collection
																		// of
																		// com.
																		// integrosys
																		// .cms.
																		// app.
																		// tradingbook
																		// .bus.
																		// OBCashMargin
				{ "CashMarginTrxValue", "com.integrosys.cms.app.tradingbook.trx.OBCashMarginTrxValue", SERVICE_SCOPE },
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

		return (new String[][] { { "InitialCashMargin", "java.lang.Object", FORM_SCOPE },
				{ "cashMarginNull", "java.lang.String", REQUEST_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		Collection paramsDeal = (Collection) map.get("CashMargin");
		DefaultLogger.debug(this, "Inside doExecute() paramsDeal  = " + paramsDeal);
		OBCashMargin[] obCashMargin = null;
		if (paramsDeal != null) {
			obCashMargin = new OBCashMargin[paramsDeal.size()];
			obCashMargin = (OBCashMargin[]) paramsDeal.toArray(obCashMargin);
		}

		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");

		ICashMarginTrxValue cashMarginTrxVal = (ICashMarginTrxValue) map.get("CashMarginTrxValue");
		DefaultLogger
				.debug(this, "Inside doExecute()  before PROXY CALL the busValue 'obCashMargin' = " + obCashMargin);
		DefaultLogger.debug(this, "Inside doExecute() trxContext  = " + trxContext);

		try {

			if ((obCashMargin == null) || (obCashMargin.length == 0)) {
				resultMap.put("cashMarginNull", "cashMarginNull");
				resultMap.put("InitialCashMargin", cashMarginTrxVal);
			}
			else {

				ITradingBookProxy proxy = TradingBookProxyFactory.getProxy();

				for (int i = 0; i < obCashMargin.length; i++) {

					OBCashMargin obCashMargins = obCashMargin[i];
					DefaultLogger.debug(this, ">>>Debug:::1014 CashMarginTrxVal = "
							+ AccessorUtil.printMethodValue(obCashMargins));

				}

				ICashMarginTrxValue trxValue = proxy.makerUpdateCashMargin(trxContext, cashMarginTrxVal, obCashMargin);
				resultMap.put("request.ITrxValue", trxValue);
				resultMap.put("CashMarginTrxValue", trxValue);
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
