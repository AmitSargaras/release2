/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.cashmargin;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tradingbook.bus.OBCashMargin;
import com.integrosys.cms.app.tradingbook.bus.TradingBookException;
import com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy;
import com.integrosys.cms.app.tradingbook.proxy.TradingBookProxyFactory;
import com.integrosys.cms.app.tradingbook.trx.ICashMarginTrxValue;
import com.integrosys.cms.app.tradingbook.trx.OBCashMarginTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: to prepare the cash margin value to be view or
 * edit Description: command that get the value from database to let the user to
 * view or edit the value
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class CashMarginPrepareCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "CashMarginTrxValue", "com.integrosys.cms.app.tradingbook.trx.OBCashMarginTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "InitialCashMargin", "com.integrosys.cms.app.tradingbook.bus.OBCashMargin", FORM_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE }, { "indexChanged", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "CashMarginTrxValue", "com.integrosys.cms.app.tradingbook.trx.OBCashMarginTrxValue", SERVICE_SCOPE },
				{ "timefrequency.labels", "java.util.Collection", REQUEST_SCOPE },
				{ "timefrequency.values", "java.util.Collection", REQUEST_SCOPE },
				{ "InitialCashMargin", "java.lang.Object", FORM_SCOPE },
				{ "indexChanged", "java.lang.String", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		OBCashMarginTrxValue obCashMarginTrxValue = (OBCashMarginTrxValue) map.get("CashMarginTrxValue");
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		OBCashMargin obCashMargin = (OBCashMargin) map.get("InitialCashMargin");

		String event = (String) map.get("event");
		String index = (String) map.get("index");
		int ind = 0;
		if (index != null) {
			ind = Integer.parseInt(index);
		}
		String indexChanged = (String) map.get("indexChanged");
		int indChanged = 0;
		if ((indexChanged != null) && !indexChanged.equals("")) {
			indChanged = Integer.parseInt(indexChanged);
		}

		DefaultLogger.debug(this, "Inside doExecute()  event= " + event + ", obCashMarginTrxValue = "
				+ obCashMarginTrxValue);
		try {

			if (!(event.equals("maker_create_cashmargin_confirm_error") || event
					.equals("maker_update_cashmargin_confirm_error"))) {
				if ((indChanged > 0) && (obCashMarginTrxValue != null)) {
					System.out.println("obCashMarginTrxValue = " + obCashMarginTrxValue);
					System.out.println("obCashMarginTrxValue.getStagingCashMargin() = "
							+ obCashMarginTrxValue.getStagingCashMargin());
					resultMap.put("CashMarginTrxValue", obCashMarginTrxValue);
					resultMap.put("InitialCashMargin", obCashMarginTrxValue);
				}
				else {
					ITradingBookProxy proxy = TradingBookProxyFactory.getProxy();

					ICashMarginTrxValue cashMarginTrxVal = proxy.getCashMarginTrxValue(trxContext, obCashMargin
							.getAgreementID());

					if (!((cashMarginTrxVal.getStatus().equals(ICMSConstant.STATE_ND)) || (cashMarginTrxVal.getStatus()
							.equals(ICMSConstant.STATE_ACTIVE)))) {
						//System.out.println("************** wip *************")
						// ;
						resultMap.put("wip", "wip");
						resultMap.put("InitialCashMargin", cashMarginTrxVal.getStagingCashMargin());

					}
					else {

						resultMap.put("CashMarginTrxValue", cashMarginTrxVal);

					}

					resultMap.put("InitialCashMargin", cashMarginTrxVal);
				}
			}

			resultMap.put("indexChanged", String.valueOf(indChanged));
			resultMap.put("timefrequency.labels", CommonDataSingleton.getCodeCategoryLabels(ICMSUIConstant.TIME_FREQ));
			resultMap.put("timefrequency.values", CommonDataSingleton.getCodeCategoryValues(ICMSUIConstant.TIME_FREQ));

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
