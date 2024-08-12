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
import com.integrosys.cms.app.tradingbook.trx.OBCashMarginTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: for Maker to add or remove the value to the
 * object Description: command that let the maker to add or remove the value to
 * the object
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name$
 */

public class CashMarginAfterCreateCmd extends AbstractCommand implements ICommonEventConstant {

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
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "indexChanged", "java.lang.String", REQUEST_SCOPE }, });
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
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		OBCashMarginTrxValue obCashMarginTrxValue = (OBCashMarginTrxValue) map.get("CashMarginTrxValue");

		String event = (String) map.get("event");
		String indexChanged = (String) map.get("indexChanged");
		int ind = 0;
		if ((indexChanged != null) && !indexChanged.equals("")) {
			ind = Integer.parseInt(indexChanged);
			ind = ind + 1;
			DefaultLogger.debug(this,"---------------------- ind : " + ind);
		}

		if (!(event.equals("maker_create_cashmargin_confirm_error") || event
				.equals("maker_update_cashmargin_confirm_error"))) {
			OBCashMarginTrxValue newCashMarginTrxValue = (OBCashMarginTrxValue) map.get("CashMargin");
			if (newCashMarginTrxValue != null) {
				DefaultLogger.debug(this,"---------------------- newCashMarginTrxValue : " + newCashMarginTrxValue);
				resultMap.put("CashMarginTrxValue", newCashMarginTrxValue);
				resultMap.put("InitialCashMargin", newCashMarginTrxValue);
				DefaultLogger.debug(this,"---------------------- newCashMarginTrxValue.getStagingCashMargin() : "
						+ newCashMarginTrxValue.getStagingCashMargin());
			}
			else {
				resultMap.put("CashMarginTrxValue", obCashMarginTrxValue);
				resultMap.put("InitialCashMargin", obCashMarginTrxValue);
			}
			resultMap.put("indexChanged", String.valueOf(ind));
		}

		resultMap.put("timefrequency.labels", CommonDataSingleton.getCodeCategoryLabels(ICMSUIConstant.TIME_FREQ));
		resultMap.put("timefrequency.values", CommonDataSingleton.getCodeCategoryValues(ICMSUIConstant.TIME_FREQ));
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
