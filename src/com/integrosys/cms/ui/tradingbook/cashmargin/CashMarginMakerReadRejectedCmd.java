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
import com.integrosys.cms.app.tradingbook.bus.TradingBookException;
import com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy;
import com.integrosys.cms.app.tradingbook.proxy.TradingBookProxyFactory;
import com.integrosys.cms.app.tradingbook.trx.ICashMarginTrxValue;
import com.integrosys.cms.app.tradingbook.trx.OBCashMarginTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: for Maker to reedit the cash margin value after
 * rejected by checker Description: command that get the value that being
 * rejected by checker from database to let the user to reedit the value
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class CashMarginMakerReadRejectedCmd extends AbstractCommand implements ICommonEventConstant {

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
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
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
				{ "CashMarginTrxValue", "com.integrosys.cms.app.tradingbook.trx.OBCashMarginTrxValue", SERVICE_SCOPE },
				{ "timefrequency.labels", "java.util.Collection", REQUEST_SCOPE },
				{ "timefrequency.values", "java.util.Collection", REQUEST_SCOPE },
				{ "timefrequencies.map", "java.util.HashMap", SERVICE_SCOPE },
				{ "InitialCashMargin", "java.lang.Object", FORM_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get the value where checker rejected
	 * from database for Interest Rate is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String trxId = (String) map.get("TrxId");
		trxId = trxId.trim();
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		OBCashMarginTrxValue obCashMarginTrxValue = (OBCashMarginTrxValue) map.get("CashMarginTrxValue");

		try {
			System.out.println("obCashMarginTrxValue = " + obCashMarginTrxValue);
			if (obCashMarginTrxValue != null) {
				resultMap.put("CashMarginTrxValue", obCashMarginTrxValue);
				resultMap.put("InitialCashMargin", obCashMarginTrxValue);
			}
			else {
				ITradingBookProxy proxy = TradingBookProxyFactory.getProxy();
				// Get Trx By TrxID
				ICashMarginTrxValue cashMarginTrxVal = proxy.getCashMarginTrxValueByTrxID(trxContext, trxId);

				// if current status is other than ACTIVE & REJECTED, then show
				// workInProgress.
				// i.e. allow edit only if status is either ACTIVE or REJECTED
				if ((!cashMarginTrxVal.getStatus().equals(ICMSConstant.STATE_ACTIVE))
						&& (!cashMarginTrxVal.getStatus().equals(ICMSConstant.STATE_REJECTED))

				) {
					resultMap.put("wip", "wip");
					resultMap.put("InitialCashMargin", cashMarginTrxVal);
				}
				else {
					resultMap.put("CashMarginTrxValue", cashMarginTrxVal);
				}

				resultMap.put("InitialCashMargin", cashMarginTrxVal);
			}
			resultMap.put("timefrequency.labels", CommonDataSingleton
					.getCodeCategoryLabels(CashMarginHelper.TIME_FREQUENCY_CODE));
			resultMap.put("timefrequency.values", CommonDataSingleton
					.getCodeCategoryValues(CashMarginHelper.TIME_FREQUENCY_CODE));

			// set FrequencyUnit List
			resultMap.put("timefrequencies.map", CashMarginHelper.buildTimeFrequencyMap());

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
