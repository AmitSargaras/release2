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
import com.integrosys.cms.app.tradingbook.bus.OBCashMargin;
import com.integrosys.cms.app.tradingbook.trx.OBCashMarginTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: for Maker to get single cash margin record
 * Description: command that get the value from database to let the user to edit
 * the value
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class CashMarginMakerReadSingleCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "CashMargin", "com.integrosys.cms.app.tradingbook.trx.OBCashMarginTrxValue", FORM_SCOPE }, // Collection
																												// of
																												// com
																												// .
																												// integrosys
																												// .
																												// cms
																												// .
																												// app
																												// .
																												// tradingbook
																												// .
																												// bus
																												// .
																												// OBCashMargin
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "CashMarginTrxValue", "com.integrosys.cms.app.tradingbook.trx.OBCashMarginTrxValue", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "index", "java.lang.String", REQUEST_SCOPE } });
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
				{ "InitialSingleCashMargin", "com.integrosys.cms.app.tradingbook.bus.OBCashMargin", FORM_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "timefrequency.labels", "java.util.Collection", REQUEST_SCOPE },
				{ "timefrequency.values", "java.util.Collection", REQUEST_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		OBCashMarginTrxValue obCashMarginTrxValue = (OBCashMarginTrxValue) map.get("CashMargin");
		// System.out.println(
		// "---------------------------------------- obCashMarginTrxValue : " +
		// obCashMarginTrxValue);

		String event = (String) map.get("event");
		DefaultLogger.debug(this, "Inside doExecute()  event= " + event);

		if (!(event.equals("maker_add_cashmargin_confirm_error") || event
				.equals("maker_update_cashmargin_confirm_error"))) {

			OBCashMargin[] parametersArray = null;

			parametersArray = (OBCashMargin[]) obCashMarginTrxValue.getStagingCashMargin();

			String index = (String) map.get("index");
			// System.out.println(
			// "---------------------------------------- parametersArray : " +
			// parametersArray);

			int ind = Integer.parseInt(index);
			OBCashMargin parameter = parametersArray[ind];
			// System.out.println(
			// "---------------------------------------- parameter : " +
			// parameter);

			resultMap.put("CashMarginTrxValue", obCashMarginTrxValue);
			resultMap.put("InitialCashMargin", parametersArray);
			resultMap.put("InitialSingleCashMargin", parameter);
			resultMap.put("index", index);
		}

		resultMap.put("timefrequency.labels", CommonDataSingleton.getCodeCategoryLabels(ICMSUIConstant.TIME_FREQ));
		resultMap.put("timefrequency.values", CommonDataSingleton.getCodeCategoryValues(ICMSUIConstant.TIME_FREQ));

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
